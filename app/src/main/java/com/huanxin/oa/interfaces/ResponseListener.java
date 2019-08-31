package com.huanxin.oa.interfaces;

import java.io.IOException;

import okhttp3.Response;

public interface ResponseListener {
        void onSuccess(String string);//参数不知道怎么传可以先不传

        void onFail(IOException e);
    }