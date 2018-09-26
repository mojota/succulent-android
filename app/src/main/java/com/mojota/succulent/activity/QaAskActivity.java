package com.mojota.succulent.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.model.ResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.AppLog;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 提问
 * Created by mojota on 18-08-21.
 */

public class QaAskActivity extends PhotoChooseSupportActivity implements View.OnClickListener {
    private static final String TAG = "QaAskActivity";
    private EditText mEtQuestion;
    private InputMethodManager mInputManager;
    private TextView mTvLength;
    private Button mBtClose;
    private ImageButton mIbtPic;
    private Button mBtCommit;
    private TextInputLayout mTiQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa_ask);
        initView();
    }

    private void initView() {
        mBtClose = findViewById(R.id.bt_close);
        mBtClose.setOnClickListener(this);
        mTiQuestion = findViewById(R.id.ti_question);
        mEtQuestion = (EditText) findViewById(R.id.et_question);
        mTvLength = (TextView) findViewById(R.id.tv_length);
        mEtQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String countDes = "" + s.length() + "/100";
                mTvLength.setText(countDes);
                if (s.length() == 0) {
                    mTvLength.setTextColor(Color.parseColor("#999999"));
                } else if (s.length() >= 100) {
                    mTvLength.setTextColor(Color.parseColor("#FF3C00"));
                } else {
                    mTvLength.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIbtPic = findViewById(R.id.ibt_pic);
        mIbtPic.setOnClickListener(this);
        mBtCommit = findViewById(R.id.bt_commit);
        mBtCommit.setOnClickListener(this);
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onBackPressed() {
        // 禁用返回键
        // super.onBackPressed();
    }


    /**
     * 提交问题
     */
    private void submitQuestion(String questionStr) {
        String picUrls = "";
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("questionTitle", questionStr);
        map.put("questionPicUrl", picUrls);
        loadingRequestSubmit(UrlConstants.QA_ADD_URL, map, CodeConstants.REQUEST_QA_ADD);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        closeKeyboard();
        setResult(CodeConstants.RESULT_QA);
        finish();
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyboard() {
        mInputManager.hideSoftInputFromWindow(mEtQuestion.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_close:
                finish();
                break;
            case R.id.ibt_pic:
                showPicDialog(mIbtPic, null);
                break;
            case R.id.bt_commit:
                if (!TextUtils.isEmpty(mEtQuestion.getText())) {
                    String questionStr = mEtQuestion.getText().toString();
                    questionStr = questionStr.replaceAll("\n+", "\n"); // 多行换行最多显示一行
                    if (!TextUtils.isEmpty(questionStr.trim())) {
                        submitQuestion(questionStr);
                    }
                } else {
                    mTiQuestion.setError("问题不可以为空");
                }
                break;
        }
    }
}
