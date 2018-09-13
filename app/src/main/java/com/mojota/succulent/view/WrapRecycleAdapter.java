package com.mojota.succulent.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 可以设置header和footer的adapter
 * Created by wangjing on 17-9-21.
 * Update by wangjing on 17-11-2.
 */

public class WrapRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_FOOTER = 3;

    private RecyclerView.Adapter mAdapter;
    private View mFooterView;
    private View mHeaderView;


    public WrapRecycleAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addFooterView(View footerView) {
        mFooterView = footerView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            if (mHeaderView != null && mFooterView == null) {
                return mAdapter.getItemCount() + 1; //为header加1
            } else if (mHeaderView == null && mFooterView != null) {
                return mAdapter.getItemCount() + 1; //为footer加1
            } else if (mHeaderView != null && mFooterView != null) {
                return mAdapter.getItemCount() + 2; //为header和footer共加2
            } else {
                return mAdapter.getItemCount();
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && mFooterView == null) {
            if (position == 0) {
                return TYPE_HEADER;
            }
        } else if (mHeaderView == null && mFooterView != null) {
            if (position == mAdapter.getItemCount()) {
                return TYPE_FOOTER;
            }
        } else if (mHeaderView != null && mFooterView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == mAdapter.getItemCount() + 1) {
                return TYPE_FOOTER;
            }
        }
        return mAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(mFooterView);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof FooterViewHolder) {

        } else {
            if (mHeaderView != null) {
                mAdapter.onBindViewHolder(holder, position - 1);
            } else {
                mAdapter.onBindViewHolder(holder, position);
            }
        }
    }

}
