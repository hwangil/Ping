package com.example.gilsoo.ping.IntroActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.gilsoo.ping.R;


public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                startActivity(new Intent(getApplicationContext(), InitActivity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);



    }
}