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
import com.example.gilsoo.ping.Network.Retrofit.MakeGroup;
import com.example.gilsoo.ping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gilsoo on 2016-07-02.
 */
public class AddGroupDialog extends Dialog {
    AddGroupDialog addGroupDialog;
    EditText addGroupName;
    TextView ok, cancel;

    public AddGroupDialog(Context context) {
        super(context);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_group_dialog);
        addGroupDialog = this;
        addGroupName = (EditText)findViewById(R.id.addGroupName);
        ok = (TextView)findViewById(R.id.addOk);
        cancel = (TextView)findViewById(R.id.addCancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewGroup(CommonData.user.getToken(), addGroupName.getText().toString());
                AddGroupDialog.this.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddGroupDialog.this.dismiss();
            }
        });
    }

    public void addNewGroup(final String token, String groupName){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callMakeGroup = networkService.newMakeGroup(token, new MakeGroup(groupName));
        callMakeGroup.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "makeNewGroup() : onResponse() : isSuccessful() : " + response.message());
                    MainActivity.downloadGroupList(token);               // 이렇게 해도 되나?
                }else{
                    Log.d("gilsoo_Retro", "makeNewGroup() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "makeNewGroup() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "makeNewGroup() : onFailure() : " + t.getMessage());

            }
        });
    }
}
