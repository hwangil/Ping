package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.DeleteGroupName;
import com.example.gilsoo.ping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gilsoo on 2016-07-02.
 */
public class DeleteGroupDialog extends Dialog {
    DeleteGroupDialog deleteGroupDialog;
    TextView ok, cancel;

    String selectedGroup;

    public DeleteGroupDialog(Context context, final String selectedGroup) {
        super(context);
        this.selectedGroup=selectedGroup;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_group_dialog);
        deleteGroupDialog = this;
        ok = (TextView)findViewById(R.id.deleteOk);
        cancel= (TextView)findViewById(R.id.deleteCancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup(CommonData.user.getToken(), selectedGroup);
                DeleteGroupDialog.this.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteGroupDialog.this.dismiss();
            }
        });

    }

    public void deleteGroup(final String token, String groupName){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callDeleteGroup = networkService.newDeleteGroupName(token, new DeleteGroupName(groupName));             // @Body 로 할필요가 있나 싶음
        callDeleteGroup.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "deleteGroup() : onResponse() : isSuccessful() : " + response.message());

                    MainActivity.downloadGroupList(token);
                }else{
                    Log.d("gilsoo_Retro", "deleteGroup() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "deleteGroup() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "deleteGroup() : onFailure() : " + t.getMessage());

            }
        });
    }
}
