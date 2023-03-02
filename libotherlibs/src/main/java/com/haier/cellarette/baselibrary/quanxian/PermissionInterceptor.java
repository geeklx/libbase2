package com.haier.cellarette.baselibrary.quanxian;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;
import com.hjq.permissions.IPermissionInterceptor;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.OnPermissionPageCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.PermissionFragment;
import com.hjq.permissions.XXPermissions;
import java.util.ArrayList;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XXPermissions
 *    time   : 2021/01/04
 *    desc   : 权限申请拦截器
 */
public final class PermissionInterceptor implements IPermissionInterceptor {

    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /** 权限申请标记 */
    private boolean mRequestFlag;

    /** 权限申请说明 Popup */
    private PopupWindow mPermissionPopup;

    @Override
    public void launchPermissionRequest(@NonNull Activity activity, @NonNull List<String> allPermissions, @Nullable OnPermissionCallback callback) {
        mRequestFlag = true;
        List<String> deniedPermissions = XXPermissions.getDenied(activity, allPermissions);
        String message = activity.getString(R.string.common_permission_message, PermissionNameConvert.getPermissionString(activity, deniedPermissions));

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int activityOrientation = activity.getResources().getConfiguration().orientation;

        boolean showPopupWindow = activityOrientation == Configuration.ORIENTATION_PORTRAIT;
        for (String permission : allPermissions) {
            if (!XXPermissions.isSpecial(permission)) {
                continue;
            }
            if (XXPermissions.isGranted(activity, permission)) {
                continue;
            }
            // 如果申请的权限带有特殊权限，并且还没有授予的话
            // 就不用 PopupWindow 对话框来显示，而是用 Dialog 来显示
            showPopupWindow = false;
            break;
        }

        if (showPopupWindow) {

            PermissionFragment.launch(activity, new ArrayList<>(allPermissions), this, callback);
            // 延迟 300 毫秒是为了避免出现 PopupWindow 显示然后立马消失的情况
            // 因为框架没有办法在还没有申请权限的情况下，去判断权限是否永久拒绝了，必须要在发起权限申请之后
            // 所以只能通过延迟显示 PopupWindow 来做这件事，如果 300 毫秒内权限申请没有结束，证明本次申请的权限没有永久拒绝
            HANDLER.postDelayed(() -> {
                if (!mRequestFlag) {
                    return;
                }
                if (activity.isFinishing() ||
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                    return;
                }
                showPopupWindow(activity, decorView, message);
            }, 300);

        } else {
            // 注意：这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.common_permission_description)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.common_permission_granted, (dialog, which) -> {
                        dialog.dismiss();
                        PermissionFragment.launch(activity, new ArrayList<>(allPermissions),
                                PermissionInterceptor.this, callback);
                    })
                    .setNegativeButton(R.string.common_permission_denied, (dialog, which) -> {
                        dialog.dismiss();
                        if (callback == null) {
                            return;
                        }
                        callback.onDenied(deniedPermissions, false);
                    })
                    .show();
        }
    }

    @Override
    public void grantedPermissionRequest(@NonNull Activity activity, @NonNull List<String> allPermissions,
                                         @NonNull List<String> grantedPermissions, boolean allGranted,
                                         @Nullable OnPermissionCallback callback) {
        if (callback == null) {
            return;
        }
        callback.onGranted(grantedPermissions, allGranted);
    }

    @Override
    public void deniedPermissionRequest(@NonNull Activity activity, @NonNull List<String> allPermissions,
                                        @NonNull List<String> deniedPermissions, boolean doNotAskAgain,
                                        @Nullable OnPermissionCallback callback) {
        if (callback != null) {
            callback.onDenied(deniedPermissions, doNotAskAgain);
        }

        if (doNotAskAgain) {
            if (deniedPermissions.size() == 1 && Permission.ACCESS_MEDIA_LOCATION.equals(deniedPermissions.get(0))) {
                ToastUtils.showLong(R.string.common_permission_media_location_hint_fail);
                return;
            }

            showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback);
            return;
        }

        if (deniedPermissions.size() == 1) {

            String deniedPermission = deniedPermissions.get(0);

            if (Permission.ACCESS_BACKGROUND_LOCATION.equals(deniedPermission)) {
                ToastUtils.showLong(R.string.common_permission_background_location_fail_hint);
                return;
            }

            if (Permission.BODY_SENSORS_BACKGROUND.equals(deniedPermission)) {
                ToastUtils.showLong(R.string.common_permission_background_sensors_fail_hint);
                return;
            }
        }

        final String message;
        List<String> permissionNames = PermissionNameConvert.permissionsToNames(activity, deniedPermissions);
        if (!permissionNames.isEmpty()) {
            message = activity.getString(R.string.common_permission_fail_assign_hint,
                    PermissionNameConvert.listToString(activity, permissionNames));
        } else {
            message = activity.getString(R.string.common_permission_fail_hint);
        }
        ToastUtils.showLong(message);
    }

    @Override
    public void finishPermissionRequest(@NonNull Activity activity, @NonNull List<String> allPermissions,
                                        boolean skipRequest, @Nullable OnPermissionCallback callback) {
        mRequestFlag = false;
        dismissPopupWindow();
    }

    private void showPopupWindow(Activity activity, ViewGroup decorView, String message) {
        if (mPermissionPopup == null) {
            View contentView = LayoutInflater.from(activity)
                    .inflate(R.layout.permission_description_popup, decorView, false);
            mPermissionPopup = new PopupWindow(activity);
            mPermissionPopup.setContentView(contentView);
            mPermissionPopup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            mPermissionPopup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            mPermissionPopup.setAnimationStyle(android.R.style.Animation_Dialog);
            mPermissionPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPermissionPopup.setTouchable(true);
            mPermissionPopup.setOutsideTouchable(true);
        }
        TextView messageView = mPermissionPopup.getContentView().findViewById(R.id.tv_permission_description_message);
        messageView.setText(message);
        // 注意：这里的 PopupWindow 只是示例，没有监听 Activity onDestroy 来处理 PopupWindow 生命周期
        mPermissionPopup.showAtLocation(decorView, Gravity.TOP, 0, 0);
    }

    private void dismissPopupWindow() {
        if (mPermissionPopup == null) {
            return;
        }
        if (!mPermissionPopup.isShowing()) {
            return;
        }
        mPermissionPopup.dismiss();
    }

    private void showPermissionSettingDialog(Activity activity, List<String> allPermissions,
                                             List<String> deniedPermissions, OnPermissionCallback callback) {
        if (activity == null || activity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            return;
        }

        final String message;

        List<String> permissionNames = PermissionNameConvert.permissionsToNames(activity, deniedPermissions);
        if (!permissionNames.isEmpty()) {
            message = activity.getString(R.string.common_permission_manual_assign_fail_hint,
                    PermissionNameConvert.listToString(activity, permissionNames));
        } else {
            message = activity.getString(R.string.common_permission_manual_fail_hint);
        }

        // 这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
        new AlertDialog.Builder(activity)
                .setTitle(R.string.common_permission_alert)
                .setMessage(message)
                .setPositiveButton(R.string.common_permission_goto_setting_page, (dialog, which) -> {
                    dialog.dismiss();
                    XXPermissions.startPermissionActivity(activity,
                            deniedPermissions, new OnPermissionPageCallback() {

                        @Override
                        public void onGranted() {
                            if (callback == null) {
                                return;
                            }
                            callback.onGranted(allPermissions, true);
                        }

                        @Override
                        public void onDenied() {
                            showPermissionSettingDialog(activity, allPermissions,
                                    XXPermissions.getDenied(activity, allPermissions), callback);
                        }
                    });
                })
                .show();
    }
}