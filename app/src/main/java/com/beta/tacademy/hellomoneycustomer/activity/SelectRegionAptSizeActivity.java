package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.fragment.AptFragment;
import com.beta.tacademy.hellomoneycustomer.fragment.Region1Fragment;
import com.beta.tacademy.hellomoneycustomer.fragment.Region2Fragment;
import com.beta.tacademy.hellomoneycustomer.fragment.Region3Fragment;
import com.beta.tacademy.hellomoneycustomer.fragment.AptSizeFragment;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;

public class SelectRegionAptSizeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Fragment fragment;
    private static String region1;
    private static String region2;
    private static String region3;
    private static String apt;
    private static String size;
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

        fragmentManager = getSupportFragmentManager();
        fragment = new Region1Fragment();

        toolbar.setTitle("아파트 선택하기");
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(fragmentManager.getBackStackEntryCount() != 0){
                fragmentManager.popBackStack();
            }else{
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void toNextStep(int step,String info){
        if(step == 0){
            region1 = info;
            //toolbar.setTitle(region1);
            fragment = Region2Fragment.newInstance(region1);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("step0");

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.fragment, fragment);

            fragmentTransaction.commit();
        }else if(step == 1){
            region2 = info;
            //toolbar.setTitle(region2);
            fragment = Region3Fragment.newInstance(region1,region2);


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("step1");

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.fragment, fragment);

            fragmentTransaction.commit();
        }else if(step == 2){
            region3 = info;
            //toolbar.setTitle(region3);
            fragment = AptFragment.newInstance(region1,region2,region3);


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("step2");

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.fragment, fragment);

            fragmentTransaction.commit();

        }else{
            apt = info;
            //toolbar.setTitle(apt);
            fragment = AptSizeFragment.newInstance(region1,region2,region3,apt);


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.fragment, fragment);

            fragmentTransaction.commit();
        }
    }

    public void sizeInfo(String info){
        size = info;

        Intent intent = new Intent();
        intent.putExtra("region1", region1);
        intent.putExtra("region2", region2);
        intent.putExtra("region3", region3);
        intent.putExtra("apt", apt);
        intent.putExtra("aptSize", size);
        setResult(RESULT_OK, intent);
        finish();
    }
}
