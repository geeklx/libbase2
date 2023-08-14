package com.geek.libskin.skinbase;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName: SkinLayoutInflaterFactory
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 10:34 AM$
 * TODO
 */
public class SkinLayoutInflaterFactory extends SkinCreateView implements Observer {


    private final Activity mActivity;

    // 用于保存所有可以替换的属性
    List<String> skinReplaceData = new ArrayList<>();

    public SkinLayoutInflaterFactory(Activity activity) {
        super(activity);
        mActivity = activity;

        for (SkinReplace skinReplace : SkinReplace.values()) {
            skinReplaceData.add(skinReplace.getName());
        }
    }

    // 缓存 skinView
    private final ArrayMap<Activity, List<SkinView>> skinViewCaches = new ArrayMap<>();
    private final List<SkinView> skinViews = new ArrayList<>();

    @Override
    void viewAttrs(Context context, View view, String name, AttributeSet attrs) {
        SkinLog.i("szjViewAttrs", String.format("name:%s\tviewName:%s", name, view.getClass().getSimpleName()));

        // 如果view不可见就返回
        if (view.getVisibility() == View.GONE) {
            return;
        }

        // 检测view是否需要忽略，如果忽略，则不解析这个view
        if (isSkinIgnore(attrs)) {
            return;
        }

//        long startTime = System.currentTimeMillis();
//        SkinLog.i("szj计时start:", startTime);
        // 用来保存一个view上的多个属性
        List<SkinAttr> skinAttrs = new ArrayList<>();

        /// 循环view上的每一个属性
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            // 如果属性不存在，那么就直接执行下一次
            if (!skinReplaceData.contains(attrName)) {
                continue;
            }
            SkinLog.i("szjAttrsData:", String.format("name:%s\tattrName:%s\tattrValue:%s", name, attrName, attrValue));

            /// 循环可以改变的每一个属性
            for (SkinReplace skinReplace : SkinReplace.values()) {


                /// 如果view上的属性不能改变就找下一个
                if (skinReplace.getName().equals(attrName)) {

                    SkinAttr skinAttr = new SkinAttr(context, attrName, attrValue);
                    if (!skinAttrs.contains(skinAttr) && skinAttr.isAddValue()) {
                        SkinLog.i("szjSkinAttr:", skinAttr.toString());
                        skinAttrs.add(skinAttr);
                    }
                }
            }
        }
//        SkinLog.i("szj计时end:", System.currentTimeMillis() - startTime);

        SkinView skinView = new SkinView(view, skinAttrs);

        if (!skinViews.contains(skinView) && skinAttrs.size() > 0) {
            SkinLog.i("szj是否添加:", "activity:" + mActivity.getClass().getSimpleName()
                    + "\tview:" + view.getClass().getSimpleName()
                    + "\t需要替换属性个数:" + skinAttrs.size() + "");
            skinViews.add(skinView);
            skinViewCaches.put(mActivity, skinViews);
        }
    }

    /*
     * 作者:szj
     * 创建时间: 2023-5-6
     * TODO
     * 判断view是否忽略
     */
    private boolean isSkinIgnore(AttributeSet attrs) {
        /// 循环view上的每一个属性
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (attrName.equals("skin_ignore") && attrValue.equals("true")) {
                return true;
            }
        }
        return false;
    }

    public void removeKey(Activity activity) {
        if (skinViewCaches.containsKey(activity)) {
            List<SkinView> remove = skinViewCaches.remove(activity);
            SkinLog.i("szjCacheRemoveKey", remove);
//            SkinLog.i("szjCacheRemoveKeySize", remove.size());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        /*
         * 作者:史大拿
         * 创建时间: 1/7/23 10:16 AM
         * TODO 优化，只刷新当前存储的view
         */
        Activity activity = mActivity;
        if (arg != null) {
            if (arg instanceof Activity) {
                activity = (Activity) arg;
            }
        }

        List<SkinView> skinViews = skinViewCaches.get(activity);

        if (skinViews == null) {
            return;
        }
        SkinLog.i("szjUpdate:", "activity:" + mActivity.getClass().getSimpleName() + "\tsize:" + skinViews.size());

        // 循环所有存储的view
        for (SkinView skinView : skinViews) {

            // 循环view上的param
            for (SkinAttr skinAttr : skinView.getSkinAttrs()) {

                ///循环所有属性
                for (SkinReplace skinReplace : SkinReplace.values()) {

                    if (skinReplace.getName().equals(skinAttr.getKey())) {
                        SkinLog.i("szjSkinReplace:", skinReplace.getName());
                        // 需要换肤的view
                        View view = skinView.getView();

                        skinReplace.loadResource(view, skinAttr);
                    }
                }
            }
        }
    }
}
