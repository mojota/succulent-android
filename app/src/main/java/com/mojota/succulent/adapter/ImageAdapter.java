package com.mojota.succulent.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojota on 18-8-27.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private final List<String> mPicUrls;
    private final String mTitle;
    private final Context mContext;
    private final RequestOptions mRequestOptions;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
        }
    }

    public ImageAdapter(Context context, List<String> picUrls, String title, RequestOptions requestOptions) {
        mPicUrls = picUrls;
        mTitle = title;
        mContext = context;
        mRequestOptions = requestOptions;
    }

    @Override
    public int getItemCount() {
        if (mPicUrls != null) {
            if (mPicUrls.size() >= 3) {
                return 3;
            } else {
                return mPicUrls.size();
            }
        }
        return 0;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageAdapter.ViewHolder holder, final int position) {
        Glide.with(mContext).load(mPicUrls.get(position)).apply(mRequestOptions).into
                (holder.ivPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startImageBrowserActivity((Activity) mContext, holder.ivPic,
                        mTitle, mPicUrls, position);
            }
        });
    }

}
