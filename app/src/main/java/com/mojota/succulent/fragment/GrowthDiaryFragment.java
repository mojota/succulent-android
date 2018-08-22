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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mojota.succulent.activity.DiaryAddActivity;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.activity.DiaryDetailActivity;
import com.mojota.succulent.adapter.GrowthDiaryAdapter;
import com.mojota.succulent.adapter.OnItemLongclickListener;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.NoteResponseInfo;
import com.mojota.succulent.utils.CodeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 多肉成长记
 * Created by mojota on 18-7-23
 */
public class GrowthDiaryFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, GrowthDiaryAdapter.OnItemClickListener,
        OnItemLongclickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRvDiary;
    private FloatingActionButton mFabAdd;
    private GrowthDiaryAdapter mDiaryAdapter;
    private List<NoteInfo> mList = new ArrayList<NoteInfo>();


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
        mDiaryAdapter = new GrowthDiaryAdapter(getActivity(), mList);
        mDiaryAdapter.setOnItemClickListener(this);
        mDiaryAdapter.setOnItemLongcickListener(this);
        mRvDiary.setAdapter(mDiaryAdapter);
        mFabAdd = view.findViewById(R.id.fab_add_my);
        mFabAdd.setOnClickListener(this);

        getData();
        return view;
    }

    private void getData() {

        mSwipeRefresh.setRefreshing(false);
        NoteResponseInfo resInfo = new Gson().fromJson(TestUtil.getDiaryList(),
                NoteResponseInfo.class);
        mList = resInfo.getList();

        setDataToView();
    }


    private void setDataToView() {
        mDiaryAdapter.setList(mList);
        mDiaryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_my:
                Intent intent = new Intent(getActivity(), DiaryAddActivity.class);
                startActivityForResult(intent, CodeConstants.REQUEST_ADD);
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CodeConstants.REQUEST_ADD:
                if (resultCode == CodeConstants.RESULT_ADD) {
                    getData();
                }
                break;
            case CodeConstants.REQUEST_DETAIL:
                if (resultCode == CodeConstants.RESULT_DETAIL) {
                    getData();
                }
                break;

        }
    }

    @Override
    public void onItemClick(ImageView view, NoteInfo diary, int position) {
        Intent intent = new Intent(getActivity(), DiaryDetailActivity.class);
        intent.putExtra(DiaryDetailActivity.KEY_DIARY, diary);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (getActivity(), view, view.getTransitionName());
        startActivityForResult(intent, CodeConstants.REQUEST_DETAIL, options.toBundle());
    }

    @Override
    public void onItemLongclick(final int position) {
        String[] items = {"删除"};
        new AlertDialog.Builder(getContext()).setItems(items, new DialogInterface
                .OnClickListener() {
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
        mDiaryAdapter.notifyItemRemoved(position);
        mDiaryAdapter.notifyItemRangeChanged(0, mList.size());
    }
}
