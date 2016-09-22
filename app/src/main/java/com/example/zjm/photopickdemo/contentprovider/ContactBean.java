package com.example.zjm.photopickdemo.contentprovider;

import java.io.Serializable;

/**
 * Created by yangyoutao on 2016/9/6.
 * 联系人信息 bean
 */
public class ContactBean implements Serializable {
    public String name;
    public String phone;
    public String userNameSortKey = "";
    public String userSimplePhoneNumber = "";//
    public String userNameSortLetters; //显示数据拼音的首字母
    public String userNameSimpleSpell = "";//简拼
    public String userNameWholeSpell = "";//全拼
    public Boolean isRegister = false;

    public ContactBean() {

    }

    public ContactBean(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public ContactBean(String name, String number, String sortKey) {
        this.name = name;
        this.userNameSortKey = sortKey;
        if (number != null) {
            this.userSimplePhoneNumber = number.replaceAll("\\-|\\s", "");
            this.userSimplePhoneNumber = this.userSimplePhoneNumber.replaceAll("\\+86", "");
        }
        this.phone = this.userSimplePhoneNumber;
    }

}
