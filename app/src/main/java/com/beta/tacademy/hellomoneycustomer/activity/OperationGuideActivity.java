package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager.MyQuotationFragmentPagerAdapter;

import java.util.ArrayList;

public class OperationGuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView end;
    private Button endButton;
    private Toolbar toolbar;
    private IntroFragmentPagerAdapter introFragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_guide);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.introViewPager);
        tabLayout = (TabLayout) findViewById(R.id.introTab);
        introFragmentPagerAdapter = new IntroFragmentPagerAdapter(getSupportFragmentManager());
        end = (TextView)findViewById(R.id.end);
        endButton = (Button)findViewById(R.id.endButton);

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.operation_guide));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 3){
                    tabLayout.setVisibility(TabLayout.INVISIBLE);
                    endButton.setVisibility(View.VISIBLE);
                }else{
                    tabLayout.setVisibility(TabLayout.VISIBLE);
                    endButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.setAdapter(introFragmentPagerAdapter);
        introFragmentPagerAdapter.init();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
