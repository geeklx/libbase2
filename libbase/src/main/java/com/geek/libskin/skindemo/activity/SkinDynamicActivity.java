package com.geek.libskin.skindemo.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinLog;
import com.geek.libskin.skinbase.SkinManager;
import com.geek.libskin.skinbase.callback.SkinStateListener;


/**
 * @ClassName: DynamicActivity
 * @Author: 史大拿
 * @CreateDate: 1/5/23$ 9:26 AM$
 * TODO 自定义换肤
 */
public class SkinDynamicActivity extends SkinBaseActivity {

    private TextView mTv;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initCreate(Bundle savedInstanceState) {

        mTv = findViewById(R.id.tv);
        mTv.setBackground(SkinManager.getInstance().getDrawable("skin_global_skin_drawable_background"));
        mTv.setText(SkinManager.getInstance().getString("skin_global_custom_view_text"));

        /// 状态监听回调
        SkinManager.getInstance().setStateListener(this, new SkinStateListener() {
            @Override
            public void skinStateResultCallBack(SkinManager.State state) {
                SkinLog.i("szj当前回调状态2", state.name());
            }
        });
    }

    @Override
    protected void notifyChanged() {
        Toast.makeText(this, "notifyChanged", Toast.LENGTH_SHORT).show();
        mTv.setBackground(SkinManager.getInstance().getDrawable("skin_global_skin_drawable_background"));
        mTv.setText(SkinManager.getInstance().getString("skin_global_custom_view_text"));
    }

    @Override
    protected int layoutId() {
        return R.layout.skin_activity_dynamic;
    }
}
