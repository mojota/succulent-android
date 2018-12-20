package com.mojota.succulent.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.NoteDetail;
import com.mojota.succulent.network.OssRequest;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.utils.AppLog;
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
public class DiaryAddActivity extends PhotoChooseSupportActivity implements View
        .OnClickListener {
    private static final String TAG = "DiaryAddActivity";

    public static final String KEY_MODE = "KEY_MODE"; // 1-添加笔记 2-添加笔记条目 3-编辑笔记条目
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_DIARY = "KEY_DIARY";
    public static final String KEY_NOTE_ID = "KEY_NOTE_ID";

    private Button mBtClose;
    private TextInputLayout mTiTitle;
    private EditText mEtTitle;
    private TextInputLayout mTiBody;
    private EditText mEtBody;
    private ImageView mIbtPic1;
    private ImageView mIbtPic2;
    private Button mBtCommit;
    private String mNoteId;
    private String mDetailId;
    private int mMode;
    private Uri[] mLocalPics = new Uri[2];
    private String[] mUploadPicKeys = new String[2];
    private OssRequest mOssRequest;
    private RequestOptions mRequestOptions;

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
        mRequestOptions = GlobalUtil.getRoundedCornersOptions();

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
            String[] pics = GlobalUtil.getStrings(diary.getPicUrls());
            if (pics != null && pics.length > 0) {
                for (int i = 0; i < pics.length; i++) {
                    mUploadPicKeys[i] = pics[i];
                    RequestBuilder<Drawable> rb = Glide.with(this).load(OssUtil
                            .getWholeImageUrl(pics[i])).apply(mRequestOptions);
                    if (i == 0) {
                        rb.into(mIbtPic1);
                    } else {
                        rb.into(mIbtPic2);
                    }
                }
            }
        }
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
            case R.id.ibt_pic1:
                showPicDialog(mIbtPic1, null);
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
            case R.id.ibt_pic2:
                showPicDialog(mIbtPic2, null);
                setOnChoosedListener(new OnChoosedListener() {
                    @Override
                    public void onChoosed(Uri localUploadUri) {
                        mLocalPics[1] = localUploadUri;
                        mUploadPicKeys[1] = ""; // 一旦选过图就置key为空,上传后再写入
                    }

                    @Override
                    public void onCanceled() {

                    }
                });
                break;
            case R.id.bt_commit:
                commit();
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
            submitData();
        }
    }


    /**
     * 顺序上传图片,最后一张结束后提交所有内容到服务器
     * @param localPics 本地准备上传的uri列表
     * @param index 外部调用由0开始
     */
    private void uploadImg(final Uri[] localPics, final int index) {
        showProgress(true);
        final String objectKey = OssUtil.getImageObjectKey(UserUtil.getCurrentUserId(),
                String.valueOf(System.currentTimeMillis()) + "-" + index);
        mOssRequest.upload(objectKey, GlobalUtil.getByte(localPics[index]), new
                OssRequest.OssOperateListener() {

            @Override
            public void onSuccess(String objectKey, String objectUrl) {
                showProgress(false);
                mUploadPicKeys[index] = objectKey;
                if (index == localPics.length - 1) {
                    submitData();
                } else {
                    uploadImg(localPics, index + 1);
                }
            }

            @Override
            public void onFailure(String objectKey, String errMsg) {
                showProgress(false);
                if (localPics[index] != null) {
                    StringBuilder tips = new StringBuilder("上传图片");
                    tips.append((index + 1) + "失败了," + errMsg);
                    GlobalUtil.makeToast(tips.toString());
                }
                if (index == localPics.length - 1) {
                    submitData();
                } else {
                    uploadImg(localPics, index + 1);
                }
            }
        });
    }

    /**
     * 根据功能类型提交请求
     */
    private void submitData() {
        AppLog.d(TAG, "submitData");
        String title = mEtTitle.getText().toString();
        String content = mEtBody.getText().toString();
        StringBuilder picKeys = new StringBuilder();
        for (String key : mUploadPicKeys) {
            if (!TextUtils.isEmpty(key)){
                picKeys.append(key).append(";"); //与服务端约定使用;做为分隔符
            }
        }
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
                    map.put("picUrls", picKeys.toString());
                    loadingRequestSubmit(UrlConstants.DIARY_ADD_URL, map, CodeConstants.REQUEST_NOTE_ADD);
                }
                break;
            case CodeConstants.NOTE_DETAIL_ADD:
                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(picKeys)) {
                    GlobalUtil.makeToast("没有要提交的内容");
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", UserUtil.getCurrentUserId());
                    map.put("noteId", mNoteId);
                    map.put("content", content);
                    map.put("picUrls", picKeys.toString());
                    loadingRequestSubmit(UrlConstants.DIARY_DETAIL_ADD_URL, map, CodeConstants
                            .REQUEST_DIARY_DETAIL_ADD);
                }
                break;
            case CodeConstants.NOTE_DETAIL_EDIT:
                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(picKeys)) {
                    GlobalUtil.makeToast("没有要提交的内容");
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId", UserUtil.getCurrentUserId());
                    map.put("noteId", mNoteId);
                    map.put("detailId", mDetailId);
                    map.put("content", content);
                    map.put("picUrls", picKeys.toString());
                    loadingRequestSubmit(UrlConstants.DIARY_DETAIL_EDIT_URL, map, CodeConstants.REQUEST_DIARY_DETAIL_EDIT);
                }
                break;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        setResult(CodeConstants.RESULT_ADD_REFRESH);
        finish();
    }
}
