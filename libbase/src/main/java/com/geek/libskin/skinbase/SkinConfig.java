package com.geek.libskin.skinbase;

/**
 * @ClassName: SkinConfig
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 2:56 PM$
 * TODO
 */
public class SkinConfig {
    public static final String SP_NAME = "sp_skin";

    // 用于保存skin状态
    public static final String SP_SKIN_STATE_NAME = "skin_state";

    // 用于保存skin路径
    public static final String SP_SKIN_PATH = "skin_path";

    // 失败
    public static final int SKIN_FAIL = -1;

    // SkinManager没有初始化
    public static final int SKIN_ERROR_1 = 10001;

    // SkinAttr#getCheckValue() 转换失败
    public static final int SKIN_ERROR_2 = 10002;

    // SkinSP未初始化
    public static final int SKIN_ERROR_3 = 10003;

    // Resources创建失败
    public static final int SKIN_ERROR_4 = 10004;

    // SkinResource没有初始化
    public static final int SKIN_ERROR_5 = 10005;

    // 皮肤包和本地资源都没有当前属性;
    public static final int SKIN_ERROR_6 = 10006;

    // 反射失败 + e.getMessage()
    public static final int SKIN_ERROR_7 = 10007;

    // 获取系统资源失败，请检查资源文件下是否存在 value packName;
    public static final int SKIN_ERROR_8 = 10008;

    // 换肤失败
    public static final int SKIN_ERROR_9 = 10009;
//    public static final int SKIN_ERROR_10 = 10010;
}
