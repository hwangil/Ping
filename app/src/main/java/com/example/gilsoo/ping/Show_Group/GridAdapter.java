package com.example.gilsoo.ping.Show_Group;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gilsoo.ping.Item.GroupItem;
import com.example.gilsoo.ping.R;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-06-28.
 */
public class GridAdapter extends BaseAdapter {
    Context context = null;
    ArrayList<GroupItem> list;
    LayoutInflater inflater;

    GridAdapter(Context context, ArrayList<GroupItem> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (list != null) ? list.size()+1 : 0;
    }

    @Override
    public Object getItem(int position) {
        return (list != null) ? list.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = inflater.inflate(R.layout.group_gridview, null); // R.layout.group_gridview, parent, false (x)
        ImageView gridViewImage = (ImageView) view.findViewById(R.id.gridViewImage);
        TextView gridViewText = (TextView) view.findViewById(R.id.gridViewText);

//        Log.d("gilsoo_Retro", "**** list.size() : " + list.size() + ", position : " + position);
        if(position == list.size()){
            Glide.with(gridViewImage.getContext()).load(R.drawable.group_add).into(gridViewImage);  // 이부분 url로 변경
        }else{
            Log.d("gilsoo_Retro" , "GridAdapter : " + list.get(position).getGroupImage());
            Glide.with(gridViewImage.getContext()).load(list.get(position).getGroupImage()).placeholder(R.drawable.logo)
                    .error(R.drawable.logo).fitCenter().thumbnail(0.01f).
                    into(gridViewImage);  // 이부분 url로 변경
            gridViewImage.setAlpha(60);
            gridViewText.setText(list.get(position).getGroupName());
        }

        return view;
    }
}
