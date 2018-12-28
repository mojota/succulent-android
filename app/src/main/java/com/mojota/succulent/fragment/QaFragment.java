package com.mojota.succulent.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.activity.QaAskActivity;
import com.mojota.succulent.activity.QaDetailActivity;
import com.mojota.succulent.adapter.QaAdapter;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.QaResponseInfo;
import com.mojota.succulent.model.QuestionInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;
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
 * 问答
 * Created by mojota on 18-8-21
 */
public class QaFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View
        .OnClickListener, OnItemClickListener, LoadMoreRecyclerView.OnLoadListener,QaAdapter.OnItemDeleteListener {
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
    private TextView mTvEmpty;
    private LoadingView mLoading;


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
        mQaAdapter.setOnItemDeleteListener(this);
        mWrapAdapter = new WrapRecycleAdapter(mQaAdapter);
        mRvQA.setAdapter(mWrapAdapter);
        mRvQA.setOnLoadListener(this);
        mFabAsk = view.findViewById(R.id.fab_ask);
        mFabAsk.setOnClickListener(this);
        mTvEmpty = view.findViewById(R.id.tv_empty);
        mLoading = view.findViewById(R.id.loading);

        onRefresh();

        return view;
    }

    private void getData(final String questionTime) {
        if (mList == null || mList.size() <= 0) {
            mLoading.show(true);
        }
        String url = UrlConstants.GET_QA_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("size", String.valueOf(PAGE_SIZE));
        paramMap.put("questionTime", questionTime);

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap, QaResponseInfo.class,
                new Response.Listener<QaResponseInfo>() {

            @Override
            public void onResponse(QaResponseInfo responseInfo) {
                mLoading.show(false);
                mSwipeRefresh.setRefreshing(false);
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    if (TextUtils.isEmpty(questionTime)) {
                        mList.clear();
                    }
                    List<QuestionInfo> list = responseInfo.getList();
                    mList.addAll(list);
                    mRvQA.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvQA.loadMoreFailed();
//                    GlobalUtil.makeToast(R.string.str_no_data);
                }
                mTvEmpty.setText(R.string.str_qa_empty);
                setDataToView();
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mLoading.show(false);
                mSwipeRefresh.setRefreshing(false);
                mRvQA.loadMoreFailed();
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
            mRvQA.setVisibility(View.VISIBLE);
            mQaAdapter.setList(mList);
            mWrapAdapter.notifyDataSetChanged();
        } else {
            mTvEmpty.setVisibility(View.VISIBLE);
            mRvQA.setVisibility(View.INVISIBLE);
            mQaAdapter.setList(mList);
            mWrapAdapter.notifyDataSetChanged();
        }
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
                if (!UserUtil.isLogin()) {
                    ActivityUtil.startLoginActivity(getActivity());
                } else {
                    Intent intent = new Intent(getActivity(), QaAskActivity.class);
                    startActivityForResult(intent, CodeConstants.REQUEST_QA_ASK);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), QaDetailActivity.class);
        QuestionInfo questionInfo = mList.get(position);
        intent.putExtra(QaDetailActivity.KEY_QA, questionInfo);
        intent.putExtra(QaDetailActivity.KEY_ITEM_POS, position);
        Bundle bundle = null;
        if (!TextUtils.isEmpty(questionInfo.getQuestionPicUrl())) {
            bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    view, view.getTransitionName()).toBundle();
        }
        startActivityForResult(intent, CodeConstants.REQUEST_QA_DETAIL, bundle);
    }

    @Override
    public void onDelete(final QuestionInfo question, final int position) {
        mList.remove(position);
        mWrapAdapter.notifyItemRemoved(position);
        mWrapAdapter.notifyItemRangeChanged(0, mList.size());
        Snackbar.make(mRvQA, "已删除一个问题", Snackbar.LENGTH_LONG).setAction(R.string.str_undo,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.add(position, question);
                        mWrapAdapter.notifyItemInserted(position);
                        mWrapAdapter.notifyItemRangeChanged(0, mList.size());
                    }
                }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                switch (event) {
                    case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                    case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                    case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                    case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                        requestDelete(question);
                        break;
                    case Snackbar.Callback.DISMISS_EVENT_ACTION:
                        break;
                }
            }
        }).show();
    }

    /**
     * 请求网络删除
     */
    private void requestDelete(QuestionInfo question) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("questionId", question.getQuestionId());

        RequestUtils.commonRequest(UrlConstants.QUESTION_DELETE_URL, map, CodeConstants
                .REQUEST_QUESTION_DELETE, null);
    }
}
