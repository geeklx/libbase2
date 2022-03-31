package com.geek.libbase.plugins.proxy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geek.libbase.plugins.PluginManager;
import com.geek.libbase.plugins.interfaces.ActivityStandardInterface;

import java.lang.reflect.Constructor;

public class ProxyActivity extends AppCompatActivity {
    //需要加载插件的全类名
    private String className;
    private ActivityStandardInterface mStandardInterface;

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");

        //加载类
        try {
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            //activity实现了ActivityStandardInterface接口
            Object instance = constructor.newInstance(new Object[]{});
            mStandardInterface = (ActivityStandardInterface) instance;

            mStandardInterface.attach(this);

            Bundle bundle = new Bundle();
            mStandardInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startActivity(Intent intent) {
        String className1 = intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className1);
        //从ProxyActivity跳转到ProxyActivity
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        String serviceName = service.getStringExtra("serviceName");
        Intent intent1 = new Intent(this, ProxyService.class);
        intent1.putExtra("serviceName", serviceName);
        //开启ProxyServer服务，传递全类名:com.example.aplugin.OneService
        return super.startService(intent1);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        IntentFilter newIntentFilter = new IntentFilter();
        for (int i = 0; i < filter.countActions(); i++) {
            newIntentFilter.addAction(filter.getAction(i));
        }
        Toast.makeText(this, "注册广播", Toast.LENGTH_SHORT).show();
        //注册ProxyBroadcastReceiver
        return super.registerReceiver(new ProxyBroadcastReceiver(receiver.getClass().getName(), this), newIntentFilter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        Toast.makeText(this, "发送广播", Toast.LENGTH_SHORT).show();
        super.sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStandardInterface.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStandardInterface.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStandardInterface.onPause();
    }
}
