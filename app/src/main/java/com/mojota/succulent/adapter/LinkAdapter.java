package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.LinkInfo;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {

    private List<LinkInfo> mList;
    private final OnItemClickListener mListener;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvTitle;
        public final TextView tvTime;
        public final TextView tvReadCount;
        public final ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvReadCount = itemView.findViewById(R.id.tv_readcount);
            ivPic = itemView.findViewById(R.id.iv_pic);
        }
    }

    public LinkAdapter(List<LinkInfo> list, OnItemClickListener listener) {
        mList = list;
        mListener = listener;
    }

    public void setList(List<LinkInfo> list) {
        mList = list;
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_link, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        LinkInfo linkInfo = mList.get(position);
        if (linkInfo != null) {
            holder.tvTitle.setText(linkInfo.getLinkTitle());
            holder.tvTime.setText(linkInfo.getLinkTime());
            holder.tvReadCount.setText(linkInfo.getLinkReadCount());
            RequestOptions requestOptions = GlobalUtil.getDefaultOptions().centerCrop();
            if (!TextUtils.isEmpty(linkInfo.getLinkPicUrl())) {
                Glide.with(mContext).load(linkInfo.getLinkPicUrl()).apply(requestOptions)
                        .into(holder.ivPic);
                holder.ivPic.setVisibility(View.VISIBLE);
            } else {
                holder.ivPic.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemClick(holder.ivPic, position);
                }
            }
        });
    }

}
