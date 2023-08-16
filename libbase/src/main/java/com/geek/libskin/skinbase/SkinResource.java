package com.geek.libskin.skinbase;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.ArrayMap;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @ClassName: SkinResource
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 9:45 AM$
 * TODO 资源具体实现
 */
@SuppressLint("StaticFieldLeak")
public class SkinResource {

    private static volatile SkinResource mInstance;
    private final Application mApplication;

    private Resources mSkinResource;
    // apk资源
    public String mPath;

    // 资源id缓存
    private static final ArrayMap<String, SkinResourceCacheBean> resourceIdCache = new ArrayMap<>();

    private static final String COLOR = "color";
    private static final String STRING = "string";
    private static final String DRAWABLE = "drawable";
    private static final String MIPMAP = "mipmap";
    private static final String DIMEN = "dimen";
    private static final String FONT = "font";


    private SkinResource(Application application, String path) {
        mApplication = application;
        this.mPath = path;
        this.mSkinResource = createSkinResource(mPath);
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/11/23 9:30 AM
     * TODO
     * path: 皮肤包路径
     * isNewPath: 是否是新的皮肤路径
     */
    public static void init(Application application, String path, boolean isNewPath) {
        if (mInstance == null || isNewPath) {
            synchronized (SkinResource.class) {
                if (mInstance == null || isNewPath) mInstance = new SkinResource(application, path);
            }
        }
    }


    public static SkinResource getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("SkinResource没有初始化;" + SkinConfig.SKIN_ERROR_5);
        }
        clearSkin();
        return mInstance;
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 10:27 AM
     * TODO 恢复皮肤包状态
     */
    public void resume() {
        if (isResume()) {
            mSkinResource = createSkinResource(mPath);
        }
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 10:27 AM
     * TODO 是否需要恢复皮肤包
     */
    public boolean isResume() {
        return mSkinResource == mApplication.getResources();
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 10:27 AM
     * TODO 重制
     */
    public void reset() {
        // 将皮肤包的资源指向system资源
        mSkinResource = mApplication.getResources();
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 9:51 AM
     * TODO 创建皮肤包中的资源
     * @param path: 皮肤包在手机中的路径
     */
    private Resources createSkinResource(String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();

            String addAssetPath = "addAssetPath";

            @SuppressLint("DiscouragedPrivateApi") Method method = AssetManager.class.getDeclaredMethod(addAssetPath, String.class);
            method.setAccessible(true);
            /// 反射执行方法
            method.invoke(assetManager, path);
            return new Resources(assetManager, createDisplayMetrics(), createConfiguration());
        } catch (Exception e) {
            throw new RuntimeException("Resources创建失败;" + SkinConfig.SKIN_ERROR_4);
        }
    }

    public DisplayMetrics createDisplayMetrics() {
        return mApplication.getResources().getDisplayMetrics();
    }

    public Configuration createConfiguration() {
        return mApplication.getResources().getConfiguration();
    }

    public Resources getResources() {
        try {
            if (mSkinResource == null) {
                return mApplication.getResources();
            }
            return mSkinResource;
        } catch (Exception e) {
            return mApplication.getResources();
        }
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 5:13 PM
     * TODO 重载, 如果重载表示获取当前apk包名
     */
    private String getPackName() {
        return getPackName(null);
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 2:33 PM
     * TODO 获取包名
     */
    public String getPackName(String path) {
        if (path != null && !path.isEmpty()) {
            PackageInfo info = mApplication.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                ApplicationInfo appInfo = info.applicationInfo;
                return appInfo.packageName;  //得到安装包名称
            }
        }

        return mApplication.getPackageName();
    }

    public int getColor(String value) {
        // 尝试在缓存中获取颜色资源
        int colorId = tryGetCacheResourceColorId(value);
        if (colorId != SkinConfig.SKIN_FAIL) {
            // 获取到了,直接返回
            return colorId;
        }

        // 先获取皮肤包中的id
        int id = mSkinResource.getIdentifier(value, COLOR, getPackName(mPath));

        // id == 0 表示皮肤包中没有该资源
        if (id == 0) {

            // 再次获取本地的资源
            id = getSystemResourceId(value, COLOR);

            // 如果本地资源没有获取到，尝试吧这个参数当作系统资源再次获取
            if (id == 0) id = getSystemResourceId("android:" + value, COLOR);


            // 本地资源存在
            if (id != 0) {
                // 缓存系统资源id
                cacheResourceId(value, COLOR, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SYSTEM));
                return ContextCompat.getColor(mApplication, id);
            }


            // 本地资源也不存在就throw异常
            throwRuntimeException(COLOR);
        }

        // 缓存皮肤包资源
        cacheResourceId(value, COLOR, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SKIN));

        // 皮肤包资源存在，直接返回
        return mSkinResource.getColor(id, null);
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/14/23 11:09 AM
     * TODO 尝试在缓存中获取颜色资源
     */
    private synchronized int tryGetCacheResourceColorId(String value) {
        // 先从缓存中取资源
        SkinResourceCacheBean cacheData = getCacheResourceId(value, COLOR);
        if (cacheData != null) {
            int resourceId = cacheData.getResourceId(); // 获取资源id
            SkinResourceCacheBean.TYPE type = cacheData.getType(); // 获取资源类型
            if (type == SkinResourceCacheBean.TYPE.SKIN) {
                // 加载皮肤包中的资源
                return mSkinResource.getColor(resourceId, null);
            } else if (type == SkinResourceCacheBean.TYPE.SYSTEM) {
                return mApplication.getColor(resourceId);
            }
        }
        return SkinConfig.SKIN_FAIL;
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/14/23 10:56 AM
     * TODO 缓存资源id
     * @param key:缓存key
     * @param defType: 缓存类型
     * @param value: 缓存值
     */
    private void cacheResourceId(String key, String defType, SkinResourceCacheBean value) {
        String resultKey = defType + ":" + key + ":" + SkinManager.getInstance().getState().name();
        // 如果不包含 就缓存一份
        if (!resourceIdCache.containsKey(resultKey)) {
            SkinLog.i("szjCacheResourcePut", "key:" + resultKey + "\t" + value);
            resourceIdCache.put(resultKey, value);
        }
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/14/23 10:51 AM
     * TODO 获取资源id
     * @param key: 缓存key
     * @param defType:缓存类型
     */
    private SkinResourceCacheBean getCacheResourceId(String key, String defType) {
        // resultKey = "color:skin_global_background:ORIGIN"
        String resultKey = defType + ":" + key + ":" + SkinManager.getInstance().getState().name();
//        SkinResourceCacheBean value = resourceIdCache.get(resultKey);
//        SkinLog.i("szjCacheResourceGet", "key:" + resultKey + "\t" + value);
//        return value;

        return resourceIdCache.get(resultKey);
    }


    // 和 getColor() 同理，这里注释就不加了
    public String getString(String value) {
        String cacheValue = tryCacheResourceStringId(value);
        if (cacheValue != null) return cacheValue;

        int id = mSkinResource.getIdentifier(value, "string", getPackName(mPath));
        if (id == 0) {
            id = getSystemResourceId(value, "string");

            if (id == 0) id = getSystemResourceId("android:" + value, "string");

            if (id != 0) {
                cacheResourceId(value, STRING, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SYSTEM));
                return mApplication.getString(id);
            }

            throwRuntimeException("string");
        }

        cacheResourceId(value, STRING, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SKIN));
        return mSkinResource.getString(id);
    }

    // 从缓存中取StringId
    private synchronized String tryCacheResourceStringId(String value) {
        // 先从缓存中取资源
        SkinResourceCacheBean cacheData = getCacheResourceId(value, STRING);
        if (cacheData != null) {
            int resourceId = cacheData.getResourceId(); // 获取资源id
            SkinResourceCacheBean.TYPE type = cacheData.getType(); // 获取资源类型
            if (type == SkinResourceCacheBean.TYPE.SKIN) {
                // 加载皮肤包中的资源
                return mSkinResource.getString(resourceId);
            } else if (type == SkinResourceCacheBean.TYPE.SYSTEM) {
                return mApplication.getString(resourceId);
            }
        }
        return null;
    }

    public Drawable getDrawable(String value) {
        Drawable cacheDrawable = tryGetCacheResourceDrawableId(value);
        if (cacheDrawable != null) return cacheDrawable;

        int id = mSkinResource.getIdentifier(value, DRAWABLE, getPackName(mPath));
        if (id == 0) {
            id = getSystemResourceId(value, DRAWABLE);

            if (id == 0) id = getSystemResourceId("android:" + value, DRAWABLE);

            if (id != 0) {
                cacheResourceId(value, DRAWABLE, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SYSTEM));
                return ContextCompat.getDrawable(mApplication, id);
            }
            throwRuntimeException(DRAWABLE);
        }
        cacheResourceId(value, DRAWABLE, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SKIN));
        return mSkinResource.getDrawable(id, null);
    }

