package com.geek.libwebview.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.geek.libbase.R;

public class AdCommPart1Activity extends WebViewActivity {

    public static final String TAG = AdCommPart1Activity.class.getSimpleName();
    private String url1;
    private String id1;
    private TextView tv_adJumps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcommpart1);
//        //虚拟键
//        if (NavigationBarUtil.hasNavigationBar(this)) {
////            NavigationBarUtil.initActivity(getWindow().getDecorView());
//            NavigationBarUtil.hideBottomUIMenu(this);
//        }
//        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);// topbar
        //
        setUp();

//        id1 = getIntent().getExtras().getString("id1");
//        url1 = getIntent().getExtras().getString("url1");
        url1 = "https://cx.o2o.bailingjk.net/wechat/#/main/medicalIndex?publicNoCode=jksd_0011&type=1&patientId=&language=";
        loadUrl(url1);
        //
        tv_adJumps = findViewById(R.id.tv_adJumps3);
        tv_adJumps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SPUtils.getInstance().put("id1", id1);
                finish();
            }
        });

//        Log.e("gongshi", (Math.sqrt(Math.pow(812, 2) + (Math.pow(375, 2))) / 25.4 + ""));
//        Log.e("gongshi", (Math.sqrt(Math.pow(640, 2) + (Math.pow(360, 2))) / 25.4 + ""));
        Log.e("gongshi", (Math.sqrt(Math.pow(667, 2) + (Math.pow(375, 2))) / 25.4 + ""));

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            this.finish();
//            return true;
//        } else {
////            this.finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        set_destory();
        finish();
    }


}
