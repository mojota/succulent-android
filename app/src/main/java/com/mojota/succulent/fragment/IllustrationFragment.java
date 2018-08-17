package com.mojota.succulent.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.FamilyAdapter;
import com.mojota.succulent.adapter.OnItemClickListener;
import com.mojota.succulent.model.Family;
import com.mojota.succulent.model.FamilyResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 图鉴
 * Created by mojota on 18-8-16
 */
public class IllustrationFragment extends Fragment implements OnItemClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRvFamily;
    private FamilyAdapter mFamilyAdapter;
    private List<Family> mFamilyList = new ArrayList<Family>();


    public IllustrationFragment() {
        // Required empty public constructor
    }

    public static IllustrationFragment newInstance() {
        IllustrationFragment fragment = new IllustrationFragment();
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
        View view = inflater.inflate(R.layout.fragment_illustration, container, false);
        mRvFamily = view.findViewById(R.id.rv_family);
        mFamilyAdapter = new FamilyAdapter(mFamilyList, this);
        mRvFamily.setAdapter(mFamilyAdapter);

        getData();
        return view;
    }

    private void getData() {
        FamilyResponseInfo resInfo = new Gson().fromJson(TestUtil.getFamilyList(),
                FamilyResponseInfo.class);
        mFamilyList = resInfo.getList();

        setDataToView();
    }


    private void setDataToView() {
        mFamilyAdapter.setList(mFamilyList);
        mFamilyAdapter.notifyDataSetChanged();
        if (mFamilyList != null && mFamilyList.size() > 0) {
            showFragment(0);
        }
    }

    @Override
    public void onItemClick(int position) {
        showFragment(position);
    }

    private void showFragment(int position) {
        Fragment fragment = SpeciesFragment.newInstance(mFamilyList.get(position));
        getChildFragmentManager().beginTransaction().replace(R.id.layout_container,
                fragment).commit();
    }
}
