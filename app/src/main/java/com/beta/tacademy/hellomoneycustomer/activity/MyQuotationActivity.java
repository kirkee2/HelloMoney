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
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager.MyQuotationFragmentPagerAdapter;

import java.util.ArrayList;

public class MyQuotationActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private MyQuotationFragmentPagerAdapter myQuotationFragmentPagerAdapter;
    private ArrayList<MainPageViewPagerObject> mainPageViewPagerObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotation);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mainPageViewPagerObject = new ArrayList<>();

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

        Intent intent = getIntent();

        viewPager.setCurrentItem(intent.getIntExtra("page",0));
        add();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void add(){
        ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectOne = new ArrayList<>();
        ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectTwo = new ArrayList<>();

        for(int i = 0 ; i < 10 ;i++){
            if(i%3 ==0){
                mainPageViewPagerObject.add(new MainPageViewPagerObject(0,1,3,"12:12","경기도","고양시","장항동","현대타운빌","1000평",3));
            }else{
                mainPageViewPagerObject.add(new MainPageViewPagerObject(0,0,3,"12:12","경기도","언니","나 마음에","안들죠??","1000평",3));
            }
        }

        for(MainPageViewPagerObject object : mainPageViewPagerObject){
            if(object.getOngoingStatus() == 0){
                mainPageViewPagerObjectOne.add(object);
            }else{
                mainPageViewPagerObjectTwo.add(object);
            }
        }

        myQuotationFragmentPagerAdapter.initOne(mainPageViewPagerObjectOne);
        myQuotationFragmentPagerAdapter.initTwo(mainPageViewPagerObjectTwo);
    }
}