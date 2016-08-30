package com.example.zjm.photopickdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Switch;

public class SwitchActivity extends AppCompatActivity {
    private Button mSwitch;
    private boolean istrue=true;
    private Animation mGroupExitAnim;
    private Switch startAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        mSwitch=(Button)findViewById(R.id.button1);
        startAnimation=(Switch)findViewById(R.id.switch1);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (istrue){
                    mSwitch.setBackground(getResources().getDrawable(R.drawable.close));
                    istrue=false;
                }else {
                    mSwitch.setBackground(getResources().getDrawable(R.drawable.open));
                    istrue=true;
                }

            }
        });

    }
}
