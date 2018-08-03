package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.GrowthDiary;

import java.util.List;

/**
 * Created by mojota on 18-7-24.
 */
public class GrowthDiaryAdapter extends RecyclerView.Adapter<GrowthDiaryAdapter.ViewHolder> {

    private Context mContext;
    private List<GrowthDiary> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPic;
        private final TextView tvTitle;
        private final TextView tvTime;
        private final ToggleButton tbLike;
        private final ToggleButton tbPermission;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
        }
    }

    public GrowthDiaryAdapter(Context context, List<GrowthDiary> list) {
        mContext = context;
        mList = list;
    }

    public void setList(List<GrowthDiary> list) {
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
                .layout.item_diary, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GrowthDiary diary = mList.get(position);
        if (diary != null) {
            holder.tvTitle.setText(diary.getTitle());
            holder.tvTime.setText(diary.getUpdateTime());

            if (diary.getHasLike() == 1) {
                holder.tbLike.setChecked(true);
                holder.tbLike.setTextOn(String.valueOf(diary.getLikeCount()));
                holder.tbLike.setText(String.valueOf(diary.getLikeCount()));
            } else {
                holder.tbLike.setChecked(false);
                holder.tbLike.setTextOff(String.valueOf(diary.getLikeCount()));
                holder.tbLike.setText(String.valueOf(diary.getLikeCount()));
            }
            holder.tbLike.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int likeCount = diary.getLikeCount();
                    if (isChecked) {
                        diary.setLikeCount(likeCount + 1);
                        holder.tbLike.setTextOn(String.valueOf(diary.getLikeCount()));
                    } else {
                        diary.setLikeCount(likeCount - 1);
                        holder.tbLike.setTextOff(String.valueOf(diary.getLikeCount()));
                    }
                }
            });

            if (diary.getPermission() == 1) {
                holder.tbPermission.setChecked(true);
            } else {
                holder.tbPermission.setChecked(false);
            }

            RequestOptions requestOptions = new RequestOptions().error(R.mipmap
                    .ic_default_pic).dontAnimate().centerCrop();
            Glide.with(mContext).load(diary.getPicUrl()).apply(requestOptions).into(holder
                    .ivPic);
        }

    }


}
