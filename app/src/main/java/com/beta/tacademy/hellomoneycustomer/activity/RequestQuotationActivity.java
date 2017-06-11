package com.beta.tacademy.hellomoneycustomer.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;
import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.github.mikephil.charting.data.LineRadarDataSet;

import java.util.ArrayList;

public class RequestQuotationActivity extends AppCompatActivity {

    private final int REGION_APT_SIZE_INTENT = 0;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RequestQuotationRecyclerViewAdapter requestQuotationRecyclerViewAdapter;
    private AnimateHorizontalProgressBar animateHorizontalProgressBar;
    private int ongoingStep;
    private int previousStep;
    private boolean stepChanged;

    private LinearLayout step1;
    private LinearLayout step2;
    private LinearLayout step3;
    private LinearLayout step4;
    private LinearLayout step5;
    private LinearLayout step6;
    private LinearLayout step7;


    private TextView step1Text1;
    private TextView step1Text2;
    private TextView step2Text;
    private EditText step3Text;
    private Button step3Button;
    private TextView step4Text1;
    private TextView step4Text2;
    private TextView step4Text3;
    private TextView step6Text1;
    private TextView step6Text2;
    private TextView step6Text3;
    private EditText step7Text;
    private Button step7Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_quotation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        animateHorizontalProgressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar);

        step1 = (LinearLayout)findViewById(R.id.step1);
        step2 = (LinearLayout)findViewById(R.id.step2);
        step3 = (LinearLayout)findViewById(R.id.step3);
        step4 = (LinearLayout)findViewById(R.id.step4);
        step5 = (LinearLayout)findViewById(R.id.step5);
        step6 = (LinearLayout)findViewById(R.id.step6);
        step7 = (LinearLayout)findViewById(R.id.step7);
        step1Text1 = (TextView)findViewById(R.id.step1Text1);
        step1Text2  = (TextView)findViewById(R.id.step1Text2);
        step2Text  = (TextView)findViewById(R.id.step2Text);
        step3Text  = (EditText) findViewById(R.id.step3Text);
        step3Button = (Button) findViewById(R.id.step3Button);
        step4Text1 = (TextView)findViewById(R.id.step4Text1);
        step4Text2 = (TextView)findViewById(R.id.step4Text2);
        step4Text3 = (TextView)findViewById(R.id.step4Text3);
        step6Text1 = (TextView)findViewById(R.id.step6Text1);
        step6Text2 = (TextView)findViewById(R.id.step6Text2);
        step6Text3 = (TextView)findViewById(R.id.step6Text3);
        step7Text = (EditText) findViewById(R.id.step7Text);
        step7Button = (Button) findViewById(R.id.step7Button);

        ongoingStep = 0;
        previousStep = 0;
        stepChanged = false;

        animateHorizontalProgressBar.setMax(100);
        //animateHorizontalProgressBar.setProgress(400);

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

        initItems();


        step1Text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stepChanged){
                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,0,"주택 담보 대출을 받겠습니다.",false));
                    ongoingStep++;
                    previousStep++;
                    step1.setVisibility(View.INVISIBLE);
                    step2.setVisibility(View.VISIBLE);
                    animateHorizontalProgressBar.setProgress(14);
                }else{

                }
            }
        });
        step1Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stepChanged){
                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,0,"전세 자금 대출을 받겠습니다.",false));
                    ongoingStep++;
                    previousStep++;
                    step1.setVisibility(View.INVISIBLE);
                    step2.setVisibility(View.VISIBLE);
                    animateHorizontalProgressBar.setProgress(14);
                }else{

                }
            }
        });
        step2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestQuotationActivity.this,SelectRegionAptSizeActivity.class);
                startActivityForResult(intent,REGION_APT_SIZE_INTENT);
            }
        });
        step3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step4Text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step4Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step4Text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step6Text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step6Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step6Text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        step7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

        private TextView no;
        private TextView yes;
        public CancelDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.cancel_custom_dialog);

            yes = (TextView)findViewById(R.id.yes);
            no = (TextView)findViewById(R.id.no);

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

    public void addItem(RequestQuotationValueObject requestQuotationValueObject){
        requestQuotationRecyclerViewAdapter.addItem(requestQuotationValueObject);
    }

    public void initItems(){
        ArrayList<RequestQuotationValueObject> tmp = new ArrayList<>();
        tmp.add(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,0,"반갑습니다 :)\n지금부터 최저 금리 대출을 확인하기 위해 꼭 필요한 7가지 사항을 알려주세요.",false));
        tmp.add(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,0,"먼저, 어떤 대출을 받을려고 하시나요??\n(아래에서 선택해주세요.)",false));
        requestQuotationRecyclerViewAdapter.initItem(tmp);
    }
}