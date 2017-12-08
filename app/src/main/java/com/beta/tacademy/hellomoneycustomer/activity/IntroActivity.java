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
import com.beta.tacademy.hellomoneycustomer.common.util.SharedReferenceUtil;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager.NETWORK_FAIL;
import static com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager.NETWORK_ID_NOT_REGISTERED;
import static com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager.NETWORK_SUCCESS;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView skip;
    private Button startButton;
    private ProgressBar progressBar;

    private IntroFragmentPagerAdapter introFragmentPagerAdapter;

    private PermissionListener permissionlistener;

    private IsIdRegistered isIdRegistered;
    private IsFCMRegistered isFCMRegistered;
    private RegisterId registerId;

    String checkFCMToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        initView();
        initVariable();
        initListener();
        initNetwork();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.introViewPager);
        tabLayout = (TabLayout) findViewById(R.id.introTab);
        introFragmentPagerAdapter = new IntroFragmentPagerAdapter(getSupportFragmentManager());
        skip = (TextView) findViewById(R.id.skip);
        startButton = (Button) findViewById(R.id.startButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setAdapter(introFragmentPagerAdapter);
    }

    private void initListener() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == introFragmentPagerAdapter.getCount() - 1) {
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

        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (SharedReferenceUtil.saveUUID()) {
                    isIdRegistered.execute();
                } else {
                    Toast.makeText(getApplication(), "진행중 오류가 발생했습니다. 다시 한번 시도해주세요.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startButton.setEnabled(true);
                    skip.setEnabled(true);
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplication(), "권한을 설정하지 않으시면 앱을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                startButton.setEnabled(true);
                skip.setEnabled(true);
            }
        };
    }

    private void initVariable() {
        introFragmentPagerAdapter.init();
    }

    private void initNetwork() {
        isIdRegistered = new IsIdRegistered();
        isFCMRegistered = new IsFCMRegistered();
        registerId = new RegisterId();
    }

    private void getPermission() {
        progressBar.setVisibility(View.VISIBLE);
        startButton.setEnabled(false);
        skip.setEnabled(false);

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한을 허용하지 않으면 서비스를 사용하실 수 없습니다.\n설정에서 권한을 허용해주세요.")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();
    }

    private class IsIdRegistered extends AsyncTask<Void, Void, Integer> {
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
            JSONObject jsonObject;

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.check_id_url), SharedReferenceUtil.getUUID()))
                        .get()
                        .build();

                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                String returedJSON;

                if (flag) {
                    returedJSON = response.body().string();

                    jsonObject = new JSONObject(returedJSON);
                } else {
                    return NETWORK_FAIL;
                }

                if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))) {
                    checkFCMToken = jsonObject.getJSONObject("data").getString("fcm_token");

                    return NETWORK_SUCCESS;
                } else {
                    return NETWORK_ID_NOT_REGISTERED;
                }
            } catch (Exception e) {
                return NETWORK_FAIL;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == NETWORK_SUCCESS) {
                if (checkFCMToken.equals(FirebaseInstanceId.getInstance().getToken())) {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    SharedReferenceUtil.saveIntro();
                    finish();
                } else {
                    isFCMRegistered.execute();
                }
            } else if (result == NETWORK_ID_NOT_REGISTERED) {
                registerId.execute();
            } else {
                Toast.makeText(IntroActivity.this, "네트워크 처리 도중 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
        }
    }

    private class IsFCMRegistered extends AsyncTask<Void, Void, Integer> {
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
            JSONObject jsonObject;

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("fcmToken", FirebaseInstanceId.getInstance().getToken())
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.check_id_url), SharedReferenceUtil.getUUID()))
                        .put(postBody)
                        .build();

                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                String returedJSON;

                if (flag) {
                    returedJSON = response.body().string();
                    jsonObject = new JSONObject(returedJSON);
                } else {
                    return NETWORK_FAIL;
                }

                if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))) {
                    return NETWORK_SUCCESS;
                } else {
                    return NETWORK_FAIL;
                }
            } catch (Exception e) {
                return NETWORK_FAIL;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == NETWORK_SUCCESS) {
                SharedReferenceUtil.saveIntro();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(IntroActivity.this, "네트워크 처리 도중 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
        }
    }


    private class RegisterId extends AsyncTask<Void, Void, Integer> {
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
            String msg;

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("customerId", SharedReferenceUtil.getUUID())
                        .add("fcmToken", FirebaseInstanceId.getInstance().getToken())
                        .build();

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.registered_id_url))
                        .post(postBody)
                        .build();
                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                String returedJSON;

                if (flag) { //성공했다면
                    returedJSON = response.body().string();
                    Log.e("resultJSON", returedJSON);
                    JSONObject jsonObject = new JSONObject(returedJSON);
                    msg = (String) jsonObject.get(getResources().getString(R.string.url_message));
                } else {
                    return NETWORK_FAIL;
                }

                if (msg.equals(getResources().getString(R.string.url_success))) {
                    return NETWORK_SUCCESS;
                } else {
                    return NETWORK_FAIL;
                }
            } catch (Exception e) {
                return NETWORK_FAIL;
            } finally {
                if (response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == NETWORK_SUCCESS) {
                SharedReferenceUtil.saveIntro();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(IntroActivity.this, "네트워크 처리 도중 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopNetWork();
    }

    private void stopNetWork() {
        if (isIdRegistered.getStatus() == AsyncTask.Status.RUNNING) {
            isIdRegistered.cancel(true);
        }

        if (isFCMRegistered.getStatus() == AsyncTask.Status.RUNNING) {
            isFCMRegistered.cancel(true);
        }

        if (registerId.getStatus() == AsyncTask.Status.RUNNING) {
            registerId.cancel(true);
        }
    }
}
