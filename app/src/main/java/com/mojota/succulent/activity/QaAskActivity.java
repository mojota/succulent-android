package com.mojota.succulent.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.network.OssRequest;
import com.mojota.succulent.network.OssUtil;
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
    private ImageView mIbtPic;
    private Button mBtCommit;
    private TextInputLayout mTiQuestion;
    private Uri[] mLocalPics = new Uri[1];
    private String[] mUploadPicKeys = new String[1];
    private OssRequest mOssRequest;
    private RequestOptions mRequestOptions;

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

        mRequestOptions = GlobalUtil.getRoundedCornersOptions();
        mOssRequest = new OssRequest();
    }

    @Override
    public void onBackPressed() {
        // 禁用返回键
        // super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_close:
                finish();
                break;
            case R.id.ibt_pic:
                showPicDialog(mIbtPic, null);
                setOnChoosedListener(new OnChoosedListener() {
                    @Override
                    public void onChoosed(Uri localUploadUri) {
                        mLocalPics[0] = localUploadUri;
                        mUploadPicKeys[0] = ""; // 一旦选过图就置key为空,上传后再写入
                    }

                    @Override
                    public void onCanceled() {

                    }
                });
                break;
            case R.id.bt_commit:
                if (TextUtils.isEmpty(mEtQuestion.getText())) {
                    mTiQuestion.setError(getString(R.string.str_question_empty));
                } else {
                    commit();
                }
                break;
        }
    }

    /**
     * 上传图片后再上传文字
     */
    private void commit() {
        boolean hasPic = false;
        // 已上传过有地址的不再重复上传
        for (int i = 0; i < mLocalPics.length; i++) {
            if (mLocalPics[i] != null && TextUtils.isEmpty(mUploadPicKeys[i])) {
                hasPic = true;
                break;
            }
        }

        // 如果有图片，则上传图片后提交，如无图片直接提交
        if (hasPic) {
            uploadImg(mLocalPics, 0);
        } else {
            submitQuestion();
        }
    }


    /**
     * 顺序上传图片,最后一张结束后提交所有内容到服务器
     *
     * @param localPics 本地准备上传的uri列表
     * @param index     外部调用由0开始
     */
    private void uploadImg(final Uri[] localPics, final int index) {
        showProgress(true);
        final String objectKey = OssUtil.getImageObjectKey(UserUtil.getCurrentUserId(),
                String.valueOf(System.currentTimeMillis()) + "-" + index);
        mOssRequest.upload(objectKey, GlobalUtil.getByte(localPics[index]), new
                OssRequest.OssOperateListener() {

            @Override
            public void onSuccess(String objectKey, String objectUrl) {
                mUploadPicKeys[index] = objectKey;
                if (index == localPics.length - 1) {
                    showProgress(false);
                    submitQuestion();
                } else {
                    uploadImg(localPics, index + 1);
                }
            }

            @Override
            public void onFailure(String objectKey, String errMsg) {
                if (localPics[index] != null) {
                    StringBuilder tips = new StringBuilder(getString(R.string.str_upload_pic));
                    tips.append((index + 1) + getString(R.string.str_upload_pic_failed) + errMsg);
                    GlobalUtil.makeToast(tips.toString());
                }
                if (index == localPics.length - 1) {
                    showProgress(false);
                    submitQuestion();
                } else {
                    uploadImg(localPics, index + 1);
                }
            }
        });
    }

    /**
     * 提交问题
     */
    private void submitQuestion() {
        String questionStr = mEtQuestion.getText().toString();
        questionStr = questionStr.replaceAll("\n+", "\n"); // 多行换行最多显示一行
        String picUrls = mUploadPicKeys[0];
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

}
