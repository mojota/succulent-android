package com.mojota.succulent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 统一loading
 * Created by mojota on 18-12-27
*/
public class LoadingView extends ProgressBar {
    public LoadingView(Context context) {
        super(context);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
    }

    /**
     * 是否显示
     */
    public void show(boolean isShow) {
        if (isShow) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }
}
