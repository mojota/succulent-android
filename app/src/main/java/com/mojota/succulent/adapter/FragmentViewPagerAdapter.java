package com.mojota.succulent.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mojota on 18-7-23.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private final List<String> mTitleList;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fList, List<String> titles) {
        super(fm);
        mFragmentList = fList;
        mTitleList = titles;
    }
    public void setList(List<Fragment> fList) {
        mFragmentList = fList;
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

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
