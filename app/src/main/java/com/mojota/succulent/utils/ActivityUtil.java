package com.mojota.succulent.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.mojota.succulent.activity.DiaryDetailActivity;
import com.mojota.succulent.activity.ImageBrowserActivity;
import com.mojota.succulent.activity.LoginActivity;
import com.mojota.succulent.model.NoteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojota on 18-8-16.
 */
public class ActivityUtil {

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


    public static void startDiaryDetailActivity(Activity activity, View view, NoteInfo diary) {
        Intent intent = new Intent(activity, DiaryDetailActivity.class);
        intent.putExtra(DiaryDetailActivity.KEY_DIARY, diary);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, view, view.getTransitionName());
        ActivityCompat.startActivityForResult(activity, intent, CodeConstants
                .REQUEST_DETAIL, options.toBundle());

    }

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent,CodeConstants.REQUEST_USER_CHANGE);
    }
}
