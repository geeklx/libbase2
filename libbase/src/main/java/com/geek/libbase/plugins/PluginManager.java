package com.geek.libbase.plugins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * @author: 王硕风
 * @date: 2021.6.9 23:57
 * @Description:
 */
public class PluginManager {
    private PackageInfo packageInfo;
    private Resources resources;
    private Context context;
    private DexClassLoader dexClassLoader;

    private static final PluginManager sInstance = new PluginManager();

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return sInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public void loadPath(Context context, String filePath) {
//        File filesDir = context.getDir("plugin", Context.MODE_PRIVATE);
//        String name = "APlugin.apk";
//        String filePath = new File(filesDir, name).getAbsolutePath();

        //通过插件apk路径加载插件apk后，获取插件apk的packageInfo、dexClassLoader、resource等信息
        //1、packageInfo
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);

        //2、dexClassLoader
        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(filePath, dexOutFile.getAbsolutePath(), null, context.getClassLoader());

        //3、resource
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, filePath);
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }

        parserReceivers(context, filePath);
    }

    /**
     * 通过系统PMS来解析apk，获取AndroidManifest.xml中静态注册的广播，从而动态注册广播
     *
     * @param context
     * @param path
     */
    private void parserReceivers(Context context, String path) {
        //获取PackageParser.Package对象   解析apk
        try {
            Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
            //不同版本中参数不同
            Method parserPackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
            Object packageParser = packageParserClass.newInstance();
            //获取PackageParser.Package对象，再获取receivrs
            Object packageObj = parserPackageMethod.invoke(packageParser, new File(path), PackageManager.GET_ACTIVITIES);

            Field receiverField = packageObj.getClass().getDeclaredField("receivers");
            //拿到receivers集合    泛型是Activity类型   只是一个javabean，没有name，但是内部的ActivityInfo中有name信息
            List receivers = (List) receiverField.get(packageObj);

            //拿到intentsField
            Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
            Field intentsField = componentClass.getDeclaredField("intents");


            //把Activity转换为ActivityInfo
            //PackageParser中通过generatetionActivityInfo()方法获取ActivityInfo     需要activity对象
            Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");

            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            //获取用户对象
            Object defaultUserState = packageUserStateClass.newInstance();
            //获取generateActivityInfo方法
            Method generateReceiverInfo =
                    packageParserClass.getDeclaredMethod("generateActivityInfo", packageParser$ActivityClass, int.class, packageUserStateClass, int.class);

            //通过getCallingUserId获取userId
            Class<?> userHandle = Class.forName("android.os.UserHandle");
            Method getCallingUserIdMethod = userHandle.getDeclaredMethod("getCallingUserId");
            //获取用户id
            int userId = (int) getCallingUserIdMethod.invoke(null);

            for (Object activity : receivers) {
                //通过反射调用generateReceiverInfo方法让activity转换为ActivityInfo
                ActivityInfo info =
                        (ActivityInfo) generateReceiverInfo.invoke(packageParser, activity, 0, defaultUserState, userId);
                //通过name全类名得到广播
                BroadcastReceiver broadcastReceiver =
                        (BroadcastReceiver) dexClassLoader.loadClass(info.name).newInstance();

                List<? extends IntentFilter> intents = (List<? extends IntentFilter>) intentsField.get(activity);
                for (IntentFilter intentFilter : intents) {
                    context.registerReceiver(broadcastReceiver, intentFilter);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
