package com.beta.tacademy.hellomoneycustomer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;
import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.github.mikephil.charting.data.LineRadarDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestQuotationActivity extends AppCompatActivity {

    private final int REGION_APT_SIZE_INTENT = 0;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RequestQuotationRecyclerViewAdapter requestQuotationRecyclerViewAdapter;
    private AnimateHorizontalProgressBar animateHorizontalProgressBar;
    private int fixStep;
    private int originStep;
    private boolean stepCheck;
    private Activity activity;


    String loanType;
    int loanAmount;
    String scheduledTime;
    String interestRateType;
    String jobType;
    String region1;
    String region2;
    String region3;
    String aptName;
    String aptSize;
    String aptSizeSupply;
    String aptSizeExclusive;
    int aptPrice;
    String telephone;

    private LinearLayout step1;
    private LinearLayout step2;
    private LinearLayout step3;
    private LinearLayout step4;
    private LinearLayout step5;
    private LinearLayout step6;
    private LinearLayout step7;
    private LinearLayout step8;

    private TextView step1Text1;
    private TextView step1Text2;
    private Button step2Button;
    private EditText step3Text;
    private Button step3Button;
    private DatePicker step5DatePicker;
    private Button step5Button;
    private TextView step4Text2;
    private TextView step4Text1;
    //private TextView step4Text3;
    private TextView step6Text1;
    private TextView step6Text2;
    private TextView step6Text3;
    private EditText step7Text;
    private Button step7Button;
    private Button step8Button;

    private RequestQuotation requestQuotation;

    private Handler handler;

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
        step8 = (LinearLayout)findViewById(R.id.step8);

        step1Text1 = (TextView)findViewById(R.id.step1Text1);
        step1Text2  = (TextView)findViewById(R.id.step1Text2);
        step2Button  = (Button)findViewById(R.id.step2Button);
        step3Text  = (EditText) findViewById(R.id.step3Text);
        step3Button = (Button) findViewById(R.id.step3Button);
        step4Text1 = (TextView)findViewById(R.id.step4Text1);
        step4Text2 = (TextView)findViewById(R.id.step4Text2);
        //step4Text3 = (TextView)findViewById(R.id.step4Text3);
        step6Text1 = (TextView)findViewById(R.id.step6Text1);
        step6Text2 = (TextView)findViewById(R.id.step6Text2);
        step6Text3 = (TextView)findViewById(R.id.step6Text3);
        step7Text = (EditText) findViewById(R.id.step7Text);
        step7Button = (Button) findViewById(R.id.step7Button);
        step5DatePicker = (DatePicker)findViewById(R.id.step5DataPicker);
        step5Button = (Button)findViewById(R.id.step5Button);
        step8Button  = (Button)findViewById(R.id.step8Button);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(new View(this).getWindowToken(), 0);

        requestQuotation = new RequestQuotation();
        aptPrice = 10000000;

        originStep = 1;
        stepCheck = false;

        handler = new Handler();

        animateHorizontalProgressBar.setMax(100);
        //animateHorizontalProgressBar.setProgress(400);

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
        requestQuotationRecyclerViewAdapter = new RequestQuotationRecyclerViewAdapter(this);
        recyclerView.setAdapter(requestQuotationRecyclerViewAdapter);

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ( bottom < oldBottom) {
                    recyclerView.smoothScrollToPosition(requestQuotationRecyclerViewAdapter.getItemCount());
                }
            }
        });

        toolbar.setTitle(getResources().getString(R.string.request_quotation));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        initItems();


        step1Text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step1Text1.setEnabled(false);
                step1Text2.setEnabled(false);
                if(!stepCheck){
                    loanType = "주택담보대출";

                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,1,"주택담보대출 입니다."));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,2,"담보할 아파트를 선택해주세요. (전세로 얻을 아파트를 선택해주세요.)"));

                            originStep++;

                            stepVisible(2);

                            animateHorizontalProgressBar.setProgress(14);
                            step1Text1.setEnabled(true);
                            step1Text2.setEnabled(true);
                        }
                    }, 400);
                }else{
                    loanType = "주택담보대출";

                    updateItem(2,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,1,"주택담보대출 입니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step1Text1.setEnabled(true);
                    step1Text2.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        step1Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step1Text1.setEnabled(false);
                step1Text2.setEnabled(false);

                if(!stepCheck){
                    loanType = "전세자금대출";

                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,1,"전세자금대출 입니다."));


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,2,"담보할 아파트를 선택해주세요. (전세로 얻을 아파트를 선택해주세요.)"));

                            originStep++;
                            step1.setVisibility(View.GONE);
                            step2.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(14);
                            step1Text1.setEnabled(true);
                            step1Text2.setEnabled(true);
                        }
                    }, 400);
                }else{
                    loanType = "전세자금대출";

                    updateItem(2,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,1,"전세자금대출 입니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step1Text1.setEnabled(true);
                    step1Text2.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });

        step2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2Button.setEnabled(false);
                startActivityForResult(new Intent(RequestQuotationActivity.this,SelectRegionAptSizeActivity.class),REGION_APT_SIZE_INTENT);
            }
        });


        step3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step3Button.setEnabled(false);
                if(!stepCheck){
                    if(step3Text.getText().length() == 0){
                        Toast.makeText(getApplicationContext(),"금액을 입력해주세요.",Toast.LENGTH_SHORT).show();
                        step3Button.setEnabled(true);
                    }else{
                        loanAmount = Integer.parseInt(step3Text.getText().toString());
                        if(loanAmount > aptPrice){
                            Toast.makeText(getApplicationContext(),"선택하신 아파트의 금액보다 높은 금액을 적으실 수 없습니다.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else if(loanAmount == 0){
                            Toast.makeText(getApplicationContext(),"만원 이상 금액을 적어주세요.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else{
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,3,loanAmount + "만원 입니다."));


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,4,"변동금리와 고정금리 중 선호하시는 금리는 무엇인가요?"));

                                    originStep++;
                                    step3.setVisibility(View.GONE);
                                    step4.setVisibility(View.VISIBLE);
                                    animateHorizontalProgressBar.setProgress(42);
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    step3Button.setEnabled(true);
                                }
                            }, 400);
                        }
                    }
                }else{
                    if(step3Text.getText().length() == 0){
                        Toast.makeText(getApplicationContext(),"금액을 입력해주세요.",Toast.LENGTH_SHORT).show();
                        step3Button.setEnabled(true);
                    }else{
                        loanAmount = Integer.parseInt(step3Text.getText().toString());

                        if(loanAmount > aptPrice){
                            Toast.makeText(getApplicationContext(),"선택하신 아파트의 금액보다 높은 금액을 적으실 수 없습니다.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else if(loanAmount == 0){
                            Toast.makeText(getApplicationContext(),"만원 이상 금액을 적어주세요.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else{
                            updateItem(6,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,3,loanAmount + "만원 입니다."));

                            stepCheck =false;
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                            stepVisible(originStep);
                            step3Button.setEnabled(true);

                            if(originStep == 3){
                                step3Text.requestFocus();
                                imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                            }else if(originStep == 7){
                                step7Text.requestFocus();
                                imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                            }
                        }
                    }
                }

                step3Text.setText("");
            }
        });

        step4Text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step4Text1.setEnabled(false);
                step4Text2.setEnabled(false);
                //step4Text3.setEnabled(false);

                if(!stepCheck){
                    interestRateType = "변동금리";

                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,4,interestRateType + "입니다."));


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,5,"대출실행예정일을 알려주세요."));

                            originStep++;
                            step4.setVisibility(View.GONE);
                            step5.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(56);
                            step4Text1.setEnabled(true);
                            step4Text2.setEnabled(true);
                            //step4Text3.setEnabled(true);
                        }
                    }, 400);
                }else{
                    interestRateType = "변동금리";

                    updateItem(8,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,4,interestRateType + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step4Text1.setEnabled(true);
                    step4Text2.setEnabled(true);
                    //step4Text3.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        step4Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step4Text1.setEnabled(false);
                step4Text2.setEnabled(false);
                //step4Text3.setEnabled(false);
                if(!stepCheck){
                    interestRateType = "고정금리";

                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,4,interestRateType + "입니다."));


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,5,"대출실행예정일을 알려주세요."));

                            originStep++;
                            step4.setVisibility(View.GONE);
                            step5.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(56);
                            step4Text1.setEnabled(true);
                            step4Text2.setEnabled(true);
                            //step4Text3.setEnabled(true);
                        }
                    }, 400);
                }else{
                    interestRateType = "고정금리";

                    updateItem(8,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,4,interestRateType + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step4Text1.setEnabled(true);
                    step4Text2.setEnabled(true);
                    //step4Text3.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        /*
        step4Text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step4Text1.setEnabled(false);
                step4Text2.setEnabled(false);
                step4Text3.setEnabled(false);

                if(!stepCheck){
                    interestRateType = "없음";

                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,4,interestRateType + "입니다."));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,5,"대출실행예정일을 알려주세요."));

                            originStep++;
                            step4.setVisibility(View.GONE);
                            step5.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(56);
                            step4Text1.setEnabled(true);
                            step4Text2.setEnabled(true);
                            step4Text3.setEnabled(true);
                        }
                    }, 400);
                }else{
                    interestRateType = "없음";

                    updateItem(8,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,4,interestRateType + "으로 하겠습니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step4Text1.setEnabled(true);
                    step4Text2.setEnabled(true);
                    step4Text3.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        */

        step5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step5Button.setEnabled(false);

                scheduledTime = step5DatePicker.getYear() +"-"+ (int)(step5DatePicker.getMonth()+1) + "-" + step5DatePicker.getDayOfMonth();
                final String printScheduledTime = step5DatePicker.getYear() +"년 "+ (int)(step5DatePicker.getMonth()+1) + "월 " + step5DatePicker.getDayOfMonth() +"일";

                if(!stepCheck){
                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,5,printScheduledTime + "입니다."));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,6,"고객님의 근로형태는 무엇인가요?"));

                            originStep++;
                            step5.setVisibility(View.GONE);
                            step6.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(70);
                            step5Button.setEnabled(true);
                        }
                    }, 400);
                }else{
                    updateItem(10,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,5, printScheduledTime + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step5Button.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });


        step6Text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step6Text1.setEnabled(false);
                step6Text2.setEnabled(false);
                step6Text3.setEnabled(false);

                if(!stepCheck){
                    jobType = "직장근로자";


                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,6,jobType + "입니다."));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,7,"마지막으로 전화번호를 입력해주세요."));

                            originStep++;
                            step6.setVisibility(View.GONE);
                            step7.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(85);
                            step7Text.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                            step6Text1.setEnabled(true);
                            step6Text2.setEnabled(true);
                            step6Text3.setEnabled(true);
                        }
                    }, 400);

                }else{
                    jobType = "직장근로자";

                    updateItem(12,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,6,jobType + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);

                    step6Text1.setEnabled(true);
                    step6Text2.setEnabled(true);
                    step6Text3.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        step6Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step6Text1.setEnabled(false);
                step6Text2.setEnabled(false);
                step6Text3.setEnabled(false);

                if(!stepCheck){
                    jobType = "개인사업자";


                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,6,jobType + "입니다."));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,7,"마지막으로 전화번호를 입력해주세요."));

                            originStep++;
                            step6.setVisibility(View.GONE);
                            step7.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(85);
                            step7Text.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                            step6Text1.setEnabled(true);
                            step6Text2.setEnabled(true);
                            step6Text3.setEnabled(true);
                        }
                    }, 400);
                }else{
                    jobType = "개인사업자";

                    updateItem(12,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,6,jobType + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);

                    step6Text1.setEnabled(true);
                    step6Text2.setEnabled(true);
                    step6Text3.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        step6Text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step6Text1.setEnabled(false);
                step6Text2.setEnabled(false);
                step6Text3.setEnabled(false);

                if(!stepCheck){
                    jobType = "프리랜서";


                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,6,jobType + "입니다."));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,7,"마지막으로 전화번호를 입력해주세요."));

                            originStep++;
                            step6.setVisibility(View.GONE);
                            step7.setVisibility(View.VISIBLE);
                            animateHorizontalProgressBar.setProgress(85);
                            step7Text.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                            step6Text1.setEnabled(true);
                            step6Text2.setEnabled(true);
                            step6Text3.setEnabled(true);
                        }
                    }, 400);

                }else{
                    jobType = "프리랜서";


                    updateItem(12,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,6,jobType + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);

                    step6Text1.setEnabled(true);
                    step6Text2.setEnabled(true);
                    step6Text3.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });

        /*

        step3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step3Button.setEnabled(false);
                if(!stepCheck){
                    if(step3Text.getText().length() == 0){
                        Toast.makeText(getApplicationContext(),"금액을 입력해주세요.",Toast.LENGTH_SHORT).show();
                        step3Button.setEnabled(true);
                    }else{
                        loanAmount = Integer.parseInt(step3Text.getText().toString());
                        if(loanAmount > aptPrice){
                            Toast.makeText(getApplicationContext(),"선택하신 아파트의 금액보다 높은 금액을 적으실 수 없습니다.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else if(loanAmount == 0){
                            Toast.makeText(getApplicationContext(),"만원 이상 금액을 적어주세요.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else{
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,3,loanAmount + "만원 입니다."));


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,4,"변동금리와 고정금리 중 선호하시는 금리는 무엇인가요?"));

                                    originStep++;
                                    step3.setVisibility(View.GONE);
                                    step4.setVisibility(View.VISIBLE);
                                    animateHorizontalProgressBar.setProgress(42);
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    step3Button.setEnabled(true);
                                }
                            }, 400);
                        }
                    }
                }else{
                    if(step3Text.getText().length() == 0){
                        Toast.makeText(getApplicationContext(),"금액을 입력해주세요.",Toast.LENGTH_SHORT).show();
                        step3Button.setEnabled(true);
                    }else{
                        loanAmount = Integer.parseInt(step3Text.getText().toString());

                        if(loanAmount > aptPrice){
                            Toast.makeText(getApplicationContext(),"선택하신 아파트의 금액보다 높은 금액을 적으실 수 없습니다.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else if(loanAmount == 0){
                            Toast.makeText(getApplicationContext(),"만원 이상 금액을 적어주세요.",Toast.LENGTH_SHORT).show();
                            step3Button.setEnabled(true);
                        }else{
                            updateItem(6,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,3,loanAmount + "만원 입니다."));

                            stepCheck =false;
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                            stepVisible(originStep);
                            step3Button.setEnabled(true);

                            if(originStep == 3){
                                step3Text.requestFocus();
                                imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                            }else if(originStep == 7){
                                step7Text.requestFocus();
                                imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                            }
                        }
                    }
                }

                step3Text.setText("");
            }
        });
         */
        step7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step7Button.setEnabled(false);

                if(step7Text.getText().length() == 10 ||step7Text.getText().length() == 11){
                    if(!stepCheck){
                        telephone = step7Text.getText().toString();

                        addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,7,telephone + "입니다."));

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,8,"수고하셨습니다. 모든 정보가 맞습니까?"));

                                originStep++;
                                step7.setVisibility(View.GONE);
                                step8.setVisibility(View.VISIBLE);
                                step7Button.setEnabled(true);
                                animateHorizontalProgressBar.setProgress(100);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                step7Button.setEnabled(true);
                            }
                        }, 400);
                    }else{
                        telephone = step7Text.getText().toString();

                        updateItem(14,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,7,telephone + "입니다."));

                        stepCheck =false;

                        stepVisible(originStep);
                        step7Button.setEnabled(true);

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                        if(originStep == 3){
                            step3Text.requestFocus();
                            imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                        }else if(originStep == 7){
                            step7Text.requestFocus();
                            imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                        }
                    }

                    step7Text.setText("");
                }else{
                    step7Button.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"전화번호를 확인 해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        step8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step8Button.setEnabled(false);
                requestQuotation.execute();
            }
        });
    }

    public void stepFix(int step){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        stepVisible(step);
        stepCheck = true;
        fixStep = step;


        if(step == 3){
            step3Text.requestFocus();
            imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
        }else if(step == 7){
            step7Text.requestFocus();
            imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
        }
    }


    public void stepVisible(int step){
        if(step == 1){
            step1.setVisibility(View.VISIBLE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.GONE);
        }else if(step ==2){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.VISIBLE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.GONE);
        }else if(step ==3){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.VISIBLE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.GONE);
        }else if(step ==4){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.VISIBLE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.GONE);
        }else if(step ==5){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.VISIBLE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.GONE);
        }else if(step ==6){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.VISIBLE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.GONE);
        }else if(step ==7){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.VISIBLE);
            step8.setVisibility(View.GONE);
        }else if(step ==8){
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.GONE);
            step3.setVisibility(View.GONE);
            step4.setVisibility(View.GONE);
            step5.setVisibility(View.GONE);
            step6.setVisibility(View.GONE);
            step7.setVisibility(View.GONE);
            step8.setVisibility(View.VISIBLE);
        }
    }

    public void stepQuotation(int step){
        if(step == 1){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,1,"먼저, 어떤 대출을 받을려고 하시나요??\n(아래에서 선택해주세요.)"));
        }else if(step ==2){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,2,"담보할 아파트를 선택해주세요. (전세로 얻을 아파트를 선택해주세요.)"));

        }else if(step ==3){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,3,"필요하신 대출금액을 입력해주세요.(대출한도는 + "+ aptPrice +"만원)"));

        }else if(step ==4){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,4,"변동금리와 고정금리 중 선호하시는 금리는 무엇인가요?"));

        }else if(step ==5){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,5,"대출실행예정일을 알려주세요."));

        }else if(step ==6){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,6,"고객님의 근로형태는 무엇인가요?"));

        }else if(step ==7){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,7,"마지막으로 전화번호를 입력해주세요."));

        }else if(step ==8){
            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,8,"모든 정보가 맞습니까?"));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REGION_APT_SIZE_INTENT) {
                region1 = data.getStringExtra("region1");
                region2 = data.getStringExtra("region2");
                region3 = data.getStringExtra("region3");
                aptName = data.getStringExtra("apt");
                aptSize = data.getStringExtra("aptSize");

                aptSize = aptSize.replace("\n","");

                StringTokenizer stringTokenizer = new StringTokenizer(aptSize,"/");

                aptSizeExclusive = stringTokenizer.nextToken();
                String tmp = stringTokenizer.nextToken();

                stringTokenizer = new StringTokenizer(tmp,"(");

                aptSizeSupply = stringTokenizer.nextToken();
                tmp = stringTokenizer.nextToken();
                aptPrice  = Integer.parseInt(tmp.substring(0,tmp.length()-3)) * 7 / 10;


                if(!stepCheck){
                    step2Button.setEnabled(false);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,2,region1 + " " + region2 + " " + region3 + "\n" + aptName + "\n" + aptSizeExclusive+ "/" +aptSizeSupply + "입니다."));
                        }
                    }, 500);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItem(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,3,"필요하신 대출금액을 입력해주세요.\n(대출한도 "+ aptPrice +"만원)"));

                            originStep++;
                            step2.setVisibility(View.GONE);
                            step3.setVisibility(View.VISIBLE);
                            step3Text.requestFocus();
                            animateHorizontalProgressBar.setProgress(28);
                            step2Button.setEnabled(true);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                            //imm.showSoftInputFromInputMethod (step3Text .getApplicationWindowToken(),InputMethodManager.SHOW_FORCED);
                        }
                    }, 700);

                }else{
                    updateItem(5,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,3,"필요하신 대출금액을 입력해주세요.\n(대출한도 "+ aptPrice +"만원)"));

                    updateItem(4,new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.MY_CHATTING,2,region1 + " " + region2 + " " + region3 + "\n" + aptName + "\n" + aptSizeExclusive+ "/" +aptSizeSupply + "입니다."));

                    stepCheck =false;

                    stepVisible(originStep);
                    step2Button.setEnabled(true);

                    if(originStep == 3){
                        step3Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step3Text, InputMethodManager.SHOW_FORCED);
                    }else if(originStep == 7){
                        step7Text.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(step7Text, InputMethodManager.SHOW_FORCED);
                    }
                }
            } else {
                Toast.makeText(RequestQuotationActivity.this, "REQUEST_ACT가 아님", Toast.LENGTH_SHORT).show();
            }
        }else if(resultCode == RESULT_CANCELED){
            if (requestCode == REGION_APT_SIZE_INTENT) {
                step2Button.setEnabled(true);
            }
        }else{
            return;
        }
    }

    private class RequestQuotation extends AsyncTask<Void, Void, Integer> {
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

                RequestBody postBody = new FormBody.Builder()
                        .add("customerId", CommonClass.getUUID())
                        .add("loanType",loanType)
                        .add("loanAmount",String.valueOf(loanAmount))
                        .add("scheduledTime",scheduledTime)
                        .add("interestRateType",interestRateType)
                        .add("jobType",jobType)
                        .add("region1",region1)
                        .add("region2",region2)
                        .add("region3",region3)
                        .add("aptName",aptName)
                        .add("phoneNumber",telephone)
                        .add("aptPrice",String.valueOf(aptPrice))
                        .add("aptSizeSupply",aptSizeSupply)
                        .add("aptSizeExclusive",aptSizeExclusive)
                        .build();

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.request_quotation_url))
                        .post(postBody)
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
                        //JSONArray data1= jsonObject1.getJSONArray("data");
                        //ArrayList<JSONArray> tmp = new ArrayList();
                        //tmp.add(data1);
                        return 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 3;
                }
            }else{
                return 1;
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 0){
                Toast.makeText(getApplicationContext(),"견적 요청을 등록하였습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }else{

            }
            step8Button.setEnabled(true);

            progressBar.setVisibility(View.GONE);
        }
    }


    //back 버튼 클릭 시 이벤트 설정.
    @Override
    public void onBackPressed() {
        CancelDialog cancelDialog = new CancelDialog(RequestQuotationActivity.this);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(new View(this).getWindowToken(), 0);
        cancelDialog.show();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            CancelDialog cancelDialog = new CancelDialog(RequestQuotationActivity.this);


            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(new View(this).getWindowToken(), 0);
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
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                    dismiss();
                }
            });
        }
    }

    public void addItem(RequestQuotationValueObject requestQuotationValueObject){
        requestQuotationRecyclerViewAdapter.addItem(requestQuotationValueObject);
        recyclerView.smoothScrollToPosition(requestQuotationRecyclerViewAdapter.getItemCount());
    }

    public void updateItem(int index,RequestQuotationValueObject requestQuotationValueObject){
        requestQuotationRecyclerViewAdapter.updateItem(index,requestQuotationValueObject);
        recyclerView.smoothScrollToPosition(requestQuotationRecyclerViewAdapter.getItemCount());
    }

    public void initItems(){
        ArrayList<RequestQuotationValueObject> tmp = new ArrayList<>();

        tmp.add(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,1,"반갑습니다 :)\n 지금부터 최저 금리 대출을 확인하기 위해 꼭 필요한 7가지 사항을 알려주세요."));
        tmp.add(new RequestQuotationValueObject(RequestQuotationRecyclerViewAdapter.SYSTEM_CHATTING,1,"먼저, 어떤 대출을 받을려고 하시나요?? (아래에서 선택해주세요.)"));
        requestQuotationRecyclerViewAdapter.initItem(tmp);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if (requestQuotation.getStatus() == AsyncTask.Status.RUNNING) {
            requestQuotation.cancel(true);
        }
    }
}