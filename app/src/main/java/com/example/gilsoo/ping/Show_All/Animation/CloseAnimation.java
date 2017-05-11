package com.example.gilsoo.ping.Show_All.Animation;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CloseAnimation extends TranslateAnimation implements
        TranslateAnimation.AnimationListener {

    private LinearLayout mainLayout;
    private LinearLayout menuLayout;
    private ViewPager viewPager;
    int panelWidth;

    public CloseAnimation(LinearLayout layout,LinearLayout menuLayout, ViewPager viewPager,  int width, int fromXType,
                          float fromXValue, int toXType, float toXValue, int fromYType,
                          float fromYValue, int toYType, float toYValue) {

        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
                toYType, toYValue);

        // Initialize
        mainLayout = layout;
        this.menuLayout = menuLayout;
        panelWidth = width;
        this.viewPager = viewPager;
        setDuration(300);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);

        // Clear left and right margins
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mainLayout.getLayoutParams();
//        params.rightMargin = 0;
//        params.leftMargin = 0;
        params.topMargin=0;
        mainLayout.setLayoutParams(params);
        mainLayout.requestLayout();
        mainLayout.startAnimation(this);

        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
        params2.topMargin = 0;
        viewPager.setLayoutParams(params2);
        viewPager.requestLayout();
        viewPager.startAnimation(this);

    }

    public void onAnimationEnd(Animation animation) {
        menuLayout.setVisibility(View.INVISIBLE);
    }

    public void onAnimationRepeat(Animation animation) {

    }

    public void onAnimationStart(Animation animation) {

    }

}