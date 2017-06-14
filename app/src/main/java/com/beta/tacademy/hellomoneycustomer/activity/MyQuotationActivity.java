package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager.MyQuotationFragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyQuotationActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private MyQuotationFragmentPagerAdapter myQuotationFragmentPagerAdapter;
    private ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectOneM;
    private ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectTwoM;
    public Timer timer;
    public TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotation);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        mainPageViewPagerObjectOneM = new ArrayList<>();
        mainPageViewPagerObjectTwoM = new ArrayList<>();


        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.my_quotation));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        new MyQuotationList().execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        /*
        mainPageViewPagerObjectOneM = new ArrayList<>();
        mainPageViewPagerObjectTwoM = new ArrayList<>();

        new MyQuotationList().execute();
        */

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyQuotationList extends AsyncTask<Void, Void, Integer> {
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

            JSONObject jsonObject = null;

            try{
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_request_quotation_url), CommonClass.getUUID(),"false"))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if(flag){ //성공했다면
                    returedJSON = response.body().string();

                    try {
                        jsonObject = new JSONObject(returedJSON);
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

            if(jsonObject != null){
                try {
                    if(jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))){
                        JSONArray data= jsonObject.getJSONArray(getResources().getString(R.string.url_data));

                        for(int i = 0 ; i < data.length(); i++){
                            try {
                                JSONObject jsonData = (JSONObject)data.get(i);

                                if(!String.valueOf(jsonData.get("status")).equals("대출실행완료")){
                                    mainPageViewPagerObjectOneM.add(new MainPageViewPagerObject((int)jsonData.get("request_id"),String.valueOf(jsonData.get("status")),String.valueOf(jsonData.get("loan_type")),String.valueOf(jsonData.get("end_time")),String.valueOf(jsonData.get("region_1")),String.valueOf(jsonData.get("region_2")),String.valueOf(jsonData.get("region_3")),String.valueOf(jsonData.get("apt_name")),String.valueOf(jsonData.get("apt_size_supply") + "(" + jsonData.get("apt_size_exclusive") +"m2)"),(int)jsonData.get("estimate_count")));
                                }else{
                                    mainPageViewPagerObjectTwoM.add(new MainPageViewPagerObject((int)jsonData.get("request_id"),String.valueOf(jsonData.get("status")),String.valueOf(jsonData.get("loan_type")),String.valueOf(jsonData.get("end_time")),String.valueOf(jsonData.get("region_1")),String.valueOf(jsonData.get("region_2")),String.valueOf(jsonData.get("region_3")),String.valueOf(jsonData.get("apt_name")),String.valueOf(jsonData.get("apt_size_supply") + "(" + jsonData.get("apt_size_exclusive") +"m2)"),(int)jsonData.get("estimate_count")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //myQuotationFragmentPagerAdapter.init(mainPageViewPagerObjectOne,mainPageViewPagerObjectTwo);
                        return 0;
                    }else if(jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))){
                        return 1;
                    }else{
                        return 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                return 4;
            }

            return 5;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 0 || result == 1){
                myQuotationFragmentPagerAdapter = new MyQuotationFragmentPagerAdapter(getSupportFragmentManager(),mainPageViewPagerObjectOneM,mainPageViewPagerObjectTwoM);
                //myQuotationFragmentPagerAdapter.init(mainPageViewPagerObjectOneM,mainPageViewPagerObjectTwoM);

                viewPager.setAdapter(myQuotationFragmentPagerAdapter);
                tabLayout.setupWithViewPager(viewPager, true);


                Intent intent = getIntent();
                viewPager.setCurrentItem(intent.getIntExtra("page",0));

            }else{
            }

            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
    }
}
