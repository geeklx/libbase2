package com.geek.libutils.libretrofit;

import java.io.Serializable;

public class HeaderBean implements Serializable {
    //
    private static final long serialVersionUID = 1L;
    private String imei;
    private String platform;
    private String token;
    private String model;
    private String version;
    private String version_code;
    private String package_name;
    private String latitude;
    private String longitude;

    public HeaderBean() {
    }

    public HeaderBean(String imei, String platform, String token, String model, String version, String version_code, String package_name, String latitude, String longitude) {
        this.imei = imei;
        this.platform = platform;
        this.token = token;
        this.model = model;
        this.version = version;
        this.version_code = version_code;
        this.package_name = package_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
