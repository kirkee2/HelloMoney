package com.beta.tacademy.hellomoneycustomer.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView.RequestQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager.MyQuotationFragmentPagerAdapter;

public class MyQuotationActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar progressBar;
    private MyQuotationFragmentPagerAdapter myQuotationFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotation);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        myQuotationFragmentPagerAdapter = new MyQuotationFragmentPagerAdapter(getSupportFragmentManager());
    }
}
