package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OkHttpInitSingtonManager;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;

import org.json.JSONArray;
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

public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ListView naviList;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private View naviHeader;
    private MainRecyclerViewAdapter mainRecyclerViewAdapter;
    private RelativeLayout relativeLayoutOne;
    private RelativeLayout relativeLayoutTwo;


    private long backPressedTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        naviList = (ListView)findViewById(R.id.navi_drawer);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        naviHeader = getLayoutInflater().inflate(R.layout.main_navi_header, naviList, false);

        relativeLayoutOne = (RelativeLayout) naviHeader.findViewById(R.id.relativeLayoutOne);
        relativeLayoutTwo = (RelativeLayout) naviHeader.findViewById(R.id.relativeLayoutTwo);
        //Toolbar

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //ActionBarDrawerToggle

        //ActionBarDrawerToggle 초기화 및 싱크 설정
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        naviList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[]{getString(R.string.how_to),getString(R.string.faq),getString(R.string.contact)}));
        naviList.setOnItemClickListener(new DrawerItemClickListener());
        naviList.setClickable(false);

        naviList.addHeaderView(naviHeader);

        //SwipeRefreshLayout

        refreshLayout.setColorSchemeResources(R.color.progress);  //SwipeRefreshLayout 색상 설정

        refreshLayout.setEnabled(false); //초기에는 SwipeRefreshLayout 비활성화

        //SwipeRefreshLayout refresh 시 이벤트 설정.
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //mainRecyclerViewAdapter.updateImage();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        relativeLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyQuotationActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
                drawer.closeDrawer(naviList);
            }
        });

        relativeLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyQuotationActivity.class);
                intent.putExtra("page",1);
                startActivity(intent);
                drawer.closeDrawer(naviList);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화

        //RecyclerView에 LayoutManager 설정 및 adapter 설정

        recyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this.getSupportFragmentManager(),1);
        recyclerView.setAdapter(mainRecyclerViewAdapter);

        //new MainAsyncTask().execute(); //
        //new MainAsyncTask2().execute(); //
        //new MainAsyncTask3().execute(); //
        //new MainAsyncTask4().execute(); //상세보기
        //new MainAsyncTask5().execute();//상담 요청
        new MainAsyncTask6().execute(); //후기작성
        //new MainAsyncTask7().execute();
        //new MainAsyncTask8().execute();
        //new MainAsyncTask9().execute();
    }

    @Override
    public void onStart(){
        super.onStart();

    }
    //back 버튼 클릭 시 이벤트 설정.
    @Override
    public void onBackPressed() {
        final long FINSH_INTERVAL_TIME = 2000;
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - backPressedTime;

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = currentTime;
                Toast.makeText(getApplicationContext(), "'뒤로' 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //http 통신으로 HTML을 받아와 원하는 정보만을 파싱하여 저장해두는 AsyncTask
    private class MainAsyncTask extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            boolean flag2;
            Response response1 = null;
            Response response2 = null;
            OkHttpClient toServer1;
            OkHttpClient toServer2;

            JSONObject jsonObject1 = null;
            JSONObject jsonObject2 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();
                toServer2 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_request_quotation_url), CommonClass.getUUID()))
                        .get()
                        .build();

                Request request2 = new Request.Builder()
                        .url(getResources().getString(R.string.get_post_script_url))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();
                response2 = toServer2.newCall(request2).execute();

                flag1 = response1.isSuccessful();
                flag2 = response1.isSuccessful();

                String returedJSON1;
                String returedJSON2;

                if(flag1 && flag2){ //성공했다면
                    returedJSON1 = response1.body().string();
                    returedJSON2 = response2.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                        jsonObject2 = new JSONObject(returedJSON2);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }

                if(response2 != null){
                    response2.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null && jsonObject2 != null){
                try {
                    if(jsonObject1.get("msg").equals("success") && jsonObject2.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        JSONArray data2= jsonObject2.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        tmp.add(data2);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 목록");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("1           " + i + "                   "+ String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                new WebHook().execute("후기 목록");
                for(int i = 0 ; i < result.get(1).length(); i++){
                    try {
                        new WebHook().execute("2           " + i + "                   " + String.valueOf(result.get(1).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    //http 통신으로 HTML을 받아와 원하는 정보만을 파싱하여 저장해두는 AsyncTask
    private class MainAsyncTask2 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_quotation_list_url), "1"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("3           " + i + "                   "+ String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask3 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_quotation_detail_url),"1"))
                        .get()
                        .build();
                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 상세");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("4           " + i + "                   "+ String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask4 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.quotation_detail_counselor_feedback_url),"1"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 상담 피드백 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("5           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }


    private class MainAsyncTask5 extends AsyncTask<Void, Void, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("selectedEstimateId","7")
                        .add("status","상담중")
                        .build();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.request_counsel_url),"1"))
                        .put(postBody)
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        //JSONArray data1= jsonObject1.getJSONArray("data");
                        //ArrayList<JSONArray> tmp = new ArrayList();
                        //tmp.add(data1);
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == 0){
                new WebHook().execute("안옴");
            }else{
                new WebHook().execute("상담 신청하기 성공");
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask6 extends AsyncTask<Void, Void, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("requestId","1")
                        .add("content","하하")
                        .add("score","4.5")
                        .build();

                Request request1 = new Request.Builder()
                        .url(getResources().getString(R.string.write_post_script_url))
                        .put(postBody)
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        //JSONArray data1= jsonObject1.getJSONArray("data");
                        //ArrayList<JSONArray> tmp = new ArrayList();
                        //tmp.add(data1);
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == 0){
                new WebHook().execute("안옴");
            }else{
                new WebHook().execute("후기 작성 성공");
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask7 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.detail_counselor_url),"agent1@naver.com"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("대출 모집인 상세");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("6           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask8 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.detail_counselor_post_script_list_url),"agent1@naver.com"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("대출 모집인 후기 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("7           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }


    private class MainAsyncTask9 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(getResources().getString(R.string.faq_url))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("faq 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("8           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {


            switch (position) {
                case 1:
                    startActivity(new Intent(MainActivity.this,OperationGuideActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this,FAQActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this,ContactActivity.class));
                    break;
            }

            drawer.closeDrawer(naviList);
        }
    }

    //RecyclerView에 해더 추가
    public void addHeaders(){
        mainRecyclerViewAdapter.initHeader();
    }

    //RecyclerView에 아이템 추가
    public void addItems(){
        for(int i = 0 ; i< 20; i++) {
            if(i%2 == 0){
                mainRecyclerViewAdapter.addItem(new MainValueObject(i,1,"1시간 전","서울시","동작구","장항동","무슨 아파트",3,"대출 모집인이 겁나 좋아조아무너웜누어ㅜ 안좋아 졸려",25));
            }else{
                mainRecyclerViewAdapter.addItem(new MainValueObject(i,0,"1시간 전","서울시","동작구","장항동","무슨 아파트",3,"대출 모집인이 겁나 좋아조아무너웜누어ㅜ 안좋아 졸려",25));
            }
        }
    }
}
