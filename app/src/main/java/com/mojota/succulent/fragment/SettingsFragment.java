package com.mojota.succulent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mojota.succulent.R;
import com.mojota.succulent.activity.AboutActivity;
import com.mojota.succulent.utils.CheckUpdateUtil;
import com.mojota.succulent.utils.GlobalUtil;

/**
 *
 * Created by 王静 on 19-1-3
*/
public class SettingsFragment extends PreferenceFragment {

    private Preference mSettingUpdate;
    private Preference mSettingAbout;

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
        // 检查升级
        mSettingUpdate = findPreference("setting_update");
        mSettingUpdate.setSummary(getString(R.string.str_version) + GlobalUtil
                .getVersionName());
        mSettingUpdate.setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                CheckUpdateUtil.checkUpdate(getActivity(), true);
                return true;
            }
        });

        // 关于
        mSettingAbout = findPreference("setting_about");
        mSettingAbout.setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

}
