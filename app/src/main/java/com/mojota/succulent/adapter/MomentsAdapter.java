package com.mojota.succulent.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mojota.succulent.R;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;
import com.mojota.succulent.utils.UserUtil;

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
        private final ViewGroup mLayoutBar;

        public BaseViewHolder(View itemView) {
            super(itemView);

            mLayoutBar = itemView.findViewById(R.id.layout_bar);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvRegion = itemView.findViewById(R.id.tv_region);
            ivNoteType = itemView.findViewById(R.id.iv_note_type);

            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
            tbPermission.setVisibility(View.GONE);
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
            viewHolder = new LandscapeViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                    .item_moment_landscape, parent, false));
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
            final UserInfo userInfo = noteInfo.getUserInfo();
            if (userInfo != null) {
                holder.tvNickname.setText(UserUtil.getDisplayName(userInfo));
//            Glide.with(mContext).load(userInfo.getAvatarUrl()).apply
//                    (mAvatarOptions).into(holder.ivAvatar);
                setPaletteImage(holder.ivAvatar, holder.mLayoutBar, OssUtil
                        .getWholeImageUrl(userInfo.getAvatarUrl()), mAvatarOptions, R
                        .mipmap.ic_default_avatar_white_18dp, R.drawable.ic_bg_user_bar);
                holder.tvRegion.setText(userInfo.getRegion());

                holder.mLayoutBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtil.startUserMomentsActivity(userInfo);
                    }
                });
            }

            if (!TextUtils.isEmpty(noteInfo.getNoteTitle())) {
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(noteInfo.getNoteTitle());
            } else {
                holder.tvTitle.setVisibility(View.GONE);
            }
            holder.tvTime.setText(GlobalUtil.formatDisplayTime(noteInfo.getUpdateTime()));

            holder.tbLike.setTextOn(String.valueOf(noteInfo.getLikeyCount()));
            holder.tbLike.setTextOff(String.valueOf(noteInfo.getLikeyCount()));
            holder.tbLike.setText(String.valueOf(noteInfo.getLikeyCount()));
            holder.tbLike.setChecked(noteInfo.getIsLike() == 1);
            holder.tbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeyCount = noteInfo.getLikeyCount();
                    int isLikey;
                    if (holder.tbLike.isChecked()) {
                        noteInfo.setLikeyCount(likeyCount + 1);
                        isLikey = 1;
                    } else {
                        noteInfo.setLikeyCount(likeyCount - 1);
                        isLikey = 0;
                    }
                    noteInfo.setIsLike(isLikey);
                    RequestUtils.requestLike(noteInfo.getNoteId(), isLikey);
                    holder.tbLike.setTextOn(String.valueOf(noteInfo.getLikeyCount()));
                    holder.tbLike.setTextOff(String.valueOf(noteInfo.getLikeyCount()));
                    holder.tbLike.setText(String.valueOf(noteInfo.getLikeyCount()));
                }
            });

            if (noteInfo.getNoteType() == 2) {
                // 笔记类型标识
                holder.ivNoteType.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor
                        (mContext, R.color.red)));
                holder.ivNoteType.setImageResource(R.mipmap.ic_type_landscape);

                // 图
                List<String> pics = GlobalUtil.getStringList(noteInfo.getPicUrls());
                if (pics != null && pics.size() > 0) {
                    ((LandscapeViewHolder) holder).rvPics.setVisibility(View.VISIBLE);
                    ((LandscapeViewHolder) holder).rvPics.setAdapter(new ImageAdapter(mContext,
                            pics, noteInfo.getNoteTitle(), mRoundedCornersOptions));
                } else {
                    ((LandscapeViewHolder) holder).rvPics.setVisibility(View.GONE);
                }
            } else if (noteInfo.getNoteType() == 1) {
                // 笔记类型标识
                holder.ivNoteType.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor
                        (mContext, R.color.lemon)));
                holder.ivNoteType.setImageResource(R.mipmap.ic_type_diary);

                // 笔记封面图
                List<String> pics = GlobalUtil.getStringList(noteInfo.getPicUrls());
                if (pics != null && pics.size() > 0) {
                    String picUrl = OssUtil.getWholeImageUrl(pics.get(0));
                    Glide.with(mContext).load(picUrl).apply(mDefaultOptions).into
                            (holder.ivPic);
                } else {
                    holder.ivPic.setImageResource(R.mipmap.ic_default_pic);
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

    private void setPaletteImage(final ImageView iv, final ViewGroup view, String
            imgUrl, RequestOptions options, final int defaultImg, final int defaultBg) {
        final int[] colors = {0xff8bc34a, 0xff8bc34a, 0xffff9800};
        Glide.with(mContext).asBitmap().load(imgUrl).apply(options).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap>
                    transition) {
                iv.setImageBitmap(resource);
                Palette.Builder pb = Palette.from(resource);
                pb.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        colors[2] = palette.getDarkVibrantColor(0xffff9800);

                        Drawable bgDrawable = new GradientDrawable(GradientDrawable
                                .Orientation.LEFT_RIGHT, colors);
                        view.setBackground(bgDrawable);
                    }
                });
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                iv.setImageResource(defaultImg);
                view.setBackgroundResource(defaultBg);
            }
        });
    }

}
