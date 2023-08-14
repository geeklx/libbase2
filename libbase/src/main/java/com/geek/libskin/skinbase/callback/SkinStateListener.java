package com.geek.libskin.skinbase.callback;


import com.geek.libskin.skinbase.SkinManager;

/**
 * @ClassName: SkinStateListener
 * @Author: 史大拿
 * @CreateDate: 5/16/23$ 5:08 PM$
 * TODO 皮肤状态监听
 */
public interface SkinStateListener {
    /*
     * 作者:史大拿
     * 创建时间: 5/16/23 5:09 PM
     * state: 当前状态枚举
     */
     void skinStateResultCallBack(SkinManager.State state);
}
