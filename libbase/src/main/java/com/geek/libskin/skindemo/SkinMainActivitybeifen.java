package com.geek.libskin.skindemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinLog;
import com.geek.libskin.skinbase.SkinManager;
import com.geek.libskin.skinbase.callback.SkinStateListener;
import com.geek.libskin.skinbase.util.BaseApp3;
import com.geek.libskin.skindemo.activity.SkinBaseActivity;
import com.geek.libskin.skindemo.activity.SkinCustomViewActivity;
import com.geek.libskin.skindemo.activity.SkinDialogActivity;
import com.geek.libskin.skindemo.activity.SkinDynamicActivity;
import com.geek.libskin.skindemo.activity.SkinFragmentActivity;
import com.geek.libskin.skindemo.activity.SkinRecyclerViewActivity;
import com.geek.libupdateapp.customview.ConfirmDialog;
import com.geek.libupdateapp.feature.Callback;

import java.util.ArrayList;
import java.util.List;

public class SkinMainActivitybeifen extends SkinBaseActivity {

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        //
        SkinManager.init(BaseApp3.get());
        /// 申请读写权限
        initPermission(new ArrayList<String>() {{
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }});

        /// 状态监听回调
        SkinManager.getInstance().setStateListener(this, new SkinStateListener() {
            @Override
            public void skinStateResultCallBack(SkinManager.State state) {
                SkinLog.i("szj当前回调状态1", state.name());
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.skin_activity_main2;
    }


    /*
     * 作者:史大拿
     * 创建时间: 12/28/22 8:14 PM
     * TODO 权限申请
     */
    private void initPermission(List<String> permissions) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            realUpdate(code);
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
//        Permission.init(this)
//                .permissions(permissions)
//                .request((allGranted, grantedList, deniedList) -> {
//                    if (!allGranted) {
//                        Toast.makeText(this, "没开权限，换不了皮肤", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    realUpdate(code);
                } else {
                    new ConfirmDialog(this, new Callback() {
                        @Override
                        public void callback(int position) {
                            if (position == 1) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                startActivity(intent);
                            }
                        }
                    }).setContent("暂无读写SD卡权限\n是否前往设置？").show();
                }
                break;
            default:
                break;
        }
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 3:55 PM
     * TODO fragment换肤
     */
    public void fragmentClick(View view) {
        startActivity(new Intent(this, SkinFragmentActivity.class));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 3:55 PM
     * TODO recyclerView换肤
     */
    public void recyclerClick(View view) {
        startActivity(new Intent(this, SkinRecyclerViewActivity.class));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 4:53 PM
     * TODO 自定义view换肤
     */
    public void customViewClick(View view) {
        startActivity(new Intent(this, SkinCustomViewActivity.class));
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/5/23 9:26 AM
     * TODO 动态换肤
     */
    public void dynamicClick(View view) {
        startActivity(new Intent(this, SkinDynamicActivity.class));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/5/23 9:43 AM
     * TODO dialog换肤
     */
    public void dialogClick(View view) {
        startActivity(new Intent(this, SkinDialogActivity.class));
    }
}