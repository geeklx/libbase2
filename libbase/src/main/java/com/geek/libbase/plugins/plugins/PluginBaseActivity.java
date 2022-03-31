package com.geek.libbase.plugins.plugins;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geek.libbase.plugins.interfaces.ActivityStandardInterface;

public class PluginBaseActivity extends AppCompatActivity implements ActivityStandardInterface {
    protected AppCompatActivity that;

    @Override
    public void attach(AppCompatActivity proxyActivity) {
        that = proxyActivity;
    }

    @Override
    public void startActivity(Intent intent) {
        //ProxyActivity跳转ProxyActivity
        Intent m = new Intent();
        m.putExtra("className", intent.getComponent().getClassName());
        //调用ProxyActivity的startActivity方法
        that.startActivity(m);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent m = new Intent();
        m.putExtra("serviceName", service.getComponent().getClassName());
        //调用ProxyActivity的startServer方法
        return that.startService(m);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return that.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        that.sendBroadcast(intent);
    }

    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (that != null) {
            that.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (that != null) {
            return that.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (that != null) {
            return that.getIntent();
        }
        return super.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        if (that != null) {
            return that.getClassLoader();
        } else {
            return super.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (that != null) {
            return that.getResources();
        } else {
            return super.getResources();
        }
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if (that != null) {
            return that.getLayoutInflater();
        } else {
            return super.getLayoutInflater();
        }
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if (that != null) {
            return that.getApplicationInfo();
        } else {
            return super.getApplicationInfo();
        }
    }

    @Override
    public Window getWindow() {
        if (that != null) {
            return that.getWindow();
        } else {
            return super.getWindow();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (that != null) {
            return that.getWindowManager();
        } else {
            return super.getWindowManager();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
