package com.beta.tacademy.hellomoneycustomer.activity;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OkHttpInitSingtonManager;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager.getOkHttpNormalClient;

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
                getPermission();
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
                getPermission();
            }
        });

        viewPager.setAdapter(introFragmentPagerAdapter);
        introFragmentPagerAdapter.init();
    }

    private void getPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                CommonClass.saveIntro();
                CommonClass.saveUUID();
                new IdCheck().execute();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
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

            private class IdCheck extends AsyncTask<Void, Void, Integer> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected Integer doInBackground(Void... params) {
                    boolean flag;
                    Response response = null;
                    OkHttpClient toServer;
                    String msg = null;

                    try{
                        toServer = OKHttp3ApplyCookieManager.getOkHttpApplyCookieClient();

                        Request request = new Request.Builder()
                                .url(getResources().getString(R.string.check_id_and_registered_id_url)+CommonClass.getUUID())
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
                                msg = (String) jsonObject.get(getResources().getString(R.string.url_message));
                            }catch(JSONException jsone){
                                Log.e("json에러", jsone.toString());
                            }
                        }else{
                            return 2;
                        }
                    }catch (UnknownHostException une) {
                    } catch (UnsupportedEncodingException uee) {
                    } catch (Exception e) {
                    } finally{
                        if(response != null) {
                            response.close(); //3.* 이상에서는 반드시 닫아 준다.
                        }
                    }

                    if(msg.equals(getResources().getString(R.string.url_success))){
                        return 1;
                    }else{
                        return 2;
                    }
                }

                @Override
                protected void onPostExecute(Integer result) {
                    //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
                    if(result == 1){
                        Toast.makeText(IntroActivity.this,"아이디 등록 되어있음.",Toast.LENGTH_LONG).show();
                    }else if(result == 2){
                        new IdRegister().execute();
                    }else{
                        Toast.makeText(IntroActivity.this,"에러 아이디 체크에서 어딘가 걸림.",Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.GONE);
                }
            }

    private class IdRegister extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;
            String msg = null;

            try{
                toServer = OKHttp3ApplyCookieManager.getOkHttpApplyCookieClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("customerId", CommonClass.getUUID())
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
                        msg = (String) jsonObject.get(getResources().getString(R.string.url_message));
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return 2;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(msg.equals(getResources().getString(R.string.url_success))){
                return 1;
            }else{
                return 2;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            if(result == 1){
                Toast.makeText(IntroActivity.this,"아이디 등록 안되있어서 추가함.",Toast.LENGTH_LONG).show();
            }else if(result == 2){
                Toast.makeText(IntroActivity.this,"아이디 등록 안되있지만 추가 못함.",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(IntroActivity.this,"에러 아이디 등록에서 어딘가 걸림.",Toast.LENGTH_LONG).show();
            }
        }
    }

    /*
    private class IdCheckAndRegister extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        .url(getResources().getString(R.string.check_id_and_registered_id_url)+CommonClass.getUUID())
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
                            .add("customerId", CommonClass.getUUID())
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
                    return 3;
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            if(result == 1){
                Toast.makeText(IntroActivity.this,"아이디 등록 되어있음.",Toast.LENGTH_LONG).show();
            }else if(result == 2){
                Toast.makeText(IntroActivity.this,"아이디 등록 안되있어서 추가함.",Toast.LENGTH_LONG).show();
            }else if(result == 3){
                Toast.makeText(IntroActivity.this,"아이디 등록 안되있지만 추가 못함.",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(IntroActivity.this,"에러 어딘가 걸림.",Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
        }
    }
    */
}
