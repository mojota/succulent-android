package com.mojota.succulent.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojota on 18-8-23.
 */
public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.BaseViewHolder> {

    private final RequestOptions mAvatarOptions;
    private final RequestOptions mDefaultOptions;
    private RequestOptions mRoundedCornersOptions;

    private Context mContext;
    private List<NoteInfo> mList;
    private OnItemClickListener mOnItemClickListener;


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPic;
        private final TextView tvTitle;
        private final TextView tvTime;
        private final ToggleButton tbLike;
        private final ToggleButton tbPermission;
        private final TextView tvNickname;
        private final ImageView ivAvatar;
        private final TextView tvRegion;
        private final ImageView ivNoteType;

        public BaseViewHolder(View itemView) {
            super(itemView);

            tvNickname = itemView.findViewById(R.id.tv_nickname);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvRegion = itemView.findViewById(R.id.tv_region);
            ivNoteType = itemView.findViewById(R.id.iv_note_type);

            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
        }
    }


    public class DiaryViewHolder extends BaseViewHolder {

        public DiaryViewHolder(View itemView) {
            super(itemView);
        }
    }


    public class LandscapeViewHolder extends BaseViewHolder {
        private final RecyclerView rvPics;

        public LandscapeViewHolder(View itemView) {
            super(itemView);
            rvPics = itemView.findViewById(R.id.rv_pics);
        }
    }

    public MomentsAdapter(List<NoteInfo> list, OnItemClickListener listener) {
        mList = list;
        mOnItemClickListener = listener;
        mAvatarOptions = GlobalUtil.getDefaultAvatarOptions();
        mDefaultOptions = GlobalUtil.getDefaultOptions().centerCrop();
        mRoundedCornersOptions = GlobalUtil.getRoundedCornersOptions();
    }

    public void setList(List<NoteInfo> list) {
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
    public int getItemViewType(int position) {
        if (mList != null && mList.get(position) != null) {
            return mList.get(position).getNoteType() == CodeConstants.TYPE_LANDSCAPE ?
                    CodeConstants.TYPE_LANDSCAPE : CodeConstants.TYPE_DIARY;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        BaseViewHolder viewHolder = null;
        if (viewType == CodeConstants.TYPE_LANDSCAPE) {
            viewHolder = new LandscapeViewHolder(LayoutInflater.from(mContext).inflate(R
                    .layout.item_moment_landscape, parent, false));
        } else if (viewType == CodeConstants.TYPE_DIARY) {
            viewHolder = new DiaryViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                    .item_moment_diary, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        final NoteInfo noteInfo = mList.get(position);
        if (noteInfo != null) {
            holder.tvNickname.setText(noteInfo.getUserInfo().getNickname());
            Glide.with(mContext).load(noteInfo.getUserInfo().getAvatarUrl()).apply
                    (mAvatarOptions).into(holder.ivAvatar);
            holder.tvRegion.setText(noteInfo.getUserInfo().getRegion());

            holder.tvTitle.setText(noteInfo.getNoteTitle());
            holder.tvTime.setText(noteInfo.getUpdateTime());

            holder.tbLike.setTextOn(String.valueOf(noteInfo.getLikeCount()));
            holder.tbLike.setTextOff(String.valueOf(noteInfo.getLikeCount()));
            holder.tbLike.setText(String.valueOf(noteInfo.getLikeCount()));
            holder.tbLike.setChecked(noteInfo.getHasLike() == 1);
            holder.tbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeCount = noteInfo.getLikeCount();
                    if (holder.tbLike.isChecked()) {
                        noteInfo.setLikeCount(likeCount + 1);
                        noteInfo.setHasLike(1);
                        holder.tbLike.setTextOn(String.valueOf(noteInfo.getLikeCount()));
                        holder.tbLike.setTextOff(String.valueOf(noteInfo.getLikeCount()));
                        holder.tbLike.setText(String.valueOf(noteInfo.getLikeCount()));
                    } else {
                        noteInfo.setLikeCount(likeCount - 1);
                        noteInfo.setHasLike(0);
                        holder.tbLike.setTextOn(String.valueOf(noteInfo.getLikeCount()));
                        holder.tbLike.setTextOff(String.valueOf(noteInfo.getLikeCount()));
                        holder.tbLike.setText(String.valueOf(noteInfo.getLikeCount()));
                    }
                }
            });

            holder.tbPermission.setChecked(noteInfo.getPermission() == 1);
            holder.tbPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.tbPermission.isChecked()) {
                        noteInfo.setPermission(1);
                    } else {
                        noteInfo.setPermission(0);
                    }
                }
            });


            if (noteInfo.getNoteType() == 2) {
                // 笔记类型标识
                holder.ivNoteType.setImageTintList(ColorStateList.valueOf(ContextCompat
                        .getColor(mContext, R.color.red)));
                holder.ivNoteType.setImageResource(R.mipmap.ic_type_landscape);

                // 图
                if (noteInfo.getPicUrls() != null) {
                    ((LandscapeViewHolder) holder).rvPics.setAdapter(new ImageAdapter
                            (noteInfo.getPicUrls(), noteInfo.getNoteTitle()));
                }
            } else if (noteInfo.getNoteType() == 1) {
                // 笔记类型标识
                holder.ivNoteType.setImageTintList(ColorStateList.valueOf(ContextCompat
                        .getColor(mContext, R.color.lemon)));
                holder.ivNoteType.setImageResource(R.mipmap.ic_type_diary);

                // 封面图
                if (noteInfo.getPicUrls() != null && noteInfo.getPicUrls().size() > 0) {
                    Glide.with(mContext).load(noteInfo.getPicUrls().get(0)).apply
                            (mDefaultOptions).into(holder.ivPic);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(holder.ivPic, position);
                        }
                    }
                });
            }
        }
    }


    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        private final ArrayList<String> mPicUrls;
        private final String mTitle;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView ivPic;

            public ViewHolder(View itemView) {
                super(itemView);
                ivPic = itemView.findViewById(R.id.iv_pic);
            }
        }

        public ImageAdapter(ArrayList<String> picUrls, String title) {
            mPicUrls = picUrls;
            mTitle = title;
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
        public void onBindViewHolder(final ImageAdapter.ViewHolder holder, final int
                position) {
            Glide.with(mContext).load(mPicUrls.get(position)).apply(mRoundedCornersOptions).into
                    (holder.ivPic);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtil.startImageBrowserActivity((Activity) mContext, holder.ivPic, mTitle, mPicUrls, position);
                }
            });
        }

    }
}
