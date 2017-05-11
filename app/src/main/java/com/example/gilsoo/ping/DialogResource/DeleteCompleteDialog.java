package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.gilsoo.ping.R;


/**
 * Created by Sansung on 2016-07-07.
 */
public class DeleteCompleteDialog extends Dialog {
    public DeleteCompleteDialog(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_complete);
    }
}
