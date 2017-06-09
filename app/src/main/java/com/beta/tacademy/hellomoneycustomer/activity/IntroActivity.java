package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.module.httpConectionModule.OkHttpInitSingtonManager;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.beta.tacademy.hellomoneycustomer.R.id.progressBar;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView skip;
    private Button startButton;
    private ProgressBar progressBar;

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
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                new IdCheckAndRegister().execute();
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
                new IdCheckAndRegister().execute();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });

        viewPager.setAdapter(introFragmentPagerAdapter);
        introFragmentPagerAdapter.init();
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

    private class IdCheckAndRegister extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;
            String msg1 = null;
            String msg2 = null;

            try{
                toServer = OkHttpInitSingtonManager.getOkHttpClient();

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.check_id_and_registered_id_url)+getUUID())
                        .get()
                        .build();
                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                String returedJSON;

                if(flag){ //성공했다면
                    returedJSON = response.body().string();
                    Log.e("resultJSON", returedJSON);
                    try {
                        JSONObject jsonObject = new JSONObject(returedJSON);
                        msg1 = (String) jsonObject.get("msg");
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(msg1.equals("success")){
                return 1;
            }else{
                try{
                    toServer = OkHttpInitSingtonManager.getOkHttpClient();

                    RequestBody postBody = new FormBody.Builder()
                            .add("customerId", getUUID())
                            .add("fcmToken", FirebaseInstanceId.getInstance().getToken())
                            .build();

                    Request request = new Request.Builder()
                            .url(getResources().getString(R.string.check_id_and_registered_id_url))
                            .post(postBody)
                            .build();
                    //동기 방식
                    response = toServer.newCall(request).execute();

                    flag = response.isSuccessful();
                    String returedJSON;
                    if( flag ){ //성공했다면
                        returedJSON = response.body().string();
                        Log.e("resultJSON", returedJSON);
                        try {
                            JSONObject jsonObject = new JSONObject(returedJSON);
                            msg2 = (String) jsonObject.get("msg");
                        }catch(JSONException jsone){
                            Log.e("json에러", jsone.toString());
                        }
                    }else{
                    }
                }catch (UnknownHostException une) {
                } catch (UnsupportedEncodingException uee) {
                } catch (Exception e) {
                } finally{
                    if(response != null) {
                        response.close(); //3.* 이상에서는 반드시 닫아 준다.
                    }
                }

                if(msg2.equals("success")){
                    return 2;
                }else{
                    return 4;
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            if(result == 1){
                Toast.makeText(IntroActivity.this,"아이디 등록 되어있음",Toast.LENGTH_LONG).show();
            }else if(result == 2){
                Toast.makeText(IntroActivity.this,"아이디 등록 안되있어서 추가함",Toast.LENGTH_LONG).show();
            }else if(result == 3){
                Toast.makeText(IntroActivity.this,"아이디 등록 안되있어서 추가함",Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    private String getUUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getString("UUID",null);
    }
}
