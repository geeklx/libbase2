package com.geek.libbase.plugins.plugins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.geek.libbase.plugins.interfaces.BroadcastReceiverStandardInterface;

public class PluginReceiver extends BroadcastReceiver implements BroadcastReceiverStandardInterface {
    @Override
    public void attach(Context context) {
        Toast.makeText(context, "绑定上下文成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到广播", Toast.LENGTH_SHORT).show();
    }
}
