package com.example.gilsoo.ping.IntroActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Item.UserInfo;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.Login;
import com.example.gilsoo.ping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    NetworkService networkService = null;
//    boolean responseCode = false;
    Button btnLogin;
    EditText login_id, login_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        login_id = (EditText) findViewById(R.id.login_id);
        login_pw = (EditText) findViewById(R.id.login_pw);
    }

    public void onBtnLoginClick(View v) {
        loginToPing(login_id.getText().toString(), login_pw.getText().toString());      // 서버와 통신하여 아이디 비번 일치 확인
    }

    public void loginToPing(String id, String pw) {

        if (networkService == null) {
            networkService = ApplicationController.getInstance().getNetworkService();
        }
        Call<UserInfo> callLogin = networkService.newLogin(new Login(id, pw));
        callLogin.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Log.d("gilsoo_Retro", "loginToPing() : onResponse() : " + response.code());
                    UserInfo userInfo= response.body();
                    Log.d("gilsoo_Retro", "loginToPing() : onResponse() :  userinfo : " + userInfo.getUser_id() +" "+ userInfo.getUser_name()+" "+ userInfo.getToken());

                    CommonData.user = userInfo;

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "환영합니다! " + userInfo.user_name + " 님" , Toast.LENGTH_SHORT).show();
                    InitActivity.initAct.finish();
                    finish();

                } else {
                    Log.d("gilsoo_Retro", "loginToPing() : onResponse() : " + response.message() + " " + response.code());
                    if(response.code() == 502){
                        Toast.makeText(getApplicationContext(), "등록되지 않은 회원입니다. " , Toast.LENGTH_SHORT).show();
                    } else if(response.code() == 503){
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. " , Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("gilsoo_Retro", "loginToPing() : onFailure() : " + t.getMessage());

                Toast.makeText(getApplicationContext(), "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
