package com.mojota.succulent.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.interfaces.OnImageClickListener;
import com.mojota.succulent.model.NoteDetail;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.List;

/**
 * Created by mojota on 18-7-24.
 */
public class DiaryDetailAdapter extends RecyclerView.Adapter<DiaryDetailAdapter.ViewHolder> {

    private final RequestOptions mRequestOptions;
    private Activity mContext;
    private List<NoteDetail> mList;
    private OnImageClickListener mOnImageClickListener;
    private OnItemOperateListener mOnItemOperateListener;
    private boolean mIsReadOnly = true;

    public interface OnItemOperateListener {

        void onEdit(NoteDetail diary);

        void onDelete(NoteDetail diary, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvContent;
        private final TextView tvTime;
        private final ImageView ivPic0;
        private final ImageView ivPic1;
        private final Button btEdit;
        private final Button btDelete;
        private final ViewGroup layoutPic;

        public ViewHolder(View itemView) {
            super(itemView);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            layoutPic = itemView.findViewById(R.id.layout_pic);
            ivPic0 = itemView.findViewById(R.id.iv_pic0);
            ivPic1 = itemView.findViewById(R.id.iv_pic1);
        }
    }

    public DiaryDetailAdapter(Activity context, List<NoteDetail> list) {
        mContext = context;
        mList = list;
        mRequestOptions = GlobalUtil.getRoundedCornersOptions();
    }

    public void setList(List<NoteDetail> list) {
        mList = list;
    }

    public void setIsReadOnly(boolean readOnly) {
        mIsReadOnly = readOnly;
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        mOnImageClickListener = listener;
    }

    public void setOnItemOperateListener(OnItemOperateListener listener) {
        mOnItemOperateListener = listener;
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
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary_detail, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == 0) { //控制进场动画
            holder.ivPic0.setTransitionName("0");
            holder.ivPic0.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver
                    .OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    holder.ivPic0.getViewTreeObserver().removeOnPreDrawListener(this);
                    mContext.startPostponedEnterTransition();
                    return true;
                }
            });
        }
        if (mIsReadOnly){
            holder.btDelete.setVisibility(View.GONE);
            holder.btEdit.setVisibility(View.GONE);
        }

        final NoteDetail diary = mList.get(position);
        if (diary != null) {
            if (TextUtils.isEmpty(diary.getContent())) {
                holder.tvContent.setVisibility(View.GONE);
            } else {
                holder.tvContent.setVisibility(View.VISIBLE);
                holder.tvContent.setText(diary.getContent());
            }
            holder.tvTime.setText(GlobalUtil.formatDisplayTime(diary.getCreateTime()));

            holder.ivPic0.setOnClickListener(null);
            holder.ivPic1.setOnClickListener(null);

            final List<String> pics = GlobalUtil.getStringList(diary.getPicUrls());
            if (pics != null && pics.size() > 0) {
                holder.layoutPic.setVisibility(View.VISIBLE);
                for (int i = 0; i < pics.size(); i++) {
                    RequestBuilder<Drawable> rb = Glide.with(mContext).load(OssUtil
                            .getWholeImageUrl(pics.get(i))).apply(mRequestOptions);
                    if (i == 0) {
                        holder.ivPic1.setImageResource(0);
                        rb.into(holder.ivPic0);
                        holder.ivPic0.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mOnImageClickListener != null) {
                                    mOnImageClickListener.onImageClick(holder.ivPic0, diary
                                            .getContent(), pics, 0);
                                }
                            }
                        });
                    } else {
                        rb.into(holder.ivPic1);
                        holder.ivPic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mOnImageClickListener != null) {
                                    mOnImageClickListener.onImageClick(holder.ivPic1, diary
                                            .getContent(), pics, 1);
                                }
                            }
                        });
                    }
                }
            } else {
                holder.ivPic0.setImageResource(0);
                holder.ivPic1.setImageResource(0);
                holder.layoutPic.setVisibility(View.GONE);
            }

            holder.btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemOperateListener != null) {
                        mOnItemOperateListener.onEdit(diary);
                    }
                }
            });

            holder.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemOperateListener != null) {
                        mOnItemOperateListener.onDelete(diary, position);
                    }

                }
            });
        }
    }

}
