package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mojota.succulent.R;
import com.mojota.succulent.model.Species;
import com.mojota.succulent.utils.GlobalUtil;

import java.util.List;

/**
 * Created by mojota on 18-8-16.
 */
public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<Species> mList;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivPic = itemView.findViewById(R.id.iv_pic);
        }
    }

    public SpeciesAdapter(List<Species> list, OnItemClickListener listener) {
        mList = list;
        mOnItemClickListener = listener;
    }

    public void setList(List<Species> list) {
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
        mContext = parent.getContext();
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_species, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Species species = mList.get(position);
        if (species != null) {
            holder.tvName.setText(species.getSpeciesName());
            Glide.with(mContext).load(species.getSpeciesPicUrl()).apply(GlobalUtil
                    .getDefaultRequestOptions().centerCrop()).into(holder.ivPic);
        }
    }


}
