package com.geek.libbase.fragmentsgeek.demo2.factorys;

import androidx.collection.SparseArrayCompat;

import com.geek.libbase.R;
import com.geek.libbase.base.SlbBaseFragment;
import com.geek.libbase.fragmentsgeek.demo2.configs.MkDemo2Config;
import com.geek.libbase.fragmentsgeek.demo2.fragments.MkDemo2Fragment1;
import com.geek.libbase.fragmentsgeek.demo2.fragments.MkDemo2Fragment2;

public class MkDemo2Factory2 {

    public static void setup() {
//        IndexConfig.PAGE_COUNT = 3;
//        IndexConfig.PAGE_ID = "old_pager_index_";
//        IndexConfig.DEFAULT_PAGE_INDEX = 1;
        registerFragments(MkDemo2Config.getFragments());
    }

    private static void registerFragments(SparseArrayCompat<Class<? extends SlbBaseFragment>> sIndexFragments) {
        sIndexFragments.put(R.id.demo2_page_0_item_1, MkDemo2Fragment2.class);//菜谱
        sIndexFragments.put(R.id.demo2_page_0_item_2, MkDemo2Fragment1.class);//视频
    }
}
