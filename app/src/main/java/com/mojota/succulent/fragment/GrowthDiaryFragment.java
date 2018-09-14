package com.mojota.succulent.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mojota.succulent.activity.DiaryAddActivity;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.activity.DiaryDetailActivity;
import com.mojota.succulent.adapter.GrowthDiaryAdapter;
import com.mojota.succulent.adapter.OnItemLongclickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.NoteResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.AppLog;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.RequestUtils;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;
import com.mojota.succulent.view.LoadMoreRecyclerView;
import com.mojota.succulent.view.WrapRecycleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多肉成长记
 * Created by mojota on 18-7-23
 */
public class GrowthDiaryFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, GrowthDiaryAdapter.OnItemClickListener,
        OnItemLongclickListener, LoadMoreRecyclerView.OnLoadListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PAGE_SIZE = 10; // 每页的条数
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefresh;
    private LoadMoreRecyclerView mRvDiary;
    private FloatingActionButton mFabAdd;
    private GrowthDiaryAdapter mDiaryAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();
    private WrapRecycleAdapter mWrapAdapter;
    private String mUpdateTime = "";


    public GrowthDiaryFragment() {
        // Required empty public constructor
    }

    public static GrowthDiaryFragment newInstance() {
        GrowthDiaryFragment fragment = new GrowthDiaryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_growth_diary, container, false);

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(this);
        mRvDiary = view.findViewById(R.id.rv_my_diary);
        final GridLayoutManager glm = (GridLayoutManager) mRvDiary.getLayoutManager();
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mWrapAdapter.getItemViewType(position) == WrapRecycleAdapter.TYPE_FOOTER)
                        ? glm.getSpanCount() : 1;
            }
        });
        mDiaryAdapter = new GrowthDiaryAdapter(getActivity(), mList);
        mDiaryAdapter.setOnItemClickListener(this);
        mDiaryAdapter.setOnItemLongcickListener(this);
        mWrapAdapter = new WrapRecycleAdapter(mDiaryAdapter);
        mRvDiary.setAdapter(mWrapAdapter);
        mRvDiary.setOnLoadListener(this);
        mFabAdd = view.findViewById(R.id.fab_add_my);
        mFabAdd.setOnClickListener(this);

        onRefresh();
        return view;
    }

    private void getData(final String updateTime) {
        String url = UrlConstants.GET_NOTE_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userId", UserUtil.getCurrentUserId());
        paramMap.put("noteType", "1");
        paramMap.put("size", String.valueOf(PAGE_SIZE));
        paramMap.put("updateTime", updateTime);
        GsonPostRequest request = new GsonPostRequest(url, null, paramMap, NoteResponseInfo
                .class, new Response.Listener<NoteResponseInfo>() {

            @Override
            public void onResponse(NoteResponseInfo responseInfo) {
                mSwipeRefresh.setRefreshing(false);
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    if (TextUtils.isEmpty(updateTime)) {
                        mList.clear();
                    }
                    List<NoteInfo> list = responseInfo.getList();
                    mList.addAll(list);
                    setDataToView();
                    mRvDiary.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvDiary.loadMoreFailed();
                    GlobalUtil.makeToast(R.string.str_no_data);
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mSwipeRefresh.setRefreshing(false);
                mRvDiary.loadMoreFailed();
                GlobalUtil.makeToast(R.string.str_network_error);
            }
        }));
        VolleyUtil.execute(request);
    }


    private void setDataToView() {
        mDiaryAdapter.setList(mList);
        mWrapAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_my:
                if (UserUtil.isLogin()) {
                    Intent intent = new Intent(getActivity(), DiaryAddActivity.class);
                    intent.putExtra(DiaryAddActivity.KEY_MODE, CodeConstants.NOTE_ADD);
                    startActivityForResult(intent, CodeConstants.REQUEST_ADD);
                } else {
                    ActivityUtil.startLoginActivity(getActivity());
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        mUpdateTime = "";
        getData(mUpdateTime);
    }

    @Override
    public void onLoadMore() {
        if (mRvDiary.isLoadSuccess()) { // 若上次失败页码不再变化
            if (mList != null && mList.size() > 0) {
                mUpdateTime = mList.get(mList.size() - 1).getUpdateTime();
            }
        }
        getData(mUpdateTime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CodeConstants.REQUEST_ADD:
                if (resultCode == CodeConstants.RESULT_REFRESH) {
                    onRefresh();
                    mRvDiary.smoothScrollToPosition(0);
                }
                break;
            case CodeConstants.REQUEST_DETAIL:
                if (resultCode == CodeConstants.RESULT_REFRESH) {
                    onRefresh();
                    mRvDiary.smoothScrollToPosition(0);
                }
                break;

        }
    }

    @Override
    public void onItemClick(ImageView view, NoteInfo diary, int position) {
        ActivityUtil.startDiaryDetailActivity(getActivity(), view, diary);
    }

    @Override
    public void onItemLongclick(final int position) {
        String[] items = {"删除"};
        new AlertDialog.Builder(getContext()).setItems(items, new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    String title = mList.get(position).getNoteTitle();
                    deleteItem(position, title);
                }
            }
        }).show();
    }

    private void deleteItem(final int position, String title) {
        new AlertDialog.Builder(getContext()).setTitle("确认删除" + title + "?").setPositiveButton
                ("删除", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(position);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    private void deleteData(int position) {
        NoteInfo note = mList.get(position);
        mList.remove(position);
        mWrapAdapter.notifyItemRemoved(position);
        mWrapAdapter.notifyItemRangeChanged(0, mList.size());

        requestDelete(note);
    }

    /**
     * 请求网络删除
     */
    private void requestDelete(NoteInfo note) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", note.getNoteId());

        RequestUtils.commonRequest(UrlConstants.NOTE_DELETE_URL, map, CodeConstants
                .REQUEST_NOTE_DELETE, null);
    }

}
