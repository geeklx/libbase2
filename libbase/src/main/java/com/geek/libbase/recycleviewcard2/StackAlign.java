package com.geek.libbase.recycleviewcard2;

/**
 * Created by CJJ on 2017/10/16.
 *
 * @author CJJ
 */

enum StackAlign {


    LEFT(1),
    RIGHT(-1),
    TOP(1),
    BOTTOM(-1);

    int layoutDirection;

    StackAlign(int sign) {
        this.layoutDirection = sign;
    }
}
