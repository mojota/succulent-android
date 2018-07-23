package com.mojota.succulent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * Created by wangjing on 18-7-23.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList;
    private final List<String> mTitleList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fList, List<String> titles) {
        super(fm);
        mFragmentList = fList;
        mTitleList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragmentList != null) {
            return mFragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mFragmentList != null) {
            return mFragmentList.size();
        }
        return 0;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        }
        return null;
    }
}
