package com.beta.tacademy.hellomoneycustomer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OkHttpInitSingtonManager;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailHeaderObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager.MyQuotationFragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuotationDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private QuotationDetailRecyclerViewAdapter quotationDetailRecyclerViewAdapter;
    private ProgressBar progressBar;
    private int quotationDetailId;
    private QuotationDetailHeaderObject quotationDetailHeaderObject;
    private ArrayList<QuotationDetailObject> quotationDetailObjectArrayList;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_detail);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        quotationDetailObjectArrayList = new ArrayList<>();

        activity = this;
        Intent intent = getIntent();
        quotationDetailId = intent.getIntExtra("id",-1);
        if(quotationDetailId == -1){
            Toast.makeText(getApplicationContext(), "인텐트 안들어옴", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "id = " +quotationDetailId + " 들어옴", Toast.LENGTH_SHORT).show();
        }

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.quotation_detail));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화

        recyclerView.setLayoutManager(linearLayoutManager);

        new QuotationDetail().execute();
        //addItems();
   }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class QuotationDetail extends AsyncTask<Void, Void, Integer> {
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
                        .url(String.format(getResources().getString(R.string.my_quotation_detail_url),String.valueOf(quotationDetailId)))
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
                        JSONObject data= jsonObject.getJSONObject(getResources().getString(R.string.url_data));

                        quotationDetailHeaderObject = new QuotationDetailHeaderObject(data.getInt("request_id"),data.getString("status"),data.getString("register_time"),data.getString("loan_type"),data.getString("region_1"),data.getString("region_2"),data.getString("region_3"),data.getString("apt_name"),data.getString("apt_size_supply") + "(" + data.getString("apt_size_exclusive") +"m2)",data.getInt("loan_amount"),data.getString("interest_rate_type"),data.getString("scheduled_time"),data.getString("job_type"),data.getString("register_number"));
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
                if(quotationDetailHeaderObject.getOngoingStatus().equals("대출실행완료")){
                    quotationDetailRecyclerViewAdapter = new QuotationDetailRecyclerViewAdapter(activity,QuotationDetailRecyclerViewAdapter.YES_WRITE_COMMENT,quotationDetailHeaderObject);
                    recyclerView.setAdapter(quotationDetailRecyclerViewAdapter);
                }else{
                    //quotationDetailRecyclerViewAdapter = new QuotationDetailRecyclerViewAdapter(activity,QuotationDetailRecyclerViewAdapter.YES_WRITE_COMMENT,quotationDetailHeaderObject);

                    //quotationDetailRecyclerViewAdapter = new QuotationDetailRecyclerViewAdapter(activity,QuotationDetailRecyclerViewAdapter.NO_WRITE_COMMENT,quotationDetailHeaderObject);
                    recyclerView.setAdapter(quotationDetailRecyclerViewAdapter);
                }

                new QuotationFeedback().execute();
            }else{
                new WebHook().execute("MyQuotationActivity 내 견적 목록 안옴 result ===== " + result);
            }

            progressBar.setVisibility(View.GONE);
        }
    }

    private class QuotationFeedback extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;

            JSONObject jsonObject = null;

            try{
                toServer = OkHttpInitSingtonManager.getOkHttpClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.quotation_detail_counselor_feedback_url),String.valueOf(quotationDetailId)))
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
                                quotationDetailObjectArrayList.add(new QuotationDetailObject(jsonData.getInt("estimate_id"),jsonData.getString("item_bank"),jsonData.getString("name"),jsonData.getString("interest_rate_type"),jsonData.getDouble("interest_rate"),jsonData.getString("photo")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

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
            if(result == 0){
                quotationDetailRecyclerViewAdapter.initItem(quotationDetailObjectArrayList);
            }else if(result == 1){

            }else{
                new WebHook().execute("MyQuotationActivity 내 견적 목록 안옴 result ===== " + result);
            }
        }
    }
}
