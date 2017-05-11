package com.example.gilsoo.ping.Show_More;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.ChangePassword;
import com.example.gilsoo.ping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2016-06-28.
 */
public class ChangePasswordActivity extends AppCompatActivity {
    EditText currentPassword, newPassword, checkNewPassword;
    Button changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        initView();


        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //세 칸을 모두 채우지 않았을 시, 토스트를 띄운다.
                if (currentPassword.getText().toString().isEmpty() || newPassword.getText().toString().isEmpty()
                        || checkNewPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 칸을 채워주세요. ", Toast.LENGTH_SHORT).show();
                } else {
                    //currentPassword.getText().toString() 과 사용자의 비밀번호가 맞는지 확인한다.
                   if (! newPassword.getText().toString().equals(checkNewPassword.getText().toString()) ){
                        //newPassword.getText().toString() 과 checkNewPassword.getText().toString() 가 맞는지 확인한다.
                        Toast.makeText(getApplicationContext(), "변경할 비밀번호가 일치하지 않습니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        changePassword(CommonData.user.getToken(), currentPassword.getText().toString()
                                , newPassword.getText().toString(), checkNewPassword.getText().toString());
                        //비밀번호를 변경해준다.

                    }
                }
            }
        });
    }

    private void initView() {
        currentPassword = (EditText) findViewById(R.id.currentPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        checkNewPassword = (EditText) findViewById(R.id.checkNewPassword);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
    }

    public void changePassword(String token, String passwd, String update_passwd, String update_repasswd){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callChangePassword = networkService.newChangePassword(token, new ChangePassword(passwd, update_passwd, update_repasswd));
        callChangePassword.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "changePassword() : onResponse() : isSuccessful() : " + response.message());

                }else{
                    Log.d("gilsoo_Retro", "changePassword() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "changePassword() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "changePassword() : onFailure() : " + t.getMessage());

            }
        });
    }
}