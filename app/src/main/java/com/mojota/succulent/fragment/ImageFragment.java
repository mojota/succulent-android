package com.mojota.succulent.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mojota.succulent.R;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.utils.FileUtil;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.view.ZoomImageView;

/**
 * 图片详情页
 * Created by mojota on 18-8-8
 */
public class ImageFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PIC_URL = "pic_url";
    private static final String ARG_TRANSITION_NAME = "transition_name";
    private static final String ARG_PICS_COUNT = "pics_count";

    private String mPicUrl;
    private String mTransitionName;
    private ZoomImageView mIvImage;
    private ImageButton mBtDownload;
    private int mPicsCount;


    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(String picUrl, String
            transitionName, int picsCount) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PIC_URL, picUrl);
        args.putString(ARG_TRANSITION_NAME, transitionName);
        args.putInt(ARG_PICS_COUNT, picsCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPicUrl = OssUtil.getWholeImageUrl(getArguments().getString(ARG_PIC_URL));
            mTransitionName = getArguments().getString(ARG_TRANSITION_NAME);
            mPicsCount = getArguments().getInt(ARG_PICS_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_image, container, false);
        mIvImage = rootView.findViewById(R.id.iv_image);
        mIvImage.setTransitionName(mTransitionName);
        mBtDownload = rootView.findViewById(R.id.bt_download);
        mBtDownload.setOnClickListener(this);
        mIvImage.setOnClickListener(this);

//        Glide.with(getContext()).load(mPicUrl).apply(GlobalUtil
//                .getDefaultOptions()).into(mIvImage);

        Glide.with(getContext()).load(mPicUrl).apply(GlobalUtil.getDefaultOptions())
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable>
                    transition) {
                getActivity().startPostponedEnterTransition(); //恢复转场动画
                mIvImage.setImageDrawable(resource);
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                getActivity().startPostponedEnterTransition(); //恢复转场动画
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().startPostponedEnterTransition();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image:
                if ("0".equals(mTransitionName)) {
                    getActivity().supportFinishAfterTransition();
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.bt_download:
                FileUtil.saveImage(mPicUrl);
                break;
        }
    }
}
