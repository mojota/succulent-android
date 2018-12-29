package com.mojota.succulent.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.adapter.NoticeAdapter;
import com.mojota.succulent.model.NoticeInfo;
import com.mojota.succulent.model.NoticeResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.CommonUtil;
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
 * 通知消息
 * Created by mojota on 18-12-25
 */
public class NoticesActivity extends BaseActivity implements LoadMoreRecyclerView
        .OnLoadListener {
    private static final int PAGE_SIZE = 10; // 每页的条数

    private Toolbar mToolbar;
    private LoadMoreRecyclerView mRvNotices;
    private List<NoticeInfo> mList = new ArrayList<NoticeInfo>();
    private NoticeAdapter mNoticeAdapter;
    private WrapRecycleAdapter mWrapAdapter;
    private String mNoticeTime = "";
    private LoadingView mLoading;
    private TextView mTvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoading = findViewById(R.id.loading);
        mTvEmpty = findViewById(R.id.tv_empty);

        mRvNotices = findViewById(R.id.rv_notices);
        mNoticeAdapter = new NoticeAdapter(mList);
        mWrapAdapter = new WrapRecycleAdapter(mNoticeAdapter);
        mRvNotices.setAdapter(mWrapAdapter);
        mRvNotices.setOnLoadListener(this);

        getData("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(final String noticeTime) {
        if (mList == null || mList.size() <= 0) {
            mLoading.show(true);
        }
        String url = UrlConstants.GET_NOTICE_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userId", UserUtil.getCurrentUserId());
        paramMap.put("size", String.valueOf(PAGE_SIZE));
        paramMap.put("noticeTime", noticeTime);

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap,
                NoticeResponseInfo.class, new Response.Listener<NoticeResponseInfo>() {

            @Override
            public void onResponse(NoticeResponseInfo responseInfo) {
                mLoading.show(false);
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    if (TextUtils.isEmpty(noticeTime)) {
                        mList.clear();
                    }
                    List<NoticeInfo> list = responseInfo.getList();
                    mList.addAll(list);
                    mRvNotices.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvNotices.loadMoreFailed();
//                    GlobalUtil.makeToast(R.string.str_no_data);
                }
                setDataToView();

            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mLoading.show(false);
                mRvNotices.loadMoreFailed();
                GlobalUtil.makeToast(R.string.str_network_error);
                setDataToView();
            }
        }));
        VolleyUtil.execute(request);
    }

    private void setDataToView() {
        if (mList != null && mList.size() > 0) {
            // 保存最新通知时间
            CommonUtil.setLatestNoticeTime(mList.get(0).getNoticeTime());
            mTvEmpty.setVisibility(View.GONE);
            mRvNotices.setVisibility(View.VISIBLE);
            mNoticeAdapter.setList(mList);
            mWrapAdapter.notifyDataSetChanged();
        } else {
            mTvEmpty.setVisibility(View.VISIBLE);
            mRvNotices.setVisibility(View.INVISIBLE);
            mNoticeAdapter.setList(mList);
            mWrapAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadMore() {
        if (mRvNotices.isLoadSuccess()) { // 若上次失败页码不再变化
            if (mList != null && mList.size() > 0) {
                mNoticeTime = mList.get(mList.size() - 1).getNoticeTime();
            }
        }
        getData(mNoticeTime);
    }
}
