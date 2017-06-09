package com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule;

import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.module.cookie.ClearableCookieJar;
import com.beta.tacademy.hellomoneycustomer.module.cookie.PersistentCookieJar;
import com.beta.tacademy.hellomoneycustomer.module.cookie.SetCookieCache;
import com.beta.tacademy.hellomoneycustomer.module.cookie.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by pyoinsoo on 2016-12-02.
 */

public class OKHttp3ApplyCookieManager {
    //쿠키가 없는 OkHttpClient 객체
    private static OkHttpClient okHttpClient;

    //쿠키를 가진 OkHttpClient(영속성/휘발성 둘다 사용가능)
    private static OkHttpClient okHttpClientApplyCookie;

    //쿠키를 제거하는 클래스
    private static ClearableCookieJar cookieJar;

    /*
      쿠키를 적용한 Okhttp3
     */
    public static OkHttpClient getOkHttpApplyCookieClient() {
        if (okHttpClientApplyCookie != null) {
            return okHttpClientApplyCookie;
        } else {
            if(cookieJar == null){
                 cookieJar =  new PersistentCookieJar(new SetCookieCache(),
                                new SharedPrefsCookiePersistor(HelloMoneyCustomerApplication.getInstance()));
            }
            okHttpClientApplyCookie = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .build();
            return okHttpClientApplyCookie;
        }
    }
    public static ClearableCookieJar getCookieJar(){
        return cookieJar;
    }
    /*
      쿠키가 적용되지 않은 평범한 Okhttp3
     */
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
