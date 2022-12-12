package com.just.agentweb.geek.hois3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.geek.libbase.R;


public class HIOSAct1 extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenthiosact);
        HiosHelperNew.config(AppUtils.getAppPackageName() + ".web.page3");
//        PgyerSDKManager.checkSoftwareUpdate(this);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this,
                        "http://www.baidu.com/");
//                        "http://www.baidu.com/?condition=login");

            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this,
                        "dataability://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.HIOSLoginDemoAct1{act}?query1={i}1&query2={s}2a&query3={s}3a");
//                        "dataability://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.HIOSLoginDemoAct1{act}?query1={i}1&query2={s}2a&query3={s}3a&condition=login");

            }
        });
        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this,
                        "dataability://com.just.agentweb.geek.hois3.HIOSLoginDemoAct1?query1={i}1&query2={s}2a&query3={s}3a");
//                        "dataability://com.just.agentweb.geek.hois3.HIOSLoginDemoAct1?query1={i}1&query2={s}2a&query3={s}3a&condition=login");

            }
        });
        findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("dataability://cs.znclass.com/" + AppUtils.getAppPackageName() + ".hs.act.slbapp.HIOSLoginDemoAct1?query1=1&query2=2a&query3=3a"));
                startActivity(intent);
//                Intent intent=new Intent();
//                intent.setData(Uri.parse("example://www.example.com/user?uid=123&name=Ming"));
//                startActivity(intent);
            }
        });
        findViewById(R.id.tv5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this, "com.tencent.mm");
//                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this, "com.tencent.tmgp.sgame");
//                Intent intent = getPackageManager().getLaunchIntentForPackage("android.settings.WIFI_SETTINGS");
//                startActivity(intent);
//                try {
//                    Intent intent = new Intent();
//                    //通过包名启动
//                    PackageManager packageManager = getPackageManager();
//                    intent = packageManager.getLaunchIntentForPackage("com.tencent.mm");
//                    if (null != intent) {
//                        startActivity(intent);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        });
        findViewById(R.id.tv6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this, "tel:15137615080");
            }
        });
        findViewById(R.id.tv7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this, "mailto:liangxiao6@live.com");
            }
        });
        findViewById(R.id.tv8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this, "sms:15137615080");
            }
        });
        findViewById(R.id.tv9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this,
                        "dataability://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.HIOSLoginDemoAct2{act}?query1={i}1&query2={s}2a&query3={s}3a");

            }
        });
        findViewById(R.id.tv10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiosAliasNew.register("登录.denglu", AppUtils.getAppPackageName(), "com.just.agentweb.geek.hois3.HIOSLoginDemoAct1");
                HiosHelperNew.resolveAd(HIOSAct1.this, HIOSAct1.this,
                        "dataability://登录.denglu?query1={i}1&query2={s}2a&query3={s}3a");
            }
        });

    }
}