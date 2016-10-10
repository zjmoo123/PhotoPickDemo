package com.example.zjm.photopickdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.zjm.photopickdemo.R;

public class BezierActivity extends AppCompatActivity {
    private Button p1;
    private BezierView bezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        p1=(Button)findViewById(R.id.p1);
        bezierView=(BezierView)findViewById(R.id.circle_xin);
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bezierView.startAnimation();
            }
        });
    }


}
