package com.mojota.succulent.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.OnItemClickListener;
import com.mojota.succulent.adapter.SpeciesAdapter;
import com.mojota.succulent.model.Family;
import com.mojota.succulent.model.Genus;
import com.mojota.succulent.model.Species;
import com.mojota.succulent.model.SpeciesResponseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 属种fragment
 * Created by mojota on 18-8-16
 */
public class SpeciesFragment extends Fragment implements OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String PARAM_FAMILY = "PARAM_FAMILY";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner mSpinnerGenus;
    private RecyclerView mRvSpecies;
    private SpeciesAdapter mSpeciesAdapter;
    private List<Species> mSpeciesList = new ArrayList<Species>();
    private Family mFamily;
    private List<Genus> mGenusList;
    private TextView mTvFamilyName;


    public SpeciesFragment() {
        // Required empty public constructor
    }

    public static SpeciesFragment newInstance(Family family) {
        SpeciesFragment fragment = new SpeciesFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_FAMILY, family);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFamily = (Family) getArguments().getSerializable(PARAM_FAMILY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_species, container, false);
        mTvFamilyName = view.findViewById(R.id.tv_family_name);
        mSpinnerGenus = view.findViewById(R.id.spinner_genus);
        mRvSpecies = view.findViewById(R.id.rv_species);
        mSpeciesAdapter = new SpeciesAdapter(mSpeciesList, this);
        mRvSpecies.setAdapter(mSpeciesAdapter);

        if (mFamily != null && mFamily.getGenusList() != null) {
            mTvFamilyName.setText(mFamily.getFamilyName());
            mGenusList = mFamily.getGenusList();
            List<String> genusStr = new ArrayList<String>();
            for (Genus genus : mGenusList) {
                genusStr.add(genus.getGenusName());
            }
            ArrayAdapter<String> genusAdapter = new ArrayAdapter<String>(getActivity(), R
                    .layout.item_genus, genusStr);
            mSpinnerGenus.setAdapter(genusAdapter);
        }
        mSpinnerGenus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long
                    id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        getData();
        return view;
    }


    private void getData() {
        SpeciesResponseInfo resInfo = new Gson().fromJson(TestUtil.getSpeciesList(),
                SpeciesResponseInfo.class);
        mSpeciesList = resInfo.getSpeciesList();

        setDataToView();
    }


    private void setDataToView() {
        mSpeciesAdapter.setList(mSpeciesList);
        mSpeciesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }
}
