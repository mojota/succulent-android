package com.mojota.succulent.interfaces;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by mojota on 18-8-16.
 */
public interface OnImageClickListener {

    void onImageClick(ImageView view, String title, List<String> picUrls, int picPos);
}