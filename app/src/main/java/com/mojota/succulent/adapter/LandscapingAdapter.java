package com.mojota.succulent.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojota on 18-8-3.
 */
public class LandscapingAdapter extends RecyclerView
        .Adapter<LandscapingAdapter.BaseViewHolder> {
    public static final int TYPE_PIC_ONE = 1;
    public static final int TYPE_PIC_TWO = 2;
    public static final int TYPE_PIC_THREE = 3;
    public static final int TYPE_PIC_FOUR = 4;

    private final Context mContext;
    private List<NoteInfo> mList;
    private OnImageClickListener mOnImageClickListener;

    private OneViewHolder mOneViewHolder;
    private TwoViewHolder mTwoViewHolder;
    private ThreeViewHolder mThreeViewHolder;
    private FourViewHolder mFourViewHolder;

    public interface OnImageClickListener {

        void onImageClick(ImageView view, String title, ArrayList<String>
                picUrls, int picPos);
    }

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

    public void setOnImageClickListener(OnImageClickListener listener) {
        mOnImageClickListener = listener;
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
        mOneViewHolder = new OneViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_landscaping_one, parent, false));
        if (viewType == TYPE_PIC_ONE) {
            return mOneViewHolder;
        } else if (viewType == TYPE_PIC_TWO) {
            return mTwoViewHolder = new TwoViewHolder(LayoutInflater.from
                    (mContext).inflate(R.layout.item_landscaping_two, parent,
                    false));
        } else if (viewType == TYPE_PIC_THREE) {
            return mThreeViewHolder = new ThreeViewHolder(LayoutInflater.from
                    (mContext).inflate(R.layout.item_landscaping_three,
                    parent, false));
        } else if (viewType == TYPE_PIC_FOUR) {
            mFourViewHolder = new FourViewHolder(LayoutInflater.from
                    (mContext).inflate(R.layout.item_landscaping_four,
                    parent, false));
            return mFourViewHolder;
        }
        return mOneViewHolder;
    }


    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int
            position) {
        final NoteInfo note = mList.get(position);
        if (note != null) {
            holder.tvTitle.setText(note.getNoteTitle());

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
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    int likeCount = note.getLikeCount();
                    if (isChecked) {
                        note.setLikeCount(likeCount + 1);
                        holder.tbLike.setTextOn(String.valueOf(note
                                .getLikeCount()));
                    } else {
                        note.setLikeCount(likeCount - 1);
                        holder.tbLike.setTextOff(String.valueOf(note
                                .getLikeCount()));
                    }
                }
            });

            if (note.getPermission() == 1) {
                holder.tbPermission.setChecked(true);
            } else {
                holder.tbPermission.setChecked(false);
            }

            RequestOptions requestOptions = GlobalUtil
                    .getDefaultRequestOptions().centerCrop();
            if (note.getPicUrls() != null) {
                if (holder instanceof TwoViewHolder) {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((TwoViewHolder) holder)
                            .ivPicOne);
                    Glide.with(mContext).load(note.getPicUrls().get(1)).apply
                            (requestOptions).into(((TwoViewHolder) holder)
                            .ivPicTwo);
                    ((TwoViewHolder) holder).ivPicOne.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (TwoViewHolder) holder).ivPicOne,
                                        note.getNoteTitle(), note.getPicUrls(), 0);
                            }
                        }
                    });
                    ((TwoViewHolder) holder).ivPicTwo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (TwoViewHolder) holder).ivPicTwo,
                                        note.getNoteTitle(), note.getPicUrls(), 1);
                            }
                        }
                    });
                } else if (holder instanceof ThreeViewHolder) {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((ThreeViewHolder) holder)
                            .ivPicOne);
                    Glide.with(mContext).load(note.getPicUrls().get(1)).apply
                            (requestOptions).into(((ThreeViewHolder) holder)
                            .ivPicTwo);
                    Glide.with(mContext).load(note.getPicUrls().get(2)).apply
                            (requestOptions).into(((ThreeViewHolder) holder)
                            .ivPicThree);
                    ((ThreeViewHolder) holder).ivPicOne.setOnClickListener
                            (new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (ThreeViewHolder) holder).ivPicOne,
                                        note.getNoteTitle(), note.getPicUrls(), 0);
                            }
                        }
                    });
                    ((ThreeViewHolder) holder).ivPicTwo.setOnClickListener
                            (new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (ThreeViewHolder) holder).ivPicTwo,
                                        note.getNoteTitle(), note.getPicUrls(), 1);
                            }
                        }
                    });
                    ((ThreeViewHolder) holder).ivPicThree.setOnClickListener
                            (new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (ThreeViewHolder) holder).ivPicThree,
                                        note.getNoteTitle(), note.getPicUrls(), 2);
                            }
                        }
                    });


                } else if (holder instanceof FourViewHolder) {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((FourViewHolder) holder)
                            .ivPicOne);
                    Glide.with(mContext).load(note.getPicUrls().get(1)).apply
                            (requestOptions).into(((FourViewHolder) holder)
                            .ivPicTwo);
                    Glide.with(mContext).load(note.getPicUrls().get(2)).apply
                            (requestOptions).into(((FourViewHolder) holder)
                            .ivPicThree);
                    Glide.with(mContext).load(note.getPicUrls().get(3)).apply
                            (requestOptions).into(((FourViewHolder) holder)
                            .ivPicFour);
                    ((FourViewHolder) holder).ivPicOne.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (FourViewHolder) holder).ivPicOne,
                                        note.getNoteTitle(), note.getPicUrls(), 0);
                            }
                        }
                    });
                    ((FourViewHolder) holder).ivPicTwo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (FourViewHolder) holder).ivPicTwo,
                                        note.getNoteTitle(), note.getPicUrls(), 1);
                            }
                        }
                    });
                    ((FourViewHolder) holder).ivPicThree.setOnClickListener
                            (new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (FourViewHolder) holder).ivPicThree,
                                        note.getNoteTitle(), note.getPicUrls(), 2);
                            }
                        }
                    });

                    ((FourViewHolder) holder).ivPicFour.setOnClickListener
                            (new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (FourViewHolder) holder).ivPicFour,
                                        note.getNoteTitle(), note.getPicUrls(), 3);
                            }
                        }
                    });
                } else {
                    Glide.with(mContext).load(note.getPicUrls().get(0)).apply
                            (requestOptions).into(((OneViewHolder) holder)
                            .ivPicOne);
                    ((OneViewHolder) holder).ivPicOne.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnImageClickListener != null) {
                                mOnImageClickListener.onImageClick((
                                        (OneViewHolder) holder).ivPicOne,
                                        note.getNoteTitle(), note.getPicUrls(), 0);
                            }
                        }
                    });
                }
            }
        }

    }

}
