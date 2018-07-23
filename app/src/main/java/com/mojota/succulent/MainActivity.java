package com.mojota.succulent;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mojota.succulent.adapter.ViewPagerAdapter;
import com.mojota.succulent.fragment.EncyclopediaFragment;
import com.mojota.succulent.fragment.MyGardenFragment;
import com.mojota.succulent.fragment.NeighbourFragment;

import java.util.ArrayList;

/**
 * Created by wangjing on 18-7-23
 */
public class MainActivity extends AppCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener {

    private ViewPager mVpMain;
    private BottomNavigationView mBottomNavigation;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ArrayList<Fragment> mFragmentList;
    private ViewPagerAdapter mPagerAdapter;
    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnBottomNaviItemSelectedListener);

        mVpMain = (ViewPager) findViewById(R.id.vp_main);
        initFragment();
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList, null);
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

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, null, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(MyGardenFragment.newInstance("", ""));
        mFragmentList.add(EncyclopediaFragment.newInstance("", ""));
        mFragmentList.add(NeighbourFragment.newInstance("", ""));
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
                case R.id.nav_neighbour:
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
