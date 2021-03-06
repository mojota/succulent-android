package com.mojota.succulent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.activity.DiaryDetailActivity;
import com.mojota.succulent.adapter.MomentsAdapter;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.NoteResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
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
 * 多肉时光
 * Created by 王静 on 18-7-23
 */
public class MomentsFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener, OnItemClickListener, LoadMoreRecyclerView.OnLoadListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PAGE_SIZE = 10; // 每页的条数

    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefresh;
    private LoadMoreRecyclerView mRvMoments;
    private MomentsAdapter mMomentsAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();
    private String mUpdateTime = "";
    private WrapRecycleAdapter mWrapAdapter;
    private TextView mTvEmpty;
    private LoadingView mLoading;


    public MomentsFragment() {
        // Required empty public constructor
    }

    public static MomentsFragment newInstance(String param1, String param2) {
        MomentsFragment fragment = new MomentsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moments, container, false);

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(this);
        mRvMoments = view.findViewById(R.id.rv_moments);
        mMomentsAdapter = new MomentsAdapter(mList, true, this);
        mWrapAdapter = new WrapRecycleAdapter(mMomentsAdapter);
        mRvMoments.setAdapter(mWrapAdapter);
        mRvMoments.setOnLoadListener(this);
        mTvEmpty = view.findViewById(R.id.tv_empty);
        mLoading = view.findViewById(R.id.loading);

        getData(mUpdateTime);
        return view;
    }

    private void getData(final String updateTime) {
        if (mList == null || mList.size() <= 0) {
            mLoading.show(true);
        }
        String url = UrlConstants.GET_MOMENTS_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("loginUserId", UserUtil.getCurrentUserId());
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
                mTvEmpty.setText(R.string.str_moments_empty);
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
            Intent intent = new Intent(getActivity(), DiaryDetailActivity.class);
            intent.putExtra(DiaryDetailActivity.KEY_DIARY, noteInfo);
            intent.putExtra(DiaryDetailActivity.KEY_ONLY_READ, true);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), view, view
                            .getTransitionName());
            startActivityForResult(intent, CodeConstants.REQUEST_DETAIL, options
                    .toBundle());
        }
    }

}
