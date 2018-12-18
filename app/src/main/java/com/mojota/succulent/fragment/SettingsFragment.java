package com.mojota.succulent.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mojota.succulent.R;
import com.mojota.succulent.utils.CheckUpdateUtil;
import com.mojota.succulent.utils.GlobalUtil;

public class SettingsFragment extends PreferenceFragment {

    private Preference mSettingUpdate;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        mSettingUpdate = findPreference("setting_update");
        mSettingUpdate.setSummary(getString(R.string.str_version) + GlobalUtil
                .getVersionName());
        mSettingUpdate.setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                CheckUpdateUtil.checkUpdate(getActivity());
                return true;
            }
        });
    }

}
