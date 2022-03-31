package com.geek.libbase.utils;

import com.blankj.utilcode.util.SPUtils;
import com.geek.libutils.app.MyLogUtil;

public class TextSizesfUtils {

    public static void setSizes(float textSizef) {
        SPUtils.getInstance().put("textSizef", textSizef);
        MyLogUtil.e("修改成功,重启应用生效");
//        ActivityManager manager = (ActivityManager) BaseApp.get().getSystemService(Context.ACTIVITY_SERVICE);
//        manager.restartPackage("com.demo.textsizechange");
    }
}
