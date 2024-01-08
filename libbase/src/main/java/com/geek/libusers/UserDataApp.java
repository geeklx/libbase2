/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libusers;

import android.content.Context;

import com.geek.libutils.app.BaseApp;

public class UserDataApp {

    private static final Object lock = new Object();

    private static UserDataApp sInstance;

    private Context mContext;


    private UserDataApp() {
    }

    public static UserDataApp get() {
        if (sInstance == null) {
            synchronized (lock) {
                if (sInstance == null) {
                    sInstance = new UserDataApp();
                }
            }
        }

        return sInstance;
    }


    public void set_data(boolean isdebug) {
        UserData.get().registerListener(new UserSpUtils.OnSpChangeListener() {
            @Override
            public void onChange() {
                // 因为sp的数据监听使用了WeakHashMap，所以不能使用匿名内部类形式
                // 否则会很快被回收掉
                fillDataProvider(UserData.get());
            }
        });
        fillDataProvider(UserData.get());
        UserUtils.get().setup(BaseApp.get(), isdebug, null);
    }

    public void set_data2(Boolean isdebug) {
//        UserData.get().registerListener(mSpListener);
        UserUtils.get().setup(BaseApp.get(), isdebug, new OnChangeListener() {
            @Override
            public void onChange() {
                fillDataProvider();
            }
        });
        fillDataProvider();
    }

    public void fillDataProvider() {
        // new
        DataProvider.setUser_token(UserUtils.get().user_token());
        DataProvider.setUser_userId(UserUtils.get().user_userId());
        DataProvider.setUser_state(UserUtils.get().user_state());
        DataProvider.setUser_grade(UserUtils.get().user_grade());
        DataProvider.setUser_gradename(UserUtils.get().user_gradename());
        DataProvider.setUser_img(UserUtils.get().user_img());
        DataProvider.setUser_signature(UserUtils.get().user_signature());
        DataProvider.setUser_sex(UserUtils.get().user_sex());
        DataProvider.setUser_name(UserUtils.get().user_name());
        DataProvider.setUser_birth(UserUtils.get().user_birth());
        DataProvider.setUser_postgraduateRecordKey(UserUtils.get().user_postgraduateRecordKey());
        DataProvider.setUser_ipStr(UserUtils.get().user_ipStr());
        // old  LoginResponse
        set_yewu_data();
        //3
//        if (TextUtils.isEmpty(DataProvider.user_token)) {
//            SpUtil.isLogin = false;
//        } else {
//            SpUtil.isLogin = true
//            var loginMyApp =new LoginResponse();
//            loginMyApp.userId = DataProvider.user_userId
//            loginMyApp.token = DataProvider.user_token
//            loginMyApp.state = DataProvider.user_state
//            SpUtil.user = loginMyApp
//            //1
//            if (SpUtil.user.state.equals("isNew")) {
//                SPUtils.getInstance().put("新用户", "1")// 1新
//            } else {
//                SPUtils.getInstance().put("新用户", "")// 1新
//            }
//            //2
//            var userInfo = UserInfo()
//            userInfo.grade = DataProvider.user_grade
//            userInfo.gradename = DataProvider.user_gradename
//            userInfo.img = DataProvider.user_img
//            userInfo.signature = DataProvider.user_signature
//            userInfo.sex = DataProvider.user_sex
//            userInfo.name = DataProvider.user_name
//            userInfo.birth = DataProvider.user_birth
//            userInfo.postgraduateRecordKey = DataProvider.user_grade
//            SpUtil.userInfo = userInfo
//
//        }
//        SpUtil.ipStr = DataProvider.user_ipStr

    }

    public void set_yewu_data() {

    }


    public void fillDataProvider(UserSpUtils sp) {
        // new
        DataProvider.setUser_token((String) sp.get(UserData.query1, ""));
        DataProvider.setUser_userId((String) sp.get(UserData.query2, ""));
        DataProvider.setUser_state((String) sp.get(UserData.query3, ""));
        DataProvider.setUser_grade((String) sp.get(UserData.query4, ""));
        DataProvider.setUser_gradename((String) sp.get(UserData.query5, ""));
        DataProvider.setUser_img((String) sp.get(UserData.query6, ""));
        DataProvider.setUser_signature((String) sp.get(UserData.query7, ""));
        DataProvider.setUser_sex((String) sp.get(UserData.query8, ""));
        DataProvider.setUser_name((String) sp.get(UserData.query9, ""));
        DataProvider.setUser_birth((String) sp.get(UserData.query10, ""));
        DataProvider.setUser_postgraduateRecordKey((String) sp.get(UserData.query11, ""));
        DataProvider.setUser_ipStr((String) sp.get(UserData.query12, ""));
        // old  LoginResponse
        set_yewu_data(sp);
        //3
//        if (TextUtils.isEmpty(DataProvider.user_token)) {
//            SpUtil.isLogin = false
//        } else {
//            SpUtil.isLogin = true
//            var loginMyApp = LoginResponse()
//            loginMyApp.userId = DataProvider.user_userId
//            loginMyApp.token = DataProvider.user_token
//            loginMyApp.state = DataProvider.user_state
//            SpUtil.user = loginMyApp
//            //1
//            if (SpUtil.user.state.equals("isNew")) {
//                SPUtils.getInstance().put("新用户", "1")// 1新
//            } else {
//                SPUtils.getInstance().put("新用户", "")// 1新
//            }
//            //2
//            var userInfo = UserInfo()
//            userInfo.grade = DataProvider.user_grade
//            userInfo.gradename = DataProvider.user_gradename
//            userInfo.img = DataProvider.user_img
//            userInfo.signature = DataProvider.user_signature
//            userInfo.sex = DataProvider.user_sex
//            userInfo.name = DataProvider.user_name
//            userInfo.birth = DataProvider.user_birth
//            userInfo.postgraduateRecordKey = DataProvider.user_grade
//            SpUtil.userInfo = userInfo
//
//        }
//        SpUtil.ipStr = DataProvider.user_ipStr


    }


    public void set_yewu_data(UserSpUtils sp) {

    }



}
