package com.mojota.succulent.utils;

import android.text.TextUtils;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.model.ResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;

import java.util.Map;

/**
 * Created by mojota on 18-9-10.
 */
public class RequestUtils {

    public interface RequestListener {

        void onRequestSuccess(int requestCode);

        void onRequestFailure(int requestCode);
    }


    /**
     * 通用请求，仅返回code,msg
     *
     * @param url
     * @param paramMap
     * @param requestCode     请求名称,用于标记请求、提示
     * @param requestListener 回调
     */
    public static void commonRequest(String url, Map<String, String> paramMap, final int
            requestCode, final RequestListener requestListener) {
        final String requestName = CodeConstants.REQUEST_MAP.get(requestCode);
        final GsonPostRequest request = new GsonPostRequest(url, null, paramMap, ResponseInfo.class,
                new Response.Listener<ResponseInfo>() {

            @Override
            public void onResponse(ResponseInfo responseInfo) {

                if (responseInfo == null || !"0".equals(responseInfo.getCode())) {
                    if (requestListener != null) {
                        requestListener.onRequestFailure(requestCode);
                    }
                    if (!TextUtils.isEmpty(requestName)){
                        if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg())) {
                            GlobalUtil.makeToast(requestName + "失败:" + responseInfo.getMsg());
                        } else {
                            GlobalUtil.makeToast(requestName + "失败");
                        }
                    }
                } else {
                    if (requestListener != null) {
                        requestListener.onRequestSuccess(requestCode);
                    }
                    if (!TextUtils.isEmpty(requestName)){
                        GlobalUtil.makeToast(requestName + "成功");
                    }
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                if (requestListener != null) {
                    requestListener.onRequestFailure(requestCode);
                }
                if (!TextUtils.isEmpty(requestName)){
                    GlobalUtil.makeToast(R.string.str_network_error);
                }
            }
        }));
        VolleyUtil.execute(request);
    }
}
