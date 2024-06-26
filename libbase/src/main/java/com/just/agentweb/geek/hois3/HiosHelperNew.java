package com.just.agentweb.geek.hois3;

import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.webkit.WebView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libutils.SlbLoginUtil;
import com.just.agentweb.geek.fragment.AgentWebFragment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiosHelperNew {

    private static final String FLAG_ACTION = "{act}";
    private static String adWebAction;
    private static String webAction;

    /**
     * 配置跳转的广告webActivity的action
     *
     * @param adAct
     * @param webAct
     */
    public static void config(String adAct, String webAct) {
        adWebAction = adAct;
        webAction = webAct;
    }

    /**
     * 配置跳转的广告webActivity的action
     *
     * @param webAct
     */
    public static void config(String webAct) {
        webAction = webAct;
    }

    /**
     * 适用广告系统2.0版本的广告点击处理
     *
     * @param act      目标activity
     * @param receiver 如果是方法执行，receiver为接收者
     * @param url      广告地址，区分http(s)和hios
     */
    public static void resolveAd(Activity act, Object receiver, String url) {
        if (!HiosHelperNew.shouldOverrideUrl(act, receiver, url)) {
            // 打开第三方APPbufen
            if (url.startsWith("com") || url.startsWith("cn")) {
                if (!AppUtils.isAppInstalled(url)) {
                    ToastUtils.showLong("未安装此应用服务");
                    return;
                }
                AppUtils.launchApp(url);
                return;
            }
            // 打开其他bufen
//        if (url.startsWith("tel")) {
            if (url.startsWith(WebView.SCHEME_TEL)
                    || url.startsWith("sms:")
                    || url.startsWith(WebView.SCHEME_MAILTO)
                    || url.startsWith(WebView.SCHEME_GEO)) {
                try {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:" + fguanyuBean.getPhone()));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    act.startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                return;
            }
            // 跳转到是否登录验证的H5bufen
            Intent it = new Intent(webAction);
            it.putExtra(AgentWebFragment.URL_KEY, url);
            // TODO old
//            try {
//                act.startActivity(it);
//            } catch (ActivityNotFoundException e) {
//                Log.e("Activity", "No Activity found to handle intent " + it);
//            }
            // TODO new
            try {
                if (url.contains(UriHelperNew.CONDITION_LOGIN)) {
                    SlbLoginUtil.get().loginTowhere(act, new Runnable() {
                        @Override
                        public void run() {
                            act.startActivity(it);
                        }
                    });
                } else if (url.contains(UriHelperNew.CONDITION_OR_LOGIN)) {
                    if (!SlbLoginUtil.get().isUserLogin()) {
                        SlbLoginUtil.get().login(act);
                    }
                } else {
                    act.startActivity(it);
                }
            } catch (ActivityNotFoundException e) {
                Log.e("Activity", "No Activity found to handle intent " + it);
            }
        }
    }

    /**
     * 是否拦截网页，如果拦截了， 这里会默认处理，调用者无需处理
     *
     * @param activity
     * @param url
     * @return true 拦截， false 不拦截
     */
    public static boolean shouldOverrideUrl(final Activity activity,
                                            final Object receiver, final String url) {
        Uri uri = Uri.parse(url);
        if (!checkUriHost(uri)) {
//            // 处理http(s)验证登录跳转bufen
//            String host = uri.getHost();
//            if (!TextUtils.isEmpty(host)) {
//                Intent intent = new Intent(host);
//                Intent it = new Intent(webAction);
//                it.putExtra(AgentWebFragment.URL_KEY, url);
//                activity(activity, uri, intent);
//            }
            return false;
        }

        String host = uri.getHost();
        if (TextUtils.isEmpty(host)) {
            return false;
        }

        if (!host.contains(".")) { // invoke method
            invokeMethod(activity, receiver, uri, host);
        } else { // target activity
            Pair<String, String> pair = HiosAliasNew.getByName(host);

            // when host is alias
            if (pair != null) {
                activity(activity, uri, pair);
                return true;
            }

            // when the host is end with {act},
            // use the host as Activity action
            if (host.endsWith(FLAG_ACTION)) {
                host = host.replace(FLAG_ACTION, "");
                activity(activity, uri, new Intent(host));
                return true;
            }

            // when host is class name
            if (host.startsWith(".")) {
                host = getApp().getPackageName() + host;
                host = host.replace(FLAG_ACTION, "");
            }

            activity(activity, uri, host);
        }

        return true;
    }


    /**
     * 测试数据是否可以跳转webview， 如果可以直接跳转webview
     *
     * @return
     */
    public static boolean testingShowWebView(final Activity activity,
                                             final String aid, final String url) {
        if (TextUtils.isEmpty(url)) {
            Intent intent = new Intent(adWebAction);
            intent.putExtra("aid", aid);
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("Activity", "No Activity found to handle intent " + intent);
            }
            return true;
        }

        return false;
    }

    public static boolean checkUriHost(Uri uri) {
        return UriHelperNew.HIOS_SCHEME.equalsIgnoreCase(uri.getScheme()) || UriHelperNew.HIOS_SCHEME2.equalsIgnoreCase(uri.getScheme());
    }

    /**
     * 调用receiver上的方法
     *
     * @param receiver
     * @param methodName
     * @param map
     */
    private static void methodInvoker(Object receiver, String methodName,
                                      HashMap<String, Object> map) {
        for (Class<?> klass = receiver.getClass();
             !(Object.class.equals(klass));
             klass = klass.getSuperclass()) {
            try {
                Method method = klass.getDeclaredMethod(methodName, Map.class);
                method.setAccessible(true);
                method.invoke(receiver, map);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void invokeMethod(Activity activity, final Object receiver, Uri uri,
                                     final String host) {
        final HashMap<String, Object> map = new HashMap<>();
        List<String> conditions = UriHelperNew.queryStringFillMap(map, uri);

        if (!conditions.isEmpty()) {
            if (conditions.contains(UriHelperNew.CONDITION_LOGIN)) {
                SlbLoginUtil.get().loginTowhere(activity, new Runnable() {
                    @Override
                    public void run() {
                        methodInvoker(receiver, host, map);
                    }
                });
                return;
            }

            if (conditions.contains(UriHelperNew.CONDITION_OR_LOGIN)) {
                if (!SlbLoginUtil.get().isUserLogin()) {
                    SlbLoginUtil.get().login(activity);
                    return;
                }
            }
        }

        methodInvoker(receiver, host, map);
    }

    private static void activity(final Activity activity, Uri uri, Pair<String, String> pair) {
        String packageName = pair.first;
        String className = pair.second;

        if (className.startsWith(".")) {
            className = packageName + className;
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, className));
        activity(activity, uri, intent);
    }

    /**
     * 跳转activity
     *
     * @param activity
     * @param uri
     * @param host
     */
    private static void activity(final Activity activity, Uri uri, String host) {
        try {
            Class<?> klass = Class.forName(host);
            final Intent intent = new Intent(activity, klass);
            activity(activity, uri, intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // 这里是未找到路径下的class，判断为分包写法的act处理
            final Intent intent = new Intent(host);
            activity(activity, uri, intent);

        }
    }

    private static void activity(final Activity activity, Uri uri, final Intent intent) {
        if (!WebViewUtilsNew.isIntentAvailable(activity, intent)) {
            ToastUtils.showLong("地址错误，请配置正确地址");
            return;
        }

        List<String> conditions = UriHelperNew.queryStringFillIntent(intent, uri);
        if (!conditions.isEmpty()) {
            if (conditions.contains(UriHelperNew.CONDITION_LOGIN)) {
                SlbLoginUtil.get().loginTowhere(activity, new Runnable() {
                    @Override
                    public void run() {
                        activity.startActivity(intent);
                    }
                });

                return;
            }

            if (conditions.contains(UriHelperNew.CONDITION_OR_LOGIN)) {
                if (!SlbLoginUtil.get().isUserLogin()) {
                    SlbLoginUtil.get().login(activity);
                    return;
                }
            }
        }

        activity.startActivity(intent);
    }


    private static Application sInstance;

    private static Application getApp() {
        if (sInstance == null) {
            Application app = null;
            try {
                app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                if (app == null) {
                    throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                }
            } catch (final Exception e) {
                try {
                    app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                } catch (final Exception ex) {
                    e.printStackTrace();
                }
            } finally {
                sInstance = app;
            }
        }

        return sInstance;
    }

}
