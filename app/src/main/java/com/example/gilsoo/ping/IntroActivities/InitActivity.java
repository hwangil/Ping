package com.example.gilsoo.ping.IntroActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Tutorial.Tutorial;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

/**
 * Created by H.Subeen on 2016-06-28.
 */
public class InitActivity extends AppCompatActivity {

    ViewPager viewPager;
    int MAX_PAGE = 5;                         //View Pager의 총 페이지 갯수를 나타내는 변수 선언
    Fragment cur_fragment = new Fragment();     //현재 Viewpager가 가리키는 Fragment를 받을 변수 선언
    Button btnGoLogin, btnGoJoin;
    LinearLayout pageMarker;
    int prevPosition = 0;
    static Activity initAct;

    private SessionCallback callback;       // kakao

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();         // kakao

//        // heap size 출력, out of heap memory error확인
//        String heapSize = String.valueOf(((ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass());
//        Toast.makeText(this, heapSize, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_init);
//        startActivity(new Intent(this, SplashActivity.class));
        initialize(); //초기화

        initAct = this;
        viewPager = (ViewPager) findViewById(R.id.viewpager);        //Viewpager 선언 및 초기화
        viewPager.setAdapter(new TutorialPagerAdapter(getSupportFragmentManager()));     //선언한 viewpager에 adapter를 연결
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageMarker.getChildAt(prevPosition).setBackgroundResource(R.drawable.nav_line);
                pageMarker.getChildAt(position).setBackgroundResource(R.drawable.nav_fill);
                prevPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initPageMark();
    }

    private class TutorialPagerAdapter extends FragmentPagerAdapter {                    //adapter클래스
        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 0 || MAX_PAGE <= position) {        //가리키는 페이지가 0 이하거나 MAX_PAGE보다 많을 시 null로 리턴
                position = position % MAX_PAGE;
                return null;
            }
            switch (position) {              //포지션에 맞는 Fragment찾아서 cur_fragment변수에 대입
                case 0:
                    cur_fragment = Tutorial.newInstance(R.drawable.tutorial_1);
                    break;
                case 1:
                    cur_fragment = Tutorial.newInstance(R.drawable.tutorial_2);
                    break;
                case 2:
                    cur_fragment = Tutorial.newInstance(R.drawable.tutorial_3);
                    break;
                case 3:
                    cur_fragment = Tutorial.newInstance(R.drawable.tutorial_4);
                    break;
                case 4:
                    cur_fragment = Tutorial.newInstance(R.drawable.tutorial_5);
                    break;
                default:
                    return null;
            }
            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }


    private void initialize() {
        btnGoLogin = (Button) findViewById(R.id.btnGoLogin);
        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(InitActivity.this, LoginActivity.class);
                startActivity(loginIntent);
//                finish();

            }
        });

        btnGoJoin = (Button) findViewById(R.id.btnGoJoin);
        btnGoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(InitActivity.this, JoinActivity.class);
                startActivity(logoutIntent);
//                finish();
            }
        });
        pageMarker = (LinearLayout) findViewById(R.id.pageMarker);
    }

    private void initPageMark() {
        for (int i = 0; i < MAX_PAGE; i++) {
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            ImageView iv = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 20, 0);
            iv.setLayoutParams(params);

            if (i == 0)
                iv.setBackgroundResource(R.drawable.nav_fill);
            else
                iv.setBackgroundResource(R.drawable.nav_line);

            pageMarker.addView(iv);
        }
        prevPosition = 0;
    }

    ///////////////////////////////////////////////////////kakao
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("gilsoo_LifeCycle", "InitActivity- onDestroy()");
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.d("gilsoo_Kakao", "onSessionOpened()");
            Log.d("gilsoo_Kakao", "kakao token : " + Session.getCurrentSession().getAccessToken());
            Log.d("gilsoo_Kakao", "kakao token : " + Session.getCurrentSession().getRefreshToken());

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

//    protected void redirectSignupActivity() {
//        Toast.makeText(InitActivity.this, "redirectSignupActivity", Toast.LENGTH_SHORT).show();
////        final Intent intent = new Intent(this, SampleSignupActivity.class);
////        startActivity(intent);
////        finish();
//    }

}

