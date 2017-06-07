package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;

import java.util.UUID;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView skip;
    private Button startButton;

    private IntroFragmentPagerAdapter introFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = (ViewPager) findViewById(R.id.introViewPager);
        tabLayout = (TabLayout) findViewById(R.id.introTab);
        introFragmentPagerAdapter = new IntroFragmentPagerAdapter(getSupportFragmentManager());
        skip = (TextView) findViewById(R.id.skip);
        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    tabLayout.setVisibility(TabLayout.INVISIBLE);
                    startButton.setVisibility(View.VISIBLE);
                } else {
                    tabLayout.setVisibility(TabLayout.VISIBLE);
                    startButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });

        viewPager.setAdapter(introFragmentPagerAdapter);
        introFragmentPagerAdapter.init();
    }

    // 값 저장하기
    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("helloMoney", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getString("UUID",null) == null){
            final TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            editor.putString("UUID", deviceId);
        }

        editor.putBoolean("beenIntro", true);
        editor.apply();
    }
}
