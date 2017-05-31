package com.beta.tacademy.hellomoneycustomer.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;

public class RequestQuotationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RequestQuotationRecyclerViewAdapter requestQuotationRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_quotation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        requestQuotationRecyclerViewAdapter = new RequestQuotationRecyclerViewAdapter();

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

        //RecyclerView에 LayoutManager 설정 및 adapter 설정

        recyclerView.setLayoutManager(linearLayoutManager);
        requestQuotationRecyclerViewAdapter = new RequestQuotationRecyclerViewAdapter();
        recyclerView.setAdapter(requestQuotationRecyclerViewAdapter);


        toolbar.setTitle(getResources().getString(R.string.request_quotation));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        addItems();

    }

    //back 버튼 클릭 시 이벤트 설정.
    @Override
    public void onBackPressed() {
        CancelDialog cancelDialog = new CancelDialog(RequestQuotationActivity.this);


        cancelDialog.show();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            CancelDialog cancelDialog = new CancelDialog(RequestQuotationActivity.this);


            cancelDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private class CancelDialog extends Dialog {

        Button no;
        Button yes;
        public CancelDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.cancel_custom_dialog);

            yes = (Button)findViewById(R.id.yes);
            no = (Button)findViewById(R.id.no);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    dismiss();
                }
            });
        }
    }

    public void addItems(){
        for(int i = 0 ; i< 20; i++) {
            if(i%2 == 0){
                requestQuotationRecyclerViewAdapter.addMember(new RequestQuotationValueObject(0,i,"qwhqwuiehqwehwuwiiunasdnkasdasmdnjkasnjkwdfndsjndjknrwoiejiwe kwrn owrnio ergi gpom sm [wefogoewgno"));
            }else{
                requestQuotationRecyclerViewAdapter.addMember(new RequestQuotationValueObject(1,i,"asdnjasndkdanasldnkasldasjdsanldjsndajsldnasdjlasndjalsndsadjlsandlasjdnasjkdsjkdaskjdasjkdnasd"));
            }
        }

        requestQuotationRecyclerViewAdapter.addMember(new RequestQuotationValueObject(0,20,"qo"));

    }
}