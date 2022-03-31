/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libbase.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libbase.R;
import com.geek.libbase.netstate.NetState;
import com.geek.libbase.netstate.NetconListener;
import com.geek.libbase.plugin.PluginActivity;
import com.geek.libbase.widgets.IBaseAction;
import com.geek.liblanguage.MultiLanguages;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.BaseAppManager;
import com.geek.libutils.app.BaseViewHelper;
import com.geek.libswipebacklayout.SwipeBack;
import com.geek.libswipebacklayout.SwipeBackLayout;
import com.geek.libswipebacklayout.SwipeBackUtil;
import com.geek.libswipebacklayout.activity.SwipeBackActivityBase;
import com.geek.libswipebacklayout.activity.SwipeBackActivityHelper;

import me.jessyan.autosize.AutoSizeCompat;

public abstract class SlbPluginBaseActivity extends PluginActivity implements SwipeBackActivityBase,
        IBaseAction, NetconListener {

    public static final String REQUEST_CODE = "request_code";
    protected NetState netState;
    protected Typeface tfLight;
    protected Typeface tfLight2;
    private SwipeBackActivityHelper mHelper;
    protected boolean enableSwipeBack;

    public String getIdentifier() {
        return getClass().getName() + System.currentTimeMillis();
    }

    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }

    @Override
    public void attachBaseContext(Context newBase) {
        // 绑定语种
        MultiLanguages.attach(newBase);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 加载注解bufen
        SwipeBack swipeBack = getClass().getAnnotation(SwipeBack.class);
        if (swipeBack != null) {
            enableSwipeBack = swipeBack.value();
        }
        if (enableSwipeBack) {
            mHelper = new SwipeBackActivityHelper(this);
        }
        try {
            if (enableSwipeBack) {
                mHelper.onActivityCreate();
                setSwipeBackEnable(enableSwipeBack);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        BaseAppManager.getInstance().add(this);
        setContentView(getLayoutId());
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        BarUtils.setStatusBarLightMode(this, false);
        setup(savedInstanceState);
        //网络监听
        netState = new NetState();
        netState.setNetStateListener(this, this);
        // 字体
        tfLight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        tfLight2 = Typeface.createFromAsset(getAssets(), "fonts/DINCond-Regular.ttf");
    }

    private void interceptCreateView() {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                if (view != null && view instanceof EditText) {
                    Log.d("***", "IME_FLAG_NO_EXTRACT_UI");
                    EditText et = (EditText) view;
                    et.setImeOptions(et.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                    return et;
                }
                return view;
            }
        });
    }

    private long lastTime;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    public abstract class OnMultiClickListener implements View.OnClickListener {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private final int MIN_CLICK_DELAY_TIME = 1500;
        private long lastClickTime;

        public abstract void onMultiClick(View v);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                onMultiClick(v);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            MobEventHelper.onEvent(this, "effective_click");
        }

        return super.dispatchTouchEvent(ev);
    }


    protected Utils.OnAppStatusChangedListener baseAppStatusChangedListener = new Utils.OnAppStatusChangedListener() {

        @Override
        public void onForeground(Activity activity) {
            // 前台
            // 护眼模式bufen
//            if (SPUtils.getInstance().getBoolean("huyan", false)) {
//                startService(new Intent(SlbBaseActivity.this, Huyanservices.class));
//            }
        }

        @Override
        public void onBackground(Activity activity) {
            // 后台
//            if (SPUtils.getInstance().getBoolean("huyan", false)) {
//                stopService(new Intent(SlbBaseActivity.this, Huyanservices.class));
//            }
        }
    };


    protected abstract int getLayoutId();

    protected void setup(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void net_con_none() {
        ToastUtils.showLong("网络异常，请检查网络连接！");
    }

    @Override
    public void net_con_success() {
    }

    @Override
    public void showNetPopup() {
    }

    protected final <T extends View> T f(@IdRes int resId) {
        return BaseViewHelper.f(this, resId);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    public void startActivity(String action) {
        startActivity(new Intent(action));
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    public void startActivityForResult(String action, int requestCode) {
        startActivityForResult(new Intent(action), requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent.resolveActivity(getPackageManager()) == null) {
            Log.e("Activity", "No Activity found to handle intent " + intent);
            return;
        }

        if (requestCode != -1 && intent.getIntExtra(REQUEST_CODE, -1) == -1) {
            intent.putExtra(REQUEST_CODE, requestCode);
        }

        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //登录和未登录成功状态
        if (SlbLoginUtil.get().login_activity_result(requestCode, resultCode, data)) {
            if (isIs_finish_login()) {
                setIs_finish_login(false);
                finish();
            } else {
            }
            return;
        }
        //正常状态
        onActResult(requestCode, resultCode, data);
    }

    public boolean is_finish_login;

    public boolean isIs_finish_login() {
        return is_finish_login;
    }

    public void setIs_finish_login(boolean is_finish_login) {
        this.is_finish_login = is_finish_login;
    }

    // 销毁当前页面操作bufen
    public void set_url_hios_finish(String url_hios_finish) {
        if (url_hios_finish.contains("condition=login")) {
            if (SlbLoginUtil.get().isUserLogin()) {
                finish();
            } else {
                // 防止des之前就finish没效果
                setIs_finish_login(true);
            }
        } else {
            finish();
        }
    }

    protected void onUserLogined(String userId) {
    }

    protected void onActResult(int requestCode, int resultCode, Intent data) {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {
        AppUtils.registerAppStatusChangedListener(baseAppStatusChangedListener);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @Override
    public void finish() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
        BaseAppManager.getInstance().remove(this);
        if (netState != null) {
            netState.unregisterReceiver();
        }
        hideSoftKeyboard();
//        ShowLoadingUtil.onDestory();
        AppUtils.unregisterAppStatusChangedListener(baseAppStatusChangedListener);


    }

    //以下不用管系列————
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (enableSwipeBack) {
            mHelper.onPostCreate();
        }
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return enableSwipeBack ? mHelper.getSwipeBackLayout() : null;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtil.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onHomePressed() {
        BaseAppManager.getInstance().clear();
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
        startActivity(intent);
        finish();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public Activity who() {
        return this;
    }

    public void back(View v) {
        finish();
    }
}
