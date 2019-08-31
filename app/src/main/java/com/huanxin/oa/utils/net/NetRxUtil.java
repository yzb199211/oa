package com.huanxin.oa.utils.net;

import android.util.Log;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class NetRxUtil {
    private final static String Base_URL = "http://112.124.10.153:1004/MobileServerNew/";
    private static Retrofit mRetrofit;

    public interface WeatherService {
        //  网络接口的使用为查询天气的接口
//
        @POST("LoginHandler.ashx")
//  此处回调返回的可为任意类型Call<T>，再也不用自己去解析json数据啦！！！
        Call<JsonObject> getData(@Body MultipartBody requestBody);
    }

    /**
     * 单纯使用Retrofit的联网请求
     */
    public static void doRequestByRetrofit(MultipartBody requestBody) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .build();
    }

    public <T> T Creat(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    public void CallNet(MultipartBody requestBody) {
        WeatherService weatherService = mRetrofit.create(WeatherService.class);
        Call<JsonObject> call = weatherService.getData(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //测试数据返回
                JsonObject weatherEntity = response.body();
                Log.e("TAG", "response == " + weatherEntity.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TAG", "Throwable : " + t);
            }
        });
    }
}
