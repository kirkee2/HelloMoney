package com.beta.tacademy.hellomoneycustomer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.util.SharedReferenceUtil;
import com.beta.tacademy.hellomoneycustomer.listView.mainNavi.MainNaviAdapter;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.module.listener.EndlessScrollListener;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager.NETWORK_FAIL;
import static com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager.NETWORK_SUCCESS;

public class MainActivity extends AppCompatActivity implements EndlessScrollListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ListView naviList;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private View naviHeader;
    private MainNaviAdapter mainNaviAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MainRecyclerViewAdapter mainRecyclerViewAdapter;
    private ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList;
    private ArrayList<MainValueObject> mainValueObjectArrayList;
    private RelativeLayout relativeLayoutOne;
    private RelativeLayout relativeLayoutTwo;
    private FragmentManager fragmentManager;
    private Activity activity;
    private ActionBarDrawerToggle toggle;

    private int uncompletedCount;
    private int completedCount;
    private int position;
    private int endlessPosition;
    private boolean hasQuotation;

    private PostscriptList postscriptList;
    private MyQuotationOngoingDoneCount myQuotationOngoingDoneCount;
    private MyQuotationList myQuotationList;
    private PostscriptListUpdate postscriptListUpdate;
    private MyQuotationUpdateList myQuotationUpdateList;

    private TextView myOngoingQuotation;
    private TextView myDoneQuotation;
    private int recyclerViewPosition;

    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initVariable();
        initListener();
        initNetwork();

        if (myQuotationList.getStatus() == AsyncTask.Status.FINISHED) {
            myQuotationList = new MyQuotationList();
        }

        basicNetworkStart();
    }

    public void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        naviList = (ListView) findViewById(R.id.navi_drawer);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        naviHeader = getLayoutInflater().inflate(R.layout.main_navi_header, naviList, false);

        relativeLayoutOne = (RelativeLayout) naviHeader.findViewById(R.id.relativeLayoutOne);
        relativeLayoutTwo = (RelativeLayout) naviHeader.findViewById(R.id.relativeLayoutTwo);
        myOngoingQuotation = (TextView) naviHeader.findViewById(R.id.myOngoingQuotation);
        myDoneQuotation = (TextView) naviHeader.findViewById(R.id.myDoneQuotation);

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정

        //Toolbar 설정
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void initVariable() {
        uncompletedCount = 0;
        completedCount = 0;
        position = 0;
        endlessPosition = 0;
        hasQuotation = false;

        activity = this;
        fragmentManager = this.getSupportFragmentManager();

        mainPageViewPagerObjectArrayList = new ArrayList<>();
        mainValueObjectArrayList = new ArrayList<>();

        mainNaviAdapter = new MainNaviAdapter();

        mainNaviAdapter.addItem(getString(R.string.how_to));
        mainNaviAdapter.addItem(getString(R.string.faq));
        mainNaviAdapter.addItem(getString(R.string.contact));
        naviList.setAdapter(mainNaviAdapter);
        naviList.setClickable(false);
        naviList.addHeaderView(naviHeader);

        refreshLayout.setColorSchemeResources(R.color.progress);

        refreshLayout.setEnabled(false);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false); //RecyclerView에 설정 할 LayoutManager 초기화

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endlessPosition = 0;
                        mainPageViewPagerObjectArrayList = new ArrayList<>();
                        mainValueObjectArrayList = new ArrayList<>();

                        basicNetworkStart();
                    }
                }, 1500);
            }
        });

        relativeLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyQuotationActivity.class);
                intent.putExtra("page", 0);
                intent.putExtra("recyclerViewPosition", linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);

                startActivity(intent);

                //startActivityForResult(intent, 1);

                drawer.closeDrawer(naviList);
            }
        });

        relativeLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyQuotationActivity.class);
                intent.putExtra("page", 1);
                intent.putExtra("recyclerViewPosition", linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);

                startActivity(intent);

                //startActivityForResult(intent, 1);
                drawer.closeDrawer(naviList);
            }
        });

        naviList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void initNetwork() {
        postscriptList = new PostscriptList();
        myQuotationOngoingDoneCount = new MyQuotationOngoingDoneCount();
        myQuotationList = new MyQuotationList();
        postscriptListUpdate = new PostscriptListUpdate();
        myQuotationUpdateList = new MyQuotationUpdateList();
    }

    public void update(int position) {
        endlessPosition = 0;

        this.position = position;
        mainPageViewPagerObjectArrayList = new ArrayList<>();
        mainValueObjectArrayList = new ArrayList<>();

        myQuotationList = new MyQuotationList();
        myQuotationList.execute();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(myQuotationList.getStatus() != AsyncTask.Status.RUNNING){
            mainPageViewPagerObjectArrayList = new ArrayList<>();

            if(myQuotationUpdateList.getStatus() == AsyncTask.Status.FINISHED){
                myQuotationUpdateList = new MyQuotationUpdateList();
            }

            myQuotationUpdateList.execute();
        }
    }

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

    @Override
    public boolean onLoadMore(int position) {
        endlessPosition++;
        postscriptListUpdate = new PostscriptListUpdate();
        postscriptListUpdate.execute();
        return true;
    }

    private class MyQuotationList extends AsyncTask<Void, Void, Integer> {
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

            JSONObject jsonObject = null;

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_request_quotation_url), SharedReferenceUtil.getUUID(), "true"))
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
                    JSONArray data = jsonObject.getJSONArray(getResources().getString(R.string.url_data));

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonData = (JSONObject) data.get(i);
                        mainPageViewPagerObjectArrayList.add(new MainPageViewPagerObject(jsonData.getInt("request_id"), jsonData.getString("status"), jsonData.getString("loan_type"), jsonData.getString("end_time"), jsonData.getString("region_1"), jsonData.getString("region_2"), jsonData.getString("region_3"), jsonData.getString("apt_name"), jsonData.getString("apt_size_supply") + "(" + jsonData.getString("apt_size_exclusive") + ")", jsonData.getInt("estimate_count")));
                    }

                    hasQuotation = true;
                    return NETWORK_SUCCESS;
                } else if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))) {
                    hasQuotation = false;
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

                if (hasQuotation) {
                    mainRecyclerViewAdapter = new MainRecyclerViewAdapter(activity, fragmentManager, MainRecyclerViewAdapter.YES_MY_QUOTATION);
                    mainRecyclerViewAdapter.initHeader(mainPageViewPagerObjectArrayList, position);
                } else {
                    mainRecyclerViewAdapter = new MainRecyclerViewAdapter(activity, fragmentManager, MainRecyclerViewAdapter.NO_MY_QUOTATION);
                }

                recyclerView.setAdapter(mainRecyclerViewAdapter);

                if (myQuotationOngoingDoneCount.getStatus() == Status.FINISHED) {
                    myQuotationOngoingDoneCount = new MyQuotationOngoingDoneCount();
                }
            } else {
                Toast.makeText(MainActivity.this, "네트워크 처리 도중 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
        }
    }

    private class MyQuotationUpdateList extends AsyncTask<Void, Void, Integer> {
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

            JSONObject jsonObject = null;

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_request_quotation_url), SharedReferenceUtil.getUUID(), "true"))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if (flag) { //성공했다면
                    returedJSON = response.body().string();

                    try {
                        jsonObject = new JSONObject(returedJSON);
                    } catch (JSONException jsone) {
                        Log.e("json에러", jsone.toString());
                    }
                } else {
                    return 2;
                }
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if (jsonObject != null) {
                try {
                    if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))) {
                        JSONArray data = jsonObject.getJSONArray(getResources().getString(R.string.url_data));

                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject jsonData = (JSONObject) data.get(i);
                                mainPageViewPagerObjectArrayList.add(new MainPageViewPagerObject(jsonData.getInt("request_id"), jsonData.getString("status"), jsonData.getString("loan_type"), jsonData.getString("end_time"), jsonData.getString("region_1"), jsonData.getString("region_2"), jsonData.getString("region_3"), jsonData.getString("apt_name"), jsonData.getString("apt_size_supply") + "(" + jsonData.getString("apt_size_exclusive") + ")", jsonData.getInt("estimate_count")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return 0;
                    } else if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))) {
                        return 1;
                    } else {
                        return 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return 4;
            }

            return 5;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                mainRecyclerViewAdapter.initHeader(mainPageViewPagerObjectArrayList, position);
            } else if (result == 1) {
            } else {
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    private class MyQuotationOngoingDoneCount extends AsyncTask<Void, Void, Integer> {
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

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_request_quotation_ongoing_done_url), SharedReferenceUtil.getUUID()))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if (flag) { //성공했다면
                    returedJSON = response.body().string();
                    jsonObject = new JSONObject(returedJSON);
                } else {
                    return 2;
                }
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            try {
                if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))) {
                    JSONObject data = jsonObject.getJSONObject(getResources().getString(R.string.url_data));
                    completedCount = data.optInt("completed_count");
                    uncompletedCount = data.optInt("uncompleted_count");
                    return 0;
                } else {
                    return 1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return 3;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            postscriptList = new PostscriptList();
            if (result == 0 || result == 1) {
                myOngoingQuotation.setText(String.valueOf(uncompletedCount));
                myDoneQuotation.setText(String.valueOf(completedCount));
            } else {
            }
        }
    }

    private class PostscriptList extends AsyncTask<Void, Void, Integer> {
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

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.get_post_script_url), String.valueOf(endlessPosition)))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if (flag) { //성공했다면
                    returedJSON = response.body().string();

                    try {
                        jsonObject = new JSONObject(returedJSON);
                    } catch (JSONException jsone) {
                        Log.e("json에러", jsone.toString());
                    }
                } else {
                    return 2;
                }
            } catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if (jsonObject != null) {
                try {
                    if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))) {
                        JSONArray data = jsonObject.getJSONArray(getResources().getString(R.string.url_data));

                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject jsonData = (JSONObject) data.get(i);
                                mainValueObjectArrayList.add(new MainValueObject((int) jsonData.get("review_id"), jsonData.getString("loan_type"), jsonData.getString("register_time"), jsonData.getString("region_1"), jsonData.getString("region_2"), jsonData.getString("region_3"), jsonData.getString("apt_name"), jsonData.getInt("score"), jsonData.getString("content"), jsonData.getDouble("benefit")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return 0;
                    } else if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))) {
                        return 1;
                    } else {
                        return 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 4;
                }
            } else {
                return 5;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 0 || result == 1) {
                mainRecyclerViewAdapter.initItem(mainValueObjectArrayList);

                refreshLayout.setEnabled(true);

                toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                recyclerViewPosition = 0;
                refreshLayout.setRefreshing(false);

                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else {
            }

            progressBar.setVisibility(View.GONE);

        }
    }

    private class PostscriptListUpdate extends AsyncTask<Void, Void, Integer> {
        ArrayList<MainValueObject> tmpMainValueObjectArrayList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tmpMainValueObjectArrayList = new ArrayList<>();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;

            JSONObject jsonObject = null;

            try {
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.get_post_script_url), String.valueOf(endlessPosition)))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if (flag) { //성공했다면
                    returedJSON = response.body().string();

                    try {
                        jsonObject = new JSONObject(returedJSON);
                    } catch (JSONException jsone) {
                        Log.e("json에러", jsone.toString());
                    }
                } else {
                    return 2;
                }
            } catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if (jsonObject != null) {
                try {
                    if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))) {
                        JSONArray data = jsonObject.getJSONArray(getResources().getString(R.string.url_data));

                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject jsonData = (JSONObject) data.get(i);
                                tmpMainValueObjectArrayList.add(new MainValueObject((int) jsonData.get("review_id"), jsonData.getString("loan_type"), jsonData.getString("register_time"), jsonData.getString("region_1"), jsonData.getString("region_2"), jsonData.getString("region_3"), jsonData.getString("apt_name"), jsonData.getInt("score"), jsonData.getString("content"), jsonData.getDouble("benefit")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return 0;
                    } else if (jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))) {
                        return 1;
                    } else {
                        return 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 4;
                }
            } else {
                return 5;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                mainRecyclerViewAdapter.updateItem(tmpMainValueObjectArrayList);
                progressBar.setVisibility(View.GONE);
            } else if (result == 1) {
                endlessPosition--;
                progressBar.setVisibility(View.GONE);
            } else {
                endlessPosition--;
            }
        }
    }

    public void basicNetworkStart(){
        if(myQuotationList.getStatus() == AsyncTask.Status.FINISHED){
            myQuotationList = new MyQuotationList();
        }
        myQuotationList.execute();

        if(postscriptList.getStatus() == AsyncTask.Status.FINISHED){
            postscriptList = new PostscriptList();
        }
        postscriptList.execute();

        if(myQuotationOngoingDoneCount.getStatus() == AsyncTask.Status.FINISHED){
            myQuotationOngoingDoneCount = new MyQuotationOngoingDoneCount();
        }
        myQuotationOngoingDoneCount.execute();
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            switch (position) {
                case 1:
                    startActivity(new Intent(MainActivity.this, OperationGuideActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, FAQActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, ContactActivity.class));
                    break;
            }
            drawer.closeDrawer(naviList);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      /*  if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                update(data.getIntExtra("position", 0));
            } else if (requestCode == 2) {
                //updateMyQuotation();
            } else {
                if (!data.getBooleanExtra("write", false)) {
                    //updateMyQuotation();
                } else {
                    update(data.getIntExtra("position", 0));
                }
            }
        }*/
    }


    @Override
    protected void onStop() {
        super.onStop();

        stopNetWork();

        mainRecyclerViewAdapter.clear();
    }


    private void stopNetWork() {

        if (postscriptList.getStatus() == AsyncTask.Status.RUNNING) {
            postscriptList.cancel(true);
        }

        if (myQuotationOngoingDoneCount.getStatus() == AsyncTask.Status.RUNNING) {
            myQuotationOngoingDoneCount.cancel(true);
        }

        if (myQuotationList.getStatus() == AsyncTask.Status.RUNNING) {
            myQuotationList.cancel(true);
        }

        if (postscriptListUpdate.getStatus() == AsyncTask.Status.RUNNING) {
            postscriptListUpdate.cancel(true);
        }
    }
}
