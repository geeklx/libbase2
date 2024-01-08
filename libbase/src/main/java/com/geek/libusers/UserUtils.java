/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libusers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/**
 * <p class="note">
 * <h2>sdk集成步骤</h2>
 * <p>
 * step 1. <br />
 * 如果要使用用户信息，在manifest中添加权限
 * <pre>&lt;uses-permission android:name="hs.permission.user.info" /&gt;</pre>
 * 如果需要请求用户登录，在manifest中添加权限
 * <pre>&lt;uses-permission android:name="hs.permission.user.login" /&gt;</pre>
 * </p>
 * <p>
 * step 2. <br />
 * 在Application中使用如下代码进行配置
 * <pre>
 *             // ctx可以传递ApplicatonContext，
 *             // debug模式在没有用户中心apk的时候传递true，
 *             // 如果用户中心apk未安装，
 *             // 所有的用户信息都返回字符串空(<b>NOT null</b>)，
 *             // loginToDo()方法的Runnable都会执行。
 *             UserUtils.get().setup(Context ctx, boolean debug);
 *         </pre>
 * <pre>
 *             // 如果需要监听数据变化，可以使用registerOnChangeListener()
 *             UserUtils.get().registerOnChangeListener(new OnChangeListener() {
 *                 public void onChange() {
 *                     String userId = UserUtils.get().userId();
 *                 }
 *             });
 *         </pre>
 * </p>
 * <p>
 * step 3. <br />
 * 在你的Activity的onActivityResult中添加如下代码
 * <pre>
 *             UserUtils.get().onActivityResult(requestCode, resultCode, data)
 *         </pre>
 * 具体实现方式可以参考如下代码
 * <pre>
 *             // 1. 在Base类中自定义一个onActResult(int requestCode, int resultCode, Intent data)方法
 *             // 2. 将onActivityResult(int requestCode, int resultCode, Intent data)设置为final
 *             // 3. 子类中通过复写onActResult方法来代替onActivityResult
 *             // 4. 在onActivityResult()中通过UserUtils.get().onActivityResult()的返回值来决定是否执行onActResult
 *             class BaseActivity extends Activity {
 *                 ...
 *                 protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
 *                         super.onActivityResult(int requestCode, int resultCode, Intent data);
 *                         if (!UserUtils.get().onActivityResult(requestCode, resultCode, data)) {
 *                                onActResult(requestCode, resultCode, data);
 *                         }
 *                 }
 *
 *                 protected void onActResult(int requestCode, int resultCode, Intent data) {
 *
 *                 }
 *             }
 *
 *             class ChildActivity extends BaseActivity {
 *                  protected void onActResult(int requestCode, int resultCode, Intent data) {
 *                      // 你的逻辑
 *                  }
 *             }
 *         </pre>
 * </p>
 *
 * <p>
 * step 4. <br />
 * 配置完成后就可以在代码中使用了，具体使用方式如下
 * <pre>
 *              // 获取用户id
 *              UserUtils.get().userId();
 *              // 判断用户是否登录
 *              UserUtils.get().isUserLogin();
 *              // 获取用户accessToken
 *              UserUtils.get().accessToken();
 *              // 获取用户绑定的手机号
 *              UserUtils.get().bindingPhone();
 *              // 获取用户的u+ Id
 *              UserUtils.get().uId();
 *              // 获取用户手机号
 *              UserUtils.get().phoneNumber();
 *              // 获取用户验证token
 *              UserUtils.get().authToken();
 *              // 获取用户中心token
 *              UserUtils.get().uCenterToken();
 *              UserUtils.get().refreshToken();
 *              // 登录
 *              UserUtils.get().login();
 *              // 用户登录后执行Runnable操作
 *              UserUtils.get().loginToDo(Activity.this, new Runnble() {
 *                  startActivity("com.android.settings");
 *              });
 *          </pre>
 * </p>
 * <p>
 * <b>注意事项</b> <br />
 * 1. 用户信息会在sdk中进行缓存，如果手动进行用户信息的清理或者修改，使用sdk获取的信息不会进行做出相应修改.<br />
 * 但是如果用户的信息是在应用内修改的，sdk可以监听到数据的变化，从而更新到最新数据。
 * <br /><br />
 * 2. 关于权限问题，软件的安装可能会存在先后顺序问题，如果你的apk安装先于用户信息apk，那么可能会引起crash，
 * 解决方案是：
 * (1)先安装用户信息apk
 * <p>
 * (2)在你的manifest中也声明一下相同的权限（仅限于签名相同的apk），具体步骤如下
 * <pre>
 *              // 如果使用用户信息，添加如下权限声明
 *              &lt;premission android:name="hs.permission.user.info" /&gt;
 *              // 如果请求用户登录，添加如下权限声明
 *              &lt;premission android:name="hs.permission.user.login" /&gt;
 *          </pre>
 * </p>
 * </p>
 * <p>
 * <br />
 * <br />
 * created by geek at 2017/6/1
 */
