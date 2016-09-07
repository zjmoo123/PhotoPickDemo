package com.example.zjm.photopickdemo.callbackTest;

import android.util.Log;

/**
 * Created by zjm on 16-9-5.
 */
public class Wang implements Callback{
    private Li li;
    public Wang (Li li){
        this.li=li;
    }

    public void askQuestion(final String question){
        Log.e("ATGW","开始问问题");
        new Thread(new Runnable() {
            @Override
            public void run() {

                li.excuteMessage(Wang.this,question);
            }
        }).start();
        Log.e("TAGW","我出去完了");
    }

    @Override
    public void solve(String result) {
        Log.e("TAGW","答案是: "+result);
    }

    public Callback getCallback(){
        return Wang.this;
    }
}
