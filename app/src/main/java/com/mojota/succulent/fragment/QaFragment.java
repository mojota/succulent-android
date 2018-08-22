package com.mojota.succulent.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.activity.QaAskActivity;
import com.mojota.succulent.activity.QaDetailActivity;
import com.mojota.succulent.adapter.OnItemClickListener;
import com.mojota.succulent.adapter.QaAdapter;
import com.mojota.succulent.model.QaResponseInfo;
import com.mojota.succulent.model.QuestionInfo;
import com.mojota.succulent.utils.CodeConstants;

import java.util.List;

/**
 * 问答
 * Created by mojota on 18-8-21
 */
public class QaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRvQA;
    private FloatingActionButton mFabAsk;
    private QaAdapter mQaAdapter;
    private List<QuestionInfo> mList;


    public QaFragment() {
        // Required empty public constructor
    }

    public static QaFragment newInstance() {
        QaFragment fragment = new QaFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qa, container, false);

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(this);
        mRvQA = view.findViewById(R.id.rv_qa);
        mQaAdapter = new QaAdapter(mList, this);
        mRvQA.setAdapter(mQaAdapter);
        mFabAsk = view.findViewById(R.id.fab_ask);
        mFabAsk.setOnClickListener(this);

        getData();

        return view;
    }

    private void getData() {

        mSwipeRefresh.setRefreshing(false);
        QaResponseInfo resInfo = new Gson().fromJson(TestUtil.getQaList(),
                QaResponseInfo.class);
        mList = resInfo.getQaList();

        setDataToView();
    }


    private void setDataToView() {
        mQaAdapter.setList(mList);
        mQaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_ask:
                Intent intent = new Intent(getActivity(), QaAskActivity.class);
                startActivityForResult(intent, CodeConstants.REQUEST_QA_ASK);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), QaDetailActivity.class);
        QuestionInfo questionInfo = mList.get(position);
        intent.putExtra(QaDetailActivity.KEY_QA, questionInfo);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (getActivity(), view, view.getTransitionName());
        startActivityForResult(intent, CodeConstants.REQUEST_QA_DETAIL, options.toBundle());

    }
}
