package com.geek.libbase.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

@SuppressLint({"StaticFieldLeak"})
public final class PluginManager {
    public static final String TAG = "PluginManager";
    public static final String TAG_NEW_ACTIVITY_NAME = "activity_class_name";
    public static boolean isPlugin;
    public static String pluginPackageName;
    public static Context appCtx;
    public static AssetManager pluginAssets;
    public static Resources pluginRes;
    public static DexClassLoader pluginDexClassLoader;
    public static PackageInfo pluginPackageArchiveInfo;
    public static final PluginManager INSTANCE;

    public final boolean isPlugin() {
        return isPlugin;
    }

    public final String getPluginPackageName() {
        return pluginPackageName;
    }

    public final void setPluginPackageName(@NotNull String var1) {
        pluginPackageName = var1;
    }

    public final Context getAppCtx() {
        return appCtx;
    }

    public final AssetManager getPluginAssets() {
        return pluginAssets;
    }

    public final Resources getPluginRes() {
        return pluginRes;
    }

    public final DexClassLoader getPluginDexClassLoader() {
        return pluginDexClassLoader;
    }

    public final PackageInfo getPluginPackageArchiveInfo() {
        return pluginPackageArchiveInfo;
    }

    @SuppressLint({"DiscouragedPrivateApi"})
    public final void loadApk(@Nullable Context ctx, @NotNull String apkPath, @NotNull String packageName) {
        if (ctx == null) {
            Log.d("PluginManager", "ctx is null, apk cannot be loaded dynamically");
            try {
                throw (Throwable) (new RuntimeException("ctx is null, apk cannot be loaded dynamically"));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            pluginPackageName = packageName;
            isPlugin = true;

            try {
                appCtx = ctx.getApplicationContext();
                Context var10003 = appCtx;
                File var9 = var10003.getDir("dex2opt", 0);
                String var10 = var9.getAbsolutePath();
                Context var10005 = appCtx;
                pluginDexClassLoader = new DexClassLoader(apkPath, var10, (String) null, var10005.getClassLoader());
                Context var10000 = appCtx;
                PackageInfo var7 = var10000.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
                pluginPackageArchiveInfo = var7;
                pluginAssets = (AssetManager) AssetManager.class.newInstance();
                Method var8 = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
                Method addAssetPath = var8;
                addAssetPath.invoke(pluginAssets, apkPath);
                Resources superResources = ctx.getResources();
                pluginRes = new Resources(pluginAssets, superResources != null ? superResources.getDisplayMetrics() : null, superResources != null ? superResources.getConfiguration() : null);
                Log.d("PluginManager", "dynamic loading of apk success");
            } catch (Exception var6) {
                Log.d("PluginManager", "dynamic loading of apk failed");
                isPlugin = false;
                var6.printStackTrace();
            }

        }
    }

    public final void startActivity(@NotNull Context ctx, @NotNull String actName) {
        Intent intent = new Intent(ctx, ProxyActivity.class);
        intent.putExtra("activity_class_name", actName);
        ctx.startActivity(intent);
    }

    public final void startActivity(@NotNull Context ctx, @NotNull String actName, @NotNull Bundle bundle) {
        Intent intent = new Intent(ctx, ProxyActivity.class);
        intent.putExtra("activity_class_name", actName);
        intent.putExtras(bundle);
        ctx.startActivity(intent);
    }

    private PluginManager() {
    }

    static {
        PluginManager var0 = new PluginManager();
        INSTANCE = var0;
        pluginPackageName = "";
    }
}
