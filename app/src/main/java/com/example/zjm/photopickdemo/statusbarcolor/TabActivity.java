package com.example.zjm.photopickdemo.statusbarcolor;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.base.BaseActivity;

public class TabActivity extends BaseActivity implements View.OnClickListener {
    FragmentOne fragmentOne;
    FragmentTwo fragmentTwo;
    TextView tabOne;
    TextView tabTwo;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        initView();
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container,fragmentOne)
                .add(R.id.container,fragmentTwo)
                .hide(fragmentTwo)
                .commit();
    }

    private void initView() {
        tabOne = (TextView) findViewById(R.id.tab_one);
        tabTwo = (TextView) findViewById(R.id.tab_two);
        tabOne.setOnClickListener(this);
        tabTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_one:
                fragmentManager.beginTransaction().show(fragmentOne).hide(fragmentTwo).commit();
                break;
            case R.id.tab_two:
                fragmentManager.beginTransaction().show(fragmentTwo).hide(fragmentOne).commit();
                break;
        }
    }
}
