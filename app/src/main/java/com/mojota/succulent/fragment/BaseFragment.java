package com.mojota.succulent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mojota.succulent.view.LoadingDialog;


public class BaseFragment extends Fragment {
    private LoadingDialog mLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoading = new LoadingDialog(getContext());
    }

    protected void showProgress(final boolean show) {
        if (show) {
            mLoading.show();
        } else {
            mLoading.dismiss();
        }
    }

}
