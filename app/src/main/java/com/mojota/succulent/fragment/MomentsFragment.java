package com.mojota.succulent.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.MomentsAdapter;
import com.mojota.succulent.adapter.OnItemClickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.NoteResponseInfo;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 邻家肉园
 * Created by mojota on 18-7-23
 */
public class MomentsFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener, OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRvMoments;
    private MomentsAdapter mMomentsAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();


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
        mMomentsAdapter = new MomentsAdapter(mList, this);
        mRvMoments.setAdapter(mMomentsAdapter);

        getData();
        return view;
    }

    private void getData() {
        mSwipeRefresh.setRefreshing(false);
        NoteResponseInfo resInfo = new Gson().fromJson(TestUtil.getMomentlist(),
                NoteResponseInfo.class);
        mList = resInfo.getList();

        setDataToView();
    }


    private void setDataToView() {
        mMomentsAdapter.setList(mList);
        mMomentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onItemClick(View view, int position) {
        int type = mMomentsAdapter.getItemViewType(position);
        NoteInfo noteInfo = mList.get(position);
        if (type == CodeConstants.TYPE_DIARY) {
            ActivityUtil.startDiaryDetailActivity(getActivity(), view, noteInfo);
        }
    }
}
