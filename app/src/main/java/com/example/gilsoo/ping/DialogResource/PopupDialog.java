package com.example.gilsoo.ping.DialogResource;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Show_All.ShowAllFragment;

import java.util.ArrayList;


/**
 * Created by Sansung on 2016-06-30.
 */
public class PopupDialog extends Dialog {
    Context context;
    PopupDialog popupDialog;
    Button btnmovingCard, btndeleteCard, btnmemo;
    int curposition;
    SparseBooleanArray selectedPositions;
    ArrayList<CardItem> selectedCardList = null;
    RecyclerView recyclerView = null;
    ShowAllFragment.ChangeBar changeBar;




    public PopupDialog(Context context, int curposition, ArrayList<CardItem> selectedCardList, RecyclerView recyclerview
    ,                   ShowAllFragment.ChangeBar changeBar) {
        super(context);
        popupDialog = this;
        this.context = context;
        this.curposition = curposition;
        selectedPositions = new SparseBooleanArray();
        popupDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        popupDialog.setContentView(R.layout.dialog_popup);
        this.selectedCardList = selectedCardList;
        this.recyclerView = recyclerview;
        this.changeBar = changeBar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popupDialog = this;
        btndeleteCard = (Button) findViewById(R.id.btndeleteCard);
        btnmovingCard = (Button) findViewById(R.id.btnmovingCard);
        btnmemo = (Button) findViewById(R.id.btnmemo);

        btndeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ShowAllFragment.multiClickFlag <= 0) {
                    Toast.makeText(context, "카드를 선택하세요 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < selectedCardList.size(); i++) {
                    if (selectedCardList.get(i).isSelected())
                        selectedPositions.append(i, true);
                }
                DeleteCardDialog deleteCard = new DeleteCardDialog(getContext(), selectedPositions, selectedCardList, recyclerView.getAdapter());
                deleteCard.show();

                ShowAllFragment.longClickFlag = false;
                PopupDialog.this.dismiss();
            }
        });


        btnmovingCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(ShowAllFragment.multiClickFlag <= 0) {
                    Toast.makeText(context, "카드를 선택하세요 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < selectedCardList.size(); i++) {
                    if (selectedCardList.get(i).isSelected())
                        selectedPositions.append(i, true);
                }
                MovingDialog movingDialog = new MovingDialog(getContext(), selectedPositions, selectedCardList, recyclerView.getAdapter() );
//                movingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                movingDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                movingDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                movingDialog.show();
                popupDialog.cancel();



            }
        });

        btnmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShowAllFragment.multiClickFlag <= 0) {
                    Toast.makeText(context, "카드를 선택하세요 ", Toast.LENGTH_SHORT).show();
                }else if (ShowAllFragment.multiClickFlag > 1) {
                    Toast.makeText(context, "메모는 카드 1개만 가능합니다. ", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < selectedCardList.size(); i++)
                        if (selectedCardList.get(i).isSelected())
                            curposition = i;
                    MemoDialog memoDialog = new MemoDialog(getContext(), curposition, selectedCardList, recyclerView.getAdapter());
                    memoDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    memoDialog.show();

                    PopupDialog.this.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        ShowAllFragment.longClickFlag = false;
//        for(int i=0; i<selectedCardList.size(); i++){               // 선택했던 카드들 해제
//            if(selectedCardList.get(i).isSelected()){
//                selectedCardList.get(i).setSelected(false);
//                recyclerView.getAdapter().notifyItemChanged(i);
//            }
//        }
//        recyclerView.getAdapter().notifyDataSetChanged();
        ShowAllFragment.releaseSelected();
        ShowAllFragment.multiClickFlag = 0;
        Activity activity = (Activity) context;
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);       //DrawerLayout 잠그기
        changeBar.chagneBarMenu();
        super.onStop();
    }
}


