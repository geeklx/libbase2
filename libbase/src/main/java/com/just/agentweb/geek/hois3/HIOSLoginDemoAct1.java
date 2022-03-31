package com.just.agentweb.geek.hois3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.geek.libbase.R;


public class HIOSLoginDemoAct1 extends AppCompatActivity {

    private String aaaa;
    private String bbbb;
    private String cccc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenthiosloginact);
        // ATTENTION: This was auto-generated to handle app links.
        // scheme://host/path?param1=value1&param2=value2
        // eg: example://www.example.com/user?uid=123&name=Ming
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
            String appLinkAction = appLinkIntent.getAction();
            if (appLinkAction != null) {
                Uri appLinkData = appLinkIntent.getData();
                if (appLinkData != null) {
                    aaaa = appLinkData.getQueryParameter("query1");
                    bbbb = appLinkData.getQueryParameter("query2");
                    cccc = appLinkData.getQueryParameter("query3");
                    ToastUtils.showLong("appLink->" + ",query1->" + aaaa + ",query2->" + bbbb + ",query3->" + cccc);
                } else {
                    // HIOS协议bufen
                    aaaa = appLinkIntent.getStringExtra("query1");
                    bbbb = appLinkIntent.getStringExtra("query2");
                    cccc = appLinkIntent.getStringExtra("query3");
                    ToastUtils.showLong("hios->" + ",query1->" + aaaa + ",query2->" + bbbb + ",query3->" + cccc);
                }
            } else {
                // HIOS协议bufen
                aaaa = appLinkIntent.getStringExtra("query1");
                bbbb = appLinkIntent.getStringExtra("query2");
                cccc = appLinkIntent.getStringExtra("query3");
                ToastUtils.showLong("hios->" + ",query1->" + aaaa + ",query2->" + bbbb + ",query3->" + cccc);
            }
        }

    }
}