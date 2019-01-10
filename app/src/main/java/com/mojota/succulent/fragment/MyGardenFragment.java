package com.mojota.succulent.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojota.succulent.R;
import com.mojota.succulent.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的花园
 * Created by 王静 on 18-7-23
 */
public class MyGardenFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String[] TITLES = {"多肉成长记", "造景后花园"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabLayout mTabMy;
    private ViewPager mVpMy;
    private List<Fragment> mFragments;
    private List<String> mTitles;


    public MyGardenFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyGardenFragment newInstance(String param1, String param2) {
        MyGardenFragment fragment = new MyGardenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        TITLES = getResources().getStringArray(R.array.str_my_gardens);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_garden, container, false);

        mTabMy = view.findViewById(R.id.tab_my);
        mVpMy = view.findViewById(R.id.vp_my);
        initFragment();
        mVpMy.setAdapter(new FragmentViewPagerAdapter(getChildFragmentManager(), mFragments, mTitles));
        mTabMy.setupWithViewPager(mVpMy);
        return view;
    }

    private void initFragment() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(GrowthDiaryFragment.newInstance());
        mFragments.add(LandscapingFragment.newInstance());
        mTitles = Arrays.asList(TITLES);
    }



}
