package com.geek.libretrofit;

public class ResponseSlbBeanDemo<T> extends ResponseSlbBean2 {

    private DataBean<T> dataBean;

    public ResponseSlbBeanDemo() {
    }

    public DataBean<T> getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean<T> dataBean) {
        this.dataBean = dataBean;
    }

    public static class DataBean<T> {
        private String a;
        private T downLoadImage;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public T getDownLoadImage() {
            return downLoadImage;
        }

        public void setDownLoadImage(T downLoadImage) {
            this.downLoadImage = downLoadImage;
        }
    }

}
