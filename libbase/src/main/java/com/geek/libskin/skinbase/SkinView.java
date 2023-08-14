package com.geek.libskin.skinbase;

import android.view.View;

import java.util.List;

/**
 * @ClassName: SkinView
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 10:52 AM$
 * TODO 用于保存view属性
 */
public class SkinView {
    // 替换view， 一个view上可能有多个属性需要替换
    // 例如imageView src / background
    //    textView text / textColor / background 等
    private final View view;


    private final List<SkinAttr> skinAttrs;

    public SkinView(View view, List<SkinAttr> mList) {
        this.view = view;
        this.skinAttrs = mList;
    }

    public View getView() {
        return view;
    }

    public List<SkinAttr> getSkinAttrs() {
        return skinAttrs;
    }

    @Override
    public String toString() {
        return "SkinView{" +
                "view=" + view +
                ", skinAttrs=" + skinAttrs +
                '}';
    }
}
