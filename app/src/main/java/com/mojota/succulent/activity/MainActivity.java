package com.mojota.succulent.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.FragmentViewPagerAdapter;
import com.mojota.succulent.fragment.EncyclopediaFragment;
import com.mojota.succulent.fragment.MyGardenFragment;
import com.mojota.succulent.fragment.MomentsFragment;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.model.UserInfoResponseInfo;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.ArrayList;

/**
 * Created by mojota on 18-7-23
 */
public class MainActivity extends PhotoChooseSupportActivity implements NavigationView
        .OnNavigationItemSelectedListener, View.OnClickListener {

    private ViewPager mVpMain;
    private BottomNavigationView mBottomNavigation;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ArrayList<Fragment> mFragmentList;
    private FragmentViewPagerAdapter mPagerAdapter;
    private MenuItem mMenuItem;
    private ImageView mIvAvatar;
    private TextView mTvNickname;
    private TextView mTvRegion;
    private TextView mTvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener
                (mOnBottomNaviItemSelectedListener);

        mVpMain = (ViewPager) findViewById(R.id.vp_main);
        initFragment();
        mPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),
                mFragmentList, null);
        mVpMain.setAdapter(mPagerAdapter);
        mVpMain.setOffscreenPageLimit(3);
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mBottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mBottomNavigation.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.iv_avatar);
        mIvAvatar.setOnClickListener(this);
        mTvNickname = mNavigationView.getHeaderView(0).findViewById(R.id.tv_nickname);
        mTvRegion = mNavigationView.getHeaderView(0).findViewById(R.id.tv_region);
        mTvLogin = mNavigationView.getHeaderView(0).findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, null, R
                .string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 100);
        }

        getData();
    }

    private void getData() {
        UserInfoResponseInfo responseInfo = new Gson().fromJson(TestUtil.getUserinfo(),
                UserInfoResponseInfo.class);
        UserInfo userInfo = responseInfo.getUserInfo();
        if (userInfo != null) {
            mTvLogin.setVisibility(View.GONE);
            mTvNickname.setText(userInfo.getNickname());
            mTvRegion.setText(userInfo.getRegion());
            Glide.with(this).load(userInfo.getAvatarUrl()).apply(GlobalUtil
                    .getDefaultAvatarOptions().error(R.mipmap.ic_default_avatar_white_48dp))
                    .into(mIvAvatar);
        } else {
            mTvLogin.setVisibility(View.VISIBLE);
        }
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(MyGardenFragment.newInstance("", ""));
        mFragmentList.add(EncyclopediaFragment.newInstance("", ""));
        mFragmentList.add(MomentsFragment.newInstance("", ""));
    }


    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnBottomNaviItemSelectedListener = new BottomNavigationView
            .OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_my:
                    mVpMain.setCurrentItem(0);
                    return true;
                case R.id.nav_encyclopedia:
                    mVpMain.setCurrentItem(1);
                    return true;
                case R.id.nav_moments:
                    mVpMain.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_feedback:
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                ActivityUtil.startLoginActivity(this);
                break;
            case R.id.iv_avatar:
                showPicDialog(mIvAvatar, GlobalUtil.getDefaultAvatarOptions());
                break;
        }
    }
}
