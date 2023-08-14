package com.geek.libskin.skinbase;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.Observable;

/**
 * @ClassName: SkinActivityLifecycleCallbacks
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 9:42 AM$
 * TODO
 */
public class SkinActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 5:49 PM
     * TODO 缓存Factory
     */
    private final ArrayMap<Activity, SkinLayoutInflaterFactory> skinFactoryCache;

    private final Observable mObservable;


    public SkinActivityLifecycleCallbacks(Observable observable) {
        skinFactoryCache = new ArrayMap<>();
        mObservable = observable;
    }


    // activity不可见
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        // 执行时机为 Activity#super.onCreate() 之后
        //          setContentView() 之前
        Log.i("szj生命周期", "onActivityCreated");
        if (!skinFactoryCache.containsKey(activity)) {
            SkinLayoutInflaterFactory skinLayoutInflaterFactory = forceSetFactory2(activity.getLayoutInflater(), activity);
            if (skinLayoutInflaterFactory != null)
                // 缓存factory
            {
                skinFactoryCache.put(activity, skinLayoutInflaterFactory);
            }
        }

        SkinLayoutInflaterFactory skinLayoutInflaterFactory = skinFactoryCache.get(activity);

        if (skinLayoutInflaterFactory != null)
            /// 观察者模式
        {
            mObservable.addObserver(skinLayoutInflaterFactory);
        }

        SkinLog.i("szjLifeCycle", "onActivityCreated:" + activity.getClass().getSimpleName());
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 1:42 PM
     * TODO 反射创建Factory
     */
    private SkinLayoutInflaterFactory forceSetFactory2(LayoutInflater inflater, Activity activity) {
        Class<LayoutInflater> inflaterClass = LayoutInflater.class;
        try {
            String mFactoryStr = "mFactory";
            Field mFactory = inflaterClass.getDeclaredField(mFactoryStr);
            mFactory.setAccessible(true);

            String mFactory2Str = "mFactory2";
            Field mFactory2 = inflaterClass.getDeclaredField(mFactory2Str);
            mFactory2.setAccessible(true);
            SkinLayoutInflaterFactory skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(activity);
            // 改变factory
            mFactory2.set(inflater, skinLayoutInflaterFactory);
            mFactory.set(inflater, skinLayoutInflaterFactory);
            return skinLayoutInflaterFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //

    // activity可见了，还没出现在前台，无法与activity交互
    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        tryInitSkin(activity);
    }

    public static void tryInitSkin(@NonNull Activity activity) {
        /// 获取皮肤状态
        String skinState = SkinSharedPreferences.getInstance().getSkinName();
        if (!TextUtils.isEmpty(skinState)) {

            // 获取皮肤路径
            String skinPath = SkinSharedPreferences.getInstance().getSkinPath();
            SkinLog.i("szj当前皮肤状态", "state:" + skinState + "\tpath:" + skinPath);
//            if (!skinPath.isEmpty() && SkinManager.State.SKIN.name().equals(skinState)) {
            // 如果皮肤路径存在，并且当前状态是需要换肤
            boolean isReplace = !TextUtils.isEmpty(skinPath) && SkinManager.State.SKIN.name().equals(skinState);
            if (isReplace) {
                // 换肤
                SkinManager.getInstance().loadSkin(skinPath, true, activity);
            } else {
                // 原始皮肤
                SkinManager.getInstance().reset(activity, true);
            }
        }
    }


    // activity可见，
    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        SkinLog.i("szjLifeCycle", "onActivityResumed:" + activity.getClass().getSimpleName());
    }

    // activity可见，不可交互
    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        SkinLog.i("szjLifeCycle", "onActivityPaused:" + activity.getClass().getSimpleName());

    }

    // activity不可见，不可交互
    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        SkinLog.i("szjLifeCycle", "onActivityStopped:" + activity.getClass().getSimpleName());

    }

    // 用来存储数据，获取数据在onCreate中，可参考:https://juejin.cn/post/7090080784807100447#heading-15
    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    // activity 销毁
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = skinFactoryCache.get(activity);
        SkinLog.i("onActivityDestroyed", skinLayoutInflaterFactory + "");
        // 删除监听者
        if (skinLayoutInflaterFactory != null) {
            skinLayoutInflaterFactory.removeKey(activity);

            mObservable.deleteObserver(skinLayoutInflaterFactory);
        }

        skinFactoryCache.remove(activity);


        SkinLog.i("szjLifeCycle", "onActivityDestroyed:" + activity.getClass().getSimpleName());
    }
}
