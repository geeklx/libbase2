package com.geek.libbase.recycleviewcard2;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class StackBean {
    private int img;
    private String url;

    public StackBean() {
    }

    public StackBean(int img, String url) {
        this.img = img;
        this.url = url;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
