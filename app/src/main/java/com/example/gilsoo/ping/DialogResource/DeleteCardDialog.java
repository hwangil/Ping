package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gilsoo on 2016-07-02.
 */
public class DeleteCardDialog extends Dialog {

    NetworkService networkService = null;
    DeleteCardDialog deleteCardDialog;
    //ImageView
    TextView deleteCardOk, deleteCardCancel;
    DeleteCompleteDialog dOkDig;
    int count = 0;
    SparseBooleanArray selectedPositions = null;
    ArrayList<CardItem> selectedCardList = null;
    RecyclerView.Adapter adapter = null;

    public DeleteCardDialog(Context context, final SparseBooleanArray selectedPositions, ArrayList<CardItem> selectedCardList, RecyclerView.Adapter adapter) {
        super(context);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        deleteCardDialog = this;
        setContentView(R.layout.delete_card_dialog);

        deleteCardOk = (TextView) findViewById(R.id.deleteCardOk);
        deleteCardCancel = (TextView) findViewById(R.id.deleteCardCancel);
        this.selectedPositions = selectedPositions;
        this.selectedCardList = selectedCardList;
        this.adapter = adapter;
        makeOnClickEvent();

    }
    public void makeOnClickEvent(){
        deleteCardOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countForMessage = 0;
                // 클라이언트 cardList 에서
                for (int i = selectedCardList.size() - 1; i >= 0; i--) {
                    if (selectedPositions.get(i) == true) {
                        countForMessage++;
                        increaseCount();
                        deleteSelectedCard(CommonData.user.getToken(), selectedCardList.get(i).getCard_id(), i);
                        selectedCardList.remove(i);
                    }
                }
                if(countForMessage == 0)
                    Toast.makeText(getContext(), "카드를 선택해주세요. ", Toast.LENGTH_SHORT).show();
                DeleteCardDialog.this.dismiss();
            }
        });
        deleteCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCardDialog.this.dismiss();
            }
        });
    }

    public void deleteSelectedCard(final String token, int card_id, final int position){
        if(networkService == null)
            networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callDeleteCard = networkService.newDeleteCard(token, card_id);
        callDeleteCard.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("gilsoo_Retro", "deleteSelectedCard() : onResponse() : isSuccessful() : " + response.message());
                    decreaseCount();
                    if(count == 0){
                        MainActivity.downloadCardList(token);
                        MainActivity.downloadGroupList(token);
                    }
                } else {
                    Log.d("gilsoo_Retro", "deleteSelectedCard() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "deleteSelectedCard() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "deleteSelectedCard() : onFailure() : " + t.getMessage());
            }
        });

    }
    synchronized public void increaseCount(){
        count ++;
    }
    synchronized public void decreaseCount(){
        count --;
    }
}
