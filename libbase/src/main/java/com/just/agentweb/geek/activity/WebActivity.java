package com.just.agentweb.geek.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.just.agentweb.geek.fragment.AgentWebFragment;

public class WebActivity extends BaseWebActivity {

//    @Override
//    public String getUrl() {
////        return super.getUrl();
//        return "https://www.mogu.com/?f=mgjlm&ptp=_qd._cps______3069826.152.1.0";
//    }


//    @Override
//    @Nullable
//    public String getUrl() {
//        Bundle bundle = this.getArguments();
//        String target = bundle.getString("url_key");
//        LogUtils.e("targetaaaaaaa=" + target);
//        if (TextUtils.isEmpty(target)) {
//            target = "http://www.jd.com/";
//        }
//        return target;
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
//                    target = "http://www.jd.com/";
                    target = "http://t-nv-app.xczx-jn.com/#/dashboard";
                } else {
                    target = appLinkIntent.getStringExtra(AgentWebFragment.URL_KEY);
                }
            }
        }
        return target;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //测试Cookies
        /*try {

            String targetUrl="";
            Log.i("Info","cookies:"+ AgentWebConfig.getCookiesByUrl(targetUrl="http://www.jd.com"));
            AgentWebConfig.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    Log.i("Info","onResume():"+value);
                }
            });

            String tagInfo=AgentWebConfig.getCookiesByUrl(targetUrl);
            Log.i("Info","tag:"+tagInfo);
            AgentWebConfig.syncCookie("http://www.jd.com","ID=IDHl3NVU0N3ltZm9OWHhubHVQZW1BRThLdGhLaFc5TnVtQWd1S2g1REcwNVhTS3RXQVFBQEBFDA984906B62C444931EA0");
            String tag=AgentWebConfig.getCookiesByUrl(targetUrl);
            Log.i("Info","tag:"+tag);
            AgentWebConfig.removeSessionCookies();
            Log.i("Info","removeSessionCookies:"+AgentWebConfig.getCookiesByUrl(targetUrl));
        }  catch (Exception e){
            e.printStackTrace();
        }*/

    }
}
