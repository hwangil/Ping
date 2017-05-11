package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.ModifyMemo;
import com.example.gilsoo.ping.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sansung on 2016-07-14.
 */
public class MemoDialog extends Dialog {
    MemoDialog memoDialog;
    TextView buttonOk, buttonCancel;
    EditText memoEditText;
    int position;
    ArrayList<CardItem> selectedCardLIst = null;
    RecyclerView.Adapter adapter = null;
//    static ModifyMemo modifyMemo=new ModifyMemo();


    public MemoDialog(final Context context, final int position, ArrayList<CardItem> selectedCardLIst,  RecyclerView.Adapter adapter) {
        super(context);
        Log.d("cardList", "MemoDialog 생성자 실행됨");
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_memo);
        this.position = position;
        this.selectedCardLIst = selectedCardLIst;
        this.adapter = adapter;
        init();
        makeOnClickEvent();
    }

    public void init(){
        buttonOk = (TextView)findViewById(R.id.buttonOk);
        buttonCancel = (TextView)findViewById(R.id.buttonCancel);
        memoEditText = (EditText)findViewById(R.id.memoEditText);
        memoEditText.setText(selectedCardLIst.get(position).getMemo());
        memoDialog = this;
    }

    public void makeOnClickEvent(){
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMemo= memoEditText.getText().toString();
                modifySelectedCardMemo(CommonData.user.getToken(), String.valueOf(selectedCardLIst.get(position).getCard_id()), newMemo);
                selectedCardLIst.get(position).setMemo(newMemo);
                memoDialog.cancel();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoDialog.cancel();
            }
        });
    }
    public void modifySelectedCardMemo(final String token, String card_id, String memo){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callModifyMemo = networkService.newModifyMemo(token, new ModifyMemo(card_id, memo));
        callModifyMemo.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "modifySelectedCardMemo() : onResponse() : isSuccessful() : " + response.message());
                    MainActivity.downloadCardList(token);
                    MainActivity.downloadGroupList(token);

                }else{
                    Log.d("gilsoo_Retro", "modifySelectedCardMemo() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "modifySelectedCardMemo() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "modifySelectedCardMemo() : onFailure() : " + t.getMessage());

            }
        });
    }
}
