package com.example.gilsoo.ping.Show_More;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gilsoo.ping.R;

import java.util.ArrayList;

/**
 * Created by admin on 2016-06-28.
 */
public class showMore_adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<showMore_item> data;
    private int layout;
    public showMore_adapter(Context context, ArrayList<showMore_item>data)
    {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
       // this.layout=layout;
    }
    public void setItem(ArrayList<showMore_item> datas)
    {
        this.data=datas;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.showmore_item,parent,false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.item_text);
        textView.setText(data.get(position).getTitle());
        return convertView;
    }
}
