package com.mojota.succulent.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.mojota.succulent.R;
import com.mojota.succulent.adapter.FragmentViewPagerAdapter;
import com.mojota.succulent.fragment.CrazyFragment;
import com.mojota.succulent.fragment.MyGardenFragment;
import com.mojota.succulent.fragment.MomentsFragment;
import com.mojota.succulent.model.NoticeInfo;
import com.mojota.succulent.model.NoticeResponseInfo;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.OssRequest;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CheckUpdateUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.CommonUtil;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ViewGroup mLayoutUser;
    private FloatingActionButton mFabUserEdit;
    private UserInfo mUserInfo;
    private TextView mTvLogout;
    private String mAvatarKey = "";
    private TextView mNewNotices;

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
        mLayoutUser = mNavigationView.getHeaderView(0).findViewById(R.id.layout_user);
        mTvLogout = mNavigationView.getHeaderView(0).findViewById(R.id.tv_logout);
        mTvLogout.setOnClickListener(this);
        mIvAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.iv_avatar);
        mIvAvatar.setOnClickListener(this);
        mTvNickname = mNavigationView.getHeaderView(0).findViewById(R.id.tv_nickname);
        mTvRegion = mNavigationView.getHeaderView(0).findViewById(R.id.tv_region);
        mTvLogin = mNavigationView.getHeaderView(0).findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);
        mFabUserEdit = mNavigationView.getHeaderView(0).findViewById(R.id.fab_user_edit);
        mFabUserEdit.setOnClickListener(this);
        mNewNotices = mNavigationView.getMenu().findItem(R.id.nav_notices)
                .getActionView().findViewById(R.id.tv_new);
        mNewNotices.setVisibility(View.GONE);

        mDrawer = findViewById(R.id.drawer_layout);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 100);
        }

        refreshUser();

        // 注册登录完成广播
        registerReceiver(mUserReceiver, new IntentFilter(LoginActivity.ACTION_LOGIN));

        checkNewNotice();
        // 检查升级
        CheckUpdateUtil.checkUpdate(this, false);
    }

    /**
     * 检查通知显示小红点
     */
    private void checkNewNotice() {
        String url = UrlConstants.GET_NOTICE_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userId", UserUtil.getCurrentUserId());
        paramMap.put("size", String.valueOf(2));

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap,
                NoticeResponseInfo.class, new Response.Listener<NoticeResponseInfo>() {

            @Override
            public void onResponse(NoticeResponseInfo responseInfo) {

                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    List<NoticeInfo> list = responseInfo.getList();
                    if (list.size() > 0 && list.get(0) != null) {
                        String newTime = list.get(0).getNoticeTime();
                        if (!TextUtils.isEmpty(newTime) && !newTime.equals(CommonUtil
                                .getLatestNoticeTime())) {
                            mNewNotices.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                }
            }
        }, new VolleyErrorListener());
        VolleyUtil.execute(request);
    }

    private BroadcastReceiver mUserReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshUser();
            refreshView();
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mUserReceiver);
        super.onDestroy();
    }

    private void refreshUser() {
        mUserInfo = UserUtil.getUser();
        setUserToView();
    }

    /**
     * 登录用户改变后刷新
     */
    private void refreshView() {
        mPagerAdapter.notifyDataSetChanged();
    }

    private void setUserToView() {
        if (mUserInfo != null && !TextUtils.isEmpty(mUserInfo.getUserName())) {
            mLayoutUser.setVisibility(View.VISIBLE);
            mFabUserEdit.setVisibility(View.VISIBLE);
            mTvLogin.setVisibility(View.GONE);
            mTvNickname.setText(UserUtil.getDisplayName(mUserInfo));
            if (TextUtils.isEmpty(mUserInfo.getRegion())) {
                mTvRegion.setVisibility(View.GONE);
            } else {
                mTvRegion.setVisibility(View.VISIBLE);
                mTvRegion.setText(mUserInfo.getRegion());
            }
            Glide.with(this).load(OssUtil.getWholeImageUrl(mUserInfo.getAvatarUrl())).apply(GlobalUtil
                    .getDefaultAvatarOptions().error(R.mipmap.ic_default_avatar_white_48dp))
                    .into(mIvAvatar);
        } else {
            mTvLogin.setVisibility(View.VISIBLE);
            mLayoutUser.setVisibility(View.GONE);
            mFabUserEdit.setVisibility(View.GONE);
        }

        mDrawer.closeDrawer(GravityCompat.START);
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(MyGardenFragment.newInstance("", ""));
        mFragmentList.add(MomentsFragment.newInstance("", ""));
        mFragmentList.add(CrazyFragment.newInstance("", ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeConstants.REQUEST_USER_CHANGE && resultCode == CodeConstants
                .RESULT_USER_CHANGE) {
            refreshUser();
            refreshView();
        }
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
                case R.id.nav_moments:
                    mVpMain.setCurrentItem(1);
                    return true;
                case R.id.nav_crazy:
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
            case R.id.nav_notices:
                mNewNotices.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, NoticesActivity.class));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                ActivityUtil.startLoginActivity(this);
                break;
            case R.id.tv_logout:
                mUserInfo = null;
                UserUtil.clearUser();
                setUserToView();
                refreshView();
                break;
            case R.id.iv_avatar:
                showPicDialog(mIvAvatar, GlobalUtil.getDefaultAvatarOptions());
                setOnChoosedListener(new OnChoosedListener() {
                    @Override
                    public void onChoosed(Uri localUploadUri) {
                        uploadImg(localUploadUri);
                    }

                    @Override
                    public void onCanceled() {

                    }
                });
                break;
            case R.id.fab_user_edit:
                Intent intent = new Intent(MainActivity.this, UserEditActivity.class);
                intent.putExtra(UserEditActivity.KEY_USER, mUserInfo);
                startActivityForResult(intent, CodeConstants.REQUEST_USER_CHANGE);
                break;
        }
    }

    /**
     * 上传头像
     * 头像名为userId
     */
    private void uploadImg(final Uri localPic) {
        showProgress(true);

        final String objectKey = OssUtil.getImageObjectKey(UserUtil.getCurrentUserId(),
                String.valueOf(UserUtil.getCurrentUserId() + "-" + System
                        .currentTimeMillis()));

        new OssRequest().upload(objectKey, GlobalUtil.getByte(localPic), new OssRequest
                .OssOperateListener() {

            @Override
            public void onSuccess(String objectKey, String objectUrl) {
                showProgress(false);
                mAvatarKey = objectKey;
                editAvatarUrl(mAvatarKey);
            }

            @Override
            public void onFailure(String objectKey, String errMsg) {
                StringBuilder tips = new StringBuilder("上传头像失败,");
                tips.append(errMsg);
                GlobalUtil.makeToast(tips.toString());
            }
        });
    }

    /**
     * 修改服务端头像地址
     * @param objectKey
     */
    private void editAvatarUrl(String objectKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("avatarUrl", objectKey);
        requestSubmit(UrlConstants.AVATAR_EDIT_URL, map, CodeConstants
                .REQUEST_EDIT_AVATAR);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == CodeConstants.REQUEST_EDIT_AVATAR) {
            UserUtil.saveAvatar(mAvatarKey);
        }
    }
}
