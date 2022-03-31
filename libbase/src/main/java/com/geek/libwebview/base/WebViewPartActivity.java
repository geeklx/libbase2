package com.geek.libwebview.base;

import android.os.Bundle;

import com.geek.libbase.R;


public class WebViewPartActivity extends WebViewActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_webview_part_layout);
        setUp();
        url = "http://www.baidu.com/?condition=login";
        loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        set_destory();
    }
}
