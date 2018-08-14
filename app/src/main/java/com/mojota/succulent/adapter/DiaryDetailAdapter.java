package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.DiaryDetail;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.List;

/**
 * Created by mojota on 18-7-24.
 */
public class DiaryDetailAdapter extends RecyclerView.Adapter<DiaryDetailAdapter.ViewHolder> {

    private Context mContext;
    private List<DiaryDetail> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPic;
        private final TextView tvTitle;
        private final TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    public DiaryDetailAdapter(Context context, List<DiaryDetail> list) {
        mContext = context;
        mList = list;
    }

    public void setList(List<DiaryDetail> list) {
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
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R
                .layout.item_diary_detail, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final DiaryDetail diary = mList.get(position);
        if (diary != null) {
            holder.tvTitle.setText(diary.getContent());
            holder.tvTime.setText(diary.getCreateTime());

            RequestOptions requestOptions = GlobalUtil.getDefaultRequestOptions().centerCrop();
            if (diary.getPicUrls() != null && diary.getPicUrls().size() > 0) {
                Glide.with(mContext).load(diary.getPicUrls().get(0)).apply(requestOptions)
                        .into(holder.ivPic);
            }
        }
    }

}
