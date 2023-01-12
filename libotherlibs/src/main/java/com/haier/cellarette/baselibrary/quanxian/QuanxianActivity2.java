package com.haier.cellarette.baselibrary.quanxian;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XXPermissions
 *    time   : 2018/06/15
 *    desc   : 权限申请演示
 */
public final class QuanxianActivity2 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanxian2);

        findViewById(R.id.btn_main_request_single).setOnClickListener(this);
        findViewById(R.id.btn_main_request_group).setOnClickListener(this);
        findViewById(R.id.btn_main_request_location).setOnClickListener(this);
        findViewById(R.id.btn_main_request_sensors).setOnClickListener(this);
        findViewById(R.id.btn_main_request_activity_recognition).setOnClickListener(this);
        findViewById(R.id.btn_main_request_bluetooth).setOnClickListener(this);
        findViewById(R.id.btn_main_request_wifi).setOnClickListener(this);
        findViewById(R.id.btn_main_request_read_media_location).setOnClickListener(this);
        findViewById(R.id.btn_main_request_media_read).setOnClickListener(this);
        findViewById(R.id.btn_main_request_manage_storage).setOnClickListener(this);
        findViewById(R.id.btn_main_request_install).setOnClickListener(this);
        findViewById(R.id.btn_main_request_window).setOnClickListener(this);
        findViewById(R.id.btn_main_request_setting).setOnClickListener(this);
        findViewById(R.id.btn_main_request_notification).setOnClickListener(this);
        findViewById(R.id.btn_main_request_post_notification).setOnClickListener(this);
        findViewById(R.id.btn_main_request_notification_listener).setOnClickListener(this);
        findViewById(R.id.btn_main_request_package).setOnClickListener(this);
        findViewById(R.id.btn_main_request_alarm).setOnClickListener(this);
        findViewById(R.id.btn_main_request_not_disturb).setOnClickListener(this);
        findViewById(R.id.btn_main_request_ignore_battery).setOnClickListener(this);
        findViewById(R.id.btn_main_request_picture_in_picture).setOnClickListener(this);
        findViewById(R.id.btn_main_request_open_vpn).setOnClickListener(this);
        findViewById(R.id.btn_main_request_get_installed_app).setOnClickListener(this);
        findViewById(R.id.btn_main_app_details).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_main_request_single) {

            XXPermissions.with(this)
                    .permission(Permission.CAMERA)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_group) {

            XXPermissions.with(this)
                    .permission(Permission.RECORD_AUDIO)
                    .permission(Permission.Group.CALENDAR)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_location) {

            XXPermissions.with(this)
                    .permission(Permission.ACCESS_COARSE_LOCATION)
                    .permission(Permission.ACCESS_FINE_LOCATION)
                    // 如果不需要在后台使用定位功能，请不要申请此权限
                    .permission(Permission.ACCESS_BACKGROUND_LOCATION)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_sensors) {

            XXPermissions.with(this)
                    .permission(Permission.BODY_SENSORS)
                    .permission(Permission.BODY_SENSORS_BACKGROUND)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_activity_recognition) {

            XXPermissions.with(this)
                    .permission(Permission.ACTIVITY_RECOGNITION)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                            addCountStepListener();
                        }
                    });

        } else if (viewId == R.id.btn_main_request_bluetooth) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_12_bluetooth_permission_hint));
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity2.this)
                            .permission(Permission.BLUETOOTH_SCAN)
                            .permission(Permission.BLUETOOTH_CONNECT)
                            .permission(Permission.BLUETOOTH_ADVERTISE)
                            .interceptor(new PermissionInterceptor())
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_wifi) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_13_wifi_permission_hint));
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity2.this)
                            .permission(Permission.NEARBY_WIFI_DEVICES)
                            .interceptor(new PermissionInterceptor())
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_read_media_location) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_10_read_media_location_permission_hint));
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity2.this)
                            // Permission.READ_EXTERNAL_STORAGE 和 Permission.MANAGE_EXTERNAL_STORAGE 二选一
                            // 如果 targetSdk >= 33，则添加 Permission.READ_MEDIA_IMAGES 和 Permission.MANAGE_EXTERNAL_STORAGE 二选一
                            // 如果 targetSdk < 33，则添加 Permission.READ_EXTERNAL_STORAGE 和 Permission.MANAGE_EXTERNAL_STORAGE 二选一
                            .permission(Permission.READ_MEDIA_IMAGES)
                            .permission(Permission.ACCESS_MEDIA_LOCATION)
                            .interceptor(new PermissionInterceptor())
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAllImagesFromGallery();
                                        }
                                    }).start();
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_media_read) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_13_read_media_permission_hint));
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity2.this)
                            // 不适配分区存储应该这样写
                            //.permission(Permission.MANAGE_EXTERNAL_STORAGE)
                            // 适配分区存储应该这样写
                            .permission(Permission.READ_MEDIA_IMAGES)
                            .permission(Permission.READ_MEDIA_VIDEO)
                            .permission(Permission.READ_MEDIA_AUDIO)
                            .interceptor(new PermissionInterceptor())
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_manage_storage) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_11_manage_storage_permission_hint));
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity2.this)
                            // 适配分区存储应该这样写
                            //.permission(Permission.Group.STORAGE)
                            // 不适配分区存储应该这样写
                            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                            .interceptor(new PermissionInterceptor())
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_install) {

            XXPermissions.with(this)
                    .permission(Permission.REQUEST_INSTALL_PACKAGES)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_window) {

            XXPermissions.with(this)
                    .permission(Permission.SYSTEM_ALERT_WINDOW)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_setting) {

            XXPermissions.with(this)
                    .permission(Permission.WRITE_SETTINGS)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_notification) {

            XXPermissions.with(this)
                    .permission(Permission.NOTIFICATION_SERVICE)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_post_notification) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_13_post_notification_permission_hint));
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity2.this)
                            .permission(Permission.POST_NOTIFICATIONS)
                            .interceptor(new PermissionInterceptor())
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_notification_listener) {

            XXPermissions.with(this)
                    .permission(Permission.BIND_NOTIFICATION_LISTENER_SERVICE)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                toggleNotificationListenerService();
                            }
                        }
                    });

        } else if (viewId == R.id.btn_main_request_package) {

            XXPermissions.with(this)
                    .permission(Permission.PACKAGE_USAGE_STATS)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_alarm) {

            XXPermissions.with(this)
                    .permission(Permission.SCHEDULE_EXACT_ALARM)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_not_disturb) {

            XXPermissions.with(this)
                    .permission(Permission.ACCESS_NOTIFICATION_POLICY)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_ignore_battery) {

            XXPermissions.with(this)
                    .permission(Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_picture_in_picture) {

            XXPermissions.with(this)
                    .permission(Permission.PICTURE_IN_PICTURE)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_open_vpn) {

            XXPermissions.with(this)
                    .permission(Permission.BIND_VPN_SERVICE)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                        }
                    });

        } else if (viewId == R.id.btn_main_request_get_installed_app) {

            XXPermissions.with(this)
                    .permission(Permission.GET_INSTALLED_APPS)
                    .interceptor(new PermissionInterceptor())
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
                                return;
                            }
                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
                                    PermissionNameConvert.getPermissionString(QuanxianActivity2.this, permissions)));
                            getAppList();
                        }
                    });

        } else if (viewId == R.id.btn_main_app_details) {

            XXPermissions.startPermissionActivity(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != XXPermissions.REQUEST_CODE) {
            return;
        }
        toast(getString(R.string.demo_return_activity_result_hint));
    }

    public void toast(CharSequence text) {
        ToastUtils.showLong(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void toggleNotificationListenerService() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(
                new ComponentName(this, NotificationMonitorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        packageManager.setComponentEnabledSetting(
                new ComponentName(this, NotificationMonitorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 获取所有图片
     */
    private void getAllImagesFromGallery() {
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                MediaStore.MediaColumns.TITLE, MediaStore.Images.Media.SIZE,
                MediaStore.Images.ImageColumns.LATITUDE, MediaStore.Images.ImageColumns.LONGITUDE};

        final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        Cursor cursor = getApplicationContext().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                        null, null, orderBy + " DESC");

        int idIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
        int pathIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {

            String filePath = cursor.getString(pathIndex);

            float[] latLong = new float[2];

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // 谷歌官方文档：https://developer.android.google.cn/training/data-storage/shared/media?hl=zh-cn#location-media-captured
                Uri photoUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getString(idIndex));
                photoUri = MediaStore.setRequireOriginal(photoUri);
                try {
                    InputStream inputStream = getApplicationContext()
                            .getContentResolver().openInputStream(photoUri);
                    if (inputStream == null) {
                        continue;
                    }
                    ExifInterface exifInterface = new ExifInterface(inputStream);
                    // 获取图片的经纬度
                    exifInterface.getLatLong(latLong);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedOperationException e) {
                    // java.lang.UnsupportedOperationException:
                    // Caller must hold ACCESS_MEDIA_LOCATION permission to access original
                    // 经过测试，在部分手机上面申请获取媒体位置权限，如果用户选择的是 "仅在使用中允许"
                    // 那么就会导致权限是授予状态，但是调用 openInputStream 时会抛出此异常
                    e.printStackTrace();
                }
            } else {
                int latitudeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE);
                int longitudeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE);
                latLong = new float[]{cursor.getFloat(latitudeIndex), cursor.getFloat(longitudeIndex)};
            }

            if (latLong[0] != 0 && latLong[1] != 0) {
                Log.i("XXPermissions", "获取到图片的经纬度：" + filePath + "，" +  Arrays.toString(latLong));
                Log.i("XXPermissions", "图片经纬度所在的地址：" + latLongToAddressString(latLong[0], latLong[1]));
            } else {
                Log.i("XXPermissions", "该图片获取不到经纬度：" + filePath);
            }
        }
        cursor.close();
    }

    /**
     * 将经纬度转换成地址
     */
    private String latLongToAddressString(float latitude, float longitude) {
        String addressString = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                addressString = strReturnedAddress.toString();
            } else {
                Log.w("XXPermissions", "没有返回地址");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("XXPermissions", "无法获取到地址");
        }
        return addressString;
    }

    private final SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.w("onSensorChanged", "event = " + event);
            switch (event.sensor.getType()) {
                case Sensor.TYPE_STEP_COUNTER:
                    Log.w("XXPermissions", "开机以来当天总步数：" + event.values[0]);
                    break;
                case Sensor.TYPE_STEP_DETECTOR:
                    if (event.values[0] == 1) {
                        Log.w("XXPermissions", "当前走了一步");
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("onAccuracyChanged", String.valueOf(accuracy));
        }
    };

    /**
     * 添加步数监听
     */
    private void addCountStepListener() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor stepSensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepSensor != null) {
            manager.registerListener(mSensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (detectorSensor != null) {
            manager.registerListener(mSensorEventListener, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void getAppList() {
        try {
            PackageManager packageManager = getPackageManager();
            int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES;
            List<PackageInfo> packageInfoList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageInfoList = packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(flags));
            } else {
                packageInfoList = packageManager.getInstalledPackages(flags);
            }

            for (PackageInfo info : packageInfoList) {
                Log.i("XXPermissions", "应用包名：" + info.packageName);
            }
        } catch (Throwable t) {
            t.printStackTrace();;
        }
    }
}