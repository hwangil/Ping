package com.example.gilsoo.ping.DialogResource;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.gilsoo.ping.R;


public class GroupDialog extends Dialog {
    GroupDialog groupDialog;
    TextView changeGroupName, deleteGroup;
    String selectedGroup;
    public GroupDialog(Context context, final String selectedGroup) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.group_dialog);
        groupDialog = this;
        this.selectedGroup=selectedGroup;
        changeGroupName = (TextView)findViewById(R.id.changeGroupName);
        deleteGroup = (TextView)findViewById(R.id.deleteGroup);
        changeGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeGroupNameDialog changeGroupName = new ChangeGroupNameDialog(getContext(),selectedGroup);
                changeGroupName.show();
                GroupDialog.this.dismiss();

            }
        });
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteGroupDialog deleteGroup = new DeleteGroupDialog(getContext(),selectedGroup);
                deleteGroup.show();
                GroupDialog.this.dismiss();

            }
        });
    }


}
