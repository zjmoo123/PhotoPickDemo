package com.example.zjm.photopickdemo.vedioplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.base.BaseActivity;
import com.orhanobut.logger.Logger;

public class VideoActivity extends BaseActivity implements View.OnClickListener {
    private Button mVideoIntentBtn;
    private VideoView mVideoView;
    private Uri uri;
    private Button mMediaPlayerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoIntentBtn = (Button) findViewById(R.id.video_btn);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mMediaPlayerBtn = (Button) findViewById(R.id.mediaplayer);
        mVideoIntentBtn.setOnClickListener(this);
        mMediaPlayerBtn.setOnClickListener(this);
        String url = "http://9890.vod.myqcloud.com/9890_9c1fa3e2aea011e59fc841df10c92278.f20.mp4";
        uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/20161125_113545.mp4");
//        mVideoView.setMediaController(new MediaController(this));
//        mVideoView.setVideoURI(uri);
//        mVideoView.start();
//        mVideoView.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_btn:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Logger.v("URI:::::::::" + uri.toString());
                intent.setDataAndType(uri, "video/mp4");
                startActivity(intent);
                break;
            case R.id.mediaplayer:
                startActivity(new Intent(this, IJKPlayerActivity.class));
        }
    }
}
