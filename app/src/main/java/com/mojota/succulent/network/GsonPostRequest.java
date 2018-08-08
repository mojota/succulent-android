package com.mojota.succulent.network;

/**
 * Created by mojota on 17-2-28.
 */

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mojota.succulent.utils.AppLog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonPostRequest<T> extends Request<T> {
    private static final String TAG = "GsonPostRequest";
    private Gson mGson;
    private final Listener<T> mListener;
    private Map<String, String> mHeaderMap;
    private Map<String, String> mParams;
    private Class<T> mClass;

    public GsonPostRequest(String url, Map<String, String> headerMap, Map<String, String> params,
                           Class<T> clazz, Listener<T> listener, ErrorListener errorlistener) {
        super(Method.POST, url, errorlistener);
        mListener = listener;
        mHeaderMap = treatMap(headerMap);
        mParams = treatMap(params);
        mGson = new Gson();
        mClass = clazz;

        // 设置超时20秒
        setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppLog.d(TAG, "url:" + url);
    }

    /**
     * 当map的value为null时会报错，故value为null的置""
     */
    private Map<String, String> treatMap(Map<String, String> map) {
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    map.put(entry.getKey(), "");
                }
            }
            AppLog.d(TAG, map.toString());
        }
        return map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeaderMap == null) {
            mHeaderMap = new HashMap<>();
        }
        return mHeaderMap;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        if (response != null) {
            AppLog.d(TAG, "statusCode=" + response.statusCode);
        }
        try {
            String responseStr = new String(response.data, HttpHeaderParser.parseCharset(response
                    .headers));
            AppLog.d(TAG, "responseStr" + responseStr);
            if (mClass.getName().equals(String.class.getName())) {
                //当T为String类型时返回String类型的值
                return Response.success((T) responseStr, HttpHeaderParser.parseCacheHeaders
                        (response));
            } else {
                return Response.success(mGson.fromJson(responseStr, mClass), HttpHeaderParser
                        .parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }


}