public class UserUtils {

    public static final int LOGIN_REQUEST_CODE = 1998;

    private static final Object lock = new Object();

    private static UserUtils sInstance;

    private boolean debugMode = false;
    private Context mContext;

    private ArrayMap<String, String> mResults = new ArrayMap<>(12);

    private Uri URI = Uri.parse("content://" + UserProvider.AUTHORITY + "/all");

    private Runnable mLastRunnable;

    private List<OnChangeListener> mListeners = new ArrayList<>();

    private UserUtils() {
    }

    public static UserUtils get() {
        if (sInstance == null) {
            synchronized (lock) {
                if (sInstance == null) {
                    sInstance = new UserUtils();
                }
            }
        }

        return sInstance;
    }

    /**
     * 配置UserUtils
     *
     * @param ctx
     * @param isDebug 在debug模式中，如果用户中心apk未安装，
     *                所有的用户信息都返回字符串空(<b>NOT null</b>)，
     *                loginToDo()方法的Runnable都会执行。true debug模式
     */
    public void setup(Context ctx, boolean isDebug) {
        setup(ctx, isDebug, null);
    }

    /**
     * 配置UserUtils
     *
     * @param ctx
     * @param isDebug 在debug模式中，如果用户中心apk未安装，
     *                所有的用户信息都返回字符串空(<b>NOT null</b>)，
     *                loginToDo()方法的Runnable都会执行。true debug模式
     * @param li      数据监听
     */
    public void setup(Context ctx, boolean isDebug, OnChangeListener li) {
        debugMode = isDebug;
        mContext = ctx.getApplicationContext();
        registerOnChangeListener(li);

        ContentResolver resolver = ctx.getApplicationContext().getContentResolver();
        resolver.registerContentObserver(URI, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                query();

                for (OnChangeListener li : mListeners) {
                    if (li != null) {
                        li.onChange();
                    }
                }
            }
        });
    }

    /**
     * 注册数据监听器
     *
     * @param li
     */
    public void registerOnChangeListener(OnChangeListener li) {
        if (li != null) {
            mListeners.add(li);
        }
    }

    /**
     * 取消改数据监听器
     *
     * @param li
     */
    public void unregisterOnChangeListener(OnChangeListener li) {
        if (li != null) {
            mListeners.remove(li);
        }
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isUserLogin() {
        return !TextUtils.isEmpty(user_token());
    }


    public String user_token() {
        return getString(UserData.query1);
    }

    public String user_userId() {
        return getString(UserData.query2);
    }

    public String user_state() {
        return getString(UserData.query3);
    }

    public String user_grade() {
        return getString(UserData.query4);
    }

    public String user_gradename() {
        return getString(UserData.query5);
    }

    public String user_img() {
        return getString(UserData.query6);
    }

    public String user_signature() {
        return getString(UserData.query7);
    }

    public String user_sex() {
        return getString(UserData.query8);
    }

    public String user_name() {
        return getString(UserData.query9);
    }

    public String user_birth() {
        return getString(UserData.query10);
    }

    public String user_postgraduateRecordKey() {
        return getString(UserData.query11);
    }

    public String user_ipStr() {
        return getString(UserData.query12);
    }


    public void loginToDo(Activity activity, Runnable runnable, String intent1) {
        if (debugMode || isUserLogin()) {
            if (runnable != null) {
                runnable.run();
            }
            return;
        }

        mLastRunnable = runnable;
        login(activity, intent1);
    }

    public void login(Activity activity, String intent1) {
//        Intent intent = new Intent("user.login");
        Intent intent = new Intent(intent1);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, LOGIN_REQUEST_CODE);
        }
    }

    /**
     * 需要在Activity里添加如下代码：
     * <pre>
     *     if (!UserUtils.get().onActivityResult(requestCode, resultCode, data)) {
     *         // 你的代码逻辑
     *     }
     * </pre>
     * 或者使用如下方式：
     * <pre>
     *      // 1. 在Base类中自定义一个onActResult(int requestCode, int resultCode, Intent data)方法
     *      // 2. 将onActivityResult(int requestCode, int resultCode, Intent data)设置为final
     *      // 3. 子类中通过复写onActResult方法来代替onActivityResult
     *      // 4. 在onActivityResult()中通过UserUtils.get().onActivityResult()的返回值来决定是否执行onActResult
     *      class BaseActivity extends Activity {
     *          ...
     *          protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
     *              super.onActivityResult(int requestCode, int resultCode, Intent data);
     *              if (!UserUtils.get().onActivityResult(requestCode, resultCode, data)) {
     *                  onActResult(requestCode, resultCode, data);
     *              }
     *          }
     *
     *          protected void onActResult(int requestCode, int resultCode, Intent data) {
     *
     *          }
     *      }
     *
     *      class ChildActivity extends BaseActivity {
     *          protected void onActResult(int requestCode, int resultCode, Intent data) {
     *              // 你的逻辑
     *          }
     *      }
     * </pre>
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        Runnable runnable = mLastRunnable;
        mLastRunnable = null;
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && runnable != null) {
                runnable.run();
            }
            return true;
        }

        return false;
    }

    @SuppressLint("Range")
    private void query() {
        if (mContext == null) {
            return;
        }
        Cursor cursor = mContext.getContentResolver().query(URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                mResults.put(UserData.query1, cursor.getString(cursor.getColumnIndex(UserData.query1)));
                mResults.put(UserData.query2, cursor.getString(cursor.getColumnIndex(UserData.query2)));
                mResults.put(UserData.query3, cursor.getString(cursor.getColumnIndex(UserData.query3)));
                mResults.put(UserData.query4, cursor.getString(cursor.getColumnIndex(UserData.query4)));
                mResults.put(UserData.query5, cursor.getString(cursor.getColumnIndex(UserData.query5)));
                mResults.put(UserData.query6, cursor.getString(cursor.getColumnIndex(UserData.query6)));
                mResults.put(UserData.query7, cursor.getString(cursor.getColumnIndex(UserData.query7)));
                mResults.put(UserData.query8, cursor.getString(cursor.getColumnIndex(UserData.query8)));
                mResults.put(UserData.query9, cursor.getString(cursor.getColumnIndex(UserData.query9)));
                mResults.put(UserData.query10, cursor.getString(cursor.getColumnIndex(UserData.query10)));
                mResults.put(UserData.query11, cursor.getString(cursor.getColumnIndex(UserData.query11)));
                mResults.put(UserData.query12, cursor.getString(cursor.getColumnIndex(UserData.query12)));
            }
            cursor.close();
        }
    }

    private DataProvider dataProvider;

    private String getString(String key) {
        if (!debugMode && mResults.isEmpty()) {
            query();
        }

        return getOrDefault(key, "");
    }

    private String getOrDefault(Object key, String defaultValue) {
        String value = mResults.get(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
}
