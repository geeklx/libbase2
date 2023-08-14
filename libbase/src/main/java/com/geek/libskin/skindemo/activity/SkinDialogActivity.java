package com.geek.libskin.skindemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinActivityLifecycleCallbacks;
import com.geek.libskin.skindemo.dialog.SkinDialogFragment;


/**
 * @ClassName: DialogActivity
 * @Author: 史大拿
 * @CreateDate: 1/5/23$ 9:44 AM$
 * TODO
 */
public class SkinDialogActivity extends SkinBaseActivity {

    private AlertDialog alertDialog;
    private SkinDialogFragment dialogFragment;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Button btnAlert = findViewById(R.id.btn_alert);
        Button btnFragmentDialog = findViewById(R.id.btn_fragment_dialog);

        btnAlert.setOnClickListener(this::showAlertDialog);
        btnFragmentDialog.setOnClickListener(this::showFragmentDialog);
    }

    private void showFragmentDialog(View v) {
        if (dialogFragment == null) {
            dialogFragment = new SkinDialogFragment();
        }

        /*
         * 作者:史大拿
         * 创建时间: 1/5/23 11:28 AM
         * TODO 当fragment没有被添加的时候在show
         */
        if (!getSupportFragmentManager().getFragments().contains(dialogFragment)) {
            dialogFragment.show(getSupportFragmentManager(), "TAG");
        }
    }

    @SuppressLint("InflateParams")
    private void showAlertDialog(View v) {

        // 缓存一波，避免重复解析皮肤包
        if (alertDialog == null) {
            View view = getLayoutInflater().inflate(R.layout.skin_item_alert_dialog, null);
            alertDialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .create();
        }

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }


        // 初始化第一次，避免第一次的时候没有换肤效果
        SkinActivityLifecycleCallbacks.tryInitSkin(this);
    }

    @Override
    protected void notifyChanged() {
//        showAlertDialog(null);
//        showFragmentDialog(null);
    }

    @Override
    protected int layoutId() {
        return R.layout.skin_activity_dialog;
    }
}
