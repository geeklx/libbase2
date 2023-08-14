package com.geek.libskin.skinbase;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


/**
 * @ClassName: SkinAttr
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 10:52 AM$
 * TODO
 */
public class SkinAttr {
    private final String key;
    private final String value;
    // 保存资源类型,用来区分background时候是drawable还是color等
    private String type;

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 5:22 PM
     * TODO 是否可以添加value, 用来判断value是否是一个资源，资源文件开头都是@
     */
    private boolean isAddValue;

    public SkinAttr(Context context, String key, String value) {
        this.key = key;
        this.value = getCheckValue(context, value);
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 1:37 PM
     * TODO 校验value
     * 将开始的@符号去掉
     */
    public String getCheckValue(Context context, String value) {

        if (value.startsWith("@")) {
            value = value.substring(1);
//            return value;
            try {
                int id = Integer.parseInt(value);
                // 保存资源类型,用来区分background时候是drawable还是color等
                type = context.getResources().getResourceTypeName(id);
                // 通过id转换成value
                String name = context.getResources().getResourceEntryName(id);
                Log.i("szjCheckValue:", name + "\t" + type);

                isAddValue = true;
                return name;
            } catch (Exception e) {
                SkinLog.e("SkinAttr#getCheckValue() 转换失败;" + SkinConfig.SKIN_ERROR_2);
                isAddValue = false;
                return null;
            }
        }

        return value;
    }

    public boolean isAddValue() {
        return isAddValue && !TextUtils.isEmpty(getKey()) && !TextUtils.isEmpty(getValue());
    }

    public String getType() {
        return type != null ? type : "";
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SkinAttr{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", isAddValue=" + isAddValue +
                '}';
    }
}
