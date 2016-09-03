package com.example.zjm.photopickdemo;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by zjm on 16-9-3.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
