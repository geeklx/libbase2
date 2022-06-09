package com.fosung.lighthouse.fosunglibs;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.geek.libwebview.base.AdCommPart1Activity;
import com.just.agentweb.geek.activity.JsWebActivity3;
import com.pgyer.pgyersdk.PgyerSDKManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new PgyerSDKManager.Init()
//                .setContext(getApplicationContext()) //设置上下问对象
//                .start();
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, com.geek.libbase.R.color.transparent));
        BarUtils.setStatusBarLightMode(this, true);
        startActivity(new Intent(MainActivity.this, JsWebActivity3.class));


    }
}