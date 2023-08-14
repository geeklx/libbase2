package com.geek.libskin.skinbase;


import android.util.Log;

/**
 * @ClassName: SkinLog
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 1:50 PM$
 * TODO
 */
public class SkinLog {
    public static boolean IS_DEBUG = true;

    public static void i(Object key, Object value) {
        if (IS_DEBUG) {
            Log.i(key.toString(), value.toString());
        }
    }

    public static void e(Object value) {
//        if (IS_DEBUG) {
        Log.e("", "******************************SKIN-ERROR-START********************************");
        Log.e("", "******************************************************************************");

        Log.e("szj出错了", value.toString());

        Log.e("", "******************************************************************************");
        Log.e("", "*******************************SKIN-ERROR-END*********************************");

//        }
    }
}
