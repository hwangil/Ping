package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.MoveCard;
import com.example.gilsoo.ping.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2016-07-06.
 */
public class MovingDialog extends Dialog {
    MovingDialog movingDialog;
    Context context;
    TextView movingCardOk, movingCardCancel;
    ListView listView;

    long count = 0;
    SparseBooleanArray selectedPositions = null;
    ArrayList<CardItem> selectedCardList = null;
    String group_name = null;
    RecyclerView.Adapter adapter = null;

    public MovingDialog( Context context, SparseBooleanArray selectedPositions,ArrayList<CardItem> selectedCardList,  RecyclerView.Adapter adapter) {
        super(context);
        this.context = context;
        movingDialog = this;
        movingDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_groupmoving);

        this.selectedPositions = selectedPositions;
        this.selectedCardList = selectedCardList;
        this.adapter = adapter;

        init();
        makeOnClickEvent();
    }

    public void init(){
        movingCardOk = (TextView) findViewById(R.id.movingCardOk);
        movingCardCancel = (TextView) findViewById(R.id.movingCardCancel);
        //그룹 정보 뿌려주기.
        listView = (ListView) findViewById(R.id.movingDialogList);
        MovingAdapter movingAdapter = new MovingAdapter(context, CommonData.groupList);
        listView.setAdapter(movingAdapter);

    }

    public void makeOnClickEvent(){

        movingCardOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group_name == null) {
                    Toast.makeText(context, "이동할 그룹을 선택해주세요 ", Toast.LENGTH_SHORT).show();
                } else {
                    int countForMessage = 0;
                    for (int i = selectedCardList.size() - 1; i >= 0; i--) {
                        if (selectedPositions.get(i) == true) {
                            countForMessage++;
                            increaseCount();    // 수정
                            moveSelectedCard(CommonData.user.getToken(), String.valueOf(selectedCardList.get(i).getCard_id()), group_name, i);
                            selectedCardList.remove(i);
                        }
                    }
                    if(countForMessage == 0)
                        Toast.makeText(getContext(), "카드를 선택해주세요. ", Toast.LENGTH_SHORT).show();
                    MovingDialog.this.dismiss();
                    group_name = null;
                }
            }
        });
        movingCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovingDialog.this.dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                group_name = CommonData.groupList.get(position).getGroupName();
                Log.d("gilsoo_Move", "listView.onItemSelected() : position - " + position + ", groupName - " + group_name);
            }
        });
    }

    public void moveSelectedCard(final String token, String card_id, String group_name, final int position) {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callMoveCard = networkService.newMoveCard(token, new MoveCard(card_id, group_name));
        Log.d("gilsoo_Retro", "moveSelctedCard() : card_id - " + card_id + ", group_name - " + group_name);
        callMoveCard.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("gilsoo_Retro", "moveSelectedCard() : onResponse() : isSuccessful() : " + response.message());
                    decreaseCount();
                    if (count == 0) {
                        MainActivity.downloadCardList(token);
                        MainActivity.downloadGroupList(token);
//                        adapter.notifyItemChanged(position);
                    }

                } else {
                    Log.d("gilsoo_Retro", "moveSelectedCard() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "moveSelectedCard() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "moveSelectedCard() : onFailure() : " + t.getMessage());

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





