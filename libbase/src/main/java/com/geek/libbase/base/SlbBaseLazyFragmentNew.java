package com.geek.libbase.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.geek.libbase.base.SlbBaseFragment;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;

public abstract class SlbBaseLazyFragmentNew extends SlbBaseFragment {

    private Context mContext;
    private boolean isFirstLoad = true; // 是否第一次加载
    private ProgressDialog mProgressDialog; // 加载进度对话框

    public int current_pos = 0;
    public static final String SAVED_CURRENT_ID = "currentId";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_CURRENT_ID, current_pos);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            int cachedId = savedInstanceState.getInt(SAVED_CURRENT_ID, 0);
            current_pos = cachedId;
        }
        mContext = getActivity();

    }

    // fragment通信
    public void call(Object value) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData();
            initEvent();
            isFirstLoad = false;
        }
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

    }

    /**
     * 显示提示框
     *
     * @param msg 等待消息字符串
     */
    protected void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}