package com.mojota.succulent.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mojota.succulent.R;


/**
 * Created by wangjing on 17-5-23.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.lightDialog);
        initView();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    private void initView() {
        LinearLayout ll = new LinearLayout(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(params);
        ll.setGravity(Gravity.CENTER_VERTICAL);

        int padding = getContext().getResources().getDimensionPixelSize(R.dimen.default_margin);
        ll.setPadding(padding, padding, padding, padding);

        ProgressBar pb = new ProgressBar(getContext());
        pb.setIndeterminateTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.di_pb_width);
        ViewGroup.LayoutParams pbParams = new ViewGroup.LayoutParams(width, width);
        pb.setLayoutParams(pbParams);

//        TextView tv = new TextView(getContext());
//        tv.setText("正在提交...");
//        tv.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
//        tv.setPadding(padding, 0, 0, 0);

        ll.addView(pb);
        setContentView(ll);
    }


    public void show() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        super.show();
    }

}
