package com.example.gilsoo.ping.Show_Group;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.DialogResource.AddGroupDialog;
import com.example.gilsoo.ping.DialogResource.GroupDialog;
import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Show_All.ShowAllFragment;

/**
 * Created by gilsoo on 2016-06-27.
 */
public class ShowGroupFragment extends Fragment {
    ShowAllFragment.ChangeBar changeBar = null;
    GridView groupGridView;
    static GridAdapter adapter;

    boolean longFlag = false;
    String groupName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.show_group, container, false);
        groupGridView = (GridView) root.findViewById(R.id.groupGridView);

        if(adapter == null)
            adapter = new GridAdapter(getActivity(), CommonData.groupList);
//        else
//            adapter.notifyDataSetChanged();
        groupGridView.setAdapter(adapter);

        groupGridView.setLongClickable(true);

        groupGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {    // 롱 클릭했을 때
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == CommonData.groupList.size())
                    return true;
                GroupDialog groupDialog= new GroupDialog(getActivity(), CommonData.groupList.get(position).getGroupName());
                groupDialog.show();


                return true;            //true를 해야 onCLick실행 X => onTouch->onLongClick ->onClick
            }
        });

        groupGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {        // 클릭했을 때

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == CommonData.groupList.size()){
                    AddGroupDialog addGroupDialog = new AddGroupDialog(getActivity());
                    addGroupDialog.show();
                    return;
                }
                groupName = CommonData.groupList.get(position).getGroupName();

                           // 그룹네임 임의로 이렇게 한거임. 테스트용
                                                                              // groupList에서 받아오는 형식으로 수정해야함.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentManager.findFragmentById(R.id.myFragment);      // content_main의 FrameLayout

                ShowAllFragment showAllFragment = ShowAllFragment.newInstance(groupName, null);
                showAllFragment.setChangeBar(changeBar);
                fragmentTransaction.replace(R.id.myFragment, showAllFragment, "show_partial");
                fragmentTransaction.addToBackStack(null);               //백스택에 저장하려면!

                fragmentTransaction.commit();


            }
        });

        return root;
    }

    public static void myNotifyDataSetChanged() {     // ?? .... 나중에 수정
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("gilsoo", "onDestoryView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("gilsoo", "onDestory");
    }

    public void setChangeBar(ShowAllFragment.ChangeBar changeBar){              //
        this.changeBar = changeBar;
    }
}
