package com.mojota.succulent.utils;

import android.util.Log;

/**
 * Created by mojota on 18-7-24.
 */
public class AppLog {
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
