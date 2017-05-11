package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Show_All.ShowAllFragment;

import java.util.ArrayList;

/**
 * Created by Sansung on 2016-06-30.
 */
public class CustomDialog extends Dialog  {

    CustomDialog customDialog;
    Button buttonOk, buttonCancel;
    EditText memoEditText;
    int curPosition;
    ArrayList<CardItem> cardList= CommonData.cardList;
    ShowAllFragment fragment = new ShowAllFragment();

    public CustomDialog(final Context context, int position) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        curPosition = position;
        setContentView(R.layout.dialog_custom);
        buttonOk = (Button)findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMemo= memoEditText.getText().toString();
                CardItem newCardItem = new CardItem(cardList.get(curPosition).getImg(),
                        cardList.get(curPosition).getMemo(),
                        cardList.get(curPosition).getUrl(),
                        cardList.get(curPosition).getGroupInfo());
                newCardItem.setMemo(newMemo);
                CommonData.cardList.set(curPosition, newCardItem);
//                ShowAllFragment sf= new ShowAllFragment();
                ShowAllFragment.myNotifyDataSetChanged();
                customDialog.cancel();
            }
        });
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        customDialog = this;
        memoEditText = (EditText)findViewById(R.id.memoEditText);
        memoEditText.setText(CommonData.cardList.get(curPosition).getMemo());

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });


    }
}
