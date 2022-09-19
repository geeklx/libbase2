package com.geek.libbase.utils;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;
import java.lang.reflect.Method;

public final class RomJumpUtils {
    public static RomJumpUtils instance;
    //    private static volatile SpannableStringUtils instance;
    private Context mContext;

    public RomJumpUtils(Context context) {
        mContext = context;
    }

    public static RomJumpUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (RomJumpUtils.class) {
                instance = new RomJumpUtils(context);
            }
        }
        return instance;
    }


    public boolean isXiaoMi() {
        return this.checkManufacturer("xiaomi");
    }

    public boolean isOppo() {
        return this.checkManufacturer("oppo");
    }

    public boolean isHuawei() {
        return this.checkManufacturer("huawei");
    }

    public boolean isVivo() {
        return this.checkManufacturer("vivo");
    }

    private boolean checkManufacturer(String manufacturer) {
        return TextUtils.equals(manufacturer.toLowerCase(), Build.MANUFACTURER.toLowerCase());
    }

    public boolean isBackgroundStartAllowed(Context context) {
        if (this.isXiaoMi()) {
            return this.isXiaomiBgStartPermissionAllowed(context);
        } else if (this.isVivo()) {
            return this.isVivoBgStartPermissionAllowed(context);
        } else {
            return (this.isHuawei() || this.isOppo()) && VERSION.SDK_INT >= 23 ? Settings.canDrawOverlays(context) : true;
        }
    }

    private boolean isXiaomiBgStartPermissionAllowed(Context context) {
        AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            int op = 10021;
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            Log.e("===", "not support");
        }
        return false;
    }

    private boolean isVivoBgStartPermissionAllowed(Context context) {
        return this.getVivoBgStartPermissionStatus(context) == 0;
    }

    @SuppressLint({"Range"})
    private int getVivoBgStartPermissionStatus(Context context) {
        Uri uri = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{context.getPackageName()};
        int state = 1;
        try {
            Cursor var16 = context.getContentResolver().query(uri, (String[]) null, selection, selectionArgs, (String) null);
            if (var16 != null) {
                Closeable var6 = (Closeable) var16;
                try {
                    Cursor it = (Cursor) var6;
                    if (it.moveToFirst()) {
                        state = it.getInt(it.getColumnIndex("currentstate"));
                    }
                } catch (Throwable var13) {
                    throw var13;
                }
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

        return state;
    }
}
