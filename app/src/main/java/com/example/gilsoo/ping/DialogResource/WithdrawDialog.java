package com.example.gilsoo.ping.DialogResource;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gilsoo.ping.IntroActivities.InitActivity;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.Withdraw;
import com.example.gilsoo.ping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Sansung on 2016-07-07.
 */
public class WithdrawDialog extends Dialog {
    EditText idEditText, pwEditText, rpwEditText;
    TextView withdrawOk, withdrawCancel;
    WithdrawDialog withdrawDialog;
    final Activity activity;

    public WithdrawDialog(final Activity activity) {
        super(activity);
        this.activity = activity;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        withdrawDialog = this;
        setContentView(R.layout.withdraw_dialog);
        initView();

        withdrawOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawFromPing(CommonData.user.getToken(), pwEditText.getText().toString(), rpwEditText.getText().toString());

                WithdrawDialog.this.dismiss();
            }
        });

        withdrawCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawDialog.this.dismiss();

            }
        });

    }

    private void initView() {
        idEditText = (EditText)findViewById(R.id.idEditText);
        pwEditText = (EditText)findViewById(R.id.pwEditText);
        rpwEditText = (EditText)findViewById(R.id.rpwEditText);
        withdrawOk = (TextView)findViewById(R.id.withdrawOk);
        withdrawCancel = (TextView)findViewById(R.id.withdrawCancel);
    }

    public void withdrawFromPing(String token, String password, String rePassword){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callWithdraw = networkService.newWithdraw(token, new Withdraw(password, rePassword));
        callWithdraw.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "withdrawFromPing() : onResponse() : isSuccessful() : " + response.message());
                    activity.startActivity(new Intent(activity, InitActivity.class));
                    activity.finish();
                    // 여기서 finish() 해주던가 어플 리셋하던가
                }else{
                    Log.d("gilsoo_Retro", "withdrawFromPing() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "withdrawFromPing() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "withdrawFromPing() : onFailure() : " + t.getMessage());

            }
        });

    }
}
