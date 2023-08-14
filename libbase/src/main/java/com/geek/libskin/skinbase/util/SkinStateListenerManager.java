package com.geek.libskin.skinbase.util;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


import com.geek.libskin.skinbase.SkinLog;
import com.geek.libskin.skinbase.SkinManager;
import com.geek.libskin.skinbase.callback.SkinStateListener;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: SkinStateListenerManager
 * @Author: 史大拿
 * @CreateDate: 5/17/23$ 9:15 AM$
 * TODO
 */
public class SkinStateListenerManager implements LifecycleObserver {
    private HashMap<AppCompatActivity, SkinStateListener> activityMap;
    private HashMap<Fragment, SkinStateListener> fragmentXMap;
    private HashMap<android.app.Fragment, SkinStateListener> appFragmentMap;

    /*
     * 作者:史大拿
     * 创建时间: 5/17/23 9:33 AM
     * TODO activity listener
     */
    public void addListener(AppCompatActivity activity, SkinStateListener listener) {
        HashMap<AppCompatActivity, SkinStateListener> map = getActivityMap();

        if (!map.containsKey(activity)) {
            activity.getLifecycle().addObserver(this);
            map.put(activity, listener);
        }
        SkinLog.i("szjAddListener", map.size() + "");
    }

    private void removeListener(AppCompatActivity activity) {
        HashMap<AppCompatActivity, SkinStateListener> map = getActivityMap();
        map.remove(activity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(Object object) {
        if (object instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) object;
            removeListener(activity);
            SkinLog.i("szjDestroy-Activity:", getActivityMap().size() + "\t" + activity.getClass().getSimpleName());
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            removeListener(fragment);
            SkinLog.i("szjDestroy-Fragment", getFragmentXMap().size() + "\t" + fragment.getClass().getSimpleName());
        }
    }

    /*
     * 作者:史大拿
     * 创建时间: 5/17/23 9:33 AM
     * TODO fragment listener
     */
    public void addListener(Fragment fragment, SkinStateListener listener) {
        HashMap<Fragment, SkinStateListener> map = getFragmentXMap();

        if (!map.containsKey(fragment)) {
            fragment.getLifecycle().addObserver(this);
            map.put(fragment, listener);
        }
    }

    private void removeListener(Fragment fragment) {
        HashMap<Fragment, SkinStateListener> map = getFragmentXMap();
        map.remove(fragment);
    }

    /*
     * 作者:史大拿
     * 创建时间: 5/17/23 9:33 AM
     * TODO android.app.fragment listener
     */
    public void addListener(android.app.Fragment fragment, SkinStateListener listener) {
        HashMap<android.app.Fragment, SkinStateListener> map = getAppFragmentMap();

        if (!map.containsKey(fragment)) {
            map.put(fragment, listener);
        }
    }

    public void removeListener(android.app.Fragment fragment) {
        HashMap<android.app.Fragment, SkinStateListener> map = getAppFragmentMap();
        map.remove(fragment);
    }

    public void notify(AppCompatActivity activity, SkinManager.State state) {
        activityNotify(activity, state);
        fragmentNotify(activity.getSupportFragmentManager().getFragments(), state);
        appFragmentNotify(state);
    }

    public void activityNotify(AppCompatActivity activity, SkinManager.State state) {
        HashMap<AppCompatActivity, SkinStateListener> activityMap = getActivityMap();

        if (activityMap.containsKey(activity)) {
            SkinStateListener listener = activityMap.get(activity);
            listener.skinStateResultCallBack(state);
        }
        SkinLog.i("szj当前activity", activity);
    }


    public void fragmentNotify(List<Fragment> fragments, SkinManager.State state) {
        HashMap<Fragment, SkinStateListener> fragmentMap = getFragmentXMap();
        for (Fragment fragment : fragmentMap.keySet()) {
            if (fragments.contains(fragment)) {
                SkinStateListener listener = fragmentMap.get(fragment);
                listener.skinStateResultCallBack(state);
            }
        }
    }

    public void appFragmentNotify(SkinManager.State state) {
        HashMap<android.app.Fragment, SkinStateListener> fragmentMap = getAppFragmentMap();
        for (SkinStateListener listener : fragmentMap.values()) {
            listener.skinStateResultCallBack(state);
        }
    }


    public HashMap<AppCompatActivity, SkinStateListener> getActivityMap() {
        if (activityMap == null) {
            activityMap = new HashMap<>();
        }
        return activityMap;
    }

    public HashMap<Fragment, SkinStateListener> getFragmentXMap() {
        if (fragmentXMap == null) {
            fragmentXMap = new HashMap<>();
        }
        return fragmentXMap;
    }

    public HashMap<android.app.Fragment, SkinStateListener> getAppFragmentMap() {
        if (appFragmentMap == null) {
            appFragmentMap = new HashMap<>();
        }
        return appFragmentMap;
    }
}
