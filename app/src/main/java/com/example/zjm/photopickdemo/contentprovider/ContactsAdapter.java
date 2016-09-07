package com.example.zjm.photopickdemo.contentprovider;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;

import java.util.List;

/**
 * Created by zjm on 16-9-5.
 */
public class ContactsAdapter extends BaseAdapter{
    private List<PeopleContact> list;
    private int source;
    private Context context;
    private TextView mNameText;
    private TextView mPhoneNoText;
    private LayoutInflater layoutInflater;

    public ContactsAdapter(List<PeopleContact> list, int source, Context context) {
        this.list = list;
        this.source = source;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater= LayoutInflater.from(context);
        view=layoutInflater.inflate(source,null);
        mNameText=(TextView)view.findViewById(R.id.contacts_name);
        mPhoneNoText=(TextView)view.findViewById(R.id.contacts_phoneNo);
        mNameText.setText(list.get(i).name);
        mPhoneNoText.setText(list.get(i).phoneNo);
        return view;
    }
}
