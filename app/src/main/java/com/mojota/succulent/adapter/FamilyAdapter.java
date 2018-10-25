package com.mojota.succulent.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mojota.succulent.R;
import com.mojota.succulent.interfaces.OnItemClickListener;
import com.mojota.succulent.model.Family;

import java.util.List;

/**
 * Created by mojota on 18-8-16.
 */
public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<Family> mList;
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    private View mLastCheckView;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public FamilyAdapter(List<Family> list, OnItemClickListener listener) {
        mList = list;
        mOnItemClickListener = listener;
        mCheckStates.put(0, false);
    }

    public void setList(List<Family> list) {
        mList = list;
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_family, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Family family = mList.get(position);
        if (family != null) {
            holder.tvName.setText(family.getFamilyName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckStates.clear();
                    if (mLastCheckView != null) {
                        mLastCheckView.setEnabled(true);
                    }
                    mCheckStates.put(position, false);
                    holder.tvName.setEnabled(mCheckStates.get(position, true));
                    mLastCheckView = holder.tvName;
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(null, position);
                    }
                }
            });
        }
        holder.tvName.setEnabled(mCheckStates.get(position, true));
        if (position == 0 && mCheckStates.get(position) == false) {
            mLastCheckView = holder.tvName;
        }

    }


}
