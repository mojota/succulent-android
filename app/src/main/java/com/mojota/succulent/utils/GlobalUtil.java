package com.mojota.succulent.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 通用工具类
 * Created by mojota on 18-7-25.
 */
public class GlobalUtil {

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;

    private static String mVersioinName;

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
     * 计算采样率的大小
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        if (reqHeight <= 0 || reqWidth <= 0) return 1;
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = Math.round((float) height / (float)
                    reqHeight);
            //宽度比例值
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 读取文件生成要求大小的bitmap
     *
     * @param reqHeight bitmap大小的宽，小于0默认1920px
     * @param reqWidth  bitmap高 ，小于0默认720
     * @param path      文件路径
     */
    public static Bitmap compressBitmap(String path, int reqWidth, int
            reqHeight) {
        try {
            if (reqHeight < 0 || reqWidth < 0) return null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);// 此时返回bm为空
            options.inJustDecodeBounds = false;
            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight);//
            // 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeFile(path, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void makeToast(String tips) {
        Toast.makeText(SucculentApplication.getInstance(), tips, Toast
                .LENGTH_LONG).show();
    }

    public static void makeToast(int tipsId) {
        Toast.makeText(SucculentApplication.getInstance(), tipsId, Toast
                .LENGTH_LONG).show();
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

    public static String getVersionName() {
        if (mVersioinName == null || TextUtils.isEmpty(mVersioinName)) {
            PackageManager pm = SucculentApplication.getInstance()
                    .getPackageManager();
            String pkgName = SucculentApplication.getInstance()
                    .getPackageName();
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
                return "刚刚";
            } else if (timeOffset > ONE_MINUTE && timeOffset <= ONE_HOUR) {
                return String.valueOf(timeOffset / ONE_MINUTE) + "分钟前";
            } else if (timeOffset > ONE_HOUR && timeOffset <= ONE_DAY) {
                return String.valueOf(timeOffset / ONE_HOUR) + "小时前";
            } if (timeOffset > ONE_DAY && timeOffset <= ONE_DAY * 3) {
                String time = formatTime(timestamp, "HH:mm");
                if (timestamp > yesterdayZero) {
                    return "昨天" + time;
                } else {
                    return String.valueOf((int) Math.ceil(((float) timeOffset
                            / ONE_DAY))) + "天前 " + time;
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
        return new RequestOptions().error(R.mipmap.ic_default_pic)
                .dontAnimate();
    }

    public static RequestOptions getDefaultAvatarOptions() {
        return new RequestOptions().error(R.mipmap
                .ic_default_avatar_gray_18dp).dontAnimate().circleCrop();
    }

    public static RequestOptions getRoundedCornersOptions() {
        return new RequestOptions().error(R.mipmap.ic_default_pic)
                .dontAnimate().transform(new CenterCropRoundedCorners
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
     * 以;分隔字符串
     */
    public static List<String> getStringList(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Arrays.asList(str.split(";"));
        }
        return null;
    }
}
