/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libusers;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.geek.libutils.app.BaseApp;

public class NewUserDataApp {

    private static final Object lock = new Object();

    private static NewUserDataApp sInstance;

    private Context mContext;


    private NewUserDataApp() {
    }

    public static NewUserDataApp get() {
        if (sInstance == null) {
            synchronized (lock) {
                if (sInstance == null) {
                    sInstance = new NewUserDataApp();
                }
            }
        }

        return sInstance;
    }

    public void setinitdata() {
        // 第一步 首页初始化宿主app
        // application:
        NewUserDataApp.get().set_application(false, AppUtils.getAppPackageName());
        // 第二步 数据模块 例如登录组件
        // mi:
        NewUserDataApp.get().set_lib_login(false, AppUtils.getAppPackageName());
        // 第三步 业务模块 例如某个业务组件
        // mi:
        NewUserDataApp.get().set_lib_other(true, "com.example.appxhlogin");

    }


    public void set_application(Boolean isdebug, String uri_name1) {
        UserUtils.get().setup(BaseApp.get(), isdebug, uri_name1, new OnChangeListener() {
            @Override
            public void onChange() {
                fillDataProvider();
            }
        });
        fillDataProvider();
    }

    public void set_lib_login(boolean isdebug, String uri_name1) {
        UserData.get().registerListener(new UserSpUtils.OnSpChangeListener() {
            @Override
            public void onChange() {
                // 因为sp的数据监听使用了WeakHashMap，所以不能使用匿名内部类形式
                // 否则会很快被回收掉
                fillDataProvider(UserData.get());
            }
        });
        fillDataProvider(UserData.get());
        UserUtils.get().setup(BaseApp.get(), isdebug, uri_name1, null);
    }

    public void set_lib_other(Boolean isdebug, String uri_name1) {
        UserUtils.get().setup(BaseApp.get(), isdebug, uri_name1, new OnChangeListener() {
            @Override
            public void onChange() {
                set_yewu_data();
            }
        });
        set_yewu_data();
    }

    public void set_yewu_data() {
        // new
        DataProvider.setUser_token(UserUtils.get().user_token());
        String aaaa = DataProvider.getUser_token();
        String bbbb = "";
    }


    public void fillDataProvider() {
        DataProvider.setUser_token(UserUtils.get().user_token());

    }


    public void fillDataProvider(UserSpUtils sp) {
        DataProvider.setUser_token((String) sp.get(UserData.query1, ""));


    }


}
