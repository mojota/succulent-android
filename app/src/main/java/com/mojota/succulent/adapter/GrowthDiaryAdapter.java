package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
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
 * Created by mojota on 18-7-24.
 */
public class GrowthDiaryAdapter extends RecyclerView.Adapter<GrowthDiaryAdapter.ViewHolder> {

    private Context mContext;
    private List<NoteInfo> mList;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongclickListener mOnItemLongcickListener;
    private RequestOptions mRequestOptions;

    public interface OnItemClickListener {

        void onItemClick(ImageView view, NoteInfo diary, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPic;
        private final TextView tvTitle;
        //        private final TextView tvTime;
        private final ToggleButton tbLike;
        private final ToggleButton tbPermission;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvTime = itemView.findViewById(R.id.tv_time);
            tbLike = itemView.findViewById(R.id.tb_like);
            tbPermission = itemView.findViewById(R.id.tb_permission);
        }
    }

    public GrowthDiaryAdapter(Context context, List<NoteInfo> list) {
        mContext = context;
        mList = list;
        mRequestOptions = GlobalUtil.getDefaultOptions().centerCrop();
    }

    public void setList(List<NoteInfo> list) {
        mList = list;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongcickListener(OnItemLongclickListener onItemLongcickListener) {
        this.mOnItemLongcickListener = onItemLongcickListener;
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
                .item_diary, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoteInfo diary = mList.get(position);
        if (diary != null) {
            holder.tvTitle.setText(diary.getNoteTitle());
//            holder.tvTime.setText(diary.getUpdateTime());

            holder.tbLike.setTextOn(String.valueOf(diary.getLikeCount()));
            holder.tbLike.setTextOff(String.valueOf(diary.getLikeCount()));
            holder.tbLike.setText(String.valueOf(diary.getLikeCount()));
            holder.tbLike.setChecked(diary.getIsLike() == 1);
            holder.tbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeCount = diary.getLikeCount();
                    if (holder.tbLike.isChecked()) {
                        diary.setLikeCount(likeCount + 1);
                        diary.setIsLike(1);
                        requestLike(diary, 1);
                        holder.tbLike.setTextOn(String.valueOf(diary.getLikeCount()));
                        holder.tbLike.setTextOff(String.valueOf(diary.getLikeCount()));
                        holder.tbLike.setText(String.valueOf(diary.getLikeCount()));
                    } else {
                        diary.setLikeCount(likeCount - 1);
                        diary.setIsLike(0);
                        requestLike(diary, 0);
                        holder.tbLike.setTextOn(String.valueOf(diary.getLikeCount()));
                        holder.tbLike.setTextOff(String.valueOf(diary.getLikeCount()));
                        holder.tbLike.setText(String.valueOf(diary.getLikeCount()));
                    }

                }
            });

            holder.tbPermission.setChecked(diary.getPermission() == 1);
            holder.tbPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newPermission = 0;
                    if (holder.tbPermission.isChecked()) {
                        newPermission = 1;
                    } else {
                        newPermission = 0;
                    }
                    requestPermission(diary, newPermission, holder.tbPermission);
                }
            });

            List<String> pics = GlobalUtil.getStringList(diary.getPicUrls());
            if (pics != null && pics.size() > 0) {
                Glide.with(mContext).load(pics.get(0)).apply(mRequestOptions).into(holder.ivPic);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.ivPic, diary, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongcickListener != null) {
                    mOnItemLongcickListener.onItemLongclick(position);
                }
                return false;
            }
        });

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
