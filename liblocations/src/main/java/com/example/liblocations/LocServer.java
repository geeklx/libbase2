package com.example.liblocations;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocServer {
    private Context context;
    private AMapLocationListener listener;
    private AMapLocationClientOption option;

    private AMapLocationClient client;

    public LocServer(Context context, AMapLocationListener listener, AMapLocationClientOption option) {
        this.context = context;
        this.listener = listener;
        this.option = option;
        init();
    }

    private void init() {
        try {
            client = new AMapLocationClient(context);
            client.setLocationListener(listener);
            client.setLocationOption(option);
        } catch (Exception e) {
            Log.e("LocUtil", e.toString());
            e.printStackTrace();
        }

    }

    public void start() {
        if (client != null) {
            client.startLocation();
        }
    }

    public boolean enStarting() {
        return client != null && client.isStarted();
    }

    public void stop() {
        if (client != null) {
            client.stopLocation();
        }
    }

    public void destory() {
        if (client != null) {
            client.onDestroy();
        }
    }

    public AMapLocation getLast() {
        return client.getLastKnownLocation();
    }

    public String getVersion() {
        return client.getVersion();
    }
}