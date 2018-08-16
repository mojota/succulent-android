package com.mojota.succulent.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.mojota.succulent.activity.ImageBrowserActivity;

import java.util.ArrayList;

/**
 * Created by mojota on 18-8-16.
 */
public class ActivityUtil {

    public static void startImageBrowserActivity(Activity activity, ImageView view, String
            title, ArrayList<String> picUrls, int picPos) {
        String transitionName = String.valueOf(picPos);
        view.setTransitionName(transitionName);
        Intent intent = new Intent(activity, ImageBrowserActivity.class);
        intent.putExtra(ImageBrowserActivity.KEY_TITLE, title);
        intent.putExtra(ImageBrowserActivity.KEY_PIC_POS, picPos);
        intent.putStringArrayListExtra(ImageBrowserActivity.KEY_PIC_URLS, picUrls);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, view, view.getTransitionName());
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
