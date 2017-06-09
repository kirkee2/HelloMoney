package com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kirkee on 2017. 6. 8..
 */


public class HttpConnectionManager {

    public static HttpURLConnection getHttpURLConnection(String targetURL) {
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(targetURL);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(10000);
            //httpConnection.setRequestMethod("POST");
            //GET은 디폴트 이다
            httpConnection.setRequestMethod("GET");

        } catch (Exception e) {
            Log.e("DEBUG_TAG", "getHttpURLConnection() -- 에러 발생 -- ", e);
        }
        return httpConnection;
    }

    public static HttpURLConnection postHttpURLConnection(String targetURL) {
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(targetURL);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(10000);
            httpConnection.setRequestMethod("POST");
        } catch (Exception e) {
            Log.e("DEBUG_TAG", "getHttpURLConnection() -- 에러 발생 -- ", e);
        }
        return httpConnection;
    }

    public static HttpURLConnection putHttpURLConnection(String targetURL) {
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(targetURL);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(10000);
            httpConnection.setRequestMethod("PUT");
        } catch (Exception e) {
            Log.e("DEBUG_TAG", "getHttpURLConnection() -- 에러 발생 -- ", e);
        }
        return httpConnection;
    }

    public static HttpURLConnection deleteHttpURLConnection(String targetURL) {
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(targetURL);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(10000);
            httpConnection.setRequestMethod("DELETE");
        } catch (Exception e) {
            Log.e("DEBUG_TAG", "getHttpURLConnection() -- 에러 발생 -- ", e);
        }
        return httpConnection;
    }
}
