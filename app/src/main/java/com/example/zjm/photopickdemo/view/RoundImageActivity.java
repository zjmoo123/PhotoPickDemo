package com.example.zjm.photopickdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.zjm.photopickdemo.R;

public class RoundImageActivity extends AppCompatActivity {
    private RoundImageView roundImageView1;
    private RoundImageView roundImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image);
        roundImageView1 = (RoundImageView) findViewById(R.id.round1);
        roundImageView2 = (RoundImageView) findViewById(R.id.round2);
        roundImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundImageView1.setType(RoundImageView.TYPE_ROUND);
            }
        });

        roundImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "ROUNDIMAGE2");
                roundImageView2.setType(RoundImageView.TYPE_ROUND);
                roundImageView2.setBorderRadius(60);

            }
        });
    }
}
