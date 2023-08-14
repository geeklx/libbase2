package com.geek.libskin.skindemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinManager;
import com.geek.libskin.skindemo.SkinConfig;


/**
 * @ClassName: SkinBaseActivity
 * @Author: 史大拿
 * @CreateDate: 1/5/23$ 9:14 AM$
 * TODO
 */
public abstract class SkinBaseActivity extends AppCompatActivity {

    private TextView mTvInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("szj生命周期", "SkinBaseActivity 1");
        super.onCreate(savedInstanceState);
        Log.i("szj生命周期", "SkinBaseActivity 2");
        setContentView(R.layout.skin_activity_skin_base);
        Log.i("szj生命周期", "SkinBaseActivity 3");

        mTvInfo = findViewById(R.id.tv_info);

        View reSkin = findViewById(R.id.bt_re_skin);

        reSkin.setOnClickListener(v -> {
            SkinManager.getInstance().loadSkin(SkinConfig.getPath(this), this);
            setInfoState();
            notifyChanged();
        });

        findViewById(R.id.bt_reset).setOnClickListener(v ->
        {
            SkinManager.getInstance().reset(this);
            setInfoState();
            notifyChanged();
        });

        FrameLayout rootView = findViewById(R.id.root_view);

        int layoutId = layoutId();
        if (layoutId != 0)
            /// 加载布局
        {
            LayoutInflater.from(this).inflate(layoutId, rootView);
        }

        initCreate(savedInstanceState);
    }


    public void setInfoState() {
        setInfoState(SkinManager.getInstance().getState());
    }

    public void setInfoState(SkinManager.State state) {
        mTvInfo.setText(String.format("当前皮肤状态:%s", state.name()));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfoState();
    }

    /// 点击回调监听
    protected void notifyChanged() {
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/5/23 9:19 AM
     * TODO onCreate方法
     */
    protected abstract void initCreate(Bundle savedInstanceState);

    /*
     * 作者:史大拿
     * 创建时间: 1/5/23 9:17 AM
     * TODO 设置布局id
     */
    @LayoutRes
    protected abstract int layoutId();
}
