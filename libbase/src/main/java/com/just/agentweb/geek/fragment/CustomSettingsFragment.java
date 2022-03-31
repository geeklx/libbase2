package com.just.agentweb.geek.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.geek.common.CustomSettings;

/**
 * Created by cenxiaozhong on 2017/5/26.
 * source code  https://github.com/Justson/AgentWeb
 */

public class CustomSettingsFragment extends AgentWebFragment {

    public static AgentWebFragment getInstance(Bundle bundle) {

        CustomSettingsFragment mCustomSettingsFragment = new CustomSettingsFragment();
        if (bundle != null){
            mCustomSettingsFragment.setArguments(bundle);
        }
        return mCustomSettingsFragment;

    }

    @Override
    protected void setTitle(WebView view, String title) {
        super.setTitle(view, title);
        if (!TextUtils.isEmpty(title)) {
            if (title.length() > 10) {
                title = title.substring(0, 10).concat("...");
            }
        }
        mTitleTextView.setText(title);
    }

    @Override
    public IAgentWebSettings getSettings() {
        return new CustomSettings(getActivity());
    }
}
