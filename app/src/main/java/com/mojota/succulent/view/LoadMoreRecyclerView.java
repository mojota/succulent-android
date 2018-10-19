package com.mojota.succulent.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mojota.succulent.R;

/**
 * 支持加载更多的recyclerView
 * Created by wangjing on 17-9-21.
 * Update by wangjing on 17-11-2.
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private static final int LOAD_SUCCESS_HAVE_MORE = 0;//加载成功且还有更多
    private static final int LOAD_SUCCESS_NO_MORE = 1;//全部加载完成
    private static final int IS_LOADING = 2;//正在加载
    private static final int LOAD_FAILED = 3;//加载失败

    private int mMode = -1; // 四种模式
    private boolean mIsLoadSuccess = true;//是否加载成功标识,默认为true

    private View mFooterView;
    private TextView mTvFooter;
    private View mLayoutFooter;
    private ProgressBar mPbLoading;

    private OnLoadListener mOnLoadListener;

    public interface OnLoadListener {
        void onLoadMore();
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
    }

    /**
     * 为适配器添加footer
     */
    public void setAdapter(WrapRecycleAdapter wrapAdapter) {
        // footer
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, this,
                false);
        wrapAdapter.addFooterView(mFooterView);

        mLayoutFooter = wrapAdapter.getFooterView().findViewById(R.id.layout_footer);
        mLayoutFooter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMode == LOAD_FAILED) {
                    startLoading();
                }
            }
        });
        mTvFooter = (TextView) wrapAdapter.getFooterView().findViewById(R.id.tv_footer);
        mPbLoading = (ProgressBar) wrapAdapter.getFooterView().findViewById(R.id.pb_loading);

        super.setAdapter(wrapAdapter);
    }


    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .MATCH_PARENT);
        setLayoutParams(params);
        setOverScrollMode(OVER_SCROLL_NEVER);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager()
                        instanceof LinearLayoutManager) {
                    LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

                    int lastVisiblePosition = llm.findLastVisibleItemPosition();
                    int totalItemCount = llm.getItemCount();

                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition > 0 &&
                            lastVisiblePosition == totalItemCount - 1 && canLoad()) {
                        startLoading();
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 开始加载
     */
    private void startLoading() {
        if (mOnLoadListener != null) {
            setupFooter(IS_LOADING);
            mOnLoadListener.onLoadMore();
        }
    }

    /**
     * 当footer显示时不可以加载
     */
    private boolean canLoad() {
        if (mLayoutFooter != null) {
            return mLayoutFooter.getVisibility() != VISIBLE;
        }
        return false;
    }

    /**
     * 显示footer,同时置状态
     */
    private void setupFooter(int mode) {
        if (mFooterView == null) {
            return;
        }
        mMode = mode;
        switch (mode) {
            case LOAD_SUCCESS_HAVE_MORE:
                mIsLoadSuccess = true;
                mTvFooter.setText("");
                mLayoutFooter.setVisibility(GONE);
                mPbLoading.setVisibility(GONE);
                break;
            case LOAD_SUCCESS_NO_MORE:
                mIsLoadSuccess = true;
                mTvFooter.setText(R.string.str_all_show);
                mLayoutFooter.setVisibility(VISIBLE);
                mPbLoading.setVisibility(GONE);
                break;
            case IS_LOADING:
                mTvFooter.setText(R.string.str_is_loading);
                mLayoutFooter.setVisibility(VISIBLE);
                mPbLoading.setVisibility(VISIBLE);
                break;
            case LOAD_FAILED:
                mIsLoadSuccess = false;
                mTvFooter.setText(R.string.str_load_faild);
                mLayoutFooter.setVisibility(VISIBLE);
                mPbLoading.setVisibility(GONE);
                break;
            default:
                mTvFooter.setText("");
                break;
        }
    }


    /**
     * 获取当前状态
     */
    public int getMode() {
        return mMode;
    }

    /**
     * 是否加载成功
     */
    public boolean isLoadSuccess() {
        return mIsLoadSuccess;
    }

    /**
     * 展示加载成功footer
     *
     * @param size     当前页的条数
     * @param pageSize 每页的条数
     */
    public void loadMoreSuccess(int size, int pageSize) {
        if (pageSize == 0) {
            setupFooter(LOAD_SUCCESS_HAVE_MORE);
        } else if (size == 0 || size % pageSize != 0) {
            setupFooter(LOAD_SUCCESS_NO_MORE);
        } else {
            setupFooter(LOAD_SUCCESS_HAVE_MORE);
        }
    }

    /**
     * 展示加载失败footer
     */
    public void loadMoreFailed() {
        setupFooter(LOAD_FAILED);
    }


}
