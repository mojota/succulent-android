package com.mojota.succulent.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 造景后花园
 * Created by mojota on 18-7-23
 */
public class LandscapingFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, OnImageClickListener, LandscapingAdapter
                .OnItemDeleteClick {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRvLandscaping;
    private FloatingActionButton mFabAddLandscaping;
    private LandscapingAdapter mLandscapingAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();


    public LandscapingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LandscapingFragment newInstance(String param1, String param2) {
        LandscapingFragment fragment = new LandscapingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        mRvLandscaping.setLayoutManager(glm);
        mLandscapingAdapter = new LandscapingAdapter(getActivity(), mList);
        mLandscapingAdapter.setOnImageClickListener(this);
        mLandscapingAdapter.setOnItemDeleteClick(this);
        mRvLandscaping.setAdapter(mLandscapingAdapter);
        mFabAddLandscaping = view.findViewById(R.id.fab_add_landscaping);
        mFabAddLandscaping.setOnClickListener(this);

        getData();
        return view;
    }

    private void getData() {

        mSwipeRefresh.setRefreshing(false);
        NoteResponseInfo resInfo = new Gson().fromJson(TestUtil.getLandscapingList(),
                NoteResponseInfo.class);
        mList = resInfo.getList();

        setDataToView();
    }

    private void setDataToView() {
        mLandscapingAdapter.setList(mList);
        mLandscapingAdapter.notifyDataSetChanged();
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
        getData();
    }

    @Override
    public void onImageClick(ImageView view, String title, ArrayList<String> picUrls, int
            picPos) {
        ActivityUtil.startImageBrowserActivity(getActivity(), view, title, picUrls, picPos);
    }

    @Override
    public void delete(final int position) {
        String title = mList.get(position).getNoteTitle();

        new AlertDialog.Builder(getContext()).setTitle("确认删除" + title + "?")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
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
        mList.remove(position);
        setDataToView();
    }

}
