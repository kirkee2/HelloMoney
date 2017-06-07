package com.beta.tacademy.hellomoneycustomer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.counselorDetailRecyclerView.CounselorDetailHeaderObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.counselorDetailRecyclerView.CounselorDetailRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;

public class CounselorDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CounselorDetailRecyclerViewAdapter counselorDetailRecyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_detail);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //RecyclerView에 LayoutManager 설정 및 adapter 설정

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.counselor_detail));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화

        recyclerView.setLayoutManager(linearLayoutManager);
        counselorDetailRecyclerViewAdapter = new CounselorDetailRecyclerViewAdapter(this,new CounselorDetailHeaderObject(0,"http://cphoto.asiae.co.kr/listimglink/6/2016122719355313871_1.png","신한 은행","이건준","졸리자낭 소개 멘트 길게 써야 되는데 쓸 말이 없잔아."));
        recyclerView.setAdapter(counselorDetailRecyclerViewAdapter);

        addItems();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItems(){
        for(int i = 0 ; i< 20; i++) {
            if(i%2 == 0){
                counselorDetailRecyclerViewAdapter.addItem(new MainValueObject(i,1,"1시간 전","서울시","동작구","장항동","무슨 아파트",3,"대출 모집인이 겁나 좋아조아무너웜누어ㅜ 안좋아 졸려",25));
            }else{
                counselorDetailRecyclerViewAdapter.addItem(new MainValueObject(i,0,"1시간 전","서울시","동작구","장항동","무슨 아파트",3,"대출 모집인이 겁나 좋아조아무너웜누어ㅜ 안좋아 졸려",25));
            }
        }
    }
}
