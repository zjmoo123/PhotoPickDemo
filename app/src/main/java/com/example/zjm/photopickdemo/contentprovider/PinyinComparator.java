package com.example.zjm.photopickdemo.contentprovider;

import android.util.Log;

import java.util.Comparator;



/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<ContactBean> {

    public int compare(ContactBean o1, ContactBean o2) {
        Log.i("PinyinComparator", "o1.sortLetters:" + o1.userNameSortLetters + "--o2.sortLetters:" + o2.userNameSortLetters);
        if (o1.userNameSortLetters.equals("@") || o2.userNameSortLetters.equals("#")) {
            return 1;
        } else if (o1.userNameSortLetters.equals("#") || o2.userNameSortLetters.equals("@")) {
            return -1;
        } else {
            return o1.userNameSortLetters.compareTo(o2.userNameSortLetters);
        }
    }

}
