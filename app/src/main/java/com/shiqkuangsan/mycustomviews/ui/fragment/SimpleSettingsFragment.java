package com.shiqkuangsan.mycustomviews.ui.fragment;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.widget.Toast;

import com.shiqkuangsan.cityselector.utils.ToastUtil;
import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by shiqkuangsan on 2016/11/18.
 *
 * @author shiqkuangsan
 * @summary MD风格使用Preference搭建设置页面
 */

public class SimpleSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_setting_frag);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findPreference("settings_data").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                CheckBoxPreference prefer = (CheckBoxPreference) preference;
                if ((boolean) newValue)
                    ToastUtil.showToast(getActivity(), "CheckBox选中了");
                else
                    ToastUtil.showToast(getActivity(), "CheckBox取消了");
                return true;
            }
        });

        findPreference("settings_feeling").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                EditTextPreference prefer = (EditTextPreference) preference;
                prefer.setSummary((String) newValue);
                return true;
            }
        });

        findPreference("settings_author").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ToastUtil.showToast(getActivity(), preference.getTitle().toString().trim() + " :" + preference.getSummary());
                return true;
            }
        });
        findPreference("settings_suggestion").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ToastUtil.showToast(getActivity(), preference.getTitle().toString() + " :" + preference.getSummary());
                return true;
            }
        });
        findPreference("settings_version").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ToastUtil.showToast(getActivity(), preference.getTitle().toString() + " : " + preference.getSummary());
                return true;
            }
        });

        findPreference("settings_ide").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                ListPreference prefer = (ListPreference) preference;
                prefer.setSummary((CharSequence) newValue);
                return true;
            }
        });

        findPreference("settings_ring").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse((String) newValue));
                RingtonePreference prefer = (RingtonePreference) preference;
                prefer.setSummary(ringtone.getTitle(getActivity()));
                return true;
            }
        });
    }
}
