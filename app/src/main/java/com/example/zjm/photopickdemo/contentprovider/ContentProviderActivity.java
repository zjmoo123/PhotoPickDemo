package com.example.zjm.photopickdemo.contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG=ContentProviderActivity.class.getSimpleName();
    private ListView mContactsListView;
    private List<PeopleContact> mContactsList=new ArrayList<>();
    private ContactsAdapter mContactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        mContactsListView=(ListView)findViewById(R.id.people);
        testReadAllContacts();
        getContactsList();

        mContactsAdapter=new ContactsAdapter(mContactsList,R.layout.contacts_item,this);
        mContactsListView.setAdapter(mContactsAdapter);
    }
    public void testReadAllContacts() {
        Cursor cursor = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        if(cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while(cursor.moveToNext()) {
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
            if(phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while(phones.moveToNext()) {
                String phoneNumber = phones.getString(phoneIndex);
                Log.e(TAG, phoneNumber);
            }



        }
    }
    private void getContactsList() {

        String columns[] = new String[] { Contacts.People.NAME, Contacts.People.NUMBER };
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
                mContactsList.add(new PeopleContact(name,phoneNo));
                Toast.makeText(this, name +" "+ phoneNo, Toast.LENGTH_LONG).show();
            } while (cur.moveToNext());
        }
    }

}
