package com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class OkHttpInitSingtonManager {
    private static OkHttpClient okHttpClient;
    static{
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }
    public static OkHttpClient getOkHttpClient(){
        if( okHttpClient != null){
            return okHttpClient;
        }else{
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
