package com.mojota.succulent.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mojota.succulent.R;
import com.mojota.succulent.SucculentApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用工具类
 * Created by mojota on 18-7-25.
 */
public class GlobalUtil {

    private static String mVersioinName;

    public static String getDeviceId() {
        String ANDROID_ID = Settings.System.getString(SucculentApplication.getInstance()
                .getContentResolver(), Settings.Secure.ANDROID_ID);
        return ANDROID_ID;
    }

    /*
     * 获取手机屏幕宽度
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) SucculentApplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /*
     * 获取手机屏幕高度
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) SucculentApplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void makeToast(Context context, String tips) {
        Toast.makeText(context, tips, Toast.LENGTH_LONG).show();
    }

    public static void makeToast(Context context, int tipsId) {
        Toast.makeText(context, tipsId, Toast.LENGTH_LONG).show();
    }

    /**
     * snackbar提示框
     */
    public static void makeSnackbar(View view, String tips, int color) {
        if (view == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(view, tips, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        if (snackbarView != null) {
            if (color != 0) {
                try {
                    ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor
                            (color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor
                        (Color.parseColor("#46A6FF"));
            }
        }
        snackbar.show();
    }

    /**
     * snackbar提示框
     */
    public static void makeSnackbar(View view, int tips, int color) {
        if (view == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(view, tips, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        if (snackbarView != null) {
            if (color != 0) {
                try {
                    ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor
                            (color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor
                        (Color.parseColor("#46A6FF"));
            }
        }
        snackbar.show();
    }

    /**
     * 检测网络是否存在 true：存在 false ：不存在网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static String getVersionName() {
        if (mVersioinName == null || TextUtils.isEmpty(mVersioinName)) {
            PackageManager pm = SucculentApplication.getInstance().getPackageManager();
            String pkgName = SucculentApplication.getInstance().getPackageName();
            try {
                PackageInfo pkgInfo = pm.getPackageInfo(pkgName, 0);
                mVersioinName = pkgInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mVersioinName;
    }


    /**
     * 将long格式化为formatStr
     */
    public static String formatTime(long milliseconds, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = new Date(milliseconds);
        return sdf.format(date);
    }

    public static String formatCurrentTime(){
        return formatTime(System.currentTimeMillis(), "yyyyMMddHHmmSS");
    }

    public static String getYYYYMMDDHHMMSS(long milliseconds) {
        return formatTime(milliseconds, "yyyy-MM-dd HH:mm:SS");
    }

}
