package com.beta.tacademy.hellomoneycustomer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.beta.tacademy.hellomoneycustomer.R;

public class SelectRegionAptSizeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region_apt_size);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.select_region_1));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void toNextStep(int step){
        if(step == 1){
            //fragmentManager = getSupportFragmentManager();

            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //fragmentTransaction.replace(R.id.fragment, fragment);

            //fragmentTransaction.commit();
        }else if(step == 1){
            //fragmentManager = getSupportFragmentManager();

            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //fragmentTransaction.replace(R.id.fragment, fragment);

            //fragmentTransaction.commit();
        }
    }

}