    private synchronized Drawable tryGetCacheResourceDrawableId(String value) {
        // 先从缓存中取资源
        SkinResourceCacheBean cacheData = getCacheResourceId(value, DRAWABLE);
        if (cacheData != null) {
            int resourceId = cacheData.getResourceId(); // 获取资源id
            SkinResourceCacheBean.TYPE type = cacheData.getType(); // 获取资源类型
            if (type == SkinResourceCacheBean.TYPE.SKIN) {
                // 加载皮肤包中的资源
                return mSkinResource.getDrawable(resourceId, null);
            } else if (type == SkinResourceCacheBean.TYPE.SYSTEM) {
                return ContextCompat.getDrawable(mApplication, resourceId);
            }
        }
        return null;
    }


    public Drawable getMipmap(String value) {
        Drawable cacheDrawable = tryGetCacheResourceMipmapId(value);
        if (cacheDrawable != null) return cacheDrawable;

        int id = mSkinResource.getIdentifier(value, MIPMAP, getPackName(mPath));
        if (id == 0) {
            id = getSystemResourceId(value, MIPMAP);

            if (id == 0) id = getSystemResourceId("android:" + value, MIPMAP);

            if (id != 0) {
                cacheResourceId(value, MIPMAP, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SYSTEM));
                return ContextCompat.getDrawable(mApplication, id);
            }
            throwRuntimeException(MIPMAP);
        }
        cacheResourceId(value, MIPMAP, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SKIN));
        return mSkinResource.getDrawable(id, null);
    }

    private synchronized Drawable tryGetCacheResourceMipmapId(String value) {
        // 先从缓存中取资源
        SkinResourceCacheBean cacheData = getCacheResourceId(value, MIPMAP);
        if (cacheData != null) {
            int resourceId = cacheData.getResourceId(); // 获取资源id
            SkinResourceCacheBean.TYPE type = cacheData.getType(); // 获取资源类型
            if (type == SkinResourceCacheBean.TYPE.SKIN) {
                // 加载皮肤包中的资源
                return mSkinResource.getDrawable(resourceId, null);
            } else if (type == SkinResourceCacheBean.TYPE.SYSTEM) {
                return ContextCompat.getDrawable(mApplication, resourceId);
            }
        }
        return null;
    }

    public float getFontSize(String value) {
        float cacheValue = tryGetCacheResourceDimenId(value);
        if (cacheValue != SkinConfig.SKIN_FAIL) {
            return cacheValue;
        }

        int id = mSkinResource.getIdentifier(value, DIMEN, getPackName(mPath));
        if (id == 0) {
            id = getSystemResourceId(value, DIMEN);

            if (id == 0) id = getSystemResourceId("android:" + value, DIMEN);

            if (id != 0) {
                cacheResourceId(value, DIMEN, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SYSTEM));
                return mApplication.getResources().getDimension(id);
            }

            throwRuntimeException(DIMEN);
        }
        cacheResourceId(value, DIMEN, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SKIN));
        return mSkinResource.getDimension(id);
    }

    private synchronized float tryGetCacheResourceDimenId(String value) {
        // 先从缓存中取资源
        SkinResourceCacheBean cacheData = getCacheResourceId(value, DIMEN);
        if (cacheData != null) {
            int resourceId = cacheData.getResourceId(); // 获取资源id
            SkinResourceCacheBean.TYPE type = cacheData.getType(); // 获取资源类型
            if (type == SkinResourceCacheBean.TYPE.SKIN) {
                // 加载皮肤包中的资源
                return mSkinResource.getDimension(resourceId);
            } else if (type == SkinResourceCacheBean.TYPE.SYSTEM) {
                return mApplication.getResources().getDimension(resourceId);
            }
        }
        return SkinConfig.SKIN_FAIL;
    }

    /*
     * 作者:史大拿
     * 创建时间: 4/17/23 2:40 PM
     * TODO font
     */
    public Typeface getFont(String value) {
        // 尝试从缓存中取
        Typeface cacheValue = tryGetCacheResourceFontId(value);
        if (cacheValue != null) {
            return cacheValue;
        }

        // 皮肤包总取
        int id = mSkinResource.getIdentifier(value, FONT, getPackName(mPath));

        // 没取到
        if (id == 0) {

            // 自己取
            id = getSystemResourceId(value, FONT);

            //没取到
            if (id == 0) id = getSystemResourceId("android:" + value, FONT);

            // 取到了，当作系统缓存
            if (id != 0) {
                cacheResourceId(value, FONT, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SYSTEM));
                return ResourcesCompat.getFont(mApplication.getApplicationContext(), id);
            }

            // 还是没取到 报错
            throwRuntimeException(FONT);
        }

        // 当作皮肤包缓存
        cacheResourceId(value, FONT, new SkinResourceCacheBean(id, SkinResourceCacheBean.TYPE.SKIN));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mSkinResource.getFont(id);
        } else {
            return ResourcesCompat.getFont(mApplication.getApplicationContext(), id);
        }
    }

    private synchronized Typeface tryGetCacheResourceFontId(String value) {
        // 先从缓存中取资源
        SkinResourceCacheBean cacheData = getCacheResourceId(value, FONT);
        if (cacheData != null) {
            int resourceId = cacheData.getResourceId(); // 获取资源id
            SkinResourceCacheBean.TYPE type = cacheData.getType(); // 获取资源类型
            if (type == SkinResourceCacheBean.TYPE.SKIN) {
                // 加载皮肤包中的资源
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return mSkinResource.getFont(resourceId);
                }
            } else if (type == SkinResourceCacheBean.TYPE.SYSTEM) {
                return ResourcesCompat.getFont(mApplication.getApplicationContext(), resourceId);
            }
        }
        return null;
    }


    public int getIdentifier(String value, String defType) {
        int id = mSkinResource.getIdentifier(value, defType, getPackName(mPath));
        if (id == 0) {
            id = getSystemResourceId(value, defType);
        }
        return id;
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 10:23 AM
     * TODO 获取系统资源id
     */
    private int getSystemResourceId(String value, String defType) {
        return mApplication.getResources().getIdentifier(value, defType, getPackName());
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/16/23 4:13 PM
     * TODO 删除皮肤包缓存
     */
    public static void clearSkin() {
        ArrayList<String> keys = new ArrayList<>();

        // 循环所有缓存数据
        for (String key : resourceIdCache.keySet()) {
            // 通过key 获取到value
            SkinResourceCacheBean value = resourceIdCache.get(key);
            if (value == null) continue;
            // 有皮肤资源 并且 如果当前activity相等
            if (value.getType() == SkinResourceCacheBean.TYPE.SKIN) {
                // 那么就保存key
                keys.add(key);
            }
        }
        SkinLog.i("szj是否删除皮肤包缓存(前):", resourceIdCache.entrySet().size());
        // 循环所有需要删除的key
        for (int i = 0; i < keys.size(); i++) {
            // 一个一个删除掉
            SkinResourceCacheBean isRemove = resourceIdCache.remove(keys.get(i));
            SkinLog.i("szj是否删除皮肤包缓存:", isRemove);
        }
        SkinLog.i("szj是否删除皮肤包缓存(后):", resourceIdCache.entrySet().size());
    }

    private void throwRuntimeException(String type) {
        throw new RuntimeException("皮肤包和本地资源都没有当前属性(" + type + ");" + SkinConfig.SKIN_ERROR_6);
    }


}
