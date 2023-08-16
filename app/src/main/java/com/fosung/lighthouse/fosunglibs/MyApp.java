package com.fosung.lighthouse.fosunglibs;

import android.app.Application;

import com.geek.libskin.skinbase.SkinManager;
import com.geek.libskin.skinbase.util.BaseApp3;

/**
 * @ClassName: MyApp
 * @Author: 史大拿
 * @CreateDate: 12/28/22$ 1:29 PM$
 * TODO
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(BaseApp3.get());

    }
}
