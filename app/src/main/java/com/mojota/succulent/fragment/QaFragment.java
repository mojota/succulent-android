package com.mojota.succulent.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.activity.QaAskActivity;
import com.mojota.succulent.activity.QaDetailActivity;
import com.mojota.succulent.adapter.OnItemClickListener;
import com.mojota.succulent.adapter.QaAdapter;
import com.mojota.succulent.model.QaResponseInfo;
import com.mojota.succulent.model.QuestionInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;
import com.mojota.succulent.view.LoadMoreRecyclerView;
import com.mojota.succulent.view.WrapRecycleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问答
 * Created by mojota on 18-8-21
 */
public class QaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View
        .OnClickListener, OnItemClickListener, LoadMoreRecyclerView.OnLoadListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PAGE_SIZE = 10; // 每页的条数

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefresh;
    private LoadMoreRecyclerView mRvQA;
    private FloatingActionButton mFabAsk;
    private QaAdapter mQaAdapter;
    private List<QuestionInfo> mList = new ArrayList<QuestionInfo>();
    private String mQuestionTime = "";
    private WrapRecycleAdapter mWrapAdapter;


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
        mWrapAdapter = new WrapRecycleAdapter(mQaAdapter);
        mRvQA.setAdapter(mWrapAdapter);
        mRvQA.setOnLoadListener(this);
        mFabAsk = view.findViewById(R.id.fab_ask);
        mFabAsk.setOnClickListener(this);

        onRefresh();

        return view;
    }

    private void getData(final String questionTime) {
        String url = UrlConstants.GET_QA_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("size", String.valueOf(PAGE_SIZE));
        paramMap.put("questionTime", questionTime);

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap, QaResponseInfo.class,
                new Response.Listener<QaResponseInfo>() {

            @Override
            public void onResponse(QaResponseInfo responseInfo) {
                mSwipeRefresh.setRefreshing(false);
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    if (TextUtils.isEmpty(questionTime)) {
                        mList.clear();
                    }
                    List<QuestionInfo> list = responseInfo.getList();
                    mList.addAll(list);
                    setDataToView();
                    mRvQA.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvQA.loadMoreFailed();
                    GlobalUtil.makeToast(R.string.str_no_data);
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mSwipeRefresh.setRefreshing(false);
                mRvQA.loadMoreFailed();
                GlobalUtil.makeToast(R.string.str_network_error);
            }
        }));
        VolleyUtil.execute(request);
    }


    private void setDataToView() {
        mQaAdapter.setList(mList);
        mWrapAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mQuestionTime = "";
        getData(mQuestionTime);
    }

    @Override
    public void onLoadMore() {
        if (mRvQA.isLoadSuccess()) { // 若上次失败页码不再变化
            if (mList != null && mList.size() > 0) {
                mQuestionTime = mList.get(mList.size() - 1).getQuestionTime();
            }
        }
        getData(mQuestionTime);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CodeConstants.REQUEST_QA_ASK:
                if (resultCode == CodeConstants.RESULT_QA) {
                    onRefresh();
                    mRvQA.smoothScrollToPosition(0);
                }
                break;
            case CodeConstants.REQUEST_QA_DETAIL:
                if (resultCode == CodeConstants.RESULT_QA) {
                    int pos = data.getIntExtra(QaDetailActivity.KEY_ITEM_POS, 0);
                    QuestionInfo question = (QuestionInfo) data.getSerializableExtra
                            (QaDetailActivity.KEY_QA);

                    mList.remove(pos);
                    mList.add(pos, question);
                    mWrapAdapter.notifyItemChanged(pos);
                    mWrapAdapter.notifyItemRangeChanged(0, mList.size());
                }
                break;
        }
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
        intent.putExtra(QaDetailActivity.KEY_ITEM_POS, position);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (getActivity(), view, view.getTransitionName());
        startActivityForResult(intent, CodeConstants.REQUEST_QA_DETAIL, options.toBundle());

    }

}
