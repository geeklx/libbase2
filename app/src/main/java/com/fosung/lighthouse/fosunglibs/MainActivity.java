//package com.fosung.lighthouse.fosunglibs;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.blankj.utilcode.util.BarUtils;
//import com.blankj.utilcode.util.SPUtils;
//import com.blankj.utilcode.util.Utils;
//import com.geek.libbase.baserecycleview.yewu1.ActViewPageYewuList1;
//import com.geek.libutils.data.MmkvUtils;
//import com.haier.cellarette.baselibrary.assetsfitandroid.AssetsMainActivity;
//import com.pgyer.pgyersdk.PgyerSDKManager;
//
//import me.jessyan.autosize.AutoSize;
//import me.jessyan.autosize.AutoSizeConfig;
//import me.jessyan.autosize.unit.Subunits;
//
//public class MainActivity extends AppCompatActivity {
//
//    private TextView tv1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        tv1 = findViewById(R.id.tv1);
//        tv1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        new PgyerSDKManager.Init()
//                .setContext(getApplicationContext()) //设置上下问对象
//                .start();
//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, com.geek.libbase.R.color.transparent));
//        BarUtils.setStatusBarLightMode(this, true);
//        Utils.init(Utils.getApp());// com.blankj:utilcode:1.17.3
//        MmkvUtils.getInstance().get("");
//        MmkvUtils.getInstance().get_demo();
//        Log.e("configShipei", SPUtils.getInstance().getFloat("textSizef", 0f) + "");
//        AutoSize.initCompatMultiProcess(this);
//        AutoSizeConfig.getInstance()
//                .setPrivateFontScale(SPUtils.getInstance().getFloat("textSizef", 0f))
//                .setExcludeFontScale(true)
//                .getUnitsManager()
//                .setSupportDP(true)
//                .setSupportSubunits(Subunits.MM);
//        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(MainActivity.this, JsWebActivity3.class));
////                startActivity(new Intent(MainActivity.this, AgentwebAct.class));
////                startActivity(new Intent(MainActivity.this, ShuiyinAct1.class));
////                startActivity(new Intent(MainActivity.this, LocActivity.class));
////                startActivity(new Intent(MainActivity.this, ActYewuList1.class));
////                startActivity(new Intent(MainActivity.this, ActViewPageYewuList1.class));
//                startActivity(new Intent(MainActivity.this, AssetsMainActivity.class));
////                Permission24Util.checkPermissonsRom(MainActivity.this,
////                        "rom1",
////                        "安装应用需要打开后台弹窗和弹出层权限" + "\n\n" + "请点击|" + "设置|" + "更多设置|" + "系统安全|" + "权限管理|" + "-允许后台弹窗和弹出层"
////                        ,60 * 1000);
//            }
//        });
////        //ces
////        HeaderBean headerBean = new HeaderBean();
////        headerBean.setImei(BanbenUtils.getInstance().getImei());
////        headerBean.setPlatform(BanbenUtils.getInstance().getPlatform());
////        headerBean.setToken(BanbenUtils.getInstance().getToken());
////        headerBean.setModel(DeviceUtils.getManufacturer());
////        headerBean.setVersion(BanbenUtils.getInstance().getVersion());
////        headerBean.setVersion_code(AppUtils.getAppVersionCode() + "");
////        headerBean.setPackage_name(AppUtils.getAppPackageName() + "");
////        headerBean.setLatitude(SPUtils.getInstance().getString("weidu", "weidu"));
////        headerBean.setLongitude(SPUtils.getInstance().getString("jingdu", "jingdu"));
////        MmkvUtils.getInstance().set_common_json("app_header", JSON.toJSONString(headerBean), HeaderBean.class);
//
//    }
//}