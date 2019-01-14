package com.mojota.succulent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mojota.succulent.R;
import com.mojota.succulent.adapter.MomentsAdapter;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.NoteResponseInfo;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.OssRequest;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;
import com.mojota.succulent.view.LoadMoreRecyclerView;
import com.mojota.succulent.view.LoadingView;
import com.mojota.succulent.view.WrapRecycleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 王静 on 18-12-11.
 */
public class UserMomentsActivity extends PhotoChooseSupportActivity implements
        SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, LoadMoreRecyclerView
        .OnLoadListener, View.OnClickListener {
    public static final String KEY_USER = "user";

    private static final int PAGE_SIZE = 10; // 每页的条数
    private SwipeRefreshLayout mSwipeRefresh;
    private LoadMoreRecyclerView mRvMoments;
    private MomentsAdapter mMomentsAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();
    private String mUpdateTime = "";
    private WrapRecycleAdapter mWrapAdapter;
    private ImageView mIvCover;
    private UserInfo mUser;
    private String mCoverKey = "";
    private Toolbar mToolBar;
    private ViewGroup mLayout;
    private TextView mTvEmpty;
    private LoadingView mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_moments);

        mUser = (UserInfo) getIntent().getSerializableExtra(KEY_USER);
        // 如果是当前登录用户,则取本地值
        if (UserUtil.isCurrentUser(mUser.getUserId())){
            mUser = UserUtil.getUser();
        }
        initView();

        getData(mUpdateTime);
    }

    private void initView() {
        mLayout = findViewById(R.id.layout_user);
        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(this);
        mLoading = findViewById(R.id.loading);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(this);
        mRvMoments = findViewById(R.id.rv_user_moments);
        mMomentsAdapter = new MomentsAdapter(mList, false, this);
        mWrapAdapter = new WrapRecycleAdapter(mMomentsAdapter);
        View headerView = LayoutInflater.from(this).inflate(R.layout
                .layout_moments_header, mRvMoments, false);
        mIvCover = headerView.findViewById(R.id.iv_cover);
        if (UserUtil.isCurrentUser(mUser.getUserId())) {
            mIvCover.setImageResource(R.mipmap.ic_add_white_48dp);
        }
        mIvCover.setOnClickListener(this);
        mTvEmpty = findViewById(R.id.tv_empty);
        mWrapAdapter.addHeaderView(headerView);
        mRvMoments.setAdapter(mWrapAdapter);
        mRvMoments.setOnLoadListener(this);

        // 控制toolbar显示
        mRvMoments.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager()
                        instanceof LinearLayoutManager) {
                    LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

                    int firstVisiblePosition = llm.findFirstVisibleItemPosition();
                    if (firstVisiblePosition > 0) {
                        mToolBar.setVisibility(View.INVISIBLE);
                    } else {
                        mToolBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    private void getData(final String updateTime) {
        if (mList == null || mList.size() <= 0) {
            mLoading.show(true);
        }

        String url = UrlConstants.GET_USER_MOMENTS_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("loginUserId", UserUtil.getCurrentUserId());
        paramMap.put("userId", mUser.getUserId());
        paramMap.put("updateTime", updateTime);
        paramMap.put("size", String.valueOf(PAGE_SIZE));

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap,
                NoteResponseInfo.class, new Response.Listener<NoteResponseInfo>() {

            @Override
            public void onResponse(NoteResponseInfo responseInfo) {
                mLoading.show(false);
                mSwipeRefresh.setRefreshing(false);
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    if (TextUtils.isEmpty(updateTime)) {
                        mList.clear();
                    }
                    List<NoteInfo> list = responseInfo.getList();
                    mList.addAll(list);
                    mRvMoments.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvMoments.loadMoreFailed();
                    GlobalUtil.makeToast(R.string.str_no_data);
                }
                mTvEmpty.setText(R.string.str_user_moments_empty);
                setDataToView();
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mLoading.show(false);
                mSwipeRefresh.setRefreshing(false);
                mRvMoments.loadMoreFailed();
                GlobalUtil.makeToast(R.string.str_network_error);
                mTvEmpty.setText(R.string.str_network_error_retry);
                setDataToView();
            }
        }));
        VolleyUtil.execute(request);
    }

    private void setDataToView() {
        if (ActivityUtil.isDead(this)){
            return;
        }
        if (!TextUtils.isEmpty(mUser.getCoverUrl())){
            mIvCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            setPaletteImage(mIvCover, mLayout, OssUtil.getWholeImageUrl(mUser
                    .getCoverUrl()), GlobalUtil.getDefaultOptions().centerCrop(), R
                    .mipmap.ic_default_pic, R.drawable.ic_bg);

//            Glide.with(SucculentApplication.getInstance()).load(OssUtil.getWholeImageUrl
//                    (mUser.getCoverUrl())).apply(GlobalUtil.getDefaultOptions().centerCrop
//                    ()).into(mIvCover);
        } else if (!UserUtil.isCurrentUser(mUser.getUserId())){
            mIvCover.setImageResource(R.mipmap.ic_default_pic);
            mLayout.setBackgroundResource(R.drawable.ic_bg);
        }

        if (mList != null && mList.size() > 0) {
            mTvEmpty.setVisibility(View.GONE);
            mRvMoments.setVisibility(View.VISIBLE);
            mMomentsAdapter.setList(mList);
            mWrapAdapter.notifyDataSetChanged();
        } else {
            mTvEmpty.setVisibility(View.VISIBLE);
            mRvMoments.setVisibility(View.INVISIBLE);
            mMomentsAdapter.setList(mList);
            mWrapAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 设置封面图及背景
     * @param iv
     * @param view
     * @param imgUrl
     * @param options
     * @param defaultImg
     * @param defaultBg
     */
    private void setPaletteImage(final ImageView iv, final ViewGroup view, String
            imgUrl, RequestOptions options, final int defaultImg, final int defaultBg) {
        final int[] colors = {0xff689f38, 0xff7cb342, 0xfff44336};
        Glide.with(this).asBitmap().load(imgUrl).apply(options).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap>
                    transition) {
                iv.setImageBitmap(resource);
                Palette.Builder pb = Palette.from(resource);
                pb.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        colors[0] = palette.getVibrantColor(0xff689f38);
                        colors[2] = palette.getDarkMutedColor(0xfff44336);

                        Drawable bgDrawable = new GradientDrawable(GradientDrawable
                                .Orientation.LEFT_RIGHT, colors);
                        view.setBackground(bgDrawable);
                    }
                });
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                iv.setImageResource(defaultImg);
                view.setBackgroundResource(defaultBg);
            }
        });
    }

    @Override
    public void onRefresh() {
        mUpdateTime = "";
        getData(mUpdateTime);
    }

    @Override
    public void onLoadMore() {
        if (mRvMoments.isLoadSuccess()) { // 若上次失败页码不再变化
            if (mList != null && mList.size() > 0) {
                mUpdateTime = mList.get(mList.size() - 1).getUpdateTime();
            }
        }
        getData(mUpdateTime);
    }

    @Override
    public void onItemClick(View view, int position) {
        int type = mMomentsAdapter.getItemViewType(position);
        NoteInfo noteInfo = mList.get(position);
        if (type == CodeConstants.TYPE_DIARY) {
            Intent intent = new Intent(this, DiaryDetailActivity.class);
            intent.putExtra(DiaryDetailActivity.KEY_DIARY, noteInfo);
            intent.putExtra(DiaryDetailActivity.KEY_ONLY_READ, true);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, view, view.getTransitionName());
            startActivityForResult(intent, CodeConstants.REQUEST_DETAIL, options
                    .toBundle());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case -1:
                finish();
                break;
            case R.id.iv_cover://更换主页封面图
                if (UserUtil.isCurrentUser(mUser.getUserId())) {//已登录当前用户才可更换
                    if (UserUtil.isLogin()) {
                        showPicDialog(mIvCover, GlobalUtil.getDefaultOptions().centerCrop());
                        setOnChoosedListener(new OnChoosedListener() {
                            @Override
                            public void onChoosed(Uri localUploadUri) {
                                uploadImg(localUploadUri);
                            }

                            @Override
                            public void onCanceled() {
                            }
                        });
                    } else {
                        ActivityUtil.startLoginActivity(this);
                    }
                }
                break;
        }
    }

    /**
     * 上传封面
     */
    private void uploadImg(final Uri localPic) {
        showProgress(true);

        final String objectKey = OssUtil.getImageObjectKey(UserUtil.getCurrentUserId(),
                String.valueOf(UserUtil.getCurrentUserId() + "-cover-" + System
                        .currentTimeMillis()));

        new OssRequest().upload(objectKey, GlobalUtil.getByte(localPic), new OssRequest
                .OssOperateListener() {

            @Override
            public void onSuccess(String objectKey, String objectUrl) {
                showProgress(false);
                mCoverKey = objectKey;
                editCoverUrl(objectKey);
            }

            @Override
            public void onFailure(String objectKey, String errMsg) {
                StringBuilder tips = new StringBuilder(getString(R.string
                        .str_upload_cover_failed) + ",");
                tips.append(errMsg);
                GlobalUtil.makeToast(tips.toString());
            }
        });
    }

    /**
     * 修改服务端封面地址
     * @param objectKey
     */
    private void editCoverUrl(String objectKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("coverUrl", objectKey);
        requestSubmit(UrlConstants.COVER_EDIT_URL, map, CodeConstants
                .REQUEST_EDIT_COVER);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == CodeConstants.REQUEST_EDIT_COVER) {
            UserUtil.saveCover(mCoverKey);
            mUser.setCoverUrl(mCoverKey);
        }
    }
}
