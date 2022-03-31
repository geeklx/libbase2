package com.geek.libbase.itemtouchhelper;

public interface DragMethod {
    void onMove(int fromPosition, int toPosition);

    void onSwiped(int position);
}
