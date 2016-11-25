package com.example.zjm.photopickdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


import com.example.zjm.photopickdemo.dbflow.DBFlowActivity;
import com.example.zjm.photopickdemo.jniTest.JniUtils;
import com.example.zjm.photopickdemo.sensor.SensorActivity;
import com.example.zjm.photopickdemo.socket.SocketActivity;
import com.example.zjm.photopickdemo.statusbarcolor.StatusBarColorActivity;
import com.example.zjm.photopickdemo.tipview.TipViewActivity;

import com.example.zjm.photopickdemo.vedioplayer.VideoActivity;
import com.example.zjm.photopickdemo.view.BezierActivity;
import com.example.zjm.photopickdemo.wechatTab.TabSelectorLayoutDemo;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mPhotoPickBtn;
    private Button mSwitchBtn;
    private Button mNotificationBtn;
    private Button mLoginBtn;
    private Button mGridViewBtn;
    private Button mWeiChatTabBtn;
    private Button mTipViewBtn;
    private Button mDiyViewBtn;
    private Button mStatusBarBtn;
    private Button mSocketBtn;
    private Button mSensorBtn;
    private Button mDBFlowBtn;
    private Button mVideoBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        Logger.d(new JniUtils().getStringFromC());
    }

    private void setListener() {
        mPhotoPickBtn.setOnClickListener(this);
        mSwitchBtn.setOnClickListener(this);
        mGridViewBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mNotificationBtn.setOnClickListener(this);
        mWeiChatTabBtn.setOnClickListener(this);
        mDiyViewBtn.setOnClickListener(this);
        mTipViewBtn.setOnClickListener(this);
        mStatusBarBtn.setOnClickListener(this);
        mSocketBtn.setOnClickListener(this);
        mSensorBtn.setOnClickListener(this);
        mDBFlowBtn.setOnClickListener(this);
        mVideoBtn.setOnClickListener(this);
    }

    private void initView() {
        mPhotoPickBtn = (Button) findViewById(R.id.photo_pick);
        mSwitchBtn = (Button) findViewById(R.id.switch_button);
        mNotificationBtn = (Button) findViewById(R.id.notification_button);
        mGridViewBtn = (Button) findViewById(R.id.gridview_button);
        mLoginBtn = (Button) findViewById(R.id.login_button);
        mWeiChatTabBtn = (Button) findViewById(R.id.weichat_tab);
        mTipViewBtn = (Button) findViewById(R.id.tipview_button);
        mDiyViewBtn = (Button) findViewById(R.id.diy_view);
        mStatusBarBtn = (Button) findViewById(R.id.status_bar_button);
        mSocketBtn = (Button) findViewById(R.id.socket_button);
        mSensorBtn = (Button) findViewById(R.id.sensor_button);
        mDBFlowBtn = (Button) findViewById(R.id.dbflow_button);
        mVideoBtn = (Button) findViewById(R.id.video_activity_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_pick:
                startActivity(new Intent(this, PhotoPickActivity.class));
                break;
            case R.id.switch_button:
                startActivity(new Intent(this, SwitchActivity.class));
                break;
            case R.id.login_button:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.notification_button:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.gridview_button:
                startActivity(new Intent(this, GridViewActivity.class));
                break;
            case R.id.tipview_button:
                startActivity(new Intent(this, TipViewActivity.class));
                break;
            case R.id.weichat_tab:
                startActivity(new Intent(this, TabSelectorLayoutDemo.class));
                break;
            case R.id.diy_view:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.status_bar_button:
                startActivity(new Intent(this, StatusBarColorActivity.class));
                break;
            case R.id.socket_button:
                startActivity(new Intent(this, SocketActivity.class));
                break;
            case R.id.sensor_button:
                startActivity(new Intent(this, SensorActivity.class));
                break;
            case R.id.dbflow_button:
                startActivity(new Intent(this, DBFlowActivity.class));
                break;
            case R.id.video_activity_button:
                startActivity(new Intent(this, VideoActivity.class));
                break;
        }
    }
}
