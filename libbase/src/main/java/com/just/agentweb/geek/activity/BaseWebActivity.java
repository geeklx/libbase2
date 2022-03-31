package com.just.agentweb.geek.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.just.agentweb.geek.AgentUtils;
import com.geek.libbase.R;
import com.just.agentweb.geek.widget.WebLayout;

/**
 * Created by cenxiaozhong on 2017/5/26.
 * <p>
 * source code  https://github.com/Justson/AgentWeb
 */

public class BaseWebActivity extends AppCompatActivity {

    protected ImageView mBackImageView;
    protected View mLineView;
    protected ImageView mFinishImageView;
    protected TextView mTitleTextView;
    protected ImageView mMoreImageView;
    protected PopupMenu mPopupMenu;
    protected AgentWeb mAgentWeb;
    protected LinearLayout mLinearLayout;
    protected AlertDialog mAlertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        BarUtils.setStatusBarLightMode(this, false);
        setContentView(R.layout.activity_web);


        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
//        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
//        mToolbar.setTitleTextColor(Color.WHITE);
//        mToolbar.setTitle("");
//        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
//        this.setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null) {
//            // Enable the Up button
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showDialog();
//            }
//        });


        long p = System.currentTimeMillis();

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(com.just.agentweb.R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());
        // https://github.com/Justson/AgentWeb/issues/272
        WebSettings ws = mAgentWeb.getWebCreator().getWebView().getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        //mAgentWeb.getUrlLoader().loadUrl(getUrl());
//        int screenDensity = getResources().getDisplayMetrics().densityDpi;
////        Logger.d(TAG, "screenDensity = " + screenDensity);
//        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
//        switch (screenDensity)
//        {
//            case DisplayMetrics.DENSITY_LOW:
//                zoomDensity = WebSettings.ZoomDensity.CLOSE;
//                break;
//            case DisplayMetrics.DENSITY_MEDIUM:
//                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
//                break;
//            case DisplayMetrics.DENSITY_HIGH:
//            case DisplayMetrics.DENSITY_XHIGH:
//            case DisplayMetrics.DENSITY_XXHIGH:
//            default:
//                zoomDensity = WebSettings.ZoomDensity.FAR;
//                break;
//
//        }
//        mAgentWeb.getAgentWebSettings().getWebSettings().setDefaultZoom(zoomDensity);
        initView();

        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p));


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
                    BaseWebActivity.this.finish();
                }
            } else if (id == R.id.iv_finish) {
                BaseWebActivity.this.finish();
            } else if (id == R.id.iv_more) {
                showPoPup(v);
            }
        }

    };


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
                    AgentUtils.toCopy(BaseWebActivity.this, mAgentWeb.getWebCreator().getWebView().getUrl());
                }
                return true;
            } else if (itemId == R.id.default_browser) {
                if (mAgentWeb != null) {
                    AgentUtils.openBrowser(BaseWebActivity.this, mAgentWeb.getWebCreator().getWebView().getUrl());
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

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
            if (url.equals(getUrl())) {
                pageNavigator(View.GONE);
            } else {
                pageNavigator(View.VISIBLE);
            }
        }
    };
    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
            }
            assert mTitleTextView != null;
            mTitleTextView.setText(title);
        }
    };

    public String getUrl() {
        return "https://m.jd.com/";
    }


    private void showDialog() {

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            BaseWebActivity.this.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
