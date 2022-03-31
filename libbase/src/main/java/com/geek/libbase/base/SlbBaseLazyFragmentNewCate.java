package com.geek.libbase.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;

public abstract class SlbBaseLazyFragmentNewCate<T> extends SlbBaseFragment {

    public static final String TAG = SlbBaseLazyFragmentNewCate.class.getSimpleName();
    private Context mContext;
    private boolean isFirstLoad = true; // 是否第一次加载
    private ProgressDialog mProgressDialog; // 加载进度对话框
    private MessageReceiverIndex mMessageReceiver;
    public T parentCategory;
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
        if (mMessageReceiver == null) {
            mMessageReceiver = new MessageReceiverIndex();
            IntentFilter filter = new IntentFilter();
            filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
            filter.addAction(TAG);
            LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
            MyLogUtil.e("tablayoutId初始化数据",TAG);
        }
    }

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (TAG.equals(intent.getAction())) {
                    onReceiveMsg(intent);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void sendMsgReceiver(T bean){
        Intent msgIntent = new Intent();
        msgIntent.setAction(TAG);
        msgIntent.putExtra("dingwei", (Bundle) bean);
        LocalBroadcastManagers.getInstance(getActivity()).sendBroadcast(msgIntent);
    }

    public void updateArguments(T parentCategory) {
        this.parentCategory = parentCategory;
        updateArgumentsData(parentCategory);
    }

    // 重新刷新数据
    protected abstract void updateArgumentsData(T parentCategory);

    // 广播更新数据
    protected abstract void onReceiveMsg(Intent intent);

    // fragment通信
    public void call(Object value) {

    }


    @Override
    public void onDestroy() {
        LocalBroadcastManagers.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
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