package com.just.agentweb.geek.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.geek.libbase.R;
import com.geek.libretrofit.BanbenUtils;
import com.geek.libretrofit.HeaderBean;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.geek.StartHiddenManagerAgent;
import com.just.agentweb.geek.base.BaseAgentWebActivityJs2;
import com.just.agentweb.geek.fragment.AgentWebFragment;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

public class JsWebActivity3 extends BaseAgentWebActivityJs2 {

    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtysweb3);
        LinearLayout mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        mTitleTextView.setText("灯塔有声");
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        startHiddenManager = new StartHiddenManagerAgent(tv1, tv2, null, new StartHiddenManagerAgent.OnClickFinish() {
            @Override
            public void onFinish() {
                new XPopup.Builder(JsWebActivity3.this)
                        //.dismissOnBackPressed(false)
                        .dismissOnTouchOutside(true) //对于只使用一次的弹窗，推荐设置这个
                        .autoOpenSoftInput(true)
//                        .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
//                        .isRequestFocus(false)
                        //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                        .asInputConfirm("修改地址", getUrl(), getUrl(), "",
                                new OnInputConfirmListener() {
                                    @Override
                                    public void onConfirm(String text) {
                                        mAgentWeb.getUrlLoader().loadUrl(text);
                                    }
                                })
                        .show();
            }
        });

    }

    private StartHiddenManagerAgent startHiddenManager;

    @Override
    protected void onDestroy() {
        startHiddenManager.onDestory();
        super.onDestroy();
    }

    @Override
    protected void onclickX(View v) {
        finish();
    }

    @Override
    protected void onclickMore(View v) {
        showPoPup(v);
    }

    @Override
    protected void javainterface() {
        if (mAgentWeb != null) {
            //注入对象
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface2(mAgentWeb, this));
        }
        mBridgeWebView.registerHandler("exitApp", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                finish();
                MyLogUtil.e("ssssssssss", "退出了");
            }

        });
        mBridgeWebView.registerHandler("back", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
//                onBackPressed();
                finish();
                MyLogUtil.e("ssssssssss", "退出了");
//                ToastUtils.showLong("退出了");
            }

        });
        //ceshi
        mBridgeWebView.registerHandler("get_token", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                function.onCallBack("get_token");
            }

        });
        mBridgeWebView.registerHandler("get_response_type", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                function.onCallBack("get_response_type");
            }

        });
        mBridgeWebView.registerHandler("get_client_id", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                function.onCallBack("get_client_id");
            }

        });
        mBridgeWebView.registerHandler("get_redirect_uri", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                function.onCallBack("get_redirect_uri");
            }

        });
        mBridgeWebView.registerHandler("get_scope", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                function.onCallBack("get_scope");
            }

        });
        mBridgeWebView.registerHandler("get_app_header", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                HeaderBean bean = MmkvUtils.getInstance().get_common_json("app_header", HeaderBean.class);
                MyLogUtil.e("RetrofitNetNew_Interceptor", JSON.toJSONString(bean));
                function.onCallBack(JSON.toJSONString(bean));
            }

        });

    }

    public class AndroidInterface2 {

        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agent;
        private Context context;

        public AndroidInterface2(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }


        @JavascriptInterface
        public void callAndroid(final String msg) {

            deliver.post(new Runnable() {
                @Override
                public void run() {

                    Log.i("Info", "main Thread:" + Thread.currentThread());
                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
                }
            });


            Log.i("Info", "Thread:" + Thread.currentThread());

        }

        @JavascriptInterface
        public void exitApp() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void back() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }

        @JavascriptInterface
        public String get_token() {
            return "get_token";
        }

        @JavascriptInterface
        public String get_response_type() {
            return "get_response_type";
        }

        @JavascriptInterface
        public String get_client_id() {
            return "get_client_id";
        }

        @JavascriptInterface
        public String get_redirect_uri() {
            return "get_redirect_uri";
        }

        @JavascriptInterface
        public String get_scope() {
            return "get_scope";
        }

        @JavascriptInterface
        public String get_app_header() {
            //ces
            HeaderBean headerBean = new HeaderBean();
            headerBean.setImei(BanbenUtils.getInstance().getImei());
            headerBean.setPlatform(BanbenUtils.getInstance().getPlatform());
            headerBean.setToken(BanbenUtils.getInstance().getToken());
            headerBean.setModel(DeviceUtils.getManufacturer());
            headerBean.setVersion(BanbenUtils.getInstance().getVersion());
            headerBean.setVersion_code(AppUtils.getAppVersionCode() + "");
            headerBean.setPackage_name(AppUtils.getAppPackageName() + "");
            headerBean.setLatitude(SPUtils.getInstance().getString("weidu", "weidu"));
            headerBean.setLongitude(SPUtils.getInstance().getString("jingdu", "jingdu"));
            MmkvUtils.getInstance().set_common_json("app_header", JSON.toJSONString(headerBean), HeaderBean.class);
            //
            HeaderBean bean = MmkvUtils.getInstance().get_common_json("app_header", HeaderBean.class);
            MyLogUtil.e("RetrofitNetNew_Interceptor", JSON.toJSONString(bean));
            return JSON.toJSONString(bean);
        }

    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.container);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getIndicatorColor() {
        return Color.parseColor("#ff0000");
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
    protected void addBGChild(FrameLayout frameLayout) {

        TextView mTextView = new TextView(frameLayout.getContext());
        mTextView.setText("技术由 福生佳信 提供");
        mTextView.setTextSize(16);
        mTextView.setTextColor(Color.parseColor("#727779"));
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
        FrameLayout.LayoutParams mFlp = new FrameLayout.LayoutParams(-2, -2);
        mFlp.gravity = Gravity.CENTER_HORIZONTAL;
        final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
        mFlp.topMargin = (int) (15 * scale + 0.5f);
        frameLayout.addView(mTextView, 0, mFlp);
    }

    @Override
    protected int getIndicatorHeight() {
        return 3;
    }

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://v.dtdjzx.gov.cn/voice/";
////        return "https://m.mogujie.com/?f=mgjlm&ptp=_qd._cps______3069826.152.1.0";
//    }

    @Override
    public String getUrl() {
        String target = "";
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
//            String appLinkAction = appLinkIntent.getAction();
//                if (appLinkAction != null) {
            Uri appLinkData = appLinkIntent.getData();
            if (appLinkData != null) {
                target = appLinkData.getQueryParameter(AgentWebFragment.URL_KEY);
            } else {
                //
                if (TextUtils.isEmpty(appLinkIntent.getStringExtra(AgentWebFragment.URL_KEY))) {
                    target = "http://www.jd.com/";
                } else {
                    target = appLinkIntent.getStringExtra(AgentWebFragment.URL_KEY);
                }
            }
        }
        return target;
    }

}
