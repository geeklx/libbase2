package com.geek.libkeyboards.k1;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by xuanweijian on 2016/3/31.
 */
public class KeyboardTouchListener implements View.OnTouchListener {
    private KeyboardUtil keyboardUtil;
    private int keyboardType = 1;

    public KeyboardTouchListener(KeyboardUtil util, int keyboardType) {
        this.keyboardUtil = util;
        this.keyboardType = keyboardType;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (keyboardUtil != null && keyboardUtil.getEd() != null && v.getId() != keyboardUtil.getEd().getId()) {
                keyboardUtil.showKeyBoardLayout((EditText) v, keyboardType);
            } else if (keyboardUtil != null && keyboardUtil.getEd() == null) {
                keyboardUtil.showKeyBoardLayout((EditText) v, keyboardType);
            } else {
                if (keyboardUtil != null) {
                    keyboardUtil.setKeyBoardCursorNew((EditText) v);
                }
            }
        }
        return false;
    }
}
