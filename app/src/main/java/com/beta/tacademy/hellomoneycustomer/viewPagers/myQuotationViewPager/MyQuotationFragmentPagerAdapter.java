package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;


public class MyQuotationFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<MainPageViewPagerObject> itemOne;
    ArrayList<MainPageViewPagerObject> itemTwo;

    public MyQuotationFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyQuotationFragmentPagerAdapter(FragmentManager fm,ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayListOne,ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayListTwo) {
        super(fm);
        itemOne = mainPageViewPagerObjectArrayListOne;
        itemTwo = mainPageViewPagerObjectArrayListTwo;
        notifyDataSetChanged();
    }

    public void init(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayListOne,ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayListTwo){
        itemOne = mainPageViewPagerObjectArrayListOne;
        itemTwo = mainPageViewPagerObjectArrayListTwo;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return MyQuotationFragment.newInstance(itemOne);
        }else{
            return MyQuotationFragment.newInstance(itemTwo);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "진행 중";
        }else{
            return "완료";
        }
    }
}
