package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private MyQuotationFragmentPagerAdapter myQuotationFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotation);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        myQuotationFragmentPagerAdapter = new MyQuotationFragmentPagerAdapter(getSupportFragmentManager());

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.my_quotation));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setAdapter(myQuotationFragmentPagerAdapter);
        myQuotationFragmentPagerAdapter.init();

        Intent intent = getIntent();

        viewPager.setCurrentItem(intent.getIntExtra("page",0));
    }

    //back 버튼 클릭 시 이벤트 설정.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
