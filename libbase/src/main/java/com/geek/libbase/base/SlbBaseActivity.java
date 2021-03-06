/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libbase.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libbase.AndroidApplication;
import com.geek.libbase.R;
import com.geek.libbase.netstate.NetState;
import com.geek.libbase.netstate.NetconListener;
import com.geek.libbase.widgets.IBaseAction;
import com.geek.liblanguage.MultiLanguages;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.BaseAppManager;
import com.geek.libutils.app.BaseViewHelper;
import com.geek.libswipebacklayout.SwipeBack;
import com.geek.libswipebacklayout.SwipeBackLayout;
import com.geek.libswipebacklayout.SwipeBackUtil;
import com.geek.libswipebacklayout.activity.SwipeBackActivityBase;
import com.geek.libswipebacklayout.activity.SwipeBackActivityHelper;

public abstract class SlbBaseActivity extends AppCompatActivity implements SwipeBackActivityBase,
        IBaseAction, NetconListener {

    public static final String REQUEST_CODE = "request_code";

    private long mCurrentMs = System.currentTimeMillis();
    //    private Handler handler;
    protected NetState netState;
    protected Typeface tfLight;
    protected Typeface tfLight2;
    //    private JPluginPlatformInterface jPluginPlatformInterface;
    private SwipeBackActivityHelper mHelper;
    protected boolean enableSwipeBack;

//    @Override
//    public Resources getResources() {
//        //??????????????? v1.1.2 ??????????????????????????? AutoSizeCompat
//        if (Looper.myLooper()==Looper.getMainLooper()){
//            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//??????????????????????????????????????????
//            AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//??????????????????????????????????????????
//        }
//        return super.getResources();
//    }

//    @Override
//    public WindowManager.LayoutParams generateLayoutParams(AttributeSet attrs) {
//        AutoSizeCompat.autoConvertDensityOfGlobal((getResources());//??????????????????????????????????????????
//        AutoSizeCompat.autoConvertDensity((getResources(), 667, false);//??????????????????????????????????????????
//        return super.generateLayoutParams(attrs);
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // ????????????
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        ScreenUtils.setNonFullScreen(this);
//        BarUtils.setStatusBarLightMode(this, false);
//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.black_000));

//        if (BarUtils.isStatusBarLightMode(this)) {
//            BarUtils.setStatusBarLightMode(this, false);
//        } else {
//            BarUtils.setStatusBarLightMode(this, true);
//        }
        // ??????
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        // ???????????????
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.web_white), 0);
//        StatusBarUtil.setLightMode(this);
        // ???????????????
//        WebStatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.web_white), 0);
//        WebStatusBarUtil.setLightMode(this);
        //
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // old
        super.onCreate(savedInstanceState);
        // ????????????bufen
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
        //????????????
        netState = new NetState();
        netState.setNetStateListener(this, this);
        // ????????????
//        set_lb_floatbutton_init();
        // ??????
        tfLight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        tfLight2 = Typeface.createFromAsset(getAssets(), "fonts/DINCond-Regular.ttf");
        //
