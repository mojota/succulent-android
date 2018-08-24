package com.mojota.succulent.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.mojota.succulent.R;
import com.mojota.succulent.model.DiaryDetail;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;

/**
 * 添加笔记
 * Created by mojota on 18-8-2
 */
public class DiaryAddActivity extends PhotoChooseSupportActivity implements View
        .OnClickListener {

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_DIARY = "KEY_DIARY";

    private Button mBtClose;
    private TextInputLayout mTiTitle;
    private EditText mEtTitle;
    private TextInputLayout mTiBody;
    private EditText mEtBody;
    private ImageButton mIbtPic1;
    private ImageButton mIbtPic2;
    private Button mBtCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        mBtClose = findViewById(R.id.bt_close);
        mBtClose.setOnClickListener(this);
        mTiTitle = findViewById(R.id.ti_title);
        mTiBody = findViewById(R.id.ti_body);
        mEtTitle = findViewById(R.id.et_title);
        mEtBody = findViewById(R.id.et_body);
        mIbtPic1 = findViewById(R.id.ibt_pic1);
        mIbtPic1.setOnClickListener(this);
        mIbtPic2 = findViewById(R.id.ibt_pic2);
        mIbtPic2.setOnClickListener(this);
        mBtCommit = findViewById(R.id.bt_commit);
        mBtCommit.setOnClickListener(this);

        String title = getIntent().getStringExtra(KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            mEtTitle.setText(title);
            mEtTitle.setEnabled(false);
            mTiTitle.setHintEnabled(false);
        }

        DiaryDetail diary = (DiaryDetail) getIntent().getSerializableExtra(KEY_DIARY);
        if (diary != null) {
            if (!TextUtils.isEmpty(diary.getContent())) {
                mEtBody.setText(diary.getContent());
            }
            if (diary.getPicUrls() != null && diary.getPicUrls().size() > 0) {
                for (int i = 0; i < diary.getPicUrls().size(); i++) {
                    RequestBuilder<Drawable> rb = Glide.with(this).load(diary.getPicUrls()
                            .get(i));
                    if (i == 0) {
                        rb.into(mIbtPic1);
                    } else {
                        rb.into(mIbtPic2);
                    }
                }
            }

        }

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
            case R.id.ibt_pic1:
                showPicDialog(mIbtPic1, null);
                break;
            case R.id.ibt_pic2:
                showPicDialog(mIbtPic2, null);
                break;
            case R.id.bt_commit:
                if (TextUtils.isEmpty(mEtTitle.getText())) {
                    mTiTitle.setError("标题不可以为空");
                    mTiTitle.setErrorEnabled(true);
                } else if (TextUtils.isEmpty(mEtBody.getText())) {
                    GlobalUtil.makeToast("没有要提交的内容");
                } else {
                    mTiTitle.setErrorEnabled(false);

                    setResult(CodeConstants.RESULT_ADD);
                    finish();
                }
                break;
        }

    }


}
