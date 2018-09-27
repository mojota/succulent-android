package com.mojota.succulent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.model.ResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;
import com.mojota.succulent.view.LoadingDialog;

import java.util.Map;

/**
 * 基类activity
 * Created by mojota on 18-8-14.
 */
public class BaseActivity extends AppCompatActivity implements RequestUtils.RequestListener {

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


    /**
     * 提交请求带loading
     */
    protected void loadingRequestSubmit(String url, Map<String, String> paramMap, final int
            requestCode) {
        showProgress(true);
        RequestUtils.commonRequest(url, paramMap, requestCode, this);
    }

    /**
     * 提交请求无loading
     */
    protected void requestSubmit(String url, Map<String, String> paramMap, final int
            requestCode) {
        RequestUtils.commonRequest(url, paramMap, requestCode, this);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        showProgress(false);
    }

    @Override
    public void onRequestFailure(int requestCode) {
        showProgress(false);
    }
}
