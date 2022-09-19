package com.geek.libbase.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libbase.R;
import com.geek.libutils.app.BaseApp;
import com.hjq.permissions.XXPermissions;

/**
 * 方式1：
 * if (PermissionUtil.checkPermissons(requireContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})) {
 * <p>
 * PermissionHelper.requestReadWriteSdCardPermission(getActivity(), new PermissionsResultAction() {
 *
 * @Override public void onGranted() {
 * ImageUtil.saveImage(getContext(), data);
 * Toast.makeText(mActivity, "图片保存成功", Toast.LENGTH_LONG).show();
 * }
 * @Override public void onDenied(String permission) {
 * PermissionUtil.showToast(requireContext(),permission);
 * }
 * });
 * }
 * 方式2：
 * if (PermissionUtil.checkPermisson(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
 * PermissionHelper.requestLocationPermission(getContext(), new PermissionsResultAction() {
 * @Override public void onGranted() {
 * final DqLocationUtil location = new DqLocationUtil(mActivity);
 * location.startLocation(new DqLocationUtil.OnGetLocation() {
 * @Override public void getLocation(AMapLocation location) {
 * if (location != null) {
 * lat = location.getLatitude();
 * lng = location.getLongitude();
 * if (pos == 0) {
 * getDxjyjd(lat, lng);
 * } else {
 * getDxjyjdNear(lat, lng);
 * }
 * <p>
 * }
 * }
 * @Override public void locationFail() {
 * ToastUtil.toastShort("定位失败,请检查位置信息是否开启");
 * }
 * });
 * }
 * @Override public void onDenied(String permission) {
 * PermissionUtil.showToast(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
 * }
 * });
 * }
 */


public class Permission24Util {

    public static boolean checkPermisson(Context mContext, String permission, int timer) {
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

    public static boolean checkPermissons(Context context, String[] permissions, int timer) {
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
     * @param context
     * @param key     RomJumpUtils
     * @param content "安装应用需要打开后台弹窗权限" + "\n\n" + "请点击|" + "设置|" + "更多设置|" + "系统安全|" + "权限管理|" + "-允许后台弹窗"
     * @param timer   86400000  24 * 60 * 60 * 1000
     * @return
     */
    public static void checkPermissonsRom(Context context, String key, String content, int timer) {
        if (!RomJumpUtils.getInstance(context).isBackgroundStartAllowed(context)) {
            // 这里是用户没有允许后台弹窗，下次超过了timer后再次弹窗让用户去选择
            if (System.currentTimeMillis() - SPUtils.getInstance().getLong(key, 0) > timer) {
                SPUtils.getInstance().put(key, System.currentTimeMillis());
                //
                Dialog dialog = new Dialog(context, R.style.basenotice_dialog);
                if (!dialog.isShowing()) {
                    dialog.setContentView(R.layout.notice_dialogbase);
                    dialog.setCancelable(false);
                    dialog.show();
                    TextView tv_notice = dialog.findViewById(R.id.tv_notice);
                    Button btn_concle = dialog.findViewById(R.id.btn_concle);
                    Button btn_settings = dialog.findViewById(R.id.btn_settings);
                    tv_notice.setText(content);
                    btn_concle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    btn_settings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            XXPermissions.startPermissionActivity(context);
                        }
                    });
                }
            }
        }
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
