package com.geek.libbase.plugins.interfaces;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;


public interface ServiceStandardInterface {
    public void attach(Service proxyService);

    public void onCreate();

    public void onStart(Intent intent, int startId);

    public int onStartCommand(Intent intent, int flags, int startId);

    public void onDestroy();

    public void onConfigurationChanged(Configuration newConfig);

    public IBinder onBind(Intent intent);

    public boolean onUnbind(Intent intent);

    public void onRebind(Intent intent);

    public void onTaskRemoved(Intent rootIntent);
}
