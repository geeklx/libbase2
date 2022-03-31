package com.just.agentweb.geek.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.just.agentweb.IWebLayout;
import com.geek.libbase.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;


public class JsWebLayout implements IWebLayout {

    private Activity mActivity;
    private final TwinklingRefreshLayout mTwinklingRefreshLayout;
    private BridgeWebView mWebView = null;

    public JsWebLayout(Activity activity) {
        this.mActivity = activity;
        mTwinklingRefreshLayout = (TwinklingRefreshLayout) LayoutInflater.from(activity).inflate(R.layout.fragment_jsweb, null);
        mTwinklingRefreshLayout.setPureScrollModeOn();
        mWebView = (BridgeWebView) mTwinklingRefreshLayout.findViewById(R.id.webView);
    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mTwinklingRefreshLayout;
    }

    @Nullable
    @Override
    public BridgeWebView getWebView() {
        return mWebView;
    }


}
