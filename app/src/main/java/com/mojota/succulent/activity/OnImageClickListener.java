package com.mojota.succulent.activity;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by mojota on 18-8-16.
 */
public interface OnImageClickListener {

    void onImageClick(ImageView view, String title, ArrayList<String> picUrls, int picPos);
}