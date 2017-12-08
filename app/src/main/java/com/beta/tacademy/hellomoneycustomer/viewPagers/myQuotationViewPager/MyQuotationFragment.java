package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.MyQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static android.R.attr.padding;
import static android.support.v4.view.PagerAdapter.POSITION_NONE;

public class MyQuotationFragment extends Fragment {
    RecyclerView recyclerView;
    RelativeLayout noResultOngoing;
    RelativeLayout noResultDone;
    MyQuotationRecyclerViewAdapter myQuotationRecyclerViewAdapter;
    ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList;
    Timer timer;
    TimerTask timerTask;
    int myPage;


    public MyQuotationFragment() {
    }

/*
    public MyQuotationFragment(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList) {
        this.mainPageViewPagerObjectArrayList = mainPageViewPagerObjectArrayList;
        timerCheck = 0;
    }
*/

    public static MyQuotationFragment newInstance(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList,int myPage) {
        MyQuotationFragment fragment = new MyQuotationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("mainPageViewPagerObjectArrayList",mainPageViewPagerObjectArrayList);
        args.putInt("myPage",myPage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mainPageViewPagerObjectArrayList = getArguments().getParcelableArrayList("mainPageViewPagerObjectArrayList");
            this.myPage = getArguments().getInt("myPage");
        }

        myQuotationRecyclerViewAdapter = new MyQuotationRecyclerViewAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_quotation, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        noResultDone = (RelativeLayout)view.findViewById(R.id.noResult_done);
        noResultOngoing = (RelativeLayout)view.findViewById(R.id.noResult_ongoing);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView.setAdapter(myQuotationRecyclerViewAdapter);
        if(mainPageViewPagerObjectArrayList.size() == 0){
            if(myPage == 1){
                noResultOngoing.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }else{
                noResultDone.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        }else{
            noResultDone.setVisibility(View.GONE);
            noResultOngoing.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            myQuotationRecyclerViewAdapter.init(mainPageViewPagerObjectArrayList,myPage);

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < mainPageViewPagerObjectArrayList.size(); i++) {
                                if (mainPageViewPagerObjectArrayList.get(i).getOngoingStatus().equals("견적접수중")) {
                                    if( mainPageViewPagerObjectArrayList.get(i).getLeftSecond() > 0){
                                        mainPageViewPagerObjectArrayList.get(i).setLeftSecond(mainPageViewPagerObjectArrayList.get(i).getLeftSecond() - 1);
                                    }else{
                                        mainPageViewPagerObjectArrayList.get(i).setOngoingStatus("선택대기중");
                                    }
                                }
                            }

                            myQuotationRecyclerViewAdapter.update(mainPageViewPagerObjectArrayList,myPage);
                        }
                    });
                }
            };

            timer = new Timer();
            timer.schedule(timerTask,0,1000);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(timer == null){

        }else{
            timer.cancel();
        }
    }
}
