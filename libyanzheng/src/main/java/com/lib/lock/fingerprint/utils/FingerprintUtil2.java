package com.lib.lock.fingerprint.utils;

import android.content.Context;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取设备指纹的工具类
 *
 * @author BarryHuang
 * @DATE 2015-8-19
 */

public class FingerprintUtil2 {
    private static final String TAG = FingerprintUtil2.class.getSimpleName();

    private static final String FINGER_PRINT = "fingerprint";

    /**
     * 获取设备指纹
     * 如果从SharedPreferences文件中拿不到，那么重新生成一个，
     * 并保存到SharedPreferences文件中。
     *
     * @param context
     * @return fingerprint 设备指纹
     */
    public static String getFingerprint(Context context) {
        String fingerprint = null;
        fingerprint = readFingerprintFromFile(context);
        if (TextUtils.isEmpty(fingerprint)) {
            fingerprint = createFingerprint(context);
        } else {
            Log.e(TAG, "从文件中获取设备指纹：" + fingerprint);
        }
        return fingerprint;
    }

    /**
     * 从SharedPreferences 文件获取设备指纹
     *
     * @return fingerprint 设备指纹
     */
    private static String readFingerprintFromFile(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(FINGER_PRINT, null);
    }

    /**
     * 生成一个设备指纹（耗时50毫秒以内）：
     * 1.IMEI + 设备硬件信息（主要）+ ANDROID_ID + WIFI MAC组合成的字符串
     * 2.用MessageDigest将以上字符串处理成32位的16进制字符串
     *
     * @param context
     * @return 设备指纹
     */
    public static String createFingerprint(Context context) {
        long startTime = System.currentTimeMillis();
        // 1.IMEI
        final String imei = DeviceUtils.getUniqueDeviceId();
        Log.i(TAG, "imei=" + imei);
        //2.android 设备信息（主要是硬件信息）
        final String hardwareInfo = Build.ID + Build.DISPLAY + Build.PRODUCT
                + Build.DEVICE + Build.BOARD /*+ Build.CPU_ABI*/
                + Build.MANUFACTURER + Build.BRAND + Build.MODEL
                + Build.BOOTLOADER + Build.HARDWARE /* + Build.SERIAL */
                + Build.TYPE + Build.TAGS + Build.FINGERPRINT + Build.HOST
                + Build.USER;
        //Build.SERIAL => 需要API 9以上    
        Log.i(TAG, "hardward info=" + hardwareInfo);
        final String androidId = DeviceUtils.getAndroidID();
        Log.i(TAG, "android_id=" + androidId);
        // Combined Device ID
        final String deviceId = imei + hardwareInfo + androidId/* + wifiMAC + bt_MAC*/;
        Log.i(TAG, "deviceId=" + deviceId);
        // 创建一个 messageDigest 实例
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //用 MessageDigest 将 deviceId 处理成32位的16进制字符串
        msgDigest.update(deviceId.getBytes(), 0, deviceId.length());
        // get md5 bytes    
        byte md5ArrayData[] = msgDigest.digest();
        // create a hex string
        String deviceUniqueId = new String();
        for (int i = 0; i < md5ArrayData.length; i++) {
            int b = (0xFF & md5ArrayData[i]);
            // if it is a single digit, make sure it have 0 in front (proper    
            // padding)    
            if (b <= 0xF) {
                deviceUniqueId += "0";
            }
            // add number to string    
            deviceUniqueId += Integer.toHexString(b);
//          Log.i(TAG,"deviceUniqueId=" + deviceUniqueId);    
        } // hex string to uppercase    
        deviceUniqueId = deviceUniqueId.toUpperCase();
        Log.d(TAG, "生成的设备指纹：" + deviceUniqueId);

        Log.e(TAG, "生成DeviceId 耗时：" + (System.currentTimeMillis() - startTime));
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(FINGER_PRINT, deviceUniqueId).commit();

        return deviceUniqueId;
    }
}