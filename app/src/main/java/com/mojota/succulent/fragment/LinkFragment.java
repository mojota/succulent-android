package com.mojota.succulent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.LinkAdapter;
import com.mojota.succulent.adapter.OnItemClickListener;
import com.mojota.succulent.model.LinkInfo;
import com.mojota.succulent.model.LinkResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 攻略
 * Created by mojota on 18-8-16
 */
public class LinkFragment extends Fragment implements OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRvLink;
    private LinkAdapter mLinkAdapter;
    private List<LinkInfo> mList = new ArrayList<LinkInfo>();

    public LinkFragment() {
    }

    public static LinkFragment newInstance() {
        LinkFragment fragment = new LinkFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link, container, false);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(this);
        mRvLink = view.findViewById(R.id.rv_link);
        mLinkAdapter = new LinkAdapter(mList, this);
        mRvLink.setAdapter(mLinkAdapter);

        getData();
        return view;
    }

    private void getData() {

        mSwipeRefresh.setRefreshing(false);
        LinkResponseInfo resInfo = new Gson().fromJson(TestUtil.getLinkList(),
                LinkResponseInfo.class);
        mList = resInfo.getList();

        setDataToView();
    }


    private void setDataToView() {
        mLinkAdapter.setList(mList);
        mLinkAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        getData();
    }
}
