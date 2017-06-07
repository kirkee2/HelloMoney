package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.beta.tacademy.hellomoneycustomer.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(getPreferences()){
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                    finish();
                }
            }
        }, 2000);
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
}
