package com.example.gilsoo.ping.Show_More;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gilsoo.ping.R;
import com.example.gilsoo.ping.Tutorial.Tutorial_guide;

/**
 * Created by admin on 2016-06-28.
 */
public class Guide extends AppCompatActivity {
    int MAX_PAGE = 5;                         //View Pager의 총 페이지 갯수를 나타내는 변수 선언
    Fragment cur_fragment = new Fragment();   //현재 Viewpager가 가리키는 Fragment를 받을 변수 선언

    LinearLayout guide_pageMarker;
    int prevPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        guide_pageMarker = (LinearLayout) findViewById(R.id.guide_pageMarker);
        ViewPager viewPager = (ViewPager) findViewById(R.id.guideViewpager);        //Viewpager 선언 및 초기화
        viewPager.setAdapter(new adapter(getSupportFragmentManager()));     //선언한 viewpager에 adapter를 연결
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                guide_pageMarker.getChildAt(prevPosition).setBackgroundResource(R.drawable.nav_line);
                guide_pageMarker.getChildAt(position).setBackgroundResource(R.drawable.nav_fill);
                prevPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initPageMark();
    }

    private class adapter extends FragmentPagerAdapter {                    //adapter클래스
        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("gilsoo_Tutorial", "" + position);
            if (position < 0 || MAX_PAGE <= position) {        //가리키는 페이지가 0 이하거나 MAX_PAGE보다 많을 시 null로 리턴
                position = position % MAX_PAGE;
                return null;
            }
            switch (position) {              //포지션에 맞는 Fragment찾아서 cur_fragment변수에 대입
                case 0:
                    cur_fragment= Tutorial_guide.newInstance(R.drawable.guide_tutorial_1);
                    break;
                case 1:
                    cur_fragment= Tutorial_guide.newInstance(R.drawable.guide_tutorial_2);
                    break;
                case 2:
                    cur_fragment= Tutorial_guide.newInstance(R.drawable.guide_tutorial_3);
                    break;
                case 3:
                    cur_fragment= Tutorial_guide.newInstance(R.drawable.guide_tutorial_4);
                    break;
                case 4:
                    cur_fragment= Tutorial_guide.newInstance(R.drawable.guide_tutorial_5);
                    break;
            }
            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
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


            guide_pageMarker.addView(iv);
        }
        prevPosition = 0;
    }

}
