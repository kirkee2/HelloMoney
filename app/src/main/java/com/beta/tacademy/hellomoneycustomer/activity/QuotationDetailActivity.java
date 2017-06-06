package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailHeaderObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;

import java.util.ArrayList;

public class QuotationDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private QuotationDetailRecyclerViewAdapter quotationDetailRecyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_detail);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        if(id == -1){
            Toast.makeText(getApplicationContext(), "인텐트 안들어옴", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "id = " +id + " 들어옴", Toast.LENGTH_SHORT).show();
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

        //RecyclerView에 LayoutManager 설정 및 adapter 설정


        ArrayList<Float> tmp = new ArrayList<>();
        tmp.add(5F);
        tmp.add(4.5F);
        tmp.add(3.5F);
        tmp.add(2.5F);
        tmp.add(5.5F);
        tmp.add(3F);

        recyclerView.setLayoutManager(linearLayoutManager);
        quotationDetailRecyclerViewAdapter = new QuotationDetailRecyclerViewAdapter(this,new QuotationDetailHeaderObject(0,tmp,"12:31",0,"서울시","상도동","동작구","미래아파트","300평",30000,0,"1992.07.12",0,"010-6263-0135"));
        recyclerView.setAdapter(quotationDetailRecyclerViewAdapter);

        addItems();
   }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItems(){
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 ==0){
                quotationDetailRecyclerViewAdapter.addItem(new QuotationDetailObject(i,"신한은행","이건준",0,3.3,"http://cphoto.asiae.co.kr/listimglink/6/2016122719355313871_1.png"));
            }else{
                quotationDetailRecyclerViewAdapter.addItem(new QuotationDetailObject(i,"우리은행","엄마",1,4,"http://img.visualdive.co.kr/sites/2/2015/10/gisa2.jpg"));
            }
        }
    }
}
