package com.mojota.succulent.network;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mojota.succulent.utils.AppLog;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangjing on 17-11-20.
 */

public class GsonForJsonObjectRequest<T> extends JsonObjectRequest {
    private static final String TAG = "GsonForJsonObjectRequest";
    private final Gson mGson;
    private final Class<T> mClass;

    public GsonForJsonObjectRequest(String url, JSONObject jsonRequest, Class<T> clazz, Response
            .Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);

        mGson = new Gson();
        mClass = clazz;

        // 设置超时20秒
        setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        if (response != null) {
            AppLog.d(TAG, "statusCode=" + response.statusCode);
        }
        try {
            String responseStr = new String(response.data, HttpHeaderParser.parseCharset(response
                    .headers, "utf-8"));
            AppLog.d(TAG, "responseStr" + responseStr);
            return Response.success(mGson.fromJson(responseStr, mClass), HttpHeaderParser
                    .parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }
}
