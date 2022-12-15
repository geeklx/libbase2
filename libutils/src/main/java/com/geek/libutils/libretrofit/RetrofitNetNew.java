package com.geek.libutils.libretrofit;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libutils.data.MmkvUtils;
import com.geek.libutils.libretrofit.cache.EnhancedCacheInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitNetNew {
    private static RetrofitNetNew sInstance;

    private static Retrofit retrofit;
    private OkHttpClient client;

    public Retrofit init() {
        //添加一个log拦截器,打印所有的log
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        //可以设置请求过滤的水平,body,basic,headers
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置 请求的缓存的大小跟位置
        File cacheFile = new File(Utils.getApp().getExternalFilesDir(null).getAbsolutePath(), "RetrofitNetNewCache1");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小
        client = new OkHttpClient.Builder()
                .cache(cache)  //添加缓存
                .addInterceptor(addQueryParameterInterceptor())  //参数添加
                .addInterceptor(addHeaderInterceptor()) // token过滤
                .addInterceptor(new LoggingInterceptor()) //日志,所有的请求响应度看到 LoggingInterceptor
                //缓存
                .addInterceptor(new EnhancedCacheInterceptor())
//                .addInterceptor(cacheControlInterceptor)
                .addInterceptor(OfflineInterceptor())
                .addNetworkInterceptor(OnlineInterceptor())
                .connectTimeout(10L, TimeUnit.SECONDS).readTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).build();

//        client.dispatcher().runningCalls().get(0).request().tag()

        retrofit = new Retrofit.Builder().baseUrl("https://www.baidu.com").addConverterFactory(GsonConverterFactory.create()).client(client).build();

        return retrofit;

    }

    private RetrofitNetNew() {
        init();
    }

    public static void config() {
        sInstance = new RetrofitNetNew();
    }

    public static RetrofitNetNew getInstance() {
        return sInstance;
    }

    /**
     * 获取网络框架
     *
     * @return
     */
    public Retrofit get() {
        return retrofit;
    }

    /**
     * 创建一个业务请求 <br />
     *
     * @param convertClass 业务请求接口的class
     * @return
     */
    public static <T> T build(Class<T> convertClass, Object tag) {
        return getInstance().get().create(convertClass);
//        return (T) Proxy.newProxyInstance(convertClass.getClassLoader(),
//                new Class<?>[] {convertClass}, new Handler(tag));
    }


    public void cancelAll() {
        client.dispatcher().cancelAll();

    }

    public void cancel(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
//                        .addQueryParameter("phoneSystem", "")
//                        .addQueryParameter("liveClientType", BanbenUtils.getInstance().getLiveClientType())
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                int numcode = (int) ((Math.random() * 9 + 1) * 100000);
                String accessSecret = SPUtils.getInstance().getString("accessSecret", "accessSecret");
                String accessKey2 = SPUtils.getInstance().getString("accessKey", "accessKey");
                String version2 = SPUtils.getInstance().getString("version", "V1");
                String latitude = SPUtils.getInstance().getString("weidu", "weidu");
                String longitude = SPUtils.getInstance().getString("jingdu", "jingdu");
                String timer = System.currentTimeMillis() + "";
                String accessKey = timer + numcode + accessSecret;
                String MD5 = version2 + EncryptUtils.encryptMD5ToString(accessKey) + "";
                //
                HeaderBean headerBean = new HeaderBean();
                headerBean.setImei(BanbenUtils.getInstance().getImei());
                headerBean.setPlatform(BanbenUtils.getInstance().getPlatform());
                headerBean.setToken(BanbenUtils.getInstance().getToken());
                headerBean.setModel(DeviceUtils.getManufacturer());
                headerBean.setVersion(BanbenUtils.getInstance().getVersion());
                headerBean.setVersion_code(AppUtils.getAppVersionCode() + "");
                headerBean.setPackage_name(AppUtils.getAppPackageName() + "");
                headerBean.setLatitude(latitude);
                headerBean.setLongitude(longitude);
                MmkvUtils.getInstance().set_common_json("app_header", JSON.toJSONString(headerBean), HeaderBean.class);
//                HeaderBean bean = MmkvUtils.getInstance().get_common_json("app_header", HeaderBean.class);
//                MyLogUtil.e("RetrofitNetNew_Interceptor", JSON.toJSONString(bean));
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("imei", BanbenUtils.getInstance().getImei()).header("platform", BanbenUtils.getInstance().getPlatform()).header("token", BanbenUtils.getInstance().getToken()).header("fs-ck", BanbenUtils.getInstance().getToken()).header("model", DeviceUtils.getManufacturer()).header("version", BanbenUtils.getInstance().getVersion()).header("version_code", AppUtils.getAppVersionCode() + "").header("package_name", AppUtils.getAppPackageName() + "").header("latitude", latitude + "").header("longitude", longitude + "").header("X-CA-KEY", accessKey2).header("X-CA-SIGNATURE", MD5.toUpperCase() + "").header("X-CA-TIMESTAMP", timer).header("X-CA-NONCE", numcode + "").method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 有网络的时候
     */
    private static Interceptor OnlineInterceptor() {
        Interceptor onlineInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int onlineCacheTime = 0;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                return response.newBuilder().header("Cache-Control", "public, max-age=" + onlineCacheTime).removeHeader("Pragma").build();
            }
        };
        return onlineInterceptor;
    }

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    //这里是设置拦截器，供下面的函数调用，辅助作用。
    private static final Interceptor cacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                // 有网络时 设置缓存为默认值
//                ToastUtils.showLong("有网络时 设置缓存为默认值");
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
//                ToastUtils.showLong("无网络时 设置超时为1周");
                return originalResponse.newBuilder()
                        .header("Cache-Control",
                                "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 有网络的时候
     */
    private static Interceptor OfflineInterceptor() {
        Interceptor offlineInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected()) {
                    //从缓存取数据
                    Request newRequest = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                    int maxTime = 60 * 60 * 24;
                    Response response = chain.proceed(newRequest);
                    return response.newBuilder().removeHeader("Pragma").header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime).build();
                }else {
                    return chain.proceed(request);
                }
            }
        };
        return offlineInterceptor;
    }


    private static Application sInstanceApp;

    private static Application getApp() {
        if (sInstanceApp == null) {
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
                sInstanceApp = app;
            }
        }

        return sInstanceApp;
    }

}
