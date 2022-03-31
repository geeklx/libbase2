package com.just.agentweb.geek.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
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
import com.just.agentweb.geek.fragment.EasyWebFragment;

/**
 * Created by cenxiaozhong on 2017/7/22.
 * source code  https://github.com/Justson/AgentWeb
 */

public abstract class BaseAgentWebFragment extends Fragment {

    protected ImageView mBackImageView;
    protected View mLineView;
    protected ImageView mFinishImageView;
    protected TextView mTitleTextView;
    protected ImageView mMoreImageView;
    protected PopupMenu mPopupMenu;
    protected AgentWeb mAgentWeb;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    private ErrorLayoutEntity mErrorLayoutEntity;
    private AgentWebUIControllerImplBase mAgentWebUIController;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                .setWebView(getWebView())
                .setWebLayout(getWebLayout())
                .setAgentWebWebSettings(getAgentWebSettings())
                .setWebViewClient(getWebViewClient())
                .setPermissionInterceptor(getPermissionInterceptor())
                .setWebChromeClient(getWebChromeClient())
                .interceptUnkownUrl()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setAgentWebUIController(getAgentWebUIController())
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .createAgentWeb()//
                .ready()//
                .go(getUrl());
        // https://github.com/Justson/AgentWeb/issues/272
        WebSettings ws = mAgentWeb.getWebCreator().getWebView().getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
    }

    protected void initView(View view) {
        mBackImageView = (ImageView) view.findViewById(R.id.iv_back);
        mLineView = view.findViewById(R.id.view_line);
        mFinishImageView = (ImageView) view.findViewById(R.id.iv_finish);
        mTitleTextView = (TextView) view.findViewById(R.id.toolbar_title);
        mBackImageView.setOnClickListener(mOnClickListener);
        mFinishImageView.setOnClickListener(mOnClickListener);
        mMoreImageView = (ImageView) view.findViewById(R.id.iv_more);
        mMoreImageView.setVisibility(View.GONE);
        pageNavigator(View.GONE);
    }

    private void pageNavigator(int tag) {

        mBackImageView.setVisibility(tag);
        mLineView.setVisibility(tag);
    }

    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_back) {// true表示AgentWeb处理了该事件
                if (!mAgentWeb.back()) {
                    BaseAgentWebFragment.this.getActivity().finish();
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
            mPopupMenu = new PopupMenu(getActivity(), view);
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
                    AgentUtils.toCopy(BaseAgentWebFragment.this.getActivity(), mAgentWeb.getWebCreator().getWebView().getUrl());
                }
                return true;
            } else if (itemId == R.id.default_browser) {
                if (mAgentWeb != null) {
                    AgentUtils.openBrowser(BaseAgentWebFragment.this.getActivity(), mAgentWeb.getWebCreator().getWebView().getUrl());
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


    protected void setTitle(WebView view, String title) {

    }

    protected @NonNull
    ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    protected @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return mAgentWebUIController;
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
    public void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    public void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    protected @Nullable
    String getUrl() {
        return "";
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    protected @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }

    protected @Nullable
    WebChromeClient getWebChromeClient() {
        return null;
    }

    protected abstract @NonNull
    ViewGroup getAgentWebParent();

    protected @ColorInt
    int getIndicatorColor() {
        return -1;
    }

    protected int getIndicatorHeight() {
        return -1;
    }

    protected @Nullable
    WebViewClient getWebViewClient() {
        return null;
    }

    protected @Nullable
    WebView getWebView() {
        return null;
    }

    protected @Nullable
    IWebLayout getWebLayout() {
        return null;
    }

    protected @Nullable
    PermissionInterceptor getPermissionInterceptor() {
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

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {
        };
    }
}
