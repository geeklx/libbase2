package com.geek.liblocations;

public interface LocListener {
    void success(LocationBean model);
    void fail(int msg);
}