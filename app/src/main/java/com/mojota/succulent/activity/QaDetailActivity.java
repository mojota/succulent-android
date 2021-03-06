package com.mojota.succulent.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.mojota.succulent.R;
import com.mojota.succulent.adapter.AnswerAdapter;
import com.mojota.succulent.model.AnswerInfo;
import com.mojota.succulent.model.AnswerResponseInfo;
import com.mojota.succulent.model.QuestionInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.ActivityUtil;
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
 * 问答详情页
 * Created by 王静 on 18-08-22.
 */

public class QaDetailActivity extends BaseActivity implements View.OnClickListener,
        LoadMoreRecyclerView.OnLoadListener, AnswerAdapter.OnItemDeleteListener {

    private static final String TAG = "DisQaDetailActivity";
    public static final String KEY_QA = "KEY_QA";
    public static final String KEY_ITEM_POS = "KEY_ITEM_POS";
    private static final int PAGE_SIZE = 10; // 每页的条数
    private Activity mActivity;
    private EditText mEtAnswer;
    private Button mBtSubmit;
    private LoadMoreRecyclerView mRvAnswer;
    private List<AnswerInfo> mAnswerList = new ArrayList<AnswerInfo>();
    private ImageView mIvAvatar;
    private TextView mTvNickname;
    private ViewGroup mLayoutBar;
    private TextView mTvQuestionTitle;
    private TextView mTvTime;
    private QuestionInfo mQuestion;
    private String mQuestionId;
    private AnswerAdapter mAnswerAdapter;
    private InputMethodManager mInputManager;
    private Toolbar mToolBar;
    private TextView mTvAnswerCount;
    private ImageView mIvPic;
    private ArrayList<String> mPicUrls = new ArrayList<String>();
    private WrapRecycleAdapter mWrapAdapter;
    private String mAnswerTime = "";
    private ViewGroup.LayoutParams mIvPicParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa_detail);
        mActivity = this;
        mQuestion = (QuestionInfo) getIntent().getSerializableExtra(KEY_QA);
        if (mQuestion != null) {
            mQuestionId = mQuestion.getQuestionId();
        } else {
            mQuestionId = getIntent().getStringExtra("questionId");
        }
        initView();
        setDataToView();
        refresh();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle(R.string.str_qa_detail);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(this);

        // 提交回答
        mEtAnswer = (EditText) findViewById(R.id.et_answer);
        mEtAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3000) {
                    GlobalUtil.makeToast(R.string.str_answer_length_tip);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mBtSubmit.setOnClickListener(this);

        // 问题

        mLayoutBar = findViewById(R.id.layout_bar);
        mLayoutBar.setOnClickListener(this);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mTvNickname = (TextView) findViewById(R.id.tv_nickname);
        mTvQuestionTitle = (TextView) findViewById(R.id.tv_question_title);
        mIvPic = findViewById(R.id.iv_pic);
        mIvPicParams = mIvPic.getLayoutParams();
        mIvPic.setOnClickListener(this);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvAnswerCount = (TextView) findViewById(R.id.tv_answer_count);

        // 回答列表
        mRvAnswer = findViewById(R.id.rv_answer);
        mAnswerAdapter = new AnswerAdapter(mActivity, mAnswerList);
        mAnswerAdapter.setOnItemDeleteListener(this);
        mWrapAdapter = new WrapRecycleAdapter(mAnswerAdapter);
        mRvAnswer.setAdapter(mWrapAdapter);
        mRvAnswer.setOnLoadListener(this);

        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    /**
     * 获取数据
     */
    private void getData(final String answerTime) {
        String url = UrlConstants.GET_ANSWER_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("size", String.valueOf(PAGE_SIZE));
        paramMap.put("userId", UserUtil.getCurrentUserId());
        paramMap.put("questionId", mQuestionId);
        paramMap.put("answerTime", answerTime);

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap, AnswerResponseInfo
                .class, new Response.Listener<AnswerResponseInfo>() {

            @Override
            public void onResponse(AnswerResponseInfo responseInfo) {
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    if (TextUtils.isEmpty(answerTime)) {
                        mAnswerList.clear();
                    }
                    List<AnswerInfo> list = responseInfo.getList();
                    mAnswerList.addAll(list);
                    mRvAnswer.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvAnswer.loadMoreFailed();
//                    GlobalUtil.makeToast(R.string.str_no_data);
                }
                setDataToView();
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mRvAnswer.loadMoreFailed();
                GlobalUtil.makeToast(R.string.str_network_error);
                setDataToView();
            }
        }));
        VolleyUtil.execute(request);
    }


    /**
     * 提交回答
     */
    private void submitAnswer(String answerStr) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("questionId", mQuestionId);
        map.put("answerContent", answerStr);
        loadingRequestSubmit(UrlConstants.ANSWER_ADD_URL, map, CodeConstants.REQUEST_ANSWER_ADD);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == CodeConstants.REQUEST_ANSWER_ADD) {
            mEtAnswer.setText("");
            mQuestion.setAnswerCount(mQuestion.getAnswerCount() + 1);
            refresh();
        } else if (requestCode == CodeConstants.REQUEST_ANSWER_DELETE) {
            mQuestion.setAnswerCount(mQuestion.getAnswerCount() - 1);
            setDataToView();
        }
        Intent intent = new Intent();
        intent.putExtra(QaDetailActivity.KEY_QA, mQuestion);
        intent.putExtra(KEY_ITEM_POS, getIntent().getIntExtra(KEY_ITEM_POS, 0));
        setResult(CodeConstants.RESULT_QA, intent);
    }

    public void refresh() {
        mAnswerTime = "";
        getData(mAnswerTime);

    }

    @Override
    public void onLoadMore() {
        if (mRvAnswer.isLoadSuccess()) { // 若上次失败页码不再变化
            if (mAnswerList != null && mAnswerList.size() > 0) {
                mAnswerTime = mAnswerList.get(mAnswerList.size() - 1).getAnswerTime();
            }
        }
        getData(mAnswerTime);
    }


    private void setDataToView() {
        if (ActivityUtil.isDead(mActivity)){
            return;
        }
        // 问题
        if (mQuestion != null) {
            String avatar = OssUtil.getWholeImageUrl(mQuestion.getUserInfo().getAvatarUrl());
            Glide.with(mActivity).load(avatar).apply(GlobalUtil.getDefaultAvatarOptions()).into
                    (mIvAvatar);
            mTvNickname.setText(UserUtil.getDisplayName(mQuestion.getUserInfo()));
            if (!TextUtils.isEmpty(mQuestion.getQuestionTitle())) {
                mTvQuestionTitle.setText(mQuestion.getQuestionTitle().replaceAll("\n+", "\n"));
            } else {
                mTvQuestionTitle.setText("");
            }
            mTvTime.setText(GlobalUtil.formatDisplayTime(mQuestion.getQuestionTime()));
            mTvAnswerCount.setText(String.valueOf(mQuestion.getAnswerCount()));

            if (!TextUtils.isEmpty(mQuestion.getQuestionPicUrl())) {
                mIvPicParams.height = getResources().getDimensionPixelSize(R.dimen
                        .di_header_max);
                mIvPic.setLayoutParams(mIvPicParams);
                mPicUrls.clear();
                mPicUrls.add(mQuestion.getQuestionPicUrl());
                Glide.with(mActivity).load(OssUtil.getWholeImageUrl(mQuestion.getQuestionPicUrl())).apply(GlobalUtil
                        .getDefaultOptions()).into(mIvPic);
            } else {
                mIvPicParams.height = getResources().getDimensionPixelSize(R.dimen
                        .di_header_default);
                mIvPic.setLayoutParams(mIvPicParams);
            }
        }

        // 回答列表
        if (mAnswerList != null && mAnswerList.size() > 0) {
            mRvAnswer.setVisibility(View.VISIBLE);
            mAnswerAdapter.setList(mAnswerList);
            mWrapAdapter.notifyDataSetChanged();
        } else {
            mRvAnswer.setVisibility(View.INVISIBLE);
            mAnswerAdapter.setList(mAnswerList);
            mWrapAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case -1:
                supportFinishAfterTransition();
                break;
            case R.id.bt_submit:
                if (!UserUtil.isLogin()) {
                    ActivityUtil.startLoginActivity(this);
                } else {
                    if (!TextUtils.isEmpty(mEtAnswer.getText())) {
                        String answerStr = mEtAnswer.getText().toString();
                        answerStr = answerStr.replaceAll("\n+", "\n"); // 多行换行最多显示一行
                        if (!TextUtils.isEmpty(answerStr.trim())) {
                            submitAnswer(answerStr);
                        }
                    }
                }
                closeKeyboard();
                break;
            case R.id.iv_pic:
                ActivityUtil.startImageBrowserActivity(this, mIvPic, "", mPicUrls, 0);
                break;
            case R.id.layout_bar:
                if (mQuestion != null && mQuestion.getUserInfo() != null) {
                    ActivityUtil.startUserMomentsActivity(mQuestion.getUserInfo());
                }
                break;
        }
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyboard() {
        mInputManager.hideSoftInputFromWindow(mEtAnswer.getWindowToken(), 0);
    }

    @Override
    public void onDelete(final AnswerInfo answer, final int position) {
        mAnswerList.remove(position);
        mWrapAdapter.notifyItemRemoved(position);
        mWrapAdapter.notifyItemRangeChanged(0, mAnswerList.size());
        Snackbar.make(mRvAnswer, R.string.str_answer_deleted, Snackbar.LENGTH_LONG).setAction(R.string.str_undo,
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerList.add(position, answer);
                mWrapAdapter.notifyItemInserted(position);
                mWrapAdapter.notifyItemRangeChanged(0, mAnswerList.size());
            }
        }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                switch (event) {
                    case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                    case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                    case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                    case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                        requestDelete(answer);
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
    private void requestDelete(AnswerInfo answer) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("answerId", answer.getAnswerId());
        map.put("questionId", mQuestionId);
        requestSubmit(UrlConstants.ANSWER_DELETE_URL, map, CodeConstants
                .REQUEST_ANSWER_DELETE);
    }


}
