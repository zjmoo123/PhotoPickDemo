package com.example.zjm.photopickdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zjm on 16-9-21.
 */
public class Receiver2 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();//获取有序广播的数据
        System.out.println("第2个接收者接收到的有序广播数据resultData:" + resultData);
        setResultData("党中央给发了5000块");//修改有序广播的数据
    }
}
