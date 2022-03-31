package com.fosung.lighthouse.fosunglibs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pgyer.pgyersdk.PgyerSDKManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PgyerSDKManager.Init()
                .setContext(getApplicationContext()) //设置上下问对象
                .start();
//        startActivity(new Intent(MainActivity.this, ScanAct2.class));


    }
}