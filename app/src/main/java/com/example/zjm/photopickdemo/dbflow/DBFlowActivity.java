package com.example.zjm.photopickdemo.dbflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zjm.photopickdemo.R;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

public class DBFlowActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG=DBFlowActivity.class.getSimpleName();
    private Button add;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dbflow);
        add=(Button)findViewById(R.id.add);
        search=(Button)findViewById(R.id.search);
        add.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                List<People> peoples = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    People people = new People();
                    people.name = "zjm";
                    people.gender = 2;
                    people.email="xx";
                    peoples.add(people);
                }
//实时保存，马上保存
                new SaveModelTransaction<>(ProcessModelInfo.withModels(peoples)).onExecute();
                break;
            case R.id.search:
                //查询gender = 1的所有People
                List<People> peoples2 = new Select().from(People.class).where(People_Table.email.eq("xx")).queryList();
                for (int i=0;i<=10;i++){
                    Log.e(TAG,peoples2.get(i).email);
                }
        }
    }
}
