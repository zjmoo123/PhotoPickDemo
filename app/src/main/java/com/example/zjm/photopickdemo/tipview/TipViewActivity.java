package com.example.zjm.photopickdemo.tipview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;

import java.util.ArrayList;

public class TipViewActivity extends AppCompatActivity {

    private Button button0,button1,button2,button3,button4;
    private RelativeLayout rl;
    private AutoCompleteTextView autoCompleteTextView;
    private String[] res = {"beijing1", "beijing2", "beijing3", "shanghai1", "shanghai2", "guangzhou1", "shenzhen"};

    private AutoCompleteAdapter adapter;
    private ArrayList<String> mOriginalValues=new ArrayList<String>();

    





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_view);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.auto_textview);
        rl = (RelativeLayout) findViewById(R.id.activity_main);





        button0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = 0;int y = 0;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    x= (int) event.getX();y = (int) event.getY();
                    Log.i("zz",x+"  "+y);
                    TipView share = new TipView.Builder(TipViewActivity.this,rl,x+v.getLeft(),y+v.getTop())
                            .addItem(new TipItem("复制复制"))
                            .addItem(new TipItem("粘贴"))
                            .addItem(new TipItem("删除"))
                            .addItem(new TipItem("收藏"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("更多"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str,int a) {
                                    Toast.makeText(TipViewActivity.this,str,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                return true;
            }
        });

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = 0;int y = 0;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    x= (int) event.getX();y = (int) event.getY();
                    Log.i("zz",x+"  "+y);
                    TipView share = new TipView.Builder(TipViewActivity.this,rl,x+v.getLeft(),y+v.getTop())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("粘贴"))
                            .addItem(new TipItem("删除"))
                            .addItem(new TipItem("收藏"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("更多更多"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str,int a) {
                                    Toast.makeText(TipViewActivity.this,str,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                return true;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = 0;int y = 0;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    x= (int) event.getX();y = (int) event.getY();
                    Log.i("zz",x+"  "+y);
                    TipView share = new TipView.Builder(TipViewActivity.this,rl,x+v.getLeft(),y+v.getTop())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("粘贴"))
                            .addItem(new TipItem("删除删除"))
                            .addItem(new TipItem("收藏"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("更多"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str,int a) {
                                    Toast.makeText(TipViewActivity.this,str,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                return true;
            }
        });

        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = 0;int y = 0;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    x= (int) event.getX();y = (int) event.getY();
                    Log.i("zz",x+"  "+y);
                    TipView share = new TipView.Builder(TipViewActivity.this,rl,x+v.getLeft(),y+v.getTop())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("粘贴"))
                            .addItem(new TipItem("删除"))
                            .addItem(new TipItem("收藏转发"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("更多"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str,int a) {
                                    Toast.makeText(TipViewActivity.this,str,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                return true;
            }
        });

        button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = 0;int y = 0;
                if(event.getAction()== MotionEvent.ACTION_UP){
                    x= (int) event.getX();y = (int) event.getY();
                    Log.i("zz",x+"  "+y);
                    TipView share = new TipView.Builder(TipViewActivity.this,rl,x+v.getLeft(),y+v.getTop())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("粘贴"))
                            .addItem(new TipItem("删除"))
                            .addItem(new TipItem("收藏"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("更多"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str,int a) {
                                    Toast.makeText(TipViewActivity.this,str, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                return true;
            }
        });
        eventsViews();

    }
    private void eventsViews() {
        mOriginalValues.add("1234561");
        mOriginalValues.add("1234562");
        mOriginalValues.add("2234563");
        mOriginalValues.add("2234564");
        mOriginalValues.add("3234561111");
        mOriginalValues.add("32345622222");
        mOriginalValues.add("323456333333");
        mOriginalValues.add("3234564444");
        mOriginalValues.add("3234565555");
        mOriginalValues.add("32345666666");
        mOriginalValues.add("32345777777");

        autoCompleteTextView.setThreshold(0);
        adapter = new AutoCompleteAdapter(this, mOriginalValues, 10);
        autoCompleteTextView.setAdapter(adapter);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, res);
//
//        autoCompleteTextView.setAdapter(adapter);

    }
}
