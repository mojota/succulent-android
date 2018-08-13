package com.mojota.succulent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mojota.succulent.adapter.FragmentViewPagerAdapter;
import com.mojota.succulent.fragment.ImageFragment;
import com.mojota.succulent.view.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class ImageBrowserActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_PIC_URLS = "PIC_URLS";
    public static final String KEY_PIC_POS = "PIC_POS";

    private ViewPager mViewPager;
    private PageIndicatorView mPageIndicator;
    private TextView mTvTitle;
    private List<Fragment> mFragments;
    private String mTitle;
    private ArrayList<String> mPicUrls; // 图片列表
    private int mPicPos; // 要显示的图片位置
    private FragmentViewPagerAdapter mVpAdapter;
//    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition(); // 延迟转场动画
        setContentView(R.layout.activity_image_browser);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(KEY_TITLE);
        mPicUrls = intent.getStringArrayListExtra(KEY_PIC_URLS);
        mPicPos = intent.getIntExtra(KEY_PIC_POS, 0);


//        mToolBar = findViewById(R.id.toolbar);
//        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        mTvTitle = findViewById(R.id.tv_image_title);
        mTvTitle.setText(mTitle);
        initFragment();
        mViewPager = (ViewPager) findViewById(R.id.vp_image);
        mVpAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager()
                , mFragments, null);
        mViewPager.setAdapter(mVpAdapter);
        mPageIndicator = (PageIndicatorView) findViewById(R.id
                .layout_indicator);
        mPageIndicator.setPagerAdapter(mViewPager);
        mViewPager.setCurrentItem(mPicPos);
    }

    private void initFragment() {
        if (mPicUrls == null) {
            return;
        }
        mFragments = new ArrayList<Fragment>();
        for (int i = 0; i < mPicUrls.size(); i++) {
            String picUrl = mPicUrls.get(i);
            mFragments.add(ImageFragment.newInstance(picUrl, String.valueOf
                    (i)));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
