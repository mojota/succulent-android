package com.mojota.succulent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mojota.succulent.model.Landscaping;

import java.util.List;

/**
 * Created by mojota on 18-8-3.
 */
public class LandscapingAdapter extends RecyclerView.Adapter<LandscapingAdapter.ViewHolder> {


    private final Context mContext;
    private List<Landscaping> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    public LandscapingAdapter(Context context, List<Landscaping> list) {
        mContext = context;
        mList = list;
    }

    public void setList(List<Landscaping> list) {
        mList = list;
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
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

}
