package com.mojota.succulent.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mojota.succulent.activity.ImageBrowserActivity;
import com.mojota.succulent.activity.LandscapingAddActivity;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.activity.OnImageClickListener;
import com.mojota.succulent.adapter.LandscapingAdapter;
import com.mojota.succulent.adapter.OnItemLongclickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.NoteResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.ActivityUtil;
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
 * 造景后花园
 * Created by mojota on 18-7-23
 */
public class LandscapingFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, OnItemLongclickListener, LoadMoreRecyclerView
                .OnLoadListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PAGE_SIZE = 10; // 每页的条数
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout mSwipeRefresh;
    private LoadMoreRecyclerView mRvLandscaping;
    private FloatingActionButton mFabAddLandscaping;
    private LandscapingAdapter mLandscapingAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();
    private WrapRecycleAdapter mWrapAdapter;
    private String mUpdateTime = "";


    public LandscapingFragment() {
        // Required empty public constructor
    }

    public static LandscapingFragment newInstance() {
        LandscapingFragment fragment = new LandscapingFragment();
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
        View view = inflater.inflate(R.layout.fragment_landscaping, container, false);

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(this);
        mRvLandscaping = view.findViewById(R.id.rv_landscaping);
        mLandscapingAdapter = new LandscapingAdapter(getActivity(), mList);
        mLandscapingAdapter.setOnItemLongClickListener(this);
        mWrapAdapter = new WrapRecycleAdapter(mLandscapingAdapter);
        mRvLandscaping.setAdapter(mWrapAdapter);
        mRvLandscaping.setOnLoadListener(this);
        mFabAddLandscaping = view.findViewById(R.id.fab_add_landscaping);
        mFabAddLandscaping.setOnClickListener(this);

        onRefresh();
        return view;
    }

    private void getData(final String updateTime) {
        String url = UrlConstants.GET_NOTE_LIST_URL;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userId", UserUtil.getCurrentUserId());
        paramMap.put("noteType", "2");
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
                    mRvLandscaping.loadMoreSuccess(list == null ? 0 : list.size(), PAGE_SIZE);
                } else {
                    mRvLandscaping.loadMoreFailed();
                    GlobalUtil.makeToast(R.string.str_no_data);
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                mSwipeRefresh.setRefreshing(false);
                mRvLandscaping.loadMoreFailed();
                GlobalUtil.makeToast(R.string.str_network_error);
            }
        }));
        VolleyUtil.execute(request);
    }

    private void setDataToView() {
        mLandscapingAdapter.setList(mList);
        mWrapAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_landscaping:
                Intent intent = new Intent(getActivity(), LandscapingAddActivity.class);
                startActivityForResult(intent, CodeConstants.REQUEST_ADD);
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
        if (mRvLandscaping.isLoadSuccess()) { // 若上次失败页码不再变化
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
                    mRvLandscaping.smoothScrollToPosition(0);
                }
                break;
        }
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
        new AlertDialog.Builder(getContext()).setTitle("确认删除?").setPositiveButton("删除", new
                DialogInterface.OnClickListener() {

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

        RequestUtils.requestDelete(note);
    }

}
