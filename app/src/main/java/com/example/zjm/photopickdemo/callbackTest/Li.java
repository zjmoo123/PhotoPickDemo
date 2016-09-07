package com.example.zjm.photopickdemo.callbackTest;

import android.util.Log;

/**
 * Created by zjm on 16-9-5.
 */
public class Li {
    public void excuteMessage(Callback callback,String question){
        Log.e("TAGL","小王问的问题是： "+question);
        for (int i=0;i<100;i++){
            Log.e("TAGL","容我想想");
        }
        String result="答案是2";
        callback.solve(result);
    }

}
