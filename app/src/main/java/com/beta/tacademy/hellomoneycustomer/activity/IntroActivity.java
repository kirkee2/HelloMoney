package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroFragmentPagerAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tab;
    private TextView skip;
    private Button startButton;

    private IntroFragmentPagerAdapter introFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = (ViewPager) findViewById(R.id.introViewPager);
        tab = (TabLayout) findViewById(R.id.introTab);
        introFragmentPagerAdapter = new IntroFragmentPagerAdapter(getSupportFragmentManager());
        skip = (TextView)findViewById(R.id.skip);
        startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


        tab.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 3){
                    tab.setVisibility(TabLayout.INVISIBLE);
                    startButton.setVisibility(View.VISIBLE);
                }else{
                    tab.setVisibility(TabLayout.VISIBLE);
                    startButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });

        viewPager.setAdapter(introFragmentPagerAdapter);
        introFragmentPagerAdapter.init();

    }

}
