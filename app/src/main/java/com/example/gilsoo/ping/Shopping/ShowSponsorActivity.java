package com.example.gilsoo.ping.Shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gilsoo.ping.R;

import java.util.ArrayList;

public class ShowSponsorActivity extends AppCompatActivity {
    GridView gridView;
    EditText sponserEditText;
    ArrayList<SponsorGrid> list = new ArrayList<SponsorGrid>();
    Button sponserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Sponsor", "onCreate 실행됨");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);

        sponserEditText = (EditText)findViewById(R.id.sponserEditText);
        sponserButton = (Button)findViewById(R.id.sponserButton);
        gridView=(GridView)findViewById(R.id.sponserGridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                startActivity(intent);
            }
        });
        sponserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                intent.putExtra("url", sponserEditText.getText().toString());
                startActivity(intent);
            }
        });

        list.add(new SponsorGrid(R.drawable.naver_somenail, "Naver", "http://m.naver.com"));
        list.add(new SponsorGrid(R.drawable.google_somenail, "Google", "http://m.google.com/"));
        list.add(new SponsorGrid(R.drawable.chrome_somenail, "Chrome", "http://m.chrome.com"));
        list.add(new SponsorGrid(R.drawable.tmon_somenail, "Tmon", "http://m.tmon.co.kr"));
        list.add(new SponsorGrid(R.drawable.s11st_icon, "11st", "http://m.11st.co.kr"));
        list.add(new SponsorGrid(R.drawable.wmp_icon, "WeMakePrice", "http://m.wemakeprice.com"));
        list.add(new SponsorGrid(R.drawable.gsshop_icon, "Gsshop", "http://m.gsshop.com"));
        list.add(new SponsorGrid(R.drawable.coupang_somenail, "Coupang", "http://m.coupang.com"));

        SponsorAdapter adapter=new SponsorAdapter(list);

        gridView.setAdapter(adapter);

        Log.d("Sponsor", "onCreate 종료됨");
    }

    class SponsorAdapter extends BaseAdapter {

        ArrayList<SponsorGrid> list;

        public SponsorAdapter(ArrayList<SponsorGrid> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {         //각각의 아이템들을 위한 view를 만들어줌
            //ImageView imageView = (ImageView) findViewById(R.id.sponsorImage);
            View view = convertView;
            view = getLayoutInflater().inflate(R.layout.sponser_gridview, null);
            ImageView sponsorImage = (ImageView) view.findViewById(R.id.sponsorImage);
            TextView sponserText = (TextView) view.findViewById(R.id.sponserText);
            sponsorImage.setImageResource(list.get(position).getImage());
            sponserText.setText(list.get(position).getName());

            return view;
        }
    }

}