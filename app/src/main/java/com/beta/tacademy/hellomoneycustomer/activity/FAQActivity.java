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
import com.beta.tacademy.hellomoneycustomer.recyclerViews.FAQRecyclerView.FAQRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.FAQRecyclerView.FAQValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailHeaderObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FAQRecyclerViewAdapter faqRecyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        faqRecyclerViewAdapter = new FAQRecyclerViewAdapter();
;
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
        recyclerView.setAdapter(faqRecyclerViewAdapter);
        for(int i = 0 ; i < 10 ; i++){

            faqRecyclerViewAdapter.addItem(new FAQValueObject("제목asd lnsa dsajdn asjndasjk naskjnasjd ansj nsdj asndkjasndaskj naskj dnaskjd naskjdsakjd ansjk dsan djksand askjd nasdj" + i,"내용 asdklm ask damslkd amslkd asjkns jen qoo fnurwiguoeq fnqjenf qouenf euon qeon eqihfbqehi niq ioqefhqu eifb qjkefqeif qeuo fnqen eqhi bequ fqepfm oqejnf quefo qe " + i));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
