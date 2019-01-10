package com.mojota.succulent.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
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
 * 添加造景后花园
 * created by 王静 on 18-8-3
 */
public class LandscapingAddActivity extends PhotoChooseSupportActivity implements View
        .OnClickListener {
    private static final String TAG = "LandscapingAddActivity";
    private Button mBtClose;
    private TextInputLayout mTiTitle;
    private EditText mEtTitle;
    private ImageView mIbtPic1;
    private ImageView mIbtPic2;
    private ImageView mIbtPic3;
    private ImageView mIbtPic4;
    private CheckBox mCbPermission;
    private Button mBtCommit;
    private Uri[] mLocalPics = new Uri[4];
    private String[] mUploadPicKeys = new String[4];
    private OssRequest mOssRequest;
    private RequestOptions mRequestOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscaping_add);

        mBtClose = findViewById(R.id.bt_close);
        mBtClose.setOnClickListener(this);
        mTiTitle = findViewById(R.id.ti_title);
        mEtTitle = findViewById(R.id.et_title);
        mIbtPic1 = findViewById(R.id.ibt_pic1);
        mIbtPic1.setOnClickListener(this);
        mIbtPic2 = findViewById(R.id.ibt_pic2);
        mIbtPic2.setOnClickListener(this);
        mIbtPic3 = findViewById(R.id.ibt_pic3);
        mIbtPic3.setOnClickListener(this);
        mIbtPic4 = findViewById(R.id.ibt_pic4);
        mIbtPic4.setOnClickListener(this);
        mCbPermission = findViewById(R.id.cb_permission);
        mBtCommit = findViewById(R.id.bt_commit);
        mBtCommit.setOnClickListener(this);

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
            case R.id.ibt_pic3:
                showPicDialog(mIbtPic3, null);
                setOnChoosedListener(new OnChoosedListener() {
                    @Override
                    public void onChoosed(Uri localUploadUri) {
                        mLocalPics[2] = localUploadUri;
                        mUploadPicKeys[2] = ""; // 一旦选过图就置key为空,上传后再写入
                    }

                    @Override
                    public void onCanceled() {

                    }
                });
                break;
            case R.id.ibt_pic4:
                showPicDialog(mIbtPic4, null);
                setOnChoosedListener(new OnChoosedListener() {
                    @Override
                    public void onChoosed(Uri localUploadUri) {
                        mLocalPics[3] = localUploadUri;
                        mUploadPicKeys[3] = ""; // 一旦选过图就置key为空,上传后再写入
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
                    submitData();
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
                    submitData();
                } else {
                    uploadImg(localPics, index + 1);
                }
            }
        });
    }


    /**
     * 提交请求
     */
    private void submitData() {
        AppLog.d(TAG, "submitData");
        String title = mEtTitle.getText().toString();
        int permission = mCbPermission.isChecked() ? 1 : 0;

        StringBuilder picKeys = new StringBuilder();
        for (String key : mUploadPicKeys) {
            if (!TextUtils.isEmpty(key)) {
                picKeys.append(key).append(";"); //与服务端约定使用;做为分隔符
            }
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(picKeys)) {
            GlobalUtil.makeToast(R.string.str_commit_empty);
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", UserUtil.getCurrentUserId());
            map.put("noteType", "2");
            map.put("permission", String.valueOf(permission));
            map.put("noteTitle", title);
            map.put("picUrls", picKeys.toString());
            loadingRequestSubmit(UrlConstants.NOTE_ADD_URL, map, CodeConstants
                    .REQUEST_NOTE_ADD);
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        setResult(CodeConstants.RESULT_REFRESH);
        finish();
    }
}
