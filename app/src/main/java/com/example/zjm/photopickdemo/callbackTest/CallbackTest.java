package com.example.zjm.photopickdemo.callbackTest;

import android.util.Log;

/**
 * Created by zjm on 16-9-5.
 */
public class CallbackTest {
    private Callback callback;

    public void setCallback(Callback callback){
        this.callback=callback;
    }

    public void test(){
        Log.e("TAG","SUCCESS");
        callback.solve("success!");
    }

}
