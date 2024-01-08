package com.geek.libusers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// 数据共享bufen SpUtil.user    SpUtil.userInfo     SpUtil.isLogin
//            UserSpUtils.SpExt(UserData.get())
//                    .add("kotlin_user",SpUtil.user)
//                    .add("kotlin_userInfo",SpUtil.userInfo)
//                    .add("kotlin_isLogin",SpUtil.isLogin)
//                    .add("ipStr",SpUtil.ipStr)
//                    .apply()
public class UserProvider extends ContentProvider implements UserSpUtils.OnSpChangeListener {

    public static final String AUTHORITY = "com.geek.user";

    private static final int QUERY_ALL = 1;
    private static final int INSERT_ALL = 2;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(AUTHORITY, "all", QUERY_ALL);
    }

    @Override
    public boolean onCreate() {
        UserData.get().registerListener(this);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String[] columnNames = {
                UserData.query1,
                UserData.query2,
                UserData.query3,
                UserData.query4,
                UserData.query5,
                UserData.query6,
                UserData.query7,
                UserData.query8,
                UserData.query9,
                UserData.query10,
                UserData.query11,
                UserData.query12
        };

        MatrixCursor cursor = new MatrixCursor(columnNames, 1);
        switch (URI_MATCHER.match(uri)) {
            case QUERY_ALL: {
                cursor.addRow(new Object[]{
                        UserData.get().get(UserData.query1, ""),
                        UserData.get().get(UserData.query2, ""),
                        UserData.get().get(UserData.query3, ""),
                        UserData.get().get(UserData.query4, ""),
                        UserData.get().get(UserData.query5, ""),
                        UserData.get().get(UserData.query6, ""),
                        UserData.get().get(UserData.query7, ""),
                        UserData.get().get(UserData.query8, ""),
                        UserData.get().get(UserData.query9, ""),
                        UserData.get().get(UserData.query10, ""),
                        UserData.get().get(UserData.query11, ""),
                        UserData.get().get(UserData.query12, "")
                });
                break;
            }
            default: {
                break;
            }
        }

        return cursor;
    }

    @Override
    public void onChange() {
        Context ctx = getContext();
        if (ctx == null) {
            return;
        }

        ContentResolver resolver = ctx.getContentResolver();
        if (resolver != null) {
            resolver.notifyChange(Uri.parse("content://" + AUTHORITY + "/all"), null);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        throw new UnsupportedOperationException("No external inserts");
        switch (URI_MATCHER.match(uri)) {
            case INSERT_ALL:
//                new UserSpUtils.SpExt(UserData.get())
//                        .add(ConstantUtil.USER_ID, values.get(USER_ID))
//                        .add(ConstantUtil.BINDINGPHONE, values.get(BINDINGPHONE))
//                        .add(ConstantUtil.U_ID, values.get(U_ID))
//                        .add(ConstantUtil.PHONE_NUMBER, values.get(PHONE_NUMBER))
//                        .add(ConstantUtil.ACCESS_TOKEN, values.get(ACCESS_TOKEN))
//                        .add(ConstantUtil.UCENTER_TOKEN, values.get(UCENTER_TOKEN))
//                        .add(ConstantUtil.REFRESH_TOKEN, values.get(REFRESH_TOKEN))
//                        .apply();
                new UserSpUtils.SpExt(UserData.get())
                        //
                        .add(UserData.query1, values.get(UserData.query1))
                        .add(UserData.query2, values.get(UserData.query2))
                        .add(UserData.query3, values.get(UserData.query3))
                        //
                        .add(UserData.query4, values.get(UserData.query4))
                        .add(UserData.query5, values.get(UserData.query5))
                        .add(UserData.query6, values.get(UserData.query6))
                        .add(UserData.query7, values.get(UserData.query7))
                        .add(UserData.query8, values.get(UserData.query8))
                        .add(UserData.query9, values.get(UserData.query9))
                        .add(UserData.query10, values.get(UserData.query10))
                        .add(UserData.query11, values.get(UserData.query11))
                        //
                        .add(UserData.query12, values.get(UserData.query12))
                        .apply();
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("No external delete");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("No external update");
    }
}
