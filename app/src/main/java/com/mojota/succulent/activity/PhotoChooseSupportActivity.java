package com.mojota.succulent.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.BuildConfig;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.FileUtil;
import com.mojota.succulent.utils.GlobalUtil;

import java.io.File;

/**
 * 基类activity,支持打开选择图片对话框
 * Created by mojota on 18-8-6
 */
public abstract class PhotoChooseSupportActivity extends BaseActivity {

    private Uri mUploadUri = null;
    private ImageView mUploadIbt;
    private RequestOptions mRequestOptions;
    private OnChoosedListener mOnChoosedListener;

    interface OnChoosedListener {

        void onChoosed(Uri localUploadUri);

        void onCanceled();
    }

    public void setOnChoosedListener(OnChoosedListener onChoosedListener) {
        this.mOnChoosedListener = onChoosedListener;
    }

    protected void showPicDialog(ImageView uploadIbt, RequestOptions requestOptions) {
        mUploadIbt = uploadIbt;
        if (requestOptions == null) {
            mRequestOptions = GlobalUtil.getRoundedCornersOptions();
        } else {
            mRequestOptions = requestOptions;
        }
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
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mOnChoosedListener != null) {
                    mOnChoosedListener.onCanceled();
                }
            }
        }).show();
    }

    /**
     * 拍照
     */
    protected void takePhoto() {
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

    /**
     * 选择图片
     */
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images
                .Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CodeConstants.REQUEST_CHOOSE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CodeConstants.REQUEST_TAKE_PHOTO:
                    if (mUploadUri != null) {
                        if (mUploadIbt != null) {
                            Glide.with(this).load(mUploadUri).apply(mRequestOptions)
                                    .into(mUploadIbt);
                        }
                        if (mOnChoosedListener != null) {
                            mOnChoosedListener.onChoosed(mUploadUri);
                        }
                    }
                    mUploadUri = null;
                    mUploadIbt = null;
                    break;
                case CodeConstants.REQUEST_CHOOSE_PHOTO:
                    if (data.getData() != null) {
                        if (mUploadIbt != null) {
                            Glide.with(this).load(data.getData()).apply
                                    (mRequestOptions).into(mUploadIbt);
                        }
                        if (mOnChoosedListener != null) {
                            mOnChoosedListener.onChoosed(data.getData());
                        }
                    }
                    mUploadIbt = null;
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (mOnChoosedListener != null) {
                mOnChoosedListener.onCanceled();
            }
        }
    }
}
