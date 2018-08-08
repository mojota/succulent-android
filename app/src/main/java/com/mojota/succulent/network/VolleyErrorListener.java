package com.mojota.succulent.network;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mojota.succulent.utils.AppLog;

/**
 * 网络请求失败统一处理
 * Created by mojota on 17-5-5.
 */

public class VolleyErrorListener implements Response.ErrorListener {
    private static final String TAG = "VolleyErrorListener";
    private String mErrorText;
    private RequestErrorListener mListener;


    public VolleyErrorListener(RequestErrorListener listener) {
        mListener = listener;
    }

    public VolleyErrorListener(String errorText) {
        mErrorText = errorText;
    }

    public VolleyErrorListener() {
    }


    public interface RequestErrorListener {
        void onError(String error);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (TextUtils.isEmpty(mErrorText)) {
            mErrorText = "请求失败或超时，未获取到最新数据";
        }
        StringBuilder strBuiler = new StringBuilder(mErrorText);
        try {
            if (error.networkResponse != null) {
                strBuiler.append(",code: " + error.networkResponse.statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppLog.d(TAG, "onErrorResponse:" + strBuiler.toString());
        if (mListener != null) {
            mListener.onError(strBuiler.toString());
        }
    }

}
