package com.geek.libbase.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.geek.libutils.app.BaseApp;

/**
 * 方式1：
 * if (PermissionUtil.checkPermissons(requireContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})) {
 *
 *                     PermissionHelper.requestReadWriteSdCardPermission(getActivity(), new PermissionsResultAction() {
 *                         @Override
 *                         public void onGranted() {
 *                             ImageUtil.saveImage(getContext(), data);
 *                             Toast.makeText(mActivity, "图片保存成功", Toast.LENGTH_LONG).show();
 *                         }
 *
 *                         @Override
 *                         public void onDenied(String permission) {
 *                                 PermissionUtil.showToast(requireContext(),permission);
 *                         }
 *                     });
 *                 }
 * 方式2：
 *   if (PermissionUtil.checkPermisson(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
 *             PermissionHelper.requestLocationPermission(getContext(), new PermissionsResultAction() {
 *                 @Override
 *                 public void onGranted() {
 *                     final DqLocationUtil location = new DqLocationUtil(mActivity);
 *                     location.startLocation(new DqLocationUtil.OnGetLocation() {
 *                         @Override
 *                         public void getLocation(AMapLocation location) {
 *                             if (location != null) {
 *                                 lat = location.getLatitude();
 *                                 lng = location.getLongitude();
 *                                 if (pos == 0) {
 *                                     getDxjyjd(lat, lng);
 *                                 } else {
 *                                     getDxjyjdNear(lat, lng);
 *                                 }
 *
 *                             }
 *                         }
 *
 *                         @Override
 *                         public void locationFail() {
 *                             ToastUtil.toastShort("定位失败,请检查位置信息是否开启");
 *                         }
 *                     });
 *                 }
 *
 *                 @Override
 *                 public void onDenied(String permission) {
 *                     PermissionUtil.showToast(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
 *                 }
 *             });
 *         }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


public class Permission24Util {

    public static boolean checkPermisson(Context mContext, String permission,int timer) {
        if (lacksPermission(mContext, permission)) {
            return true;
        } else {
            if (System.currentTimeMillis() - getPermissonTime(permission) > timer) {
                savePermissonTime(permission);
                return true;
            } else {
                showToast(mContext, permission);
                return false;
            }
        }

    }

    public static boolean checkPermissons(Context context, String[] permissions,int timer) {
        for (String p : permissions) {
            if (!lacksPermission(context, p)) {
                if (System.currentTimeMillis() - getPermissonTime(p) > timer) {
                    int aaa = 24 * 60 * 60 * 1000;
                    savePermissonTime(p);// 86400000  24 * 60 * 60 * 1000
                    return true;
                } else {
                    showToast(context, p);
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static void savePermissonTime(String permission) {
        SharedPreferences sp = BaseApp.get().getSharedPreferences("Permission", Context.MODE_PRIVATE);
        sp.edit().putLong(permission, System.currentTimeMillis()).commit();
    }

    private static long getPermissonTime(String permission) {
        SharedPreferences sp = BaseApp.get().getSharedPreferences("Permission", Context.MODE_PRIVATE);
        return sp.getLong(permission, 0);
    }

    public static void showToast(Context context, String permission) {
        try {
            String strKey = permission.replace(".", "_");
            int resId = context.getResources().getIdentifier(strKey, "string", context.getPackageName());
            String strValue = context.getResources().getString(resId);
            ToastUtils.showLong(strValue);
        } catch (Exception e) {


        }
    }

}
