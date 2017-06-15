package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
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

public class MyQuotationFragment extends Fragment {
    RecyclerView recyclerView;
    RelativeLayout noResultOngoing;
    RelativeLayout noResultDone;
    MyQuotationRecyclerViewAdapter myQuotationRecyclerViewAdapter;
    ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList;
    Timer timer;
    TimerTask timerTask;
    int timerCheck;
    int page;


    public MyQuotationFragment() {
        // Required empty public constructor
    }

    public MyQuotationFragment(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList) {
        this.mainPageViewPagerObjectArrayList = mainPageViewPagerObjectArrayList;
        timerCheck = 0;
    }


    // TODO: Rename and change types and number of parameters
    public static MyQuotationFragment newInstance(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList,int page) {
        MyQuotationFragment fragment = new MyQuotationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("mainPageViewPagerObjectArrayList",mainPageViewPagerObjectArrayList);
        args.putInt("page",page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mainPageViewPagerObjectArrayList = getArguments().getParcelableArrayList("mainPageViewPagerObjectArrayList");
            this.page = getArguments().getInt("page");
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
    public void onResume() {
        super.onResume();

        recyclerView.setAdapter(myQuotationRecyclerViewAdapter);
        if(mainPageViewPagerObjectArrayList.size() == 0){
            if(page == 1){
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

            myQuotationRecyclerViewAdapter.init(mainPageViewPagerObjectArrayList);


            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerCheck = 0;
                            for (int i = 0; i < mainPageViewPagerObjectArrayList.size(); i++) {
                                if (mainPageViewPagerObjectArrayList.get(i).getOngoingStatus().equals("견적접수중")) {
                                    mainPageViewPagerObjectArrayList.get(i).setLeftSecond(mainPageViewPagerObjectArrayList.get(i).getLeftSecond() - 1);
                                    timerCheck++;
                                }
                            }

                            if(timerCheck == 0){
                                timer.cancel();
                            }
                            myQuotationRecyclerViewAdapter.update(mainPageViewPagerObjectArrayList);
                        }
                    });
                }
            };

            timer = new Timer();
            timer.schedule(timerTask,1000,1000);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        timer.cancel();
    }
}
