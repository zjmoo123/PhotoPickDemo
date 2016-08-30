package com.example.zjm.photopickdemo;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by zjm on 16-8-26.
 */
public class GridViewAdapter extends BaseAdapter{
    private Context context;
    private Integer[] integers={};
    private int source;
    private ImageView imageView;
    private LayoutInflater layoutInflater;

    public GridViewAdapter(Context context,Integer[] integers,int source){
        this.context=context;
        this.source=source;
        this.integers=integers;
    }
    @Override
    public int getCount() {
        return integers.length;
    }

    @Override
    public Object getItem(int i) {
        return integers[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(source,null);
        imageView=(ImageView)view.findViewById(R.id.image);
        imageView.setImageDrawable(ContextCompat.getDrawable(context,integers[i]));
        return view;
    }
}
