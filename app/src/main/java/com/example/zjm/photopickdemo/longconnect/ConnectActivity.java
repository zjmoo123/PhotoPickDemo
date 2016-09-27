package com.example.zjm.photopickdemo.longconnect;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;

import com.example.zjm.photopickdemo.R;

import java.net.InetAddress;
import java.net.Socket;

public class ConnectActivity extends AppCompatActivity {
    private static final String TAG=ConnectActivity.class.getSimpleName();
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }
    public void connect(){

    }
}
