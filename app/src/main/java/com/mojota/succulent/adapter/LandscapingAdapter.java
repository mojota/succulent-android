package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.request.RequestOptions;
import com.mojota.succulent.R;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mojota on 18-8-3.
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
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R
                .layout.item_landscaping, parent, false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoteInfo note = mList.get(position);
        if (note != null) {
            holder.tvTitle.setText(note.getNoteTitle());
            holder.tvTime.setText(note.getUpdateTime());

            holder.tbLike.setTextOn(String.valueOf(note.getLikeCount()));
            holder.tbLike.setTextOff(String.valueOf(note.getLikeCount()));
            holder.tbLike.setText(String.valueOf(note.getLikeCount()));
            holder.tbLike.setChecked(note.getIsLike() == 1);

            holder.tbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeCount = note.getLikeCount();
                    if (holder.tbLike.isChecked()) {
                        note.setLikeCount(likeCount + 1);
                        note.setIsLike(1);
                        requestLike(note, 1);
                        holder.tbLike.setTextOn(String.valueOf(note.getLikeCount()));
                        holder.tbLike.setTextOff(String.valueOf(note.getLikeCount()));
                        holder.tbLike.setText(String.valueOf(note.getLikeCount()));
                    } else {
                        note.setLikeCount(likeCount - 1);
                        note.setIsLike(0);
                        requestLike(note, 0);
                        holder.tbLike.setTextOn(String.valueOf(note.getLikeCount()));
                        holder.tbLike.setTextOff(String.valueOf(note.getLikeCount()));
                        holder.tbLike.setText(String.valueOf(note.getLikeCount()));
                    }
                }
            });

            holder.tbPermission.setChecked(note.getPermission() == 1);
            holder.tbPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newPermission = 0;
                    if (holder.tbPermission.isChecked()) {
                        newPermission = 1;
                    } else {
                        newPermission = 0;
                    }
                    requestPermission(note, newPermission, holder.tbPermission);
                }
            });

            // 图
            List<String> pics = GlobalUtil.getStringList(note.getPicUrls());
            if (note.getPicUrls() != null) {
                holder.rvPics.setAdapter(new ImageAdapter(mContext, pics, note
                        .getNoteTitle(), mRoundedCornersOptions));
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


    /**
     * 请求网络赞
     */
    private void requestLike(NoteInfo note, int isLike) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", note.getNoteId());
        map.put("isLike", String.valueOf(isLike));
        RequestUtils.commonRequest(UrlConstants.NOTE_LIKE_URL, map, CodeConstants.REQUEST_LIKE,
                null);
    }


    /**
     * 请求网络修改权限
     */
    private void requestPermission(final NoteInfo note, final int newPermission, final ToggleButton tbPermission) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", note.getNoteId());
        map.put("permission", String.valueOf(newPermission));
        RequestUtils.commonRequest(UrlConstants.NOTE_PERMISSION_CHANGE_URL, map, CodeConstants
                .REQUEST_PERMISSION_CHANGE, new RequestUtils.RequestListener() {

            @Override
            public void onRequestSuccess(int requestCode) {
                note.setPermission(newPermission);
            }

            @Override
            public void onRequestFailure(int requestCode) {
                int oldPermission = note.getPermission();
                note.setPermission(oldPermission);
                tbPermission.setChecked(oldPermission == 1);
            }
        });
    }

}
