package com.mojota.succulent.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.SucculentApplication;
import com.mojota.succulent.view.CenterCropRoundedCorners;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 通用工具类
 * Created by mojota on 18-7-25.
 */
public class GlobalUtil {

    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;

    private static String mVersioinName;
    private static int mVersioinCode;

    public static String getDeviceId() {
        String ANDROID_ID = Settings.System.getString(SucculentApplication
                .getInstance().getContentResolver(), Settings.Secure
                .ANDROID_ID);
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

    /**
     * 状态栏的高度,得到的值单位px
     */
    public float getStatusBarHeight() {
        float result = 0;
        int resourceId = SucculentApplication.getInstance().getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = SucculentApplication.getInstance().getResources()
                    .getDimension(resourceId);
        }
        return result;
    }

    /**
     * 导航栏的高度,得到的值单位px
     */
    public float getNavigationBarHeight() {
        float result = 0;
        int resourceId = SucculentApplication.getInstance().getResources()
                .getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = SucculentApplication.getInstance().getResources()
                    .getDimension(resourceId);
        }
        return result;
    }

    /**
     * bitmap 压缩后转换 byte[]
     */
    public static byte[] getByte(Uri uri) {
        byte[] data = null;
        if (uri == null) {
            return data;
        }
        Bitmap bitmap = compressBitmapBySize(uri, 1920, 1080); // 压缩
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            data = baos.toByteArray();
        }
        return data;
    }

    /**
     * 压缩
     * 读取文件生成要求大小的bitmap
     *
     * @param targetHeight 目标宽，可设置1920px(目前主流分辨率)
     * @param targetWidth  目标高 ，可设置1080px(目前主流分辨率)
     * @param uri      文件uri
     */
    public static Bitmap compressBitmapBySize(Uri uri, int targetWidth,
                                              int targetHeight) {
        if (uri == null) {
            return null;
        }
        if (targetHeight <= 0 || targetWidth <= 0) {
            targetHeight = 1920;
            targetWidth = 1080;
        }
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = SucculentApplication.getInstance().getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 把options.inJustDecodeBounds 设回true,不去真的解析图片,只是获取图片的头部信息,包含宽高等;
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(is, null, options);//此时bitmap返回空
            if (is != null){
                is.close();
            }
//            Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);// 此时返回bm为空
            options.inSampleSize = calculateInSampleSize(options, targetWidth,
                    targetHeight);
            options.inJustDecodeBounds = false; // 这里一定要设置false
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            is = SucculentApplication.getInstance().getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is, null, options);
//            bitmap = BitmapFactory.decodeFile(pathName, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 计算采样率的大小
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int
            targetWidth, int targetHeight) {
        if (targetHeight <= 0 || targetWidth <= 0) {
            return 1;
        }
        int inSampleSize = 1;
        final float imgWidth = options.outWidth;
        final float imgHeight = options.outHeight;
        if (imgHeight > targetHeight || imgWidth > targetWidth) {
            //宽度比例值
            final int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static void makeToast(String tips) {
        Toast.makeText(SucculentApplication.getInstance(), tips, Toast
                .LENGTH_LONG).show();
    }

    public static void makeToast(int tipsId) {
        Toast.makeText(SucculentApplication.getInstance(), tipsId, Toast
                .LENGTH_LONG).show();
    }
    public static void makeToastShort(String tips) {
        Toast.makeText(SucculentApplication.getInstance(), tips, Toast
                .LENGTH_SHORT).show();
    }

    public static void makeToastShort(int tipsId) {
        Toast.makeText(SucculentApplication.getInstance(), tipsId, Toast
                .LENGTH_SHORT).show();
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
                    ((TextView) snackbarView.findViewById(R.id.snackbar_text)
                    ).setTextColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ((TextView) snackbarView.findViewById(R.id.snackbar_text))
                        .setTextColor(Color.parseColor("#689f38"));
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
                    ((TextView) snackbarView.findViewById(R.id.snackbar_text)
                    ).setTextColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ((TextView) snackbarView.findViewById(R.id.snackbar_text))
                        .setTextColor(Color.parseColor("#689f38"));
            }
        }
        snackbar.show();
    }

    /**
     * 检测网络是否存在 true：存在 false ：不存在网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }


    public static int getVersionCode() {
        if (mVersioinCode == 0) {
            PackageManager pm = SucculentApplication.getInstance().getPackageManager();
            String pkgName = SucculentApplication.getInstance().getPackageName();
            try {
                PackageInfo pkgInfo = pm.getPackageInfo(pkgName, 0);
                mVersioinCode = pkgInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mVersioinCode;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(mVersioinName)) {
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
     * 格式化时间为formatStr
     */
    private static String formatTime(Long timestamp, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public static String formatDisplayTime(String timestampStr) {
        try {
            Long timestamp = Long.parseLong(timestampStr);
            Long currentTime = System.currentTimeMillis();
            Long yesterdayZero = getYesterdayZero();
            Long timeOffset = currentTime - timestamp;
            if (timeOffset < ONE_MINUTE && timeOffset > 0) {
                return SucculentApplication.getInstance().getString(R.string
                        .str_second_ago);
            } else if (timeOffset > ONE_MINUTE && timeOffset <= ONE_HOUR) {
                return String.valueOf(timeOffset / ONE_MINUTE) + SucculentApplication
                        .getInstance().getString(R.string.str_minute_ago);
            } else if (timeOffset > ONE_HOUR && timeOffset <= ONE_DAY) {
                return String.valueOf(timeOffset / ONE_HOUR) + SucculentApplication
                        .getInstance().getString(R.string.str_hour_ago);
            } if (timeOffset > ONE_DAY && timeOffset <= ONE_DAY * 3) {
                String time = formatTime(timestamp, "HH:mm");
                if (timestamp > yesterdayZero) {
                    return SucculentApplication.getInstance().getString(R.string
                            .str_yesterday) + time;
                } else {
                    return String.valueOf((int) Math.ceil(((float) timeOffset /
                            ONE_DAY))) + SucculentApplication.getInstance().getString(R
                            .string.str_day_ago) + time;
                }
            } else {
                return formatTime(timestamp, "yyyy-MM-dd HH:mm");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestampStr;
    }

    private static Long getYesterdayZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE ,-1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    public static String formatCurrentTime() {
        return formatTime(System.currentTimeMillis(), "yyyyMMddHHmmSS");
    }


    public static RequestOptions getDefaultOptions() {
        return new RequestOptions().placeholder(R.mipmap.ic_default_pic).error(R.mipmap
                .ic_default_pic).dontAnimate();
    }

    public static RequestOptions getDefaultAvatarOptions() {
        return new RequestOptions().error(R.mipmap
                .ic_default_avatar_gray_18dp).dontAnimate().circleCrop();
    }

    public static RequestOptions getRoundedCornersOptions() {
        return new RequestOptions().placeholder(R.mipmap.ic_default_pic).error(R.mipmap
                .ic_default_pic).dontAnimate().transform(new CenterCropRoundedCorners
                (SucculentApplication.getInstance().getResources()
                        .getDimensionPixelSize(R.dimen.di_corner)));
    }


    /**
     * 放大动画
     */
    public static void startAnim(Context context, final View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim
                .anim_up);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    /**
     * 以;分隔字符串，返回数组
     */
    public static String[] getStrings(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.split(";");
        }
        return null;
    }

    /**
     * 以;分隔字符串，返回list
     */
    public static List<String> getStringList(String str) {
        List<String> list = new ArrayList<String>();
        String[] strs = getStrings(str);
        if (strs != null) {
            for (String s : strs) {
                list.add(s);
            }
        }
        return list;
    }
}
