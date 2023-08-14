package com.geek.libskin.skinbase;


/**
 * @ClassName: SkinResourceCacheBean
 * @Author: 史大拿
 * @CreateDate: 1/14/23$ 10:58 AM$
 * TODO
 */
public class SkinResourceCacheBean {
    // 缓存资源id
    private final int resourceId;

    private final TYPE type;

    public SkinResourceCacheBean(int resourceId, TYPE type) {
        this.resourceId = resourceId;
        this.type = type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public TYPE getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Cache{" +
                "resourceId=" + resourceId +
                ", type=" + type +
                '}';
    }

    enum TYPE{
        SYSTEM, // 系统
        SKIN // 皮肤包
    }
}
