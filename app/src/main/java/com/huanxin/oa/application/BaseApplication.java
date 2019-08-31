package com.huanxin.oa.application;

import android.app.Application;

import com.huanxin.oa.service.LocationService;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    private OkHttpClient okHttpClient;
    private static BaseApplication baseApplication;
    public LocationService locationService;

    @Override
    public final void onCreate() {
        super.onCreate();
        locationService = new LocationService(getApplicationContext());
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
        if (okHttpClient == null)
            okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    ;
}
