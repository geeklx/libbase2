package com.geek.libglide47.demo.commonutil;

import com.geek.libglide47.demo.domain.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil {

    public static List<ImageModel> getImages() {
        List<ImageModel> list = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        images.add("https://img.zcool.cn/community/013bce592505d3b5b3086ed49f70e6.gif");
        list.add(new ImageModel("一共" + images.size() + "张图片", images));
        return list;
    }

}
