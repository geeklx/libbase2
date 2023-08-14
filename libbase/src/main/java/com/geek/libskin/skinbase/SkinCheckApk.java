package com.geek.libskin.skinbase;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

/**
 * @ClassName: SkinCheckApk
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 1:22 PM$
 * TODO
 */
public class SkinCheckApk {

    public static boolean checkPath(Application application, String path) {

        if (TextUtils.isEmpty(path)) {
            showDialog(application, "path不合格");
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            showDialog(application, path + "不存在;(SkinCheckApk)");
            return false;
        }

        String packName = getPackName(application, path);
        if (packName == null || packName.isEmpty()) {
            showDialog(application, "无法获取到包名,path不合格,不是一个apk文件，无法获取资源");
            return false;
        }

        /// 还可以校验皮肤包是否合格...
        /// ...
        /// 新年快乐~

        return true;
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 2:33 PM
     * TODO 获取包名
     */
    private static String getPackName(Application application, String path) {
        if (path != null && !path.isEmpty()) {
            PackageInfo info = application.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                ApplicationInfo appInfo = info.applicationInfo;
                return appInfo.packageName;  //得到安装包名称
            }
        }
        return null;
    }

    private static void showDialog(Application application, String value) {
        Toast.makeText(application, value, Toast.LENGTH_LONG).show();
    }
}
