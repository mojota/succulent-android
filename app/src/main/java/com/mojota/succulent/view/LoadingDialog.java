package com.mojota.succulent.view;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mojota.succulent.R;


/**
 * Created by wangjing on 17-5-23.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.TransparentDialog);
        initView();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    private void initView() {
        ProgressBar pb = new ProgressBar(getContext());
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.di_pb_width);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, width);
        pb.setLayoutParams(params);
        setContentView(pb);
    }


    public void show() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        super.show();
    }

}
