package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostscriptDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int id;
    private int counselorId;
    private BarChart barChart;
    private ArrayList<Float> rate;
    private TextView finalQuotationCount;
    private TextView averageInterestRate;
    private TextView bank;
    private TextView name;
    private TextView goCounselor;
    private ImageView loanType;
    private CircleImageView image;
    private TextView region;
    private TextView apt;
    private RatingBar starRatingBar;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postscript_detail);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        barChart = (BarChart)findViewById(R.id.barChart);
        rate = new ArrayList<>();
        finalQuotationCount = (TextView)findViewById(R.id.finalQuotationCount);
        averageInterestRate = (TextView)findViewById(R.id.averageInterestRate);
        bank = (TextView)findViewById(R.id.bank);
        name = (TextView)findViewById(R.id.name);
        goCounselor =(TextView)findViewById(R.id.goCounselor);
        loanType = (ImageView)findViewById(R.id.loanType);
        image = (CircleImageView)findViewById(R.id.image);
        region = (TextView)findViewById(R.id.region);
        apt = (TextView)findViewById(R.id.apt);
        starRatingBar = (RatingBar)findViewById(R.id.starRatingBar);
        content = (TextView)findViewById(R.id.content);

        /////
        rate.add(3.5F);
        rate.add(3F);
        rate.add(5F);
        rate.add(4F);
        rate.add(2.5F);
        rate.add(1.5F);
        rate.add(4F);
        rate.add(3F);
        rate.add(2.5F);

        String bankTmp = "국민은행";
        String nameTmp = "이건준";
        int loanTypeTmp = 0;
        String imageTmp = "http://img.visualdive.co.kr/sites/2/2015/10/gisa2.jpg";
        String regionTmp = "경기도 고양시 마두동";
        String aptTmp = "훼미리아파트";
        int starRatingBarTmp = 4;
        String contentTmp = "밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘밤샘";

        /////////
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        //toolbar
        toolbar.setTitle(getResources().getString(R.string.postscript_detail));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));


        //barchart
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> entriesMin = new ArrayList<>();
        float min = 100;
        int minIndex = 0;
        for(int i = 0 ; i <rate.size() ; i++){
            if(rate.get(i) < min){
                min = rate.get(i);
                minIndex = i;
            }
            entries.add(new BarEntry(i*0.5F,rate.get(i)));
        }

        entriesMin.add(entries.get(minIndex));
        entries.remove(minIndex);

        Description d = new Description();
        d.setText("");

        BarDataSet dataSet = new BarDataSet(entries, "금리");
        BarDataSet dataSetMin = new BarDataSet(entriesMin, "최저 금리");

        dataSet.setColor(0xFF00BFA5);
        dataSet.setHighlightEnabled(false);
        dataSet.setValueTextSize(10);

        dataSetMin.setColor(0xFFFF4081);
        dataSetMin.setHighlightEnabled(false);
        dataSetMin.setValueTextSize(10);

        BarData data = new BarData(dataSet);
        data.addDataSet(dataSetMin);

        data.setBarWidth(0.15F);
        barChart.setData(data);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barChart.setDescription(d);
        barChart.setEnabled(false);
        barChart.animateY(500);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.invalidate();

        //
        finalQuotationCount.setText(String.valueOf(rate.size()));
        double averageInterestRateTmp = 0;
        for(int i = 0 ; i < rate.size() ; i++){
            averageInterestRateTmp += rate.get(i);
        }

        averageInterestRateTmp = averageInterestRateTmp/(double)rate.size();
        averageInterestRate.setText(String.valueOf(averageInterestRateTmp));


        //

        Glide.with(this)
                .load(imageTmp)
                .animate(android.R.anim.slide_in_left)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(image);
        bank.setText(bankTmp);
        name.setText(nameTmp);

        //

        if(loanTypeTmp == 0){
            loanType.setImageResource(R.drawable.lease_loan);
        }else{
            loanType.setImageResource(R.drawable.secured_loan);
        }

        region.setText(regionTmp);
        apt.setText(aptTmp);
        starRatingBar.setRating(starRatingBarTmp);
        starRatingBar.setEnabled(false);
        content.setText(contentTmp);

        goCounselor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
