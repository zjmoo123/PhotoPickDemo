package com.example.zjm.photopickdemo.contentprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG = ContentProviderActivity.class.getSimpleName();
    private ListView mContactsListView;
    private List<PeopleContact> mContactsList = new ArrayList<>();
    private ContactsAdapter mContactsAdapter;
    private Button mInsertPeople;
    private EditText mName, mPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        mContactsListView = (ListView) findViewById(R.id.people);
        mInsertPeople = (Button) findViewById(R.id.insert_people);
        mName = (EditText) findViewById(R.id.name_edit);
        mPhone = (EditText) findViewById(R.id.phone_edit);
        mInsertPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPeople();
            }
        });
//        testReadAllContacts();
//        getContactsList();
        searchUser();
        mContactsAdapter = new ContactsAdapter(mContactsList, R.layout.contacts_item, this);
        mContactsListView.setAdapter(mContactsAdapter);
    }

    public void testReadAllContacts() {
        Cursor cursor = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        if (cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);
            Log.e(TAG, contactId);
            Log.e(TAG, name);

            /*
             * 查找该联系人的phone信息
             */
            Cursor phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if (phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phoneIndex);
                Log.e(TAG, phoneNumber);
            }


        }
    }

    private void getContactsList() {

        String columns[] = new String[]{Contacts.People.NAME, Contacts.People.NUMBER};
        Uri mContacts = Contacts.People.CONTENT_URI;
        Cursor cur = managedQuery(
                mContacts,
                columns,  // 要返回的数据字段
                null,// WHERE子句
                null,// WHERE 子句的参数
                null// Order-by子句
        );
        if (cur.moveToFirst()) {
            String name = null;
            String phoneNo = null;
            do {
                // 获取字段的值
                name = cur.getString(cur.getColumnIndex(Contacts.People.NAME));
                phoneNo = cur.getString(cur.getColumnIndex(Contacts.People.NUMBER));
                mContactsList.add(new PeopleContact(name, phoneNo));
               // Toast.makeText(this, name + " " + phoneNo, Toast.LENGTH_LONG).show();
                Log.i(TAG,name + " " + phoneNo);
            } while (cur.moveToNext());
        }
    }

    /**
     * 查询联系人
     */
    private void searchUser() {
        //定义两个list来放数据

        //获取Cursor
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            //拿到name 和电话号码
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String phonename = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //如果是一个正常的手机号码，则添加到列表中
            if (isMobileNO(phoneNumber)) {//如果是电话，则添加到list中。
                PeopleContact peopleContact = new PeopleContact();
                peopleContact.name = phonename;
                peopleContact.phoneNo = phoneNumber;
                mContactsList.add(peopleContact);
            }
        }
        phones.close();
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）、177
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 增加联系人
     */
    public void insertPeople() {
        String name = mName.getText().toString();
        String phone = mPhone.getText().toString();
        if ((!name.isEmpty()) && isMobileNO(phone)) {
            //创建一个空的contentvalues
            ContentValues values = new ContentValues();
            //向rawContentUri 插入一个空值，获取返回的rawContactId
            Uri rawContentUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContentUri);
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            //设置内容类型
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
            //向联系人中插入name
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

            values.clear();

            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            //设置内容类型
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
            PeopleContact peopleContact = new PeopleContact();
            peopleContact.name = name;
            peopleContact.phoneNo = phone;
            mContactsList.add(peopleContact);
            mContactsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请输入正确的手机号和联系人", Toast.LENGTH_SHORT).show();
        }


    }

}
