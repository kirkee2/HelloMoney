package com.beta.tacademy.hellomoneycustomer.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OkHttpInitSingtonManager;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kirkee on 2017. 6. 8..
 */

public class CommonClass {
    public static String getUUID() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getString("UUID",null);
    }

    public static void saveUUID() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getString("UUID",null) == null){
            final TelephonyManager tm = (TelephonyManager) HelloMoneyCustomerApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, androidId;
            tmDevice = String.valueOf(tm.getDeviceId());
            tmSerial = String.valueOf(tm.getSimSerialNumber());
            androidId = String.valueOf(android.provider.Settings.Secure.getString(HelloMoneyCustomerApplication.getInstance().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            editor.putString("UUID", deviceId);
            editor.apply();
        }
    }

    public static void saveIntro(){
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.getBoolean("beenIntro",false)){
            editor.putBoolean("beenIntro", true);
            editor.apply();
        }
    }
    public static boolean getIntro() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getBoolean("beenIntro",false);
    }

    public static String timeParsing(String time){
        TimeZone utc = TimeZone.getTimeZone("UTC");

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일");
        transFormat.setTimeZone(utc);
        dateFormat.setTimeZone(utc);


        try {
            Date toDate = transFormat.parse(time);
            String pastTimeInfo = dateFormat.format(toDate.getTime());
            return pastTimeInfo;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String timeDashParsing(String time){
        TimeZone utc = TimeZone.getTimeZone("UTC");

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        transFormat.setTimeZone(utc);
        dateFormat.setTimeZone(utc);


        try {
            Date toDate = transFormat.parse(time);
            String pastTimeInfo = dateFormat.format(toDate.getTime());
            return pastTimeInfo;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int timeLeftSecondParsing(String time){
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
        transFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date endDate = transFormat.parse(time);

            long endTime = endDate.getTime(); //현재의 시간 설정

            Calendar cal = Calendar.getInstance();
            Date startDate=cal.getTime();

            long startTime=startDate.getTime();

            long mills=endTime-startTime; //분으로 변환

            //long hour=mills/3600000;
            //long min=mills/60000;
            int second= (int)mills/1000;

            return second;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String formatNumber2(int num){
        return String.format("%02d",num);
    }

    public static String formatMoney(int num){
        return String.format("%,d", num);
    }

    public static String formatPhoneNumber(String phoneNumber) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

        if(!Pattern.matches(regEx, phoneNumber)) return null;

        return phoneNumber.replaceAll(regEx, "$1-$2-$3");

    }


}
