package com.geek.libskin.skinbase;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.geek.libskin.skinbase.callback.SkinStateListener;
import com.geek.libskin.skinbase.util.SkinStateListenerManager;

import java.util.Observable;

/**
 * @ClassName: SkinManager
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 9:39 AM$
 * TODO
 */
public class SkinManager extends Observable {
    private static volatile SkinManager mInstance;

    private final Application mApplication;

    private SkinResource skinResource;

    // 默认使用原始皮肤
    private State mState = State.ORIGIN;

    private final SkinStateListenerManager skinStateListenerManager;

    private SkinManager(Application application) {
        mApplication = application;

        // 用来存储skin状态，为了第n次启动app的时候也保持skin状态
        SkinSharedPreferences.init(mApplication);

        String skinState = SkinSharedPreferences.getInstance().getSkinName();
        if (!TextUtils.isEmpty(skinState)) {
            // 设置第一次的状态
            mState = State.valueOf(skinState);
        }

        skinStateListenerManager = new SkinStateListenerManager();

        // 监听所有Activity
        mApplication.registerActivityLifecycleCallbacks(new SkinActivityLifecycleCallbacks(this));
    }


    public static void init(Application application) {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("SkinManager没有初始化;" + SkinConfig.SKIN_ERROR_1);
        }
        return mInstance;
    }

    public Resources getResource() {
        return skinResource.getResources();
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 1:44 PM
     * TODO 加载皮肤
     * @path: 皮肤路径
     */
    public void loadSkin(String path, @NonNull Activity activity) {
        if (path == null) {
            reset(activity);
            return;
        }
        loadSkin(path, false, activity);
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 1:18 PM
     * TODO 加载皮肤
     * @param isNotify: 是否强制刷新
     */
    public void loadSkin(String path, boolean isNotify, @NonNull Activity activity) {

        // 校验路径是否合格
        if (!checkPath(path)) {
            return;
        }

        // 获取之前保存路径
        String oldPath = SkinSharedPreferences.getInstance().getSkinPath();

//        String skinState = SkinSharedPreferences.getInstance().getSkinName();

        // 判断当前皮肤路径和之前保存的皮肤路径是否相等
        boolean isNewPath = !oldPath.equals(path);

        // 如果皮肤路径不一样，说明又使用了新的皮肤，所以需要重新加载skinResource
        if (skinResource == null || isNewPath) {
            SkinResource.init(mApplication, path, isNewPath);
            skinResource = SkinResource.getInstance();
        }


        // 判断当前状态是否是和系统资源相同，如果相同，那么就优先使用皮肤包资源
        skinResource.resume();

        /*
         * 作者:史大拿
         * 创建时间: 1/4/23 10:34 AM
         * TODO 如果状态为原始状态才换肤
         *
         * 如果当前是原始状态 或者 需要强制刷新 或者当前是新皮肤路径
         */
        if (/*mState == State.ORIGIN || */ isNotify || isNewPath) {
            SkinLog.i("szj是否执行", "111");

            // 已经换肤
            mState = State.SKIN;

            notifyChanged(activity);

            // 存储皮肤状态
            SkinSharedPreferences.getInstance().setSkinName(State.SKIN.name());
            SkinSharedPreferences.getInstance().setSkinPath(path);
        }

    }

    private boolean checkPath(String path) {
        return SkinCheckApk.checkPath(mApplication, path);
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 10:16 AM
     * TODO 清空皮肤
     */
    public void reset(@NonNull Activity activity) {
        reset(activity, false);
    }

    /*
     * 作者:史大拿
     * 创建时间: 5/17/23 10:41 AM
     * TODO
     *
     */
    public void reset(@NonNull Activity activity, Boolean isNotify) {
        if (mState == State.SKIN || isNotify) {
            mState = State.ORIGIN;

            if (skinResource != null) {
                skinResource.reset();
            }

            notifyChanged(activity);
            SkinSharedPreferences.getInstance().setSkinName(State.ORIGIN.name());
            SkinSharedPreferences.getInstance().setSkinPath("");
        }
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 2:13 PM
     * TODO 通知观察者
     */
    private void notifyChanged(@NonNull Activity activity) {
        setChanged();
        notifyObservers(activity);

        SkinLog.i("szj换肤", "notifyChanged:" + getState().name());
        // 回调当前皮肤状态
        if (skinStateListenerManager != null) {
            if (activity instanceof AppCompatActivity) {
                skinStateListenerManager.notify((AppCompatActivity) activity, getState());
            }
        }
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 3:36 PM
     * TODO 尝试初始化皮肤包
     */
    public void tryInitSkin(@NonNull Activity activity) {
        SkinActivityLifecycleCallbacks.tryInitSkin(activity);
    }


    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 2:37 PM
     * TODO 获取color
     * return 0:没有获取到资源
     */
    public int getColor(@NonNull String value) {
        if (isSkinState()) {
            return skinResource.getColor(value);
        }
        return ContextCompat.getColor(mApplication, getSystemResourceId(value, "color"));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 5:09 PM
     * TODO 获取String
     */
    public String getString(String value) {
        if (isSkinState()) {
            return skinResource.getString(value);
        }
        return mApplication.getResources().getString(getSystemResourceId(value, "string"));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 9:32 AM
     * TODO 获取Drawable
     */
    public Drawable getDrawable(String value) {
        if (isSkinState()) {
            return skinResource.getDrawable(value);
        }
        return ContextCompat.getDrawable(mApplication, getSystemResourceId(value, "drawable"));
    }
    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 9:32 AM
     * TODO 获取Drawable
     */
    public Drawable getMipmap(String value) {
        if (isSkinState()) {
            return skinResource.getMipmap(value);
        }
        return ContextCompat.getDrawable(mApplication, getSystemResourceId(value, "mipmap"));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/6/23 4:50 PM
     * TODO 获取id
     */
    public int getIdentifier(String value, String defType) {
        if (isSkinState()) {
            return skinResource.getIdentifier(value, defType);
        }
        return mApplication.getResources().getIdentifier(value, defType, mApplication.getPackageName());
    }

    /*
     * 作者:史大拿
     * 创建时间: 4/17/23 2:30 PM
     * TODO 自定义替换字体
     */
    public Typeface getFont(String value) {
        if (isSkinState()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return skinResource.getFont(value);
            }
        }
        return ResourcesCompat.getFont(mApplication.getApplicationContext(), getSystemResourceId(value, "font"));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 10:09 AM
     * TODO 获取fontSize
     */
    public float getFontSize(String value) {
        if (isSkinState()) {
            return skinResource.getFontSize(value);
        }
        return mApplication.getResources().getDimension(getSystemResourceId(value, "dimen"));
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 4:43 PM
     * TODO 获取系统资源
     */
    private int getSystemResourceId(String value, String defType) {
        int identifier = mApplication.getResources().getIdentifier(value, defType, mApplication.getPackageName());
        if (identifier == 0) {
            throw new RuntimeException("获取系统资源失败，请检查资源文件下是否存在:" + value + "(SkinManager);" + SkinConfig.SKIN_ERROR_8);
        }
        return identifier;
    }

    /*
     * 作者:史大拿
     * 创建时间: 4/17/23 2:21 PM
     * TODO 是否是换肤状态
     */
    public boolean isSkinState() {
        return getState() == State.SKIN && skinResource != null;
    }

    public synchronized State getState() {
        return mState;
    }


    /*
     * 作者:史大拿
     * 创建时间: 5/17/23 10:00 AM
     * TODO 注册 activity, [无需解绑]
     */
    public void setStateListener(AppCompatActivity activity, SkinStateListener stateListener) {
        skinStateListenerManager.addListener(activity, stateListener);
    }

    public void setStateListener(Fragment fragment, SkinStateListener stateListener) {
        skinStateListenerManager.addListener(fragment, stateListener);
    }

    public void setStateListener(android.app.Fragment fragment, SkinStateListener stateListener) {
        skinStateListenerManager.addListener(fragment, stateListener);
    }

    public void unregisterStateListener(android.app.Fragment activity) {
        skinStateListenerManager.removeListener(activity);
    }

    public enum State {
        /*
         * 作者:史大拿
         * 创建时间: 1/4/23 10:35 AM
         * TODO 换肤状态
         */
        SKIN,


        /*
         * 作者:史大拿
         * 创建时间: 1/4/23 10:35 AM
         * TODO 原始状态
         */
        ORIGIN
    }


}
