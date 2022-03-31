package com.just.agentweb.geek.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.libbase.R;
import com.just.agentweb.geek.base.BaseAgentWebFragment;

/**
 * Created by cenxiaozhong on 2017/7/22.
 */

public class EasyWebFragment extends BaseAgentWebFragment {

    private ViewGroup mViewGroup;

    public static EasyWebFragment getInstance(Bundle bundle) {
        EasyWebFragment mEasyWebFragment = new EasyWebFragment();
        if (bundle != null) {
            mEasyWebFragment.setArguments(bundle);
        }
        return mEasyWebFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_agentweb, container, false);
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.mViewGroup.findViewById(R.id.linearLayout);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void onclickX(View v) {
        getActivity().finish();
    }

    @Override
    protected void onclickMore(View v) {
        showPoPup(v);
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

    @Nullable
    @Override
    protected String getUrl() {
        return "https://m.v.qq.com/index.html";
    }
}
