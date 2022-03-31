package com.geek.libbase.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class PluginActivity extends AppCompatActivity implements IPlugin {
    private String TAG = this.getClass().getSimpleName();
    public Activity proxy;

    @NotNull
    public final Activity getProxy() {
        Activity var10000 = this.proxy;
        return var10000;
    }

    public final void setProxy(@NotNull Activity var1) {
        this.proxy = var1;
    }

    @Override
    public void attach(@NotNull Activity activity) {
        this.proxy = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onCreate(bundle);
            this.proxy = (Activity) this;
        }

    }

    @Override
    public void onStart() {
        Log.d(this.TAG, "onStart");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onStart();
        }

    }

    @Override
    public void onResume() {
        Log.d(this.TAG, "onResume");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onResume();
        }

    }

    @Override
    public void onPause() {
        Log.d(this.TAG, "onPause");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onPause();
        }

    }

    @Override
    public void onStop() {
        Log.d(this.TAG, "onStop");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onStop();
        }

    }

    @Override
    public void onRestart() {
        Log.d(this.TAG, "onRestart");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onRestart();
        }

    }

    @Override
    public void onNewIntent(@Nullable Intent intent) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onNewIntent(intent);
        }

    }

    @Override
    public void onDestroy() {
        Log.d(this.TAG, "onDestroy");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onDestroy();
        }

    }

    @Override
    public void onBackPressed() {
        Log.d(this.TAG, "onBackPressed");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onBackPressed();
        }

    }

    @Override
    public void finish() {
        Log.d(this.TAG, "finish");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Log.d(this.TAG, "onRequestPermissionsResult");
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.onConfigurationChanged(newConfig);
        }

    }

    @Override
    public void startActivity(@Nullable Intent intent) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.startActivity(intent);
        } else {
            Activity var10000 = this.proxy;
            var10000.startActivity(intent);
        }

    }

    @Override
    @NotNull
    public LayoutInflater getLayoutInflater() {
        LayoutInflater var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getLayoutInflater();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getLayoutInflater();
        }

        return var10000;
    }

    @Override
    @Nullable
    public WindowManager getWindowManager() {
        WindowManager var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getWindowManager();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getWindowManager();
        }

        return var10000;
    }

    @Override
    @Nullable
    public ApplicationInfo getApplicationInfo() {
        ApplicationInfo var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getApplicationInfo();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getApplicationInfo();
        }

        return var10000;
    }

    @Override
    @NotNull
    public Context getBaseContext() {
        Context var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getBaseContext();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getBaseContext();
        }

        return var10000;
    }

    @Override
    @NotNull
    public Resources getResources() {
        Resources var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getResources();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getResources();
        }

        return var10000;
    }

    @Override
    @NotNull
    public AssetManager getAssets() {
        AssetManager var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getAssets();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getAssets();
        }

        return var10000;
    }

    @Override
    @NotNull
    public ClassLoader getClassLoader() {
        ClassLoader var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getClassLoader();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getClassLoader();
        }

        return var10000;
    }

    @Override
    public View findViewById(int id) {
        View var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.findViewById(id);
        } else {
            Activity var2 = this.proxy;
            var10000 = var2.findViewById(id);
        }

        return var10000;
    }

    @Override
    public void setContentView(int layoutResID) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.setContentView(layoutResID);
        } else {
            Activity var10000 = this.proxy;
            var10000.setContentView(layoutResID);
        }

    }

    @Override
    public void setContentView(@Nullable View view) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.setContentView(view);
        } else {
            Activity var10000 = this.proxy;
            var10000.setContentView(view);
        }

    }

    @Override
    public void setContentView(@Nullable View view, @Nullable LayoutParams params) {
        if (!PluginManager.INSTANCE.isPlugin()) {
            super.setContentView(view, params);
        } else {
            Activity var10000 = this.proxy;
            var10000.setContentView(view, params);
        }

    }

    @Override
    @NotNull
    public Window getWindow() {
        Window var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getWindow();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getWindow();
        }

        return var10000;
    }

    @Override
    @NotNull
    public String getPackageName() {
        String var10000;
        if (!PluginManager.INSTANCE.isPlugin()) {
            var10000 = super.getPackageName();
        } else {
            Activity var1 = this.proxy;
            var10000 = var1.getPackageName();
        }

        return var10000;
    }
}
