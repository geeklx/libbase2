package com.geek.libusers;

import com.geek.libutils.app.BaseApp;

public class UserData {
    public static final String DEFAULT_SP_USER_FILE = "sp_geek_user";
    private static UserSpUtils spUtils;

    public static final String query1 = "user_token";
    public static final String query2 = "user_userId";
    public static final String query3 = "user_state";
    public static final String query4 = "user_grade";
    public static final String query5 = "user_gradename";
    public static final String query6 = "user_img";
    public static final String query7 = "user_signature";
    public static final String query8 = "user_sex";
    public static final String query9 = "user_name";
    public static final String query10 = "user_birth";
    public static final String query11 = "user_postgraduateRecordKey";
    public static final String query12 = "user_ipStr";

    public static UserSpUtils get() {
        if (spUtils == null) {
            spUtils = new UserSpUtils(BaseApp.get(), DEFAULT_SP_USER_FILE);
        }
        return spUtils;
    }
}
