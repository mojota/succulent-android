package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mojota.succulent.R;
import com.mojota.succulent.model.NoticeInfo;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.List;

/**
 * Created by mojota on 18-12-25
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {


    private List<NoticeInfo> mList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvTime;
        private final TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public NoticeAdapter(List<NoticeInfo> list) {
        this.mList = list;

    }

    public void setList(List<NoticeInfo> list) {
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
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notice, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoticeInfo noticeInfo = mList.get(position);
        if (noticeInfo != null) {
            holder.tvTitle.setText(noticeInfo.getNoticeTitle());
            holder.tvTime.setText(GlobalUtil.formatDisplayTime(noticeInfo.getNoticeTime
                    ()));
            holder.tvContent.setText(noticeInfo.getNoticeContent());
        }
    }


}
