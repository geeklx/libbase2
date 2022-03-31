package xyz.doikki.dkplayer.bean;

import java.io.Serializable;

public class HTyxsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studyTimes;
    private String studyStatus;
    private boolean drag;

    public HTyxsBean() {
    }

    public HTyxsBean(String studyTimes, String studyStatus, boolean drag) {
        this.studyTimes = studyTimes;
        this.studyStatus = studyStatus;
        this.drag = drag;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStudyTimes() {
        return studyTimes;
    }

    public void setStudyTimes(String studyTimes) {
        this.studyTimes = studyTimes;
    }

    public String getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(String studyStatus) {
        this.studyStatus = studyStatus;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }
}
