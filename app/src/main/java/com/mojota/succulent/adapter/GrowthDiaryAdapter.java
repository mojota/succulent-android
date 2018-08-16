package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.List;

/**
 * Created by mojota on 18-7-24.
 */
public class GrowthDiaryAdapter extends RecyclerView.Adapter<GrowthDiaryAdapter.ViewHolder> {

    private Context mContext;
    private List<NoteInfo> mList;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongclickListener mOnItemLongcickListener;

    public interface OnItemClickListener{

        void onItemClick(ImageView view, NoteInfo diary, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPic;
        private final TextView tvTitle;
        private final TextView tvTime;
        private final ToggleButton tbLike;
        private final ToggleButton tbPermission;
        private final CardView cvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.cv_item);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
        }
    }

    public GrowthDiaryAdapter(Context context, List<NoteInfo> list) {
        mContext = context;
        mList = list;
    }

    public void setList(List<NoteInfo> list) {
        mList = list;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongcickListener(OnItemLongclickListener onItemLongcickListener) {
        this.mOnItemLongcickListener =onItemLongcickListener;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoteInfo diary = mList.get(position);
        if (diary != null) {
            holder.tvTitle.setText(diary.getNoteTitle());
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
            holder.tbPermission.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        diary.setPermission(1);
                    } else {
                        diary.setPermission(0);
                    }
                }
            });

            RequestOptions requestOptions = GlobalUtil.getDefaultRequestOptions().centerCrop();
            if (diary.getPicUrls() != null && diary.getPicUrls().size() > 0) {
                Glide.with(mContext).load(diary.getPicUrls().get(0)).apply(requestOptions)
                        .into(holder.ivPic);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.ivPic, diary,position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongcickListener !=null) {
                    mOnItemLongcickListener.onItemLongclick(position);
                }
                return false;
            }
        });

    }


}
