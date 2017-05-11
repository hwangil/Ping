package com.example.gilsoo.ping.Show_All;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Show_All.Animation.CloseAnimation;
import com.example.gilsoo.ping.Show_All.Animation.OpenAnimation;

import java.util.ArrayList;


public class CardpageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    // **   Animation Event   **//
    private DisplayMetrics metrics;
    private LinearLayout ll_mainLayout;
    private LinearLayout ll_menuLayout;
    private FrameLayout.LayoutParams MenuLayoutPrams;
    private ViewPager viewPager;
    private TextView memoText;
    private int topMenuHeight;
    private static boolean isTopExpanded;

    private Button memoButton;
    Toolbar toolbar;

    /////////////////////////////////////
    ArrayList<CardItem> selectedCardList = null;
    int numOfCards;
    int MAX_PAGE;                         //View Pager의 총 페이지 갯수를 나타내는 변수 선언
    // Fragment cur_fragment=new Fragment();   //현재 Viewpager가 가리키는 Fragment를 받을 변수 선언
    Page cur_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ViewPager", "onCreate()");
        setContentView(R.layout.activity_cardpage);
        // toolbar=(Toolbar)findViewById(R.id.toolbar_webView_card);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initSildeMenu();    //Animation
        init();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        memoText.setText(selectedCardList.get(position).getMemo());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private class CardPageAdapter extends FragmentPagerAdapter {                    //adapter클래스
        public CardPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position < 0 || MAX_PAGE <= position)        //가리키는 페이지가 0 이하거나 MAX_PAGE보다 많을 시 null로 리턴
                return null;
            Log.d("gilsoo_Cards", selectedCardList.get(position).getUrl());
            cur_fragment = Page.newInstance(selectedCardList.get(position).getUrl(), position, selectedCardList);

            if (position >= numOfCards)                           //pager갯수 제한 시키기.
                position = position - 1;

            //numOfUrl++;
            return cur_fragment;
        }

//        public boolean isViewFromObject(View view,Object o){
//            return view.equals(o);
//        }


        @Override
        public int getCount() {
            return numOfCards;
        }

    }

    public void init(){
        memoText = (TextView) findViewById(R.id.memoText);
        ll_menuLayout.setVisibility(View.INVISIBLE);
        selectedCardList = getIntent().getParcelableArrayListExtra("selectedCardList");
        numOfCards = selectedCardList.size();
        MAX_PAGE = numOfCards;
        viewPager = (ViewPager) findViewById(R.id.viewpager);        //Viewpager 선언 및 초기화
        viewPager.setAdapter(new CardPageAdapter(getSupportFragmentManager()));     //선언한 viewpager에 adapter를 연결
        viewPager.setOffscreenPageLimit(5);
        viewPager.setOnPageChangeListener(this);
        int selectPosition = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(selectPosition);
        memoText.setText(selectedCardList.get(selectPosition).getMemo());
    }
    //*******    Animation  **********////
    private void initSildeMenu() {

        // init left menu width
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        topMenuHeight = (int) ((metrics.heightPixels) * 0.15);

        // init main view
        ll_mainLayout = (LinearLayout) findViewById(R.id.ll_mainlayout);

        // init left menu
        ll_menuLayout = (LinearLayout) findViewById(R.id.ll_menuLayout);
        MenuLayoutPrams = (FrameLayout.LayoutParams) ll_menuLayout
                .getLayoutParams();
        MenuLayoutPrams.height = topMenuHeight;
        ll_menuLayout.setLayoutParams(MenuLayoutPrams);

        // init ui
        memoButton = (Button) findViewById(R.id.memoButton);
        memoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDownSlideAnimationToggle();
            }
        });


    }

    /**
     * left menu toggle
     */
    private void menuDownSlideAnimationToggle() {

        if (!isTopExpanded) {
            Log.d("sliding", "open");
            isTopExpanded = true;

            // Expand
//            fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue
            new OpenAnimation(ll_mainLayout, ll_menuLayout, viewPager, topMenuHeight,
                    0, 0.0f,
                    0, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.15f);

            // enable all of menu view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_menuLayout)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);


        } else {
            Log.d("sliding", "closed");

            isTopExpanded = false;

            // Collapse
            new CloseAnimation(ll_mainLayout, ll_menuLayout, viewPager, topMenuHeight,
                    0, 0.0f,
                    0, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, 0.15f, TranslateAnimation.RELATIVE_TO_SELF, 0.0f);


            // enable all of menu view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_menuLayout)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);

            // disable empty view
//            ((LinearLayout) findViewById(R.id.ll_empty))
//                    .setVisibility(View.GONE);
//            findViewById(R.id.ll_empty).setEnabled(false);
            memoButton.setEnabled(true);
        }
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup,
                                              boolean enabled) {
        int childCount = viewGroup.getChildCount();
        Log.d("Appjam", String.valueOf(childCount));

        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

}