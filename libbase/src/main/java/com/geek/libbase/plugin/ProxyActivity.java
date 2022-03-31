package com.geek.libbase.plugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dalvik.system.DexClassLoader;

public final class ProxyActivity extends Activity {
    private IPlugin plugin;
    private static final String TAG = "ProxyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ProxyActivity", "onCreate");
        if (PluginManager.INSTANCE.isPlugin()) {
            try {
                String clsName = this.getIntent().getStringExtra("activity_class_name");
                Log.d("ProxyActivity", "activity name:" + clsName);
                DexClassLoader var10000 = PluginManager.INSTANCE.getPluginDexClassLoader();
                Class cls = var10000.loadClass(clsName);
                Object newInstance = cls.newInstance();
                if (newInstance instanceof IPlugin) {
                    this.plugin = (IPlugin) newInstance;
                    IPlugin var9 = this.plugin;
                    if (var9 != null) {
                        var9.attach((Activity) this);
                    }

                    Bundle bundle = new Bundle();
                    var9 = this.plugin;
                    if (var9 != null) {
                        var9.onCreate(bundle);
                    }
                }
            } catch (ClassNotFoundException var6) {
                var6.printStackTrace();
            } catch (InstantiationException var7) {
                var7.printStackTrace();
            } catch (IllegalAccessException var8) {
                var8.printStackTrace();
            }
        }

    }

    @Override
    public void startActivity(@NotNull Intent intent) {
        Intent newIntent = new Intent((Context) this, ProxyActivity.class);
        ComponentName var10002 = intent.getComponent();
        newIntent.putExtra("activity_class_name", var10002.getClassName());
        super.startActivity(newIntent);
    }

    @Override
    @Nullable
    public Resources getResources() {
        return !PluginManager.INSTANCE.isPlugin() ? super.getResources() : PluginManager.INSTANCE.getPluginRes();
    }

    @Override
    @Nullable
    public AssetManager getAssets() {
        return !PluginManager.INSTANCE.isPlugin() ? super.getAssets() : PluginManager.INSTANCE.getPluginAssets();
    }

    @Override
    @Nullable
    public ClassLoader getClassLoader() {
        return !PluginManager.INSTANCE.isPlugin() ? super.getClassLoader() : (ClassLoader) PluginManager.INSTANCE.getPluginDexClassLoader();
    }

    @Override
    protected void onStart() {
        Log.d("ProxyActivity", "onStart");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onStart();
        }

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("ProxyActivity", "onResume");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onResume();
        }

        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d("ProxyActivity", "onRestart");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onRestart();
        }

        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d("ProxyActivity", "onPause");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onPause();
        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("ProxyActivity", "onStop");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onStop();
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("ProxyActivity", "onDestroy");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onDestroy();
        }

        super.onDestroy();
    }

    @Override
    public void finish() {
        Log.d("ProxyActivity", "finish");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.finish();
        }

        super.finish();
    }

    @Override
    public void onBackPressed() {
        Log.d("ProxyActivity", "onBackPressed");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onBackPressed();
        }

        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(@Nullable Intent intent) {
        Log.d("ProxyActivity", "onNewIntent");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onNewIntent(intent);
        }

        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("ProxyActivity", "onActivityResult");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Log.d("ProxyActivity", "onRequestPermissionsResult");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        Log.d("ProxyActivity", "onConfigurationChanged");
        IPlugin var10000 = this.plugin;
        if (var10000 != null) {
            var10000.onConfigurationChanged(newConfig);
        }

        super.onConfigurationChanged(newConfig);
    }
}
