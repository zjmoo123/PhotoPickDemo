package com.example.zjm.photopickdemo.dbflow;

import android.os.Build;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by zjm on 16-9-3.
 */
@Database(name = AppDatabase.NAME,version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME="AppDatabase";
    public static final int VERSION=2;
}
