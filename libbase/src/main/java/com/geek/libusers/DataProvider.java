package com.geek.libusers;

import android.text.TextUtils;

import com.geek.libutils.app.BaseApp;

public class DataProvider {
    public static String user_token;
    public static String user_userId;
    public static String user_state;
    public static String user_grade;
    public static String user_gradename;
    public static String user_img;
    public static String user_signature;
    public static String user_sex;
    public static String user_name;
    public static String user_birth;
    public static String user_postgraduateRecordKey;
    public static String user_ipStr;

    public DataProvider() {
    }

    public static String getUser_token() {
        if (TextUtils.isEmpty(user_token)) {
            user_token = (String) UserSpUtils.get(BaseApp.get()).get("user_token", "");
        }
        return user_token;
    }

    public static void setUser_token(String user_token) {
        DataProvider.user_token = user_token;
    }

    public static String getUser_userId() {
        if (TextUtils.isEmpty(user_userId)) {
            user_userId = (String) UserSpUtils.get(BaseApp.get()).get("user_userId", "");
        }
        return user_userId;
    }

    public static void setUser_userId(String user_userId) {
        DataProvider.user_userId = user_userId;
    }

    public static String getUser_state() {
        if (TextUtils.isEmpty(user_state)) {
            user_state = (String) UserSpUtils.get(BaseApp.get()).get("user_state", "");
        }
        return user_state;
    }

    public static void setUser_state(String user_state) {
        DataProvider.user_state = user_state;
    }

    public static String getUser_grade() {
        if (TextUtils.isEmpty(user_grade)) {
            user_grade = (String) UserSpUtils.get(BaseApp.get()).get("user_grade", "");
        }
        return user_grade;
    }

    public static void setUser_grade(String user_grade) {
        DataProvider.user_grade = user_grade;
    }

    public static String getUser_gradename() {
        if (TextUtils.isEmpty(user_gradename)) {
            user_gradename = (String) UserSpUtils.get(BaseApp.get()).get("user_gradename", "");
        }
        return user_gradename;
    }

    public static void setUser_gradename(String user_gradename) {
        DataProvider.user_gradename = user_gradename;
    }

    public static String getUser_img() {
        if (TextUtils.isEmpty(user_img)) {
            user_img = (String) UserSpUtils.get(BaseApp.get()).get("user_img", "");
        }
        return user_img;
    }

    public static void setUser_img(String user_img) {
        DataProvider.user_img = user_img;
    }

    public static String getUser_signature() {
        if (TextUtils.isEmpty(user_signature)) {
            user_signature = (String) UserSpUtils.get(BaseApp.get()).get("user_signature", "");
        }
        return user_signature;
    }

    public static void setUser_signature(String user_signature) {
        DataProvider.user_signature = user_signature;
    }

    public static String getUser_sex() {
        if (TextUtils.isEmpty(user_sex)) {
            user_sex = (String) UserSpUtils.get(BaseApp.get()).get("user_sex", "");
        }
        return user_sex;
    }

    public static void setUser_sex(String user_sex) {
        DataProvider.user_sex = user_sex;
    }

    public static String getUser_name() {
        if (TextUtils.isEmpty(user_name)) {
            user_name = (String) UserSpUtils.get(BaseApp.get()).get("user_name", "");
        }
        return user_name;
    }

    public static void setUser_name(String user_name) {
        DataProvider.user_name = user_name;
    }

    public static String getUser_birth() {
        if (TextUtils.isEmpty(user_birth)) {
            user_birth = (String) UserSpUtils.get(BaseApp.get()).get("user_birth", "");
        }
        return user_birth;
    }

    public static void setUser_birth(String user_birth) {
        DataProvider.user_birth = user_birth;
    }

    public static String getUser_postgraduateRecordKey() {
        if (TextUtils.isEmpty(user_postgraduateRecordKey)) {
            user_postgraduateRecordKey = (String) UserSpUtils.get(BaseApp.get()).get("user_postgraduateRecordKey", "");
        }
        return user_postgraduateRecordKey;
    }

    public static void setUser_postgraduateRecordKey(String user_postgraduateRecordKey) {
        DataProvider.user_postgraduateRecordKey = user_postgraduateRecordKey;
    }

    public static String getUser_ipStr() {
        if (TextUtils.isEmpty(user_ipStr)) {
            user_ipStr = (String) UserSpUtils.get(BaseApp.get()).get("user_ipStr", "");
        }
        return user_ipStr;
    }

    public static void setUser_ipStr(String user_ipStr) {
        DataProvider.user_ipStr = user_ipStr;
    }
}
