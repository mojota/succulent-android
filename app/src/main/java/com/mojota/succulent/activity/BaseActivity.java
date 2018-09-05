package com.mojota.succulent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mojota.succulent.view.LoadingDialog;

/**
 * 基类activity
 * Created by mojota on 18-8-14.
 */
public class BaseActivity extends AppCompatActivity {

    private LoadingDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoading = new LoadingDialog(this);
    }

    protected void showProgress(final boolean show) {
        if (show) {
            mLoading.show();
        } else {
            mLoading.dismiss();
        }
    }
}
