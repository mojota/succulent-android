package com.mojota.succulent.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mojota.succulent.SucculentApplication;

public class VolleyUtil {

    private static RequestQueue mRequestQueue;


    /**
     * 创建一个RequestQueue对象,网络请求队列,全局一个就够了
     */
    public static void init(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
    }

    private static RequestQueue getRequestQueue() {
        init(SucculentApplication.getInstance());
        return mRequestQueue;
    }

    public static void execute(Request request) {
        getRequestQueue().add(request);
    }

}
