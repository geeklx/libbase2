package com.geek.libbase.itemtouchhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * description:添加滑动隐藏软键盘，解决嵌套view失去惯性
 */
public class RecyclerViewScrollView extends ScrollView {
 
 
    ScrollViewListener scrollViewListener;
    private int downX;
    private int downY;
    private int mTouchSlop;
 
    public RecyclerViewScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
 
    public RecyclerViewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
 
    public RecyclerViewScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
 
 
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(y);
        }
    }
 
    public void setOnScroll(ScrollViewListener l) {
        scrollViewListener = l;
    }
 
    public interface ScrollViewListener{
        void onScrollChanged(int y);
    }
 
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    //如果有滑动了，则屏蔽滑动事件
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}