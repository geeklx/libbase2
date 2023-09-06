package com.geek.libutils.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.geek.libutils.R;

import eightbitlab.com.blurview.BlurView;

public class LxBlurView extends BlurView {

    private boolean itouch;

    public LxBlurView(Context context) {
        super(context);
    }

    public LxBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LxBlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LxBlurView, defStyleAttr, 0);
        itouch = array.getBoolean(R.styleable.LxBlurView_istouch1, false);
        array.recycle();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return itouch;
    }

    public void setTouch(boolean is) {
        itouch = is;
    }
}
