package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.interfaces.OnItemLongclickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;

import java.util.List;

/**
 * Created by 王静 on 18-8-3.
 */
public class LandscapingAdapter extends RecyclerView.Adapter<LandscapingAdapter.ViewHolder> {

    private final Context mContext;
    private List<NoteInfo> mList;
    private OnItemLongclickListener mOnItemLongClickListener;
    private RequestOptions mRoundedCornersOptions;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView rvPics;
        private final TextView tvTitle;
        private final TextView tvTime;
        private final ToggleButton tbLike;
        private final ToggleButton tbPermission;

        public ViewHolder(View itemView) {
            super(itemView);
            rvPics = itemView.findViewById(R.id.rv_pics);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
        }
    }


    public LandscapingAdapter(Context context, List<NoteInfo> list) {
        mContext = context;
        mList = list;
        mRoundedCornersOptions = GlobalUtil.getRoundedCornersOptions();
    }

    public void setList(List<NoteInfo> list) {
        mList = list;
    }


    public OnItemLongclickListener setOnItemLongClickListener(OnItemLongclickListener
                                                                      onItemLongclickListener) {
        return mOnItemLongClickListener = onItemLongclickListener;
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
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                .item_landscaping, parent, false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoteInfo note = mList.get(position);
        if (note != null) {
            if (!TextUtils.isEmpty(note.getNoteTitle())) {
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(note.getNoteTitle());
            } else {
                holder.tvTitle.setVisibility(View.GONE);
            }
            holder.tvTime.setText(GlobalUtil.formatDisplayTime(note.getUpdateTime()));

            holder.tbLike.setTextOn(String.valueOf(note.getLikeyCount()));
            holder.tbLike.setTextOff(String.valueOf(note.getLikeyCount()));
            holder.tbLike.setText(String.valueOf(note.getLikeyCount()));
            holder.tbLike.setChecked(note.getIsLike() == 1);

            holder.tbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeyCount = note.getLikeyCount();
                    int isLikey;
                    if (holder.tbLike.isChecked()) {
                        note.setLikeyCount(likeyCount + 1);
                        isLikey = 1;
                    } else {
                        note.setLikeyCount(likeyCount - 1);
                        isLikey = 0;
                    }
                    note.setIsLike(isLikey);
                    RequestUtils.requestLike(note.getNoteId(), isLikey);
                    holder.tbLike.setTextOn(String.valueOf(note.getLikeyCount()));
                    holder.tbLike.setTextOff(String.valueOf(note.getLikeyCount()));
                    holder.tbLike.setText(String.valueOf(note.getLikeyCount()));
                }
            });

            holder.tbPermission.setChecked(note.getPermission() == 1);
            holder.tbPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newPermission = holder.tbPermission.isChecked() ? 1 : 0;
                    RequestUtils.requestPermission(note, newPermission, holder.tbPermission);
                }
            });

            // 图
            List<String> pics = GlobalUtil.getStringList(note.getPicUrls());
            if (pics != null && pics.size() > 0) {
                holder.rvPics.setVisibility(View.VISIBLE);
                holder.rvPics.setAdapter(new ImageAdapter(mContext, pics, note.getNoteTitle(),
                        mRoundedCornersOptions));
            } else {
                holder.rvPics.setVisibility(View.GONE);
            }

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongclick(position);
                    }
                    return false;
                }
            });
        }
    }

}
