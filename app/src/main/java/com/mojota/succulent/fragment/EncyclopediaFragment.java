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
 * 多肉百科
 * Created by mojota on 18-7-23
*/
public class EncyclopediaFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String[] TITLES = {"图鉴", "攻略"};

    private String mParam1;
    private String mParam2;

    private TabLayout mTab;
    private ViewPager mVp;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public EncyclopediaFragment(){

    }

    public static EncyclopediaFragment newInstance(String param1, String param2) {
        EncyclopediaFragment fragment = new EncyclopediaFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_encyclopedia, container, false);

        mTab = view.findViewById(R.id.tab);
        mVp = view.findViewById(R.id.vp);
        initFragment();
        mVp.setAdapter(new FragmentViewPagerAdapter(getChildFragmentManager(), mFragments, mTitles));
        mTab.setupWithViewPager(mVp);
        return view;

    }

    private void initFragment() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(IllustrationFragment.newInstance());
        mFragments.add(StrategyFragment.newInstance());
        mTitles = Arrays.asList(TITLES);
    }
}
