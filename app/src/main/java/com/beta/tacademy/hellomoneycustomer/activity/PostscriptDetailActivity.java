package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostscriptDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int id;
    private BarChart barChart;
    private ArrayList<Float> rate;
    private TextView finalQuotationCount;
    private TextView averageInterestRate;
    private TextView bank;
    private TextView name;
    private TextView loanType;
    private TextView interestRate;
    private CircleImageView image;
    private TextView region;
    private TextView apt;
    private RatingBar star;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postscript_detail);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);


        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.postscript_detail));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
