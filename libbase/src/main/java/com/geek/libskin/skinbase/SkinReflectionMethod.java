package com.geek.libskin.skinbase;

/**
 * @ClassName: SkinReflectionMethod
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 8:06 PM$
 * TODO
 */
public class SkinReflectionMethod {
    private final Class<?> cls;
    private final Object obj;

    public SkinReflectionMethod(Class<?> cls, Object obj) {
        this.cls = cls;
        this.obj = obj;
    }

    public Class<?> getCls() {
        return cls;
    }

    public Object getObj() {
        return obj;
    }
}
