package com.example.gilsoo.ping.Show_All;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.gilsoo.ping.DialogResource.PopupDialog;
import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Show_All.Masonry.CustomItemClickListener;
import com.example.gilsoo.ping.Show_All.Masonry.MasonryAdapter;
import com.example.gilsoo.ping.Show_All.Masonry.SpacesItemDecoration;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-06-27.
 */
public class ShowAllFragment extends Fragment implements CustomItemClickListener {

    public ChangeBar changeBar = null;              // 바 메뉴 바꾸는 콜백함수 구현을 위한 인터페이스
    public static boolean longClickFlag;
    public static int multiClickFlag;
    static RecyclerView mRecyclerView;
    static MasonryAdapter adapter;
//    ArrayList<CardItem> groupCardList = null;                    // 이거 바꾸기
    ArrayList<CardItem> selectedCardList = null;                // ShowAllFragment를 재활용하기 때문에, 그때그떄 보여줄 카드리스트들이 다르다. 그 리스트를 고르기위해 이 변수 선언
    ArrayList<CardItem> searchedCardList = null;            // seach 결과 카드 담을 리스트
    private PopupDialog popupDialog;
    String groupName;

    public static ShowAllFragment newInstance(String groupName, ArrayList<CardItem> searchedCardList) {        //Fragment 생성자 이런식으로
        ShowAllFragment saf = new ShowAllFragment();
        Bundle args = new Bundle();
        args.putString("groupName", groupName);
        args.putParcelableArrayList("searchedCardList", searchedCardList);
        saf.setArguments(args);                //bundle 세팅!
        return saf;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("gilsoo_Cards", "onCreateView 실행");
        longClickFlag = false;
        multiClickFlag = 0;
        Bundle bundle = getArguments();                //bundle가져오기
        if (bundle != null) {
            groupName = (bundle.getString("groupName"));
            searchedCardList = bundle.getParcelableArrayList("searchedCardList");
        }
        View root = inflater.inflate(R.layout.show_all, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.myFragment);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {            // 보류
//            @Override
//            public void onLoadMore(int current_page) {
//
//            }
//        });
        //** 카드들 보여주기 **//
        if (fragment.getTag().equals("show_partial")) {                     // 그룹에 들어있는 카드만 보여준다
            for (int i = 0; i < CommonData.groupList.size(); i++) {
                if (CommonData.groupList.get(i).getGroupName().equals(groupName)) {    // 같은 그룹의 카드를 골라 새로운 ArrayList에 담는다.
                    selectedCardList = CommonData.groupList.get(i).getCardItems();
                    break;
                }
            }
        } else if(fragment.getTag().equals("show_search")) {
            selectedCardList = searchedCardList;

        } else {
            selectedCardList = CommonData.cardList;
        }
        if(adapter != null )
            adapter = null;
        adapter = new MasonryAdapter(getActivity(), this, selectedCardList, Glide.with(this));            // cardItems or groupItems 로 adapter 생성

        mRecyclerView.setAdapter(adapter);                                          // adapter set!
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);             // 카드들의 간격 설정해주는 클래스
        mRecyclerView.addItemDecoration(decoration);

        return root;
    }

    @Override
    public void onItemClick(View v, int position) {
//        if (getFragmentManager().findFragmentById(R.id.myFragment).getTag().equals("show_partial")) {
//            selectedCardList = groupCardList;
//        } else {
//            selectedCardList = CommonData.cardList;
//        }
        if (longClickFlag) {     //longClick 이벤트일 때만
            if (selectedCardList.get(position).isSelected() == false) {       //추가한 코드
                multiClickFlag++;
                selectedCardList.get(position).setSelected(true);
            } else {
                multiClickFlag--;
                selectedCardList.get(position).setSelected(false);
            }
        } else {
            Intent intent = new Intent(getActivity(), CardpageActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("selectedCardList", selectedCardList);
            startActivity(intent);
        }
    }

    @Override
    public void onLongItemClick(View v, int position) {
        if (!longClickFlag) {
            if(changeBar != null)
                changeBar.chagneBarMenu();                  // 앱바 메뉴 변경
            popupDialog = new PopupDialog(getActivity(), position, selectedCardList, mRecyclerView, changeBar);
            popupDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            popupDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

            popupDialog.getWindow().setGravity(Gravity.BOTTOM);
            popupDialog.show();

            longClickFlag = true;
            DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);       //DrawerLayout 잠그기

        }
    }

    public static void myNotifyDataSetChanged() {     // ???
        if(adapter != null)
            adapter.notifyDataSetChanged();
        Log.d("gilsoo_Cards", ""+adapter);
    }

    public static void releaseSelected() {
        for (int i = 0; i < CommonData.cardList.size(); i++) {       //롱클릭 이벤트가 풀리면 선택 자동 해제
            if (CommonData.cardList.get(i).isSelected()) {
                CommonData.cardList.get(i).setSelected(false);
                adapter.notifyItemChanged(i);
            }
        }
        multiClickFlag = 0;
    }

    public static interface ChangeBar{
        void chagneBarMenu();
    }
    public void setChangeBar(ChangeBar changeBar){              //
        this.changeBar = changeBar;
    }
}

