package com.geek.libskin.skinbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import org.xmlpull.v1.XmlPullParser;

/**
 * @ClassName: SkinLayoutInflaterFactory
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 9:54 AM$
 * TODO 粘贴系统源码
 */
public abstract class SkinCreateView implements LayoutInflater.Factory2 {
    @SuppressLint("ObsoleteSdkInt")
    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;
    private final Activity mActivity;

    public SkinCreateView(Activity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        SkinLog.i("onCreateView",name);
        SystemAppCompatViewInflater systemAppCompatViewInflater = new SystemAppCompatViewInflater();
        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext(mActivity, (ViewParent) parent);
        }
        View view = systemAppCompatViewInflater
                .createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP /* Only read android:theme pre-L (L+ handles this anyway) */
                /* Read read app:theme as a fallback at all times for legacy reasons */
        );



        if (view != null) {
            viewAttrs(context, view, name, attrs);
        }

        return view;
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 10:49 AM
     * TODO 用来收集view信息
     */
    abstract void viewAttrs(Context context, View view, String name, AttributeSet attrs);

    private boolean shouldInheritContext(Activity activity, ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = activity.getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                return false;
            }
            parent = parent.getParent();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }
}
