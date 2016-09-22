package com.example.zjm.photopickdemo.contentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.example.zjm.photopickdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


/**
 * Created by zjm on 16-9-8.
 */
public class LocalContactsHelper {
    private static LocalContactsHelper mLocalContactsHelper;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator mPinyinComparator;

    private String mChReg = "[\\u4E00-\\u9FA5]+";//中文字符串匹配
    /**
     * 本地导入监听
     */
    private List<OnLoadLocalContectsListener> mListenerList = new ArrayList<>();
    private List<ContactBean> mAllContactsList;

    private LocalContactsHelper() {
        mPinyinComparator = new PinyinComparator();
        mCharacterParser = CharacterParser.getInstance();
    }

    public static LocalContactsHelper getInstance() {
        if (mLocalContactsHelper == null) {
            mLocalContactsHelper = new LocalContactsHelper();

        }
        return mLocalContactsHelper;
    }

    /**
     * 返回数据
     *
     * @param context
     * @return
     */
    public void loadLocalAllContactsList(Context context, OnLoadLocalContectsListener onLoadLocalContectsListener) {
        addOnLoadLocalContectsListener(onLoadLocalContectsListener);
        if (null != mAllContactsList && mAllContactsList.size() > 0) {
            setLisener(mAllContactsList, "导入完成");
        } else {
            loadLocalContacts(context);
        }
    }

    /**
     * 导入本地通讯录
     */
    public void loadLocalContacts(final Context context) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    mAllContactsList = new ArrayList<ContactBean>();
                    ContentResolver resolver = context.getContentResolver();
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key"}, null, null, "sort_key COLLATE LOCALIZED ASC");
                    if (phoneCursor == null || phoneCursor.getCount() == 0) {
                        //ToastUtils.showShort("未获得读取联系人权限 或 未获得联系人数据");
                        return;
                    }
                    String NativePhoneNumber = "xxx";
                    Log.e("TAG", "benjihaoma  " + NativePhoneNumber);
                    ContactBean mNativePhone = new ContactBean("本机号码", NativePhoneNumber, "# BEN 本 JI 机 HAO 号 MA 码");
                    mNativePhone.userNameSortLetters = "#";
                    mNativePhone.userNameSimpleSpell = "#BJHM";
                    mNativePhone.userNameWholeSpell = "#BENJIHAOMA";
                    int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");

                    if (phoneCursor.getCount() > 0) {
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                            String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
                            ContactBean sortModel = new ContactBean(contactName, phoneNumber, sortKey);
                            String sortLetters = getSortLetter(contactName);
                            sortModel.userNameSortLetters = sortLetters;
                            SortTokenBean stoken = parseSortKey(sortKey);
                            sortModel.userNameSimpleSpell = stoken.simpleSpell;
                            sortModel.userNameWholeSpell = stoken.wholeSpell;
                            Log.e("TAG", contactName + "   " + sortKey + "     " + sortModel.userNameSortLetters + "   "
                                    + sortModel.userNameSimpleSpell + "      " + sortModel.userNameWholeSpell);
                            mAllContactsList.add(sortModel);
                        }
                    }
                    phoneCursor.close();
                    if (mAllContactsList != null) {
                        Collections.sort(mAllContactsList, mPinyinComparator);// 根据a-z进行排序源数据
                    }
                    List<ContactBean> mContactcList = new ArrayList<ContactBean>();
                    mContactcList.add(mNativePhone);
                    if (mAllContactsList != null) {
                        for (int i = 0; i < mAllContactsList.size(); i++) {
                            mContactcList.add(mAllContactsList.get(i));
                        }
                    }
                    setLisener(mContactcList, "导入完成");
                } catch (Exception e) {
                    Log.e("LocalContactsHelper-e", e.getLocalizedMessage());
                    setLisener(null, e.toString());
                }
            }
        });

    }

    /**
     * 名字转拼音,取首字母
     *
     * @param name
     * @return
     */
    public String getSortLetter(String name) {
        String letter = "#";
        if (name == null) {
            return letter;
        }
        //汉字转换成拼音
        String pinyin = mCharacterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    /**
     * 解析sort_key,封装简拼,全拼
     *
     * @param sortKey
     * @return
     */
    public SortTokenBean parseSortKey(String sortKey) {
        SortTokenBean token = new SortTokenBean();
        if (sortKey != null && sortKey.length() > 0) {
            //其中包含的中文字符
            String[] enStrs = sortKey.replace(" ", "").split(mChReg);
            for (int i = 0, length = enStrs.length; i < length; i++) {
                if (enStrs[i].length() > 0) {
                    //拼接简拼
                    token.simpleSpell += enStrs[i].charAt(0);
                    token.wholeSpell += enStrs[i];
                }
            }
        }
        return token;
    }

    /**
     * 模糊查询
     *
     * @param str
     * @return
     */
    public List<ContactBean> search(String str, List<ContactBean> mAllContactsList) {
        List<ContactBean> filterList = new ArrayList<ContactBean>();// 过滤后的list
        //if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
        if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
            String simpleStr = str.replaceAll("\\-|\\s", "");
            for (ContactBean contact : mAllContactsList) {
                if (contact.phone != null && contact.name != null) {
                    if (contact.userSimplePhoneNumber.contains(simpleStr) || contact.name.contains(str)) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        } else {
            for (ContactBean contact : mAllContactsList) {
                if (contact.phone != null && contact.name != null) {
                    //姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
                    if (contact.name.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
                            || contact.userNameSortKey.toLowerCase(Locale.CHINESE).replace(" ", "").contains(str.toLowerCase(Locale.CHINESE))
                            || contact.userNameSimpleSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
                            || contact.userNameWholeSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        }
        return filterList;
    }

    /**
     * 设置监听
     *
     * @param onLoadLocalContectsListener
     */
    public void addOnLoadLocalContectsListener(OnLoadLocalContectsListener onLoadLocalContectsListener) {
        if (null != onLoadLocalContectsListener) {
            mListenerList.add(onLoadLocalContectsListener);
        }
    }

    /**
     * 发送监听
     *
     * @param mAllContactsList
     * @param str
     */
    private void setLisener(List<ContactBean> mAllContactsList, String str) {
        for (OnLoadLocalContectsListener onLoadLocalContectsListener : mListenerList) {
            onLoadLocalContectsListener.onLoad(mAllContactsList, str);
        }

    }

    public interface OnLoadLocalContectsListener {
        void onLoad(List<ContactBean> mAllContactsList, String str);
    }

    /**
     * 根据文字获取图片
     *
     * @param text
     * @return
     */
    public static Bitmap getIndustry(Context context, String text) {
        String color = "#ffeeeade";//图片背景颜色
        Bitmap src = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);
        int x = src.getWidth();
        int y = src.getHeight();
        Bitmap bmp = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(bmp);
        canvasTemp.drawColor(Color.parseColor(color));
        Paint p = new Paint(Paint.FAKE_BOLD_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#ff4e0a13"));//字体颜色
        p.setAlpha(45);
        p.setFilterBitmap(true);
        int size = (int) (18 * context.getResources().getDisplayMetrics().density);
        p.setTextSize(size);
        float tX = (x - getFontlength(p, text)) / 2;
        float tY = (y - getFontHeight(p)) / 2 + getFontLeading(p);
        canvasTemp.drawText(text, tX, tY, p);
        return toRoundCorner(bmp);
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    /**
     * 获取圆角图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
