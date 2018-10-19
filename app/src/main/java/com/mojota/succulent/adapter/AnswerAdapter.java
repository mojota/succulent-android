package com.mojota.succulent.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.AnswerInfo;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;
import com.mojota.succulent.utils.UserUtil;

import java.util.List;

/**
 * Created by mojota on 18-08-22.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private final int mTotalWidth;
    private List<AnswerInfo> mList;
    private Activity mActivity;
    private RequestOptions mOptions;
    private OnItemDeleteListener mOnItemDeleteListener;

    public interface OnItemDeleteListener{

        void onDelete(AnswerInfo answer, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivAvatar;
        private final TextView tvNickname;
        private final TextView tvAnswer;
        private final ToggleButton tbAnswerAll;
        private final TextView tvTime;
        private final ViewGroup layoutUser;
        private final ToggleButton tbUp;
        private final ImageView ivUp;
        private final Button btDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvNickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            layoutUser = (ViewGroup) itemView.findViewById(R.id.layout_user);
            tvAnswer = (TextView) itemView.findViewById(R.id.tv_answer);
            tbAnswerAll = (ToggleButton) itemView.findViewById(R.id.tb_answer_all);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tbUp = (ToggleButton) itemView.findViewById(R.id.tb_up);
            ivUp = (ImageView) itemView.findViewById(R.id.iv_anim_up);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }

    public AnswerAdapter(Activity context, List<AnswerInfo> list) {
        this.mActivity = context;
        this.mList = list;
        mOptions = GlobalUtil.getDefaultAvatarOptions();
        mTotalWidth = GlobalUtil.getScreenWidth() - context.getResources().getDimensionPixelSize
                (R.dimen.item_qa_margin);
    }


    public void setList(List<AnswerInfo> list) {
        this.mList = list;
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
        return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_answer,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AnswerInfo answer = mList.get(position);
        holder.btDelete.setVisibility(View.GONE);
        if (answer != null) {
            UserInfo userInfo = answer.getUserInfo();
            if (userInfo != null) {
                holder.tvNickname.setText(UserUtil.getDisplayName(userInfo));
                Glide.with(mActivity).load(userInfo.getAvatarUrl()).apply(mOptions).into(holder
                        .ivAvatar);
                if (userInfo.getUserId().equals(UserUtil.getCurrentUserId())){
                    holder.btDelete.setVisibility(View.VISIBLE);
                    holder.btDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemDeleteListener != null) {
                                mOnItemDeleteListener.onDelete(answer, position);
                            }
                        }
                    });
                }
            }

            if (!TextUtils.isEmpty(answer.getAnswerContent())) {
                String answerContent = answer.getAnswerContent().replaceAll("\n+", "\n");
                holder.tvAnswer.setText(answerContent);
                if (answerContent.length() >= (int) (mTotalWidth / holder.tvAnswer.getTextSize())
                        * 5) {
                    holder.tbAnswerAll.setVisibility(View.VISIBLE);
                } else {
                    holder.tbAnswerAll.setVisibility(View.GONE);
                }
                holder.tbAnswerAll.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.tvAnswer.setMaxLines(Integer.MAX_VALUE);
                        } else {
                            holder.tvAnswer.setMaxLines(5);
                        }
                    }
                });
            } else {
                holder.tvAnswer.setText("");
            }

            holder.tvTime.setText(GlobalUtil.formatDisplayTime(answer.getAnswerTime()));

            holder.tbUp.setText(String.valueOf(answer.getUpCount()));
            holder.tbUp.setTextOn(String.valueOf(answer.getUpCount()));
            holder.tbUp.setTextOff(String.valueOf(answer.getUpCount()));
            holder.tbUp.setChecked(answer.getIsUp() == 1);
            holder.tbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int upCount = answer.getUpCount();
                    int isUp;
                    if (holder.tbUp.isChecked()) {
                        GlobalUtil.startAnim(mActivity, holder.ivUp);
                        answer.setUpCount(upCount + 1);
                        isUp = 1;
                    } else {
                        answer.setUpCount(upCount - 1);
                        isUp = 0;
                    }
                    answer.setIsUp(isUp);
                    RequestUtils.answerUp(answer.getAnswerId(), isUp);
                    holder.tbUp.setText(String.valueOf(answer.getUpCount()));
                    holder.tbUp.setTextOn(String.valueOf(answer.getUpCount()));
                    holder.tbUp.setTextOff(String.valueOf(answer.getUpCount()));

                }
            });
        }
    }

}
