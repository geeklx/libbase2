package com.fosung.lighthouse.fosunglibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libretrofit.BanbenUtils;
import com.geek.libretrofit.HeaderBean;
import com.geek.libutils.data.MmkvUtils;
import com.just.agentweb.geek.activity.AgentwebAct;
import com.just.agentweb.geek.activity.JsWebActivity3;
import com.pgyer.pgyersdk.PgyerSDKManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PgyerSDKManager.Init()
                .setContext(getApplicationContext()) //设置上下问对象
                .start();
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, com.geek.libbase.R.color.transparent));
        BarUtils.setStatusBarLightMode(this, true);
        Utils.init(Utils.getApp());// com.blankj:utilcode:1.17.3
        MmkvUtils.getInstance().get("");
        MmkvUtils.getInstance().get_demo();
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, JsWebActivity3.class));
                startActivity(new Intent(MainActivity.this, AgentwebAct.class));
            }
        });
//        //ces
//        HeaderBean headerBean = new HeaderBean();
//        headerBean.setImei(BanbenUtils.getInstance().getImei());
//        headerBean.setPlatform(BanbenUtils.getInstance().getPlatform());
//        headerBean.setToken(BanbenUtils.getInstance().getToken());
//        headerBean.setModel(DeviceUtils.getManufacturer());
//        headerBean.setVersion(BanbenUtils.getInstance().getVersion());
//        headerBean.setVersion_code(AppUtils.getAppVersionCode() + "");
//        headerBean.setPackage_name(AppUtils.getAppPackageName() + "");
//        headerBean.setLatitude(SPUtils.getInstance().getString("weidu", "weidu"));
//        headerBean.setLongitude(SPUtils.getInstance().getString("jingdu", "jingdu"));
//        MmkvUtils.getInstance().set_common_json("app_header", JSON.toJSONString(headerBean), HeaderBean.class);

    }
}