package com.geek.libbase.plugins.interfaces;

import android.content.Context;
import android.content.Intent;


public interface BroadcastReceiverStandardInterface {

    void attach(Context context);

    void onReceive(Context context, Intent intent);
}
