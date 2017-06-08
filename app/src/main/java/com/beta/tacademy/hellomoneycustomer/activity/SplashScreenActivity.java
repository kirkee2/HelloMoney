package com.beta.tacademy.hellomoneycustomer.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

import com.beta.tacademy.hellomoneycustomer.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.UUID;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getPermission();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 값 불러오기
    private boolean getPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getBoolean("beenIntro",false);
    }

    private void saveUUID() {
        final TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        SharedPreferences sharedPreferences = getSharedPreferences("helloMoney", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UUID", deviceId);
        editor.apply();
    }

    private void getPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                saveUUID();

                if(getPreferences()){
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }else{
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                            finish();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                getPermission();
            }


        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();
    }
}
