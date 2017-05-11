package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.ChangeGroupName;
import com.example.gilsoo.ping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gilsoo on 2016-07-02.
 */
public class ChangeGroupNameDialog extends Dialog {
    ChangeGroupNameDialog changeGroupName;
    EditText newGroupName;
    TextView ok, cancel;
    String selectedGroup;

    public ChangeGroupNameDialog(Context context, final String selectedGroup) {
        super(context);
        this.selectedGroup=selectedGroup;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_groupname_dialog);
        changeGroupName = this;
        newGroupName = (EditText)findViewById(R.id.newGroupName);
        ok = (TextView)findViewById(R.id.changeOk);
        cancel= (TextView)findViewById(R.id.changeCancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGroupName(CommonData.user.getToken(), selectedGroup, newGroupName.getText().toString());
                ChangeGroupNameDialog.this.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeGroupNameDialog.this.dismiss();

            }
        });
    }
    public void changeGroupName(final String token, String groupName, String newGroupName){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callChangeGroupName = networkService.newChangeGroupName(token, new ChangeGroupName(groupName, newGroupName));
        callChangeGroupName.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "changeGroupName() : onResponse() : isSuccessful() : " + response.message());
                    MainActivity.downloadGroupList(token);
                    MainActivity.downloadCardList(token);       // 이름 변경하면 해당 카드의 그룹이름도 바꿔주었으면

                }else{
                    Log.d("gilsoo_Retro", "changeGroupName() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "changeGroupName() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "changeGroupName() : onFailure() : " + t.getMessage());

            }
        });
    }
}
