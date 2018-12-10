package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.QuestionInfo;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UserUtil;

import java.util.List;

public class QaAdapter extends RecyclerView.Adapter<QaAdapter.ViewHolder> {

    private List<QuestionInfo> mList;
    private final OnItemClickListener mListener;
    private Context mContext;
    private RequestOptions mAvatarOptions;
    private RequestOptions mDefaultOptions;
    private OnItemDeleteListener mOnItemDeleteListener;

    public interface OnItemDeleteListener{
        void onDelete(QuestionInfo question, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvTitle;
        public final TextView tvTime;
        public final TextView tvAnswerCount;
        public final ImageView ivPic;
        private final TextView tvNickname;
        private final ImageView ivAvatar;
        private final Button btDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAnswerCount = itemView.findViewById(R.id.tv_answer_count);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }

    public QaAdapter(List<QuestionInfo> list, OnItemClickListener listener) {
        mList = list;
        mListener = listener;
        mDefaultOptions = GlobalUtil.getRoundedCornersOptions();
        mAvatarOptions = GlobalUtil.getDefaultAvatarOptions();
    }

    public void setList(List<QuestionInfo> list) {
        mList = list;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.mOnItemDeleteListener = onItemDeleteListener;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_qa, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.ivPic.setVisibility(View.GONE);
        holder.btDelete.setVisibility(View.GONE);
        holder.btDelete.setOnClickListener(null);
        final QuestionInfo questionInfo = mList.get(position);
        if (questionInfo != null) {
            holder.tvTitle.setText(questionInfo.getQuestionTitle());
            holder.tvTime.setText(GlobalUtil.formatDisplayTime(questionInfo.getQuestionTime()));
            holder.tvAnswerCount.setText(String.valueOf(questionInfo.getAnswerCount()));

            if (!TextUtils.isEmpty(questionInfo.getQuestionPicUrl())) {
                String picUrl = OssUtil.getWholeImageUrl(questionInfo.getQuestionPicUrl
                        ());
                Glide.with(mContext).load(picUrl).apply(mDefaultOptions).into(holder.ivPic);
                holder.ivPic.setVisibility(View.VISIBLE);
            } else {
                holder.ivPic.setVisibility(View.GONE);
            }
            UserInfo userInfo = questionInfo.getUserInfo();
            if (userInfo != null) {
                holder.tvNickname.setText(UserUtil.getDisplayName(userInfo));
                Glide.with(mContext).load(userInfo.getAvatarUrl()).apply(mAvatarOptions).into
                        (holder.ivAvatar);
                if (userInfo.getUserId().equals(UserUtil.getCurrentUserId())){
                    holder.btDelete.setVisibility(View.VISIBLE);
                    holder.btDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemDeleteListener != null) {
                                mOnItemDeleteListener.onDelete(questionInfo, position);
                            }
                        }
                    });
                }
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
