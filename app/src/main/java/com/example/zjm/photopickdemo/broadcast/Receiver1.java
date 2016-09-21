package com.example.zjm.photopickdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zjm on 16-9-21.
 */
public class Receiver1 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean orderedBroadcast = isOrderedBroadcast();
        Log.i("TAG",String.valueOf(orderedBroadcast));
        if(orderedBroadcast){
            System.out.println("第1个广播接收者是有序广播");
            String resultData = getResultData();//获取有序广播的数据
            System.out.println("第1个接收者接收到的有序广播数据resultData:" + resultData);
            setResultData("党中央给发了8000块");//修改有序广播的数据
        }else{
            System.out.println("第1个广播接收者是无序广播");
            String value = intent.getStringExtra("info");
            System.out.println(value);
        }

//        abortBroadcast();
    }
}
