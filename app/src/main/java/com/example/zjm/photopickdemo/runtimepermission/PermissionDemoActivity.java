package com.example.zjm.photopickdemo.runtimepermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.base.BaseActivity;

public class PermissionDemoActivity extends BaseActivity {
    private static final int PERMISSIONS_REQUEST_SEND_SMS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_demo);
        int permissionCHeck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCHeck== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"可以发送短信",Toast.LENGTH_SHORT).show();
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"申请权限成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"shibai",Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    /**
     * 判断用户是否勾选了不在询问选项
     * @param activity
     * @param permission
     */
    private void requestPermission(Activity activity, final String permission) {
//        boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
//        if (getLastRequestState() && !flag) {
//            //当用户勾选`不再询问`时, 进入设置界面
//            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
//            startActivityForResult(intent, COME_CODE);
//        } else if (flag) {
//            //之前有过`拒绝`授权时，提醒用户需要某权限
//            showRationaleDialog();
//
//            //同时保存返回值
//            SharedPrefsUtils.setBooleanPreference(getApplicationContext(), KEY_RESUEST_SOME_PERMISSION, flag);
//        } else {
//            //第一次申请权限时，直接申请权限
//            ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_PERMISSION_CODE);
//        }
    }
    //或者权限被拒绝后在调用shouldShowRequestPermissionRationale函数，如果再返回false则用户勾选了不在询问

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }
}
