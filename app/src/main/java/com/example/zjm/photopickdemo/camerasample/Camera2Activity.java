package com.example.zjm.photopickdemo.camerasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zjm.photopickdemo.R;

public class Camera2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2Fragment.newInstance())
                    .commit();
        }
    }
}
