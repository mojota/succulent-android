package com.mojota.succulent.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.AnswerInfo;
import com.mojota.succulent.model.ResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mojota on 18-08-22.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private final int mTotalWidth;
    private List<AnswerInfo> mList;
    private Activity mActivity;
    private RequestOptions mOptions;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivAvatar;
        private final TextView tvNickname;
        private final TextView tvAnswer;
        private final ToggleButton tbAnswerAll;
        private final TextView tvTime;
        private final ViewGroup layoutUser;
        private final ToggleButton tbUp;
        private final ImageView ivUp;

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
        }
    }

    public AnswerAdapter(Activity context, List<AnswerInfo> list) {
        this.mActivity = context;
        this.mList = list;
        mOptions = GlobalUtil.getDefaultAvatarRequestOptions();
        mTotalWidth = GlobalUtil.getScreenWidth() - context.getResources()
                .getDimensionPixelSize(R.dimen.item_qa_margin);
    }


    public void setList(List<AnswerInfo> list) {
        this.mList = list;
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
        return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout
                .item_answer, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AnswerInfo answer = mList.get(position);
        if (answer != null) {
            String avatar = answer.getAvatarUrl();
            Glide.with(mActivity).load(avatar).apply(mOptions).into(holder.ivAvatar);
            holder.tvNickname.setText(answer.getNickname());

            if (!TextUtils.isEmpty(answer.getAnswerContent())) {
                String answerContent = answer.getAnswerContent().replaceAll("\n+", "\n");
                holder.tvAnswer.setText(answerContent);
                if (answerContent.length() >= (int) (mTotalWidth / holder.tvAnswer
                        .getTextSize()) * 5) {
                    holder.tbAnswerAll.setVisibility(View.VISIBLE);
                } else {
                    holder.tbAnswerAll.setVisibility(View.GONE);
                }
                holder.tbAnswerAll.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean
                            isChecked) {
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

            holder.tvTime.setText(answer.getAnswerTime());

            holder.tbUp.setText(answer.getAnswerUpCount());
            holder.tbUp.setTextOn(answer.getAnswerUpCount());
            holder.tbUp.setTextOff(answer.getAnswerUpCount());
            holder.tbUp.setChecked("1".equals(answer.getAnswerUpState()));
            holder.tbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.tbUp.isChecked()) {
                        GlobalUtil.startAnim(mActivity, holder.ivUp);
                        qaUp(answer.getAnswerId(), 1);
                        try {
                            int count = Integer.parseInt(answer.getAnswerUpCount());
                            answer.setAnswerUpCount(String.valueOf(count + 1));
                            answer.setAnswerUpState("1");
                            holder.tbUp.setText(answer.getAnswerUpCount());
                            holder.tbUp.setTextOn(answer.getAnswerUpCount());
                            holder.tbUp.setTextOff(answer.getAnswerUpCount());
                        } catch (Exception e) {
                        }
                    } else {
                        qaUp(answer.getAnswerId(), 0);
                        try {
                            int count = Integer.parseInt(answer.getAnswerUpCount());
                            if (count > 0) {
                                answer.setAnswerUpCount(String.valueOf(count - 1));
                                answer.setAnswerUpState("0");
                                holder.tbUp.setText(answer.getAnswerUpCount());
                                holder.tbUp.setTextOn(answer.getAnswerUpCount());
                                holder.tbUp.setTextOff(answer.getAnswerUpCount());
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });
        }
    }


    /**
     * 问答顶
     *
     * @param answerId 回答ID， 传值时表示操作回答
     * @param upState  点赞操作状态：0-取消点赞，1-点赞
     */
    public static void qaUp(String answerId, int upState) {
        String url = "url";
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", "");
        map.put("answerId", answerId);
        map.put("upState", String.valueOf(upState));
        GsonPostRequest<ResponseInfo> request = new GsonPostRequest<ResponseInfo>(url, null,
                map, ResponseInfo.class, new Response.Listener<ResponseInfo>() {

            @Override
            public void onResponse(ResponseInfo repInfo) {
                if (repInfo != null && "0".equals(repInfo.getCode())) {
                }
            }
        }, new VolleyErrorListener());
        VolleyUtil.execute(request);
    }

}
