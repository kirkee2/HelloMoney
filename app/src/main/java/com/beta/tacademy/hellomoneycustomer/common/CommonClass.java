package com.beta.tacademy.hellomoneycustomer.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;

import java.util.UUID;

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

}
