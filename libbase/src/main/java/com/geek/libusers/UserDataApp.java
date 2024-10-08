/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libusers;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.geek.libutils.app.BaseApp;

//login:登录
//    mi:
//    <permission
//            android:name="hs.permission.user.login"
//                    android:label="@string/login_permission_label"
//                    android:protectionLevel="normal" />
//    <permission
//            android:name="hs.permission.user.token"
//                    android:label="@string/token_permission_label"
//                    android:protectionLevel="normal" />
//    <permission
//            android:name="hs.permission.user.info"
//                    android:label="@string/info_permission_label"
//                    android:protectionLevel="normal" />
//    <provider
//                android:name="com.geek.libusers.UserProvider"
//                        android:authorities="${applicationId}.libusers"
//                        android:enabled="true"
//                        android:exported="true"
//                        android:permission="hs.permission.user.info" />
//    <activity
//                android:name="com.example.appxhlogin.login.LoginActivity"
//                        android:configChanges="orientation|keyboardHidden|navigation|screenSize|layoutDirection|smallestScreenSize|screenLayout|mnc|uiMode|fontScale|locale|mcc|keyboard"
//                        android:exported="false"
//                        android:label="登录"
//                        android:launchMode="singleTask"
//                        android:permission="hs.permission.user.login"
//                        android:screenOrientation="portrait">
//
//
//    mainxml:
//    <permission
//            android:name="hs.permission.user.login"
//                    android:label="@string/login_permission_label"
//                    android:protectionLevel="normal" />
//    <permission
//            android:name="hs.permission.user.token"
//                    android:label="@string/token_permission_label"
//                    android:protectionLevel="normal" />
//    <permission
//            android:name="hs.permission.user.info"
//                    android:label="@string/info_permission_label"
//                    android:protectionLevel="normal" />
//
//    <provider
//                android:name="com.geek.libusers.UserProvider"
//                        android:authorities="${applicationId}.libusers"
//                        android:enabled="true"
//                        android:exported="true"
//                        android:permission="hs.permission.user.info" />
//    <activity
//                android:name="com.example.appxhlogin.login.LoginActivity"
//                        android:configChanges="orientation|keyboardHidden|navigation|screenSize|layoutDirection|smallestScreenSize|screenLayout|mnc|uiMode|fontScale|locale|mcc|keyboard"
//                        android:exported="false"
//                        android:label="登录"
//                        android:launchMode="singleTask"
//                        android:permission="hs.permission.user.login"
//                        android:screenOrientation="portrait">
//
//    mi->application:
//                        // 跨进程数据同步
//                        UserDataApp.get().set_data(false, AppUtils.getAppPackageName())
//
//
//
//
//业务1：我的：
//mi:
//    <permission android:name="hs.permission.user.info" />
//    <permission android:name="hs.permission.user.login" />
//    <permission android:name="hs.permission.user.token" />
//
//    <uses-permission android:name="hs.permission.user.info" />
//    <uses-permission android:name="hs.permission.user.login" />
//    <uses-permission android:name="hs.permission.user.token" />
//
//
//mainxml:
//    无
//
//
//mi->applcation:
//       UserDataApp.get().set_data1(true, "com.example.appxhlogin")
//
//
//
//
//
//
//
//
//
//
//宿主app:
//        //
//        UserDataApp.get()
//        .set_data2(!BuildConfig.IS_USER_APK_INSTALLED, AppUtils.getAppPackageName())


//LoginActivity登录成功：
//        // 数据共享bufen SpUtil.user    SpUtil.userInfo     SpUtil.isLogin
//        UserSpUtils.SpExt(UserData.get())
//        //
//        .add(UserData.query1, SpUtil.user.token)
//        .add(UserData.query2, SpUtil.user.userId)
//        .add(UserData.query3, SpUtil.user.state)
//        //
//        .add(UserData.query4, SpUtil.userInfo.grade)
//        .add(UserData.query5, SpUtil.userInfo.gradename)
//        .add(UserData.query6, SpUtil.userInfo.img)
//        .add(UserData.query7, SpUtil.userInfo.signature)
//        .add(UserData.query8, SpUtil.userInfo.sex)
//        .add(UserData.query9, SpUtil.userInfo.name)
//        .add(UserData.query10, SpUtil.userInfo.birth)
//        .add(UserData.query11, SpUtil.userInfo.postgraduateRecordKey)
//        //
//        .add(UserData.query12, SpUtil.ipStr)
//        .apply()





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


    public void set_data(boolean isdebug,String uri_name1) {
        UserData.get().registerListener(new UserSpUtils.OnSpChangeListener() {
            @Override
            public void onChange() {
                // 因为sp的数据监听使用了WeakHashMap，所以不能使用匿名内部类形式
                // 否则会很快被回收掉
                fillDataProvider(UserData.get());
            }
        });
        fillDataProvider(UserData.get());
        UserUtils.get().setup(BaseApp.get(), isdebug, uri_name1,null);
    }

    public void set_data1(Boolean isdebug, String uri_name1) {
//        UserData.get().registerListener(mSpListener);
        UserUtils.get().setup(BaseApp.get(), isdebug, uri_name1, new OnChangeListener() {
            @Override
            public void onChange() {
                set_yewu_data();
            }
        });
        set_yewu_data();
    }

    public void set_data2(Boolean isdebug,String uri_name1) {
//        UserData.get().registerListener(mSpListener);
        UserUtils.get().setup(BaseApp.get(), isdebug,uri_name1, new OnChangeListener() {
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
//        set_yewu_data();
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
        //3 放开下面的业务 才有数据
//        if (TextUtils.isEmpty(DataProvider.user_token)) {
//            SpUtil.INSTANCE.setLogin(false);
//        } else {
//            SpUtil.INSTANCE.setLogin(true);
//            LoginResponse loginMyApp = new LoginResponse();
//            loginMyApp.setUserId(DataProvider.getUser_userId());
//            loginMyApp.setToken(DataProvider.getUser_token());
//            loginMyApp.setState(DataProvider.getUser_state());
//            SpUtil.INSTANCE.setUser(loginMyApp);
//            //1
//            if (SpUtil.INSTANCE.getUser().getState().equals("isNew")) {
//                SPUtils.getInstance().put("新用户", "1");// 1新
//            } else {
//                SPUtils.getInstance().put("新用户", "");// 1新
//            }
//            //2
//            UserInfo userInfo = new UserInfo();
//            userInfo.setGrade(DataProvider.getUser_grade());
//            userInfo.setGradename(DataProvider.getUser_gradename());
//            userInfo.setImg(DataProvider.getUser_img());
//            userInfo.setSignature(DataProvider.getUser_signature());
//            userInfo.setSex(DataProvider.getUser_sex());
//            userInfo.setName(DataProvider.getUser_name());
//            userInfo.setBirth(DataProvider.getUser_birth());
//            userInfo.setPostgraduateRecordKey(DataProvider.getUser_postgraduateRecordKey());
//            SpUtil.INSTANCE.setUserInfo(userInfo);
//
//        }
//        SpUtil.INSTANCE.setIpStr(DataProvider.getUser_ipStr());
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
