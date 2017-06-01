package com.beta.tacademy.hellomoneycustomer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager.MyQuotationFragmentPagerAdapter;

public class MyQuotationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MyQuotationFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotation);
    }
}
