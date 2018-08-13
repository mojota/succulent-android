package com.mojota.succulent.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mojota.succulent.R;


/**
 * viewpager页数指示器
 * Created by mojota on 18-8-9.
 */
public class PageIndicatorView extends LinearLayout implements ViewPager.OnPageChangeListener{

    public PageIndicatorView(Context context) {
        super(context);
        initView();
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setGravity(Gravity.CENTER);
    }

    public void setPagerAdapter(ViewPager viewPager) {
        removeAllViews();
        if (viewPager.getAdapter() != null) {
            for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                ImageView ivPoint = new ImageView(getContext());
                ivPoint.setBackgroundResource(R.drawable.indicator_selector);
                if (i == 0) {
                    ivPoint.setEnabled(true);
                } else {
                    ivPoint.setEnabled(false);
                }
                addView(ivPoint);
            }

            viewPager.addOnPageChangeListener(this);
        }
    }

    private void setIndicatorSelected(int position) {
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).setEnabled(position == i);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setIndicatorSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
