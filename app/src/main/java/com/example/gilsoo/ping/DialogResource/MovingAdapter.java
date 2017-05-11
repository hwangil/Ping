package com.example.gilsoo.ping.DialogResource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gilsoo.ping.Item.GroupItem;
import com.example.gilsoo.ping.R;

import java.util.ArrayList;

/**
 * Created by admin on 2016-07-06.
 */
public class MovingAdapter extends BaseAdapter {
    ArrayList<GroupItem> groupList;
    LayoutInflater inflater;
    public MovingAdapter(Context context, ArrayList<GroupItem> groupList)
    {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupList=groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  groupList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.dialog_groupmoving_listview_item, parent,false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.dialog_groupmoving_text);
        textView.setText(groupList.get(position).getGroupName());
        return convertView;


    }
}
