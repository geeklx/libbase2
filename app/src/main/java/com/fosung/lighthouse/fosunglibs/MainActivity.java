package com.fosung.lighthouse.fosunglibs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.baselibrary.shuiyin.DownloadPictureUtil1;
import com.haier.cellarette.baselibrary.shuiyin.OnDownloadListener1;
import com.haier.cellarette.baselibrary.shuiyin.ShuiyinUtils3;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
//        tv1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        new PgyerSDKManager.Init()
//                .setContext(getApplicationContext()) //设置上下问对象
//                .start();
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, com.geek.libbase.R.color.transparent));
        BarUtils.setStatusBarLightMode(this, true);
        Utils.init(Utils.getApp());// com.blankj:utilcode:1.17.3
        MmkvUtils.getInstance().get("");
        MmkvUtils.getInstance().get_demo();
        Log.e("configShipei", SPUtils.getInstance().getFloat("textSizef", 0f) + "");
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance()
                .setPrivateFontScale(SPUtils.getInstance().getFloat("textSizef", 0f))
                .setExcludeFontScale(true)
                .getUnitsManager()
                .setSupportDP(true)
                .setSupportSubunits(Subunits.MM);
        //
        String url111 = ShuiyinUtils3.getInstance(BaseApp.get()).init1("shuiyin2_geek", "filename_geek");
        String url112 = DownloadPictureUtil1.INSTANCE.init1("shuiyin2_geek", "filename_geek");
        DownloadPictureUtil1.INSTANCE.onbackclicklistener(new OnDownloadListener1() {
            @Override
            public void onDownloadStart(Activity activity, int position) {

            }

            @Override
            public void onDownloadSuccess(Activity activity, int position) {

            }

            @Override
            public void onDownloadFailed(Activity activity, int position) {

            }
        });
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, JsWebActivity3.class));
//                startActivity(new Intent(MainActivity.this, AgentwebAct.class));
//                startActivity(new Intent(MainActivity.this, ShuiyinAct1.class));
//                startActivity(new Intent(MainActivity.this, LocActivity.class));
//                startActivity(new Intent(MainActivity.this, ActYewuList1.class));
//                startActivity(new Intent(MainActivity.this, ActViewPageYewuList1.class));
//                startActivity(new Intent(MainActivity.this, AssetsMainActivity.class));
                //
//                SkinManager.init(BaseApp3.get());
//                startActivity(new Intent(MainActivity.this, SkinMainActivity.class));
//                startActivity(new Intent(MainActivity.this, ActKeyboard1.class));
//                Permission24Util.checkPermissonsRom(MainActivity.this,
//                        "rom1",
//                        "安装应用需要打开后台弹窗和弹出层权限" + "\n\n" + "请点击|" + "设置|" + "更多设置|" + "系统安全|" + "权限管理|" + "-允许后台弹窗和弹出层"
//                        ,60 * 1000);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
//
//                    PermissionUtlis.checkPermissions(
//                            act,
//                            Manifest.permission.READ_MEDIA_VIDEO,
//                            Manifest.permission.READ_MEDIA_AUDIO,
//                            Manifest.permission.READ_MEDIA_IMAGES
//                    ) {
//
//                    }
//                } else {
//                    PermissionUtlis.checkPermissions(
//                            act,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                    ) {
//
//                    }
//                }
                // 水印bufen
//                ImageView iv1 = findViewById(R.id.iv1);
//                ImageView iv_shang1 = findViewById(R.id.iv_shang);
//                ImageView iv_xia1 = findViewById(R.id.iv_xia);
//                ImageView iv_zhong = findViewById(R.id.iv_zhong);
//                ImageView iv_zhong2 = findViewById(R.id.iv_zhong2);
////                NestedScrollView sv1 = findViewById(R.id.sv1);
//                LinearLayout ll1 = findViewById(R.id.ll1);
////                ImageView iv_shang = new ImageView(MainActivity.this);
////                iv_shang.setImageResource(com.haier.cellarette.baselibrary.R.drawable.img03);
////                ll1.addView(iv_shang1, 0);
//                //
////                ImageView iv_xia = new ImageView(MainActivity.this);
////                iv_xia.setImageResource(com.haier.cellarette.baselibrary.R.drawable.img02);
////                ViewGroup.LayoutParams layoutParams = iv_xia.getLayoutParams();
////                int height = layoutParams.height;
////                iv_xia.setImageDrawable(getResources().getDrawable(com.haier.cellarette.baselibrary.R.drawable.img02)); //可用
////                LinearLayout.LayoutParams params1 =
////                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////                iv_xia.setLayoutParams(params1);
//                // 中+下
////                Bitmap bitmap1 = ShuiyinUtils3.getInstance(BaseApp.get()).mergeBitmapTopBottomByScrollView(sv1, iv_xia1);
////                // 上+中下
////                iv_zhong2.setImageBitmap(bitmap1);
//////                iv_zhong.setImageBitmap(bitmap);
////                Bitmap bitmap2 = ShuiyinUtils3.getInstance(BaseApp.get()).mergeBitmapTopBottom(iv_zhong2, iv_zhong);
//                //
//                Bitmap bitmap3 = ShuiyinUtils3.getInstance(BaseApp.get())
//                        .mergeBitmapTopBottomByScrollView2(
//                                com.haier.cellarette.baselibrary.R.color.white,
//                                iv_shang1,
//                                ll1,
//                                com.haier.cellarette.baselibrary.R.drawable.shape_red2,
//                                iv_xia1);
////                //
//                long timeStamp = System.currentTimeMillis();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String sd = sdf.format(new Date(timeStamp));
//                ShuiyinUtils3.getInstance(BaseApp.get()).saveImageToGallery1("成功保存到相册", bitmap3,
//                        "shuiyin2_geek", sd);
////                        "shuiyin2_geek", "filename_geek");
//                ToastUtils.showLong("测试测试");
                // ces
//                DownloadPictureUtil1.INSTANCE.save(MainActivity.this, new File(url112), 0);
//                DownloadPictureUtil1.INSTANCE.downloadPicture(MainActivity.this, 0, url112);
                //
//                ShuiyinUtils3.getInstance(BaseApp.get()).diaoyong2(iv1, url111);
                //
//                //3. notify
//                MediaScannerConnection.scanFile(BaseApp.get(), new String[]{target.getAbsolutePath()},
//                        new String[]{"image/" + ext}, new MediaScannerConnection.OnScanCompletedListener() {
//                            @Override
//                            public void onScanCompleted(final String path, Uri uri) {
//                                mainHandler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        ToastUtils.showLong("成功保存到相册");
//                                        mContext = null;
//                                    }
//                                });
//                            }
//                        });
                //
//                startActivity(new Intent(AppUtils.getAppPackageName()+".hs.act.slbapp.ActCircleTextProgressbar3"));
                //
                startActivity(new Intent(AppUtils.getAppPackageName()+".hs.act.slbapp.QuanxianActivity3"));

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

