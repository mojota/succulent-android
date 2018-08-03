package com.mojota.succulent;

import android.app.Application;

import com.mojota.succulent.network.VolleyUtil;


/**
 *
 * Created by mojota on 18-8-3
*/
public class SucculentApplication extends Application {

    private static final String TAG = "SucculentApplication";
    private static SucculentApplication mContext;

    public SucculentApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        VolleyUtil.init(this);
    }


    public static SucculentApplication getInstance() {
        return mContext;
    }

}