//        jPluginPlatformInterface = new JPluginPlatformInterface(this);
        if (SPUtils.getInstance().getBoolean("zhihui", false)) {
            //??????
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        }
        if (SPUtils.getInstance().getBoolean("jieping", false)) {
            // 2.5.3?????? ?????????????????? ????????????????????????
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
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
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) { HookUtil.hookClick(this);}

    }

    public abstract class OnMultiClickListener implements View.OnClickListener {
        // ???????????????????????????????????????????????????1000??????
        private final int MIN_CLICK_DELAY_TIME = 1500;
        private long lastClickTime;

        public abstract void onMultiClick(View v);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // ???????????????????????????lastClickTime???????????????????????????
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

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
//        MobEvent.onResume(this);
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
//        MobclickAgent.onPause(this);
//        MobEvent.onPause(this);
        AppUtils.registerAppStatusChangedListener(baseAppStatusChangedListener);
    }

    protected Utils.OnAppStatusChangedListener baseAppStatusChangedListener = new Utils.OnAppStatusChangedListener() {

        @Override
        public void onForeground(Activity activity) {
            // ??????
            // ????????????bufen
//            if (SPUtils.getInstance().getBoolean("huyan", false)) {
//                startService(new Intent(SlbBaseActivity.this, Huyanservices.class));
//            }
        }

        @Override
        public void onBackground(Activity activity) {
            // ??????
//            if (SPUtils.getInstance().getBoolean("huyan", false)) {
//                stopService(new Intent(SlbBaseActivity.this, Huyanservices.class));
//            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        jPluginPlatformInterface.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        jPluginPlatformInterface.onStop(this);
    }

    protected abstract int getLayoutId();

    protected void setup(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void net_con_none() {
        ToastUtils.showLong("???????????????????????????????????????");
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
        //??????????????????????????????
        if (SlbLoginUtil.get().login_activity_result(requestCode, resultCode, data)) {
            if (isIs_finish_login()) {
                setIs_finish_login(false);
                finish();
            } else {
            }
            return;
        }
        //????????????
        onActResult(requestCode, resultCode, data);
    }

    // ?????????????????????????????????
    public void onLoginSuccess(String action) {
        setResult(SlbLoginUtil.LOGIN_RESULT_OK);
        if (ActivityUtils.getActivityList().size() == 1) {
            startActivity(new Intent(action));
        }
        finish();
    }

    // ?????????????????????????????????
    public void onLoginCanceled(String action) {
        setResult(SlbLoginUtil.LOGIN_RESULT_CANCELED);
        if (ActivityUtils.getActivityList().size() == 1) {
            startActivity(new Intent(action));
        }
        finish();
    }


    public boolean is_finish_login;

    public boolean isIs_finish_login() {
        return is_finish_login;
    }

    public void setIs_finish_login(boolean is_finish_login) {
        this.is_finish_login = is_finish_login;
    }

    // ????????????????????????bufen
    public void set_url_hios_finish(String url_hios_finish) {
        if (url_hios_finish.contains("condition=login")) {
            if (SlbLoginUtil.get().isUserLogin()) {
                finish();
            } else {
                // ??????des?????????finish?????????
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

    @Override
    public void finish() {
//        MyLogUtil.e("ssssssss", !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappindex.index.ShouyeActivity") + "");
//        MyLogUtil.e("ssssssss", !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappsplash.welcome.WelComeActivity") + "");
//        if (ActivityUtils.getActivityList().size() == 1
//                && !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappindex.index.ShouyeActivity")
//                && !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappsplash.welcome.WelComeActivity")) {
//            MyLogUtil.e("ssssssss", "????????????~");
//            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
//            startActivity(intent);
////            finish();
//        }
//        ShowLoadingUtil.onDestory();
        super.finish();
//        ActivityUtils.getActivityList();
//        BaseAppManager.getInstance().remove(this);
//        overridePendingTransition(R.anim.open_main, R.anim.close_next);
        // ???????????????bufen
//        LocalBroadcastManagers.getInstance(BaseApp.get()).unregisterReceiver(floatButtonReceiverListenBooks);
    }

    @Override
    protected void onDestroy() {
        BaseAppManager.getInstance().remove(this);
        if (netState != null) {
            netState.unregisterReceiver();
        }
        hideSoftKeyboard();
//        ShowLoadingUtil.onDestory();
        AppUtils.unregisterAppStatusChangedListener(baseAppStatusChangedListener);
        super.onDestroy();

    }

    //?????????????????????????????????
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
//        finish();
//        while (!BaseAppManager.getInstance().getAll().isEmpty()) {
//            BaseAppManager.getInstance().top().finish();
//        }

//        Stack<Activity> all = BaseAppManager.getInstance().getAll();
//        for (Iterator<Activity> iterator = all.iterator(); iterator.hasNext();) {
//            iterator.next().finish();
//        }

        Intent homeIntent = new Intent(Intent.ACTION_MAIN, null);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(homeIntent);
        finish();
        //
        while(!BaseAppManager.getInstance().getAll().isEmpty()) {
            BaseAppManager.getInstance().top().finish();
        }
        BaseAppManager.getInstance().clear();
//        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
//        startActivity(intent);
//        finish();

        Application app = BaseApp.get();
        if (app instanceof AndroidApplication) {
            ((AndroidApplication) app).onHomePressed();
        }
    }

    /**
     * ???????????????
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

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }

    public void back(View v) {
        finish();
    }
}
