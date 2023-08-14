package com.geek.libskin.skinbase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: SkinSharedPreferences
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 10:40 AM$
 * TODO 用于存储皮肤状态,方便重启时引用皮肤包
 */
public class SkinSharedPreferences {
    private volatile static SkinSharedPreferences mInstance;

    public Application mApplication;
    private final SharedPreferences mSp;

    public SkinSharedPreferences(Application application) {
        mApplication = application;
        mSp = mApplication.getSharedPreferences(SkinConfig.SP_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Application application) {
        if (mInstance == null) {
            synchronized (SkinSharedPreferences.class) {
                if (mInstance == null) {
                    mInstance = new SkinSharedPreferences(application);
                }
            }
        }
    }

    public static SkinSharedPreferences getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("SkinSP未初始化;" + SkinConfig.SKIN_ERROR_3);
        }
        return mInstance;
    }

    public void setString(String key, String value) {
        mSp.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return mSp.getString(key, "");
    }

    /*
     * 作者:史大拿
     * 创建时间: 5/16/23 5:23 PM
     * TODO 获取皮肤路径
     */
    public String getSkinPath() {
        return getString(SkinConfig.SP_SKIN_PATH);
    }

    public void setSkinPath(String value) {
        setString(SkinConfig.SP_SKIN_PATH, value);
    }

    /*
     * 作者:史大拿
     * 创建时间: 5/16/23 5:24 PM
     * TODO 获取皮肤名字
     */
    public String getSkinName() {
        return getString(SkinConfig.SP_SKIN_STATE_NAME);
    }

    public void setSkinName(String value) {
        setString(SkinConfig.SP_SKIN_STATE_NAME, value);
    }
}
