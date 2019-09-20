package com.huanxin.oa.application;

import android.app.Application;
import android.util.Log;

import com.huanxin.oa.service.LocationService;
import com.tencent.smtt.sdk.QbSdk;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    private OkHttpClient okHttpClient;
    private static BaseApplication baseApplication;
    public LocationService locationService;

    @Override
    public final void onCreate() {
        super.onCreate();
        locationService = new LocationService(getApplicationContext());
        initX5WebView();
//        SDKInitializer.initialize(getApplicationContext());
        baseApplication = this;
    }

    /**
     * 返回应用程序实例
     */
    public static BaseApplication getInstance() {
        return baseApplication;
    }


    public OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                    .build();
        }
        return okHttpClient;
    }

    public void setClinet(int readTimeout) {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(readTimeout, TimeUnit.SECONDS)//设置读取超时时间
                    .build();
        }
    }

    private void initX5WebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    ;
}
