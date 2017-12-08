package com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule;

import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class OKHttp3ApplyCookieManager {
    //쿠키가 없는 OkHttpClient 객체
    public static final int NETWORK_SUCCESS = 0;
    public static final int NETWORK_FAIL = -1;
    public static final int NETWORK_ID_NOT_REGISTERED = 2;

    private static OkHttpClient okHttpClient;

    //쿠키를 제거하는 클래스
    public static OkHttpClient getOkHttpNormalClient() {
        if (okHttpClient != null) {
            return okHttpClient;
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
