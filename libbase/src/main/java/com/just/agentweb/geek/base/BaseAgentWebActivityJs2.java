package com.just.agentweb.geek.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libutils.app.MyLogUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.just.agentweb.geek.AgentUtils;
import com.geek.libbase.R;
import com.just.agentweb.geek.widget.JsWebLayout;
import com.just.agentweb.geek.widget.WebLayout;

/**
 * Created by cenxiaozhong on 2017/7/22.
 * <p> 用于支撑H5代码写的垃圾的js类
 * source code  https://github.com/Justson/AgentWeb
 */

public abstract class BaseAgentWebActivityJs2 extends AppCompatActivity {

    protected ImageView mBackImageView;
    protected View mLineView;
    protected ImageView mFinishImageView;
    protected TextView mTitleTextView;
    protected ImageView mMoreImageView;
    protected PopupMenu mPopupMenu;
    protected AgentWeb mAgentWeb;
    private AgentWebUIControllerImplBase mAgentWebUIController;
    private ErrorLayoutEntity mErrorLayoutEntity;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    protected BridgeWebView mBridgeWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        BarUtils.setStatusBarLightMode(this, false);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        buildAgentWeb();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        buildAgentWeb();
    }

    protected void buildAgentWeb() {

        initView();
        ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        mBridgeWebView = new BridgeWebView(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(-1, -1))
//                .setAgentWebParent(getAgentWebParent(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setWebView(getWebView())
                .setPermissionInterceptor(getPermissionInterceptor())
//                .setWebLayout(getWebLayout())
                .setAgentWebUIController(getAgentWebUIController())
                .interceptUnkownUrl()
                .setOpenOtherPageWays(getOpenOtherAppWay())
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .setAgentWebWebSettings(getAgentWebSettings())
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(getUrl());
        WebSettings ws = mAgentWeb.getWebCreator().getWebView().getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        // 得到 AgentWeb 最底层的控件
//        addBGChild((FrameLayout) mAgentWeb.getWebCreator().getWebParentLayout());
        // Javainterface
//        mBridgeWebView.registerHandler("back", new BridgeHandler() {
//
//            @Override
//            public void handler(String data, CallBackFunction function) {
////                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
////                onBackPressed();
//                MyLogUtil.e("ssssssssss", "退出了");
//                ToastUtils.showLong("退出了");
//            }
//
//        });
        javainterface();
    }

    protected void initView() {
        mBackImageView = findViewById(R.id.iv_back);
        mLineView = findViewById(R.id.view_line);
        mFinishImageView = findViewById(R.id.iv_finish);
        mTitleTextView = findViewById(R.id.toolbar_title);
        mBackImageView.setOnClickListener(mOnClickListener);
        mFinishImageView.setOnClickListener(mOnClickListener);
        mMoreImageView = findViewById(R.id.iv_more);
        mMoreImageView.setOnClickListener(mOnClickListener);
        pageNavigator(View.GONE);
    }


    protected void pageNavigator(int tag) {

        mBackImageView.setVisibility(tag);
        mLineView.setVisibility(tag);
    }

    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_back) {// true表示AgentWeb处理了该事件
                if (!mAgentWeb.back()) {
                    BaseAgentWebActivityJs2.this.finish();
                }
            } else if (id == R.id.iv_finish) {
//                BaseAgentWebActivity.this.finish();
                onclickX(v);
            } else if (id == R.id.iv_more) {
//                showPoPup(v);
                onclickMore(v);
            }
        }

    };

    protected abstract void onclickX(View v);

    protected abstract void onclickMore(View v);


    /**
     * 显示更多菜单
     *
     * @param view 菜单依附在该View下面
     */
    protected void showPoPup(View view) {
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(this, view);
            mPopupMenu.inflate(R.menu.toolbar_menu);
            mPopupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
        mPopupMenu.show();
    }

    /**
     * 菜单事件
     */
    protected PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int itemId = item.getItemId();
            if (itemId == R.id.refresh) {
                if (mAgentWeb != null) {
                    mAgentWeb.getUrlLoader().reload(); // 刷新
                }
                return true;
            } else if (itemId == R.id.copy) {
                if (mAgentWeb != null) {
                    AgentUtils.toCopy(BaseAgentWebActivityJs2.this, mAgentWeb.getWebCreator().getWebView().getUrl());
                }
                return true;
            } else if (itemId == R.id.default_browser) {
                if (mAgentWeb != null) {
                    AgentUtils.openBrowser(BaseAgentWebActivityJs2.this, mAgentWeb.getWebCreator().getWebView().getUrl());
                }
                return true;
            } else if (itemId == R.id.default_clean) {
                toCleanWebCache();
                return true;
            } else if (itemId == R.id.error_website) {
                loadErrorWebSite();
                // test DownloadingService
//			        LogUtils.i(TAG, " :" + mDownloadingService + "  " + (mDownloadingService == null ? "" : mDownloadingService.isShutdown()) + "  :" + mExtraService);
//                    if (mDownloadingService != null && !mDownloadingService.isShutdown()) {
//                        mExtraService = mDownloadingService.shutdownNow();
//                        LogUtils.i(TAG, "mExtraService::" + mExtraService);
//                        return true;
//                    }
//                    if (mExtraService != null) {
//                        mExtraService.performReDownload();
//                    }

                return true;
            }
            return false;

        }
    };

    /**
     * 测试错误页的显示
     */
    protected void loadErrorWebSite() {
        if (mAgentWeb != null) {
            mAgentWeb.getUrlLoader().loadUrl("http://www.unkownwebsiteblog.me");
        }
    }

    /**
     * 清除 WebView 缓存
     */
    protected void toCleanWebCache() {

        if (this.mAgentWeb != null) {

            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb.clearWebCache();
            ToastUtils.showLong("已清理缓存");
            //清空所有 AgentWeb 硬盘缓存，包括 WebView 的缓存 , AgentWeb 下载的图片 ，视频 ，apk 等文件。
//            AgentWebConfig.clearDiskCache(this.getContext());
        }

    }

    protected abstract void javainterface();


    protected void addBGChild(FrameLayout frameLayout) {

        TextView mTextView = new TextView(frameLayout.getContext());
        mTextView.setText("技术由 Geek 提供");
        mTextView.setTextSize(16);
        mTextView.setTextColor(Color.parseColor("#727779"));
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
        FrameLayout.LayoutParams mFlp = new FrameLayout.LayoutParams(-2, -2);
        mFlp.gravity = Gravity.CENTER_HORIZONTAL;
        final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
        mFlp.topMargin = (int) (15 * scale + 0.5f);
        frameLayout.addView(mTextView, 0, mFlp);
    }


    protected @NonNull
    ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    protected AgentWeb getAgentWeb() {
        return this.mAgentWeb;
    }


    protected static class ErrorLayoutEntity {
        private int layoutRes = com.just.agentweb.R.layout.agentweb_error_page;
        private int reloadId;

        public void setLayoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
            if (layoutRes <= 0) {
                layoutRes = -1;
            }
        }

        public void setReloadId(int reloadId) {
            this.reloadId = reloadId;
            if (reloadId <= 0) {
                reloadId = -1;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }


    protected
    @Nullable
    String getUrl() {
        return null;
    }

    public @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }

    protected abstract @NonNull
    ViewGroup getAgentWebParent();

    protected @Nullable
    WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("TAG", "onProgressChanged:" + newProgress + "  view:" + view);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
                    if (title.length() > 10) {
                        title = title.substring(0, 10).concat("...");
                    }
                }
                mTitleTextView.setText(title);
            }
        };
    }

    protected @ColorInt
    int getIndicatorColor() {
        return -1;
    }

    protected int getIndicatorHeight() {
        return -1;
    }

    protected @Nullable
    WebViewClient getWebViewClient() {
        return new WebViewClient() {

            private BridgeWebViewClient mBridgeWebViewClient = new BridgeWebViewClient(mBridgeWebView);

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mBridgeWebViewClient.shouldOverrideUrlLoading(view, url)) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (mBridgeWebViewClient.shouldOverrideUrlLoading(view, request.getUrl().toString())) {
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //do you  work
                Log.i("Info", "BaseWebActivity onPageStarted");
                if (url.equals(getUrl())) {
                    pageNavigator(View.GONE);
                } else {
                    pageNavigator(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBridgeWebViewClient.onPageFinished(view, url);
            }
        };
    }


    protected @Nullable
    WebView getWebView() {
        return mBridgeWebView;
    }

    protected @Nullable
    IWebLayout getWebLayout() {
        return new WebLayout(this);
    }

    protected @Nullable
    PermissionInterceptor getPermissionInterceptor() {
        return null;
    }

    public @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return null;
    }

    public @Nullable
    DefaultWebClient.OpenOtherPageWays getOpenOtherAppWay() {
        return null;
    }

    protected @NonNull
    MiddlewareWebChromeBase getMiddleWareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareWebChromeBase() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(view, title);
            }
        };
    }

    protected void setTitle(WebView view, String title) {

    }

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {
        };
    }
}
