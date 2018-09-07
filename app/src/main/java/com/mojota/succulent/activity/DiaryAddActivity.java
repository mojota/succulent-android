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
import com.mojota.succulent.model.NoteDetail;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加笔记
 * Created by mojota on 18-8-2
 */
public class DiaryAddActivity extends PhotoChooseSupportActivity implements View.OnClickListener {

    public static final String KEY_MODE = "KEY_MODE"; // 1-添加笔记 2-添加笔记条目 3-编辑笔记条目
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_DIARY = "KEY_DIARY";
    public static final String KEY_NOTE_ID = "KEY_NOTE_ID";

    private Button mBtClose;
    private TextInputLayout mTiTitle;
    private EditText mEtTitle;
    private TextInputLayout mTiBody;
    private EditText mEtBody;
    private ImageButton mIbtPic1;
    private ImageButton mIbtPic2;
    private Button mBtCommit;
    private String mNoteId;
    private String mDetailId;
    private int mMode;

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

        mMode = getIntent().getIntExtra(KEY_MODE, 0);
        mNoteId = getIntent().getStringExtra(KEY_NOTE_ID);

        String title = getIntent().getStringExtra(KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            mEtTitle.setText(title);
            mEtTitle.setEnabled(false);
            mTiTitle.setHintEnabled(false);
        }

        NoteDetail diary = (NoteDetail) getIntent().getSerializableExtra(KEY_DIARY);
        if (diary != null) {
            mDetailId = diary.getDetailId();
            if (!TextUtils.isEmpty(diary.getContent())) {
                mEtBody.setText(diary.getContent());
            }
            if (diary.getPicUrls() != null && diary.getPicUrls().size() > 0) {
                for (int i = 0; i < diary.getPicUrls().size(); i++) {
                    RequestBuilder<Drawable> rb = Glide.with(this).load(diary.getPicUrls().get(i));
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
                submit();
                break;
        }
    }

    /**
     * 根据功能类型提交请求
     */
    private void submit() {
        String title = mEtTitle.getText().toString();
        String content = mEtBody.getText().toString();
        String picUrls = "";
        switch (mMode) {
            case CodeConstants.NOTE_ADD:
                if (TextUtils.isEmpty(title)) {
                    mTiTitle.setError("标题不可以为空");
                    mTiTitle.setErrorEnabled(true);
                } else {
                    mTiTitle.setErrorEnabled(false);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", UserUtil.getCurrentUserId());
                    map.put("noteTitle", title);
                    map.put("content", content);
                    map.put("noteType", "1");
                    map.put("strPicUrls", picUrls);
                    requestSubmit(UrlConstants.DIARY_ADD_URL, map, CodeConstants.REQUEST_NOTE_ADD);
                }
                break;
            case CodeConstants.NOTE_DETAIL_ADD:
                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(picUrls)) {
                    GlobalUtil.makeToast("没有要提交的内容");
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", UserUtil.getCurrentUserId());
                    map.put("noteId", mNoteId);
                    map.put("content", content);
                    map.put("strPicUrls", picUrls);
                    requestSubmit(UrlConstants.DIARY_DETAIL_ADD_URL, map, CodeConstants
                            .REQUEST_DIARY_DETAIL_ADD);
                }
                break;
            case CodeConstants.NOTE_DETAIL_EDIT:
                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(picUrls)) {
                    GlobalUtil.makeToast("没有要提交的内容");
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", UserUtil.getCurrentUserId());
                    map.put("detailId", mDetailId);
                    map.put("content", content);
                    map.put("strPicUrls", picUrls);
                    requestSubmit(UrlConstants.DIARY_DETAIL_EDIT_URL, map, CodeConstants.REQUEST_DIARY_DETAIL_EDIT);
                }
                break;
        }
    }

    @Override
    protected void onRequestSuccess(int requestCode) {
        setResult(CodeConstants.RESULT_REFRESH);
        finish();
    }
}
