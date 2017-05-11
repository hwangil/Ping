package com.example.gilsoo.ping.Show_All.Animation;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class OpenAnimation extends TranslateAnimation implements
        Animation.AnimationListener {

    private LinearLayout mainLayout;
    private LinearLayout menuLayout;
    private ViewPager viewPager;
    int panelWidth;

    public OpenAnimation(LinearLayout layout, LinearLayout menuLayout, ViewPager viewPager, int width, int fromXType,
                         float fromXValue, int toXType, float toXValue, int fromYType,
                         float fromYValue, int toYType, float toYValue) {

        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
                toYType, toYValue);

        // init
        this.menuLayout = menuLayout;
        menuLayout.setVisibility(View.VISIBLE);
        mainLayout = layout;
        panelWidth = width;
        this.viewPager = viewPager;
        setDuration(300);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);
        mainLayout.startAnimation(this);
        viewPager.startAnimation(this);
    }

    public void onAnimationEnd(Animation arg0) {


        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams) mainLayout.getLayoutParams();
        params.topMargin = panelWidth;
        mainLayout.clearAnimation();
        mainLayout.setLayoutParams(params);
        mainLayout.requestLayout();

        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
        params2.topMargin = panelWidth;
        viewPager.clearAnimation();
        viewPager.setLayoutParams(params2);
        viewPager.requestLayout();

    }

    public void onAnimationRepeat(Animation arg0) {

    }

    public void onAnimationStart(Animation arg0) {

    }

}