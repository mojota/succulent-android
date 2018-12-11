package com.mojota.succulent.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.mojota.succulent.SucculentApplication;
import com.mojota.succulent.activity.ImageBrowserActivity;
import com.mojota.succulent.activity.LoginActivity;
import com.mojota.succulent.activity.UserMomentsActivity;
import com.mojota.succulent.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojota on 18-8-16.
 */
public class ActivityUtil {

    /**
     * 进入图片详情页
     */
    public static void startImageBrowserActivity(Activity activity, View view, String
            title, List<String> picUrls, int picPos) {
        String transitionName = String.valueOf(picPos);
        view.setTransitionName(transitionName);
        Intent intent = new Intent(activity, ImageBrowserActivity.class);
        intent.putExtra(ImageBrowserActivity.KEY_TITLE, title);
        intent.putExtra(ImageBrowserActivity.KEY_PIC_POS, picPos);
        intent.putStringArrayListExtra(ImageBrowserActivity.KEY_PIC_URLS, (ArrayList<String>) picUrls);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, view, view.getTransitionName());
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    /**
     * 跳转登录页
     */
    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent,CodeConstants.REQUEST_USER_CHANGE);
    }

    /**
     * 进入某个人主页
     * @param userInfo
     */
    public static void startUserActivity(UserInfo userInfo) {
        Intent intent = new Intent(SucculentApplication.getInstance(), UserMomentsActivity
                .class);
        intent.putExtra(UserMomentsActivity.KEY_USER, userInfo);
        SucculentApplication.getInstance().startActivity(intent);
    }
}
