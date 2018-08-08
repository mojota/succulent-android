package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.List;

/**
 * Created by mojota on 18-8-3.
 */
public class LandscapingAdapter extends RecyclerView.Adapter<LandscapingAdapter
        .BaseViewHolder> {
    public static final int TYPE_PIC_ONE = 1;
    public static final int TYPE_PIC_TWO = 2;
    public static final int TYPE_PIC_THREE = 3;
    public static final int TYPE_PIC_FOUR = 4;

    private final Context mContext;
    private List<NoteInfo> mList;

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final ToggleButton tbLike;
        private final ToggleButton tbPermission;

        public BaseViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
        }
    }

    public class OneViewHolder extends BaseViewHolder {
        private final ImageView ivPicOne;

        public OneViewHolder(View itemView) {
            super(itemView);
            ivPicOne = itemView.findViewById(R.id.iv_pic_one);
        }
    }

    public class TwoViewHolder extends BaseViewHolder {
        private final ImageView ivPicOne;
        private final ImageView ivPicTwo;

        public TwoViewHolder(View itemView) {
            super(itemView);
            ivPicOne = itemView.findViewById(R.id.iv_pic_one);
            ivPicTwo = itemView.findViewById(R.id.iv_pic_two);
        }
    }

    public class ThreeViewHolder extends BaseViewHolder {
        private final ImageView ivPicOne;
        private final ImageView ivPicTwo;
        private final ImageView ivPicThree;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            ivPicOne = itemView.findViewById(R.id.iv_pic_one);
            ivPicTwo = itemView.findViewById(R.id.iv_pic_two);
            ivPicThree = itemView.findViewById(R.id.iv_pic_three);
        }
    }

    public class FourViewHolder extends BaseViewHolder {
        private final ImageView ivPicOne;
        private final ImageView ivPicTwo;
        private final ImageView ivPicThree;
        private final ImageView ivPicFour;

        public FourViewHolder(View itemView) {
            super(itemView);
            ivPicOne = itemView.findViewById(R.id.iv_pic_one);
            ivPicTwo = itemView.findViewById(R.id.iv_pic_two);
            ivPicThree = itemView.findViewById(R.id.iv_pic_three);
            ivPicFour = itemView.findViewById(R.id.iv_pic_four);
        }
    }

    public LandscapingAdapter(Context context, List<NoteInfo> list) {
        mContext = context;
        mList = list;
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
        if (mList != null && mList.size() > 0 && mList.get(position) != null) {
            if (mList.get(position).getPicUrls() != null) {
                int picCount = mList.get(position).getPicUrls().size();
                if (picCount == 1) {
                    return TYPE_PIC_ONE;
                } else if (picCount == 2) {
                    return TYPE_PIC_TWO;
                } else if (picCount == 3) {
                    return TYPE_PIC_THREE;
                } else if (picCount >= 4) {
                    return TYPE_PIC_FOUR;
                }
            }
        }
        return TYPE_PIC_ONE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OneViewHolder oneViewHolder = new OneViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_landscaping_one, parent, false));
        if (viewType == TYPE_PIC_ONE) {
            return oneViewHolder;
        } else if (viewType == TYPE_PIC_TWO) {
            return new TwoViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                    .item_landscaping_two, parent, false));
        } else if (viewType == TYPE_PIC_THREE) {
            return new ThreeViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                    .item_landscaping_three, parent, false));
        } else if (viewType == TYPE_PIC_FOUR) {
            return new FourViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                    .item_landscaping_four, parent, false));
        }
        return oneViewHolder;
    }


    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        final NoteInfo note = mList.get(position);
        if (note != null) {
            holder.tvTitle.setText(note.getTitle());

            if (note.getHasLike() == 1) {
                holder.tbLike.setChecked(true);
                holder.tbLike.setTextOn(String.valueOf(note.getLikeCount()));
                holder.tbLike.setText(String.valueOf(note.getLikeCount()));
            } else {
                holder.tbLike.setChecked(false);
                holder.tbLike.setTextOff(String.valueOf(note.getLikeCount()));
                holder.tbLike.setText(String.valueOf(note.getLikeCount()));
            }
            holder.tbLike.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int likeCount = note.getLikeCount();
                    if (isChecked) {
                        note.setLikeCount(likeCount + 1);
                        holder.tbLike.setTextOn(String.valueOf(note.getLikeCount()));
                    } else {
                        note.setLikeCount(likeCount - 1);
                        holder.tbLike.setTextOff(String.valueOf(note.getLikeCount()));
                    }
                }
            });

            if (note.getPermission() == 1) {
                holder.tbPermission.setChecked(true);
            } else {
                holder.tbPermission.setChecked(false);
            }

            RequestOptions requestOptions = new RequestOptions().error(R.mipmap
                    .ic_default_pic).dontAnimate().centerCrop();
            if (note.getPicUrls() != null) {
                if (holder instanceof TwoViewHolder) {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((TwoViewHolder) holder).ivPicOne);
                    Glide.with(mContext).load(note.getPicUrls().get(1)).apply
                            (requestOptions).into(((TwoViewHolder) holder).ivPicTwo);
                } else if (holder instanceof ThreeViewHolder) {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((ThreeViewHolder) holder).ivPicOne);
                    Glide.with(mContext).load(note.getPicUrls().get(1)).apply
                            (requestOptions).into(((ThreeViewHolder) holder).ivPicTwo);
                    Glide.with(mContext).load(note.getPicUrls().get(2)).apply
                            (requestOptions).into(((ThreeViewHolder) holder).ivPicThree);
                } else if (holder instanceof FourViewHolder) {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((FourViewHolder) holder).ivPicOne);
                    Glide.with(mContext).load(note.getPicUrls().get(1)).apply
                            (requestOptions).into(((FourViewHolder) holder).ivPicTwo);
                    Glide.with(mContext).load(note.getPicUrls().get(2)).apply
                            (requestOptions).into(((FourViewHolder) holder).ivPicThree);
                    Glide.with(mContext).load(note.getPicUrls().get(3)).apply
                            (requestOptions).into(((FourViewHolder) holder).ivPicFour);
                } else {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((OneViewHolder) holder).ivPicOne);
                }
            }
        }

    }

}
