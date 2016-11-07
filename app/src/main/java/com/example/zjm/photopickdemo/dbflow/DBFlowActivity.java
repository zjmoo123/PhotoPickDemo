package com.example.zjm.photopickdemo.dbflow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.base.ActivityController;
import com.example.zjm.photopickdemo.callbackTest.Callback;
import com.example.zjm.photopickdemo.callbackTest.Callback2;
import com.example.zjm.photopickdemo.callbackTest.CallbackTest;
import com.example.zjm.photopickdemo.callbackTest.Li;
import com.example.zjm.photopickdemo.callbackTest.Wang;
import com.example.zjm.photopickdemo.view.HiveDrawable;
import com.example.zjm.photopickdemo.view.RoundImageActivity;
import com.example.zjm.photopickdemo.view.ShowActivity;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBFlowActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DBFlowActivity.class.getSimpleName();
    private Button add;
    private Button search;
    private Button callback_bt;
    private Button toView;
    private Button mForceExit;
    private ImageView mHiveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbflow);
      //  FlowManager.init(this);
        ActivityController.addActivity(this);
        ActivityController.setCurrentActivity(this);
        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.search);
        callback_bt = (Button) findViewById(R.id.callback);
        toView = (Button) findViewById(R.id.toView);
        mForceExit = (Button) findViewById(R.id.force_exit);
        mHiveImage=(ImageView)findViewById(R.id.hive_image);
        mHiveImage.setImageDrawable(new HiveDrawable(ContextCompat.getDrawable(this,R.mipmap.bg)));
        add.setOnClickListener(this);
        search.setOnClickListener(this);
        callback_bt.setOnClickListener(this);
        toView.setOnClickListener(this);
        mForceExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                List<People> peoples = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    People people = new People();
                    people.name = "zjm";
                    people.gender = 2;
                    people.email = "xx";
                    peoples.add(people);
                }
//实时保存，马上保存
                new SaveModelTransaction<>(ProcessModelInfo.withModels(peoples)).onExecute();
                break;
            case R.id.search:
                //查询gender = 1的所有People
                List<People> peoples2 = new Select().from(People.class).where(People_Table.email.eq("xx")).queryList();
                for (int i = 0; i <= 10; i++) {
                    Log.e(TAG, peoples2.get(i).email);
                }
                break;
            case R.id.callback:
                Li li = new Li();
                Wang wang = new Wang(li);
                wang.askQuestion("1+1等于几");
                CallbackTest callbackTest = new CallbackTest();
                callbackTest.setCallback(wang.getCallback());
                callbackTest.test();
                callbackTest.setCallback(new Callback() {
                    @Override
                    public void solve(String result) {
                        Log.e("TAG", "直接实现");
                    }
                });
                callbackTest.test();
                String patchPath  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk";
                File file = new File(patchPath);
                if (file.exists()) {
                    Log.v(TAG,"补丁文件存在");
                    TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), patchPath);
                } else {
                    Log.v(TAG,"补丁文件不存在");
                }
                break;
            case R.id.toView:
                startActivity(new Intent(DBFlowActivity.this, RoundImageActivity.class));
                finish();
                break;
            case R.id.force_exit://发送广播 
                Intent intent = new Intent("com.example.broadcast.FORCE_EXIT");
                sendBroadcast(intent);
                break;
            default:
                break;
        }
    }

}
