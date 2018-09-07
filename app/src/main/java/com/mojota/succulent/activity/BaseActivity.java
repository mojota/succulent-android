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
import com.mojota.succulent.view.LoadingDialog;

import java.util.Map;

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


    /**
     * 提交请求
     */
    protected void requestSubmit(String url, Map<String, String> paramMap, final int requestCode) {
        showProgress(true);

        GsonPostRequest request = new GsonPostRequest(url, null, paramMap, ResponseInfo.class,
                new Response.Listener<ResponseInfo>() {

            @Override
            public void onResponse(ResponseInfo responseInfo) {
                showProgress(false);
                if (responseInfo == null || !"0".equals(responseInfo.getCode())) {
                    if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg())) {
                        GlobalUtil.makeToast(CodeConstants.REQUEST_MAP.get(requestCode) + "失败:" +
                                responseInfo.getMsg());
                    } else {
                        GlobalUtil.makeToast(CodeConstants.REQUEST_MAP.get(requestCode) + "失败");
                    }
                } else {
                    GlobalUtil.makeToast(CodeConstants.REQUEST_MAP.get(requestCode) + "成功");
                    onRequestSuccess(requestCode);
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                showProgress(false);
                GlobalUtil.makeToast(R.string.str_network_error);
            }
        }));
        VolleyUtil.execute(request);
    }


    /**
     * 请求成功
     * 重写此方法,用于处理请求成功后逻辑
     *
     * @param requestCode 请求码
     */
    protected void onRequestSuccess(int requestCode) {
    }


}
