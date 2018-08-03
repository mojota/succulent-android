package com.mojota.succulent;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.FileUtil;
import com.mojota.succulent.utils.GlobalUtil;

import java.io.File;

/**
 * 添加笔记
 * Created by mojota on 18-8-2
 */
public class DiaryAddActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtClose;
    private TextInputLayout mTiTitle;
    private EditText mEtTitle;
    private TextInputLayout mTiBody;
    private EditText mEtBody;
    private ImageButton mIbtPic1;
    private ImageButton mIbtPic2;
    private Button mBtCommit;
    private Uri mUploadUri = null;
    private ImageButton mUploadIbt;

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
                showPicDialog(mIbtPic1);
                break;
            case R.id.ibt_pic2:
                showPicDialog(mIbtPic2);
                break;
            case R.id.bt_commit:
                if (TextUtils.isEmpty(mEtTitle.getText())) {
                    mTiTitle.setError("标题不可以为空");
                    mTiTitle.setErrorEnabled(true);
                } else {
                    mTiTitle.setErrorEnabled(false);

                    setResult(CodeConstants.RESULT_ADD);
                    finish();
                }
                break;
        }

    }

    private void showPicDialog(ImageButton uploadIbt) {
        mUploadIbt = uploadIbt;
        String[] items = {"拍照", "从相册选择"};
        new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    takePhoto();
                } else if (which == 1) {
                    choosePhoto();
                }
            }
        }).show();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String fileName = GlobalUtil.formatCurrentTime() + ".jpg";
        File file = FileUtil.createPicFile(fileName);
        if (Build.VERSION.SDK_INT >= 24) {
            mUploadUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + "" +
                    ".provider", file);
        } else {
            mUploadUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUploadUri);
        startActivityForResult(intent, CodeConstants.REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images
                .Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CodeConstants.REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CodeConstants.REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK && mUploadUri != null && mUploadIbt != null) {
                    Glide.with(this).load(mUploadUri).into(mUploadIbt);
                }
                mUploadUri = null;
                mUploadIbt = null;
                break;
            case CodeConstants.REQUEST_CHOOSE_PHOTO:
                if (resultCode == RESULT_OK && mUploadIbt != null) {
                    Glide.with(this).load(data.getData()).into(mUploadIbt);
                }
                mUploadIbt = null;
                break;
        }
    }
}
