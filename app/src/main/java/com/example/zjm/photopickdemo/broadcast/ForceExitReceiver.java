package com.example.zjm.photopickdemo.broadcast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.example.zjm.photopickdemo.LoginActivity;
import com.example.zjm.photopickdemo.base.ActivityController;

/**
 * Created by zjm on 16-9-21.
 */
public class ForceExitReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        final Activity activity = ActivityController.getCurrentActivity();
        Log.d("activity", activity + "");
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Warning")
                .setMessage("您已经被强制退出登录，请重新登录")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityController.finishAll();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);  //重新启动LoginActivity
                    }
                });
        dialog.create().show();
    }
}
