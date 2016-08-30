package com.example.zjm.photopickdemo.bledemo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;

import java.util.List;

/**
 * Created by zjm on 16-8-30.
 */
public class DeviceListAdapter extends BaseAdapter{
    private int source;
    private List<String> devicelist;
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView textView;

    public DeviceListAdapter(Context context, List<String> devicelist, int source) {
        this.source=source;
        this.devicelist=devicelist;
        this.context=context;
    }

    public void setNotify(){
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return devicelist.size();
    }

    @Override
    public Object getItem(int i) {
        return devicelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater= LayoutInflater.from(context);
        view=layoutInflater.inflate(source,null);
        textView=(TextView) view.findViewById(R.id.device_text);
        textView.setText(devicelist.get(i));
        return view;
    }
}
