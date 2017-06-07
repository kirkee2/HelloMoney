package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;

import java.io.IOException;
import java.util.ArrayList;

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

        Toast.makeText(getApplicationContext(), "UUID = " + getUUID(), Toast.LENGTH_SHORT).show();
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
        //LayoutManager

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화

        //RecyclerView에 LayoutManager 설정 및 adapter 설정

        recyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this.getSupportFragmentManager(),1);
        recyclerView.setAdapter(mainRecyclerViewAdapter);

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
        new MainAsyncTask().execute(); //HTML 파싱 수행
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
    private class MainAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //RecyclerView에 해더 및 아이템 추가
            addHeaders();
            addItems();

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

    private String getUUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getString("UUID",null);
    }
}
