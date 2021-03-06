package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;


public class MyQuotationFragmentPagerAdapter extends FragmentStatePagerAdapter {
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
            return MyQuotationFragment.newInstance(itemOne,0);
        }else{
            return MyQuotationFragment.newInstance(itemTwo,1);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "진행중";
        }else{
            return "종료";
        }
    }
}
