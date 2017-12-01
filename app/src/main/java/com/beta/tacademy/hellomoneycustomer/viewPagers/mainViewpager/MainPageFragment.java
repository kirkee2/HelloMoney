package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.MyQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.activity.QuotationDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.RequestQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.common.util.StringUtil;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainPageFragment extends Fragment {

    MainPageViewPagerObject mainPageViewPagerObject;
    //TextView region;
    TextView apt;
    //TextView size;
    //TextView type;
    TextView currentQuotation;
    TextView leftTime;
    FrameLayout frameLayout;
    Timer timer;
    TimerTask timerTask;
    TextView ongoingSub;
    int position;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance(MainPageViewPagerObject mainPageViewPagerObject ,int position) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putParcelable("mainPageViewPagerObject", mainPageViewPagerObject);
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mainPageViewPagerObject = getArguments().getParcelable("mainPageViewPagerObject");
            this.position = getArguments().getInt("position");
        }else{
            this.mainPageViewPagerObject = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        //region = (TextView)view.findViewById(R.id.region);
        apt = (TextView)view.findViewById(R.id.apt);
        //type = (TextView)view.findViewById(R.id.type);
        currentQuotation = (TextView)view.findViewById(R.id.currentQuotation);
        leftTime = (TextView)view.findViewById(R.id.leftTime);
        frameLayout = (FrameLayout)view.findViewById(R.id.frame_layout);
        //size = (TextView)view.findViewById(R.id.size);
        ongoingSub = (TextView)view.findViewById(R.id.ongoing_sub);

        return view;
    }

    public void onResume(){
        super.onResume();

        //region.setText(mainPageViewPagerObject.getRegion1() + " " + mainPageViewPagerObject.getRegion2() + " " +  mainPageViewPagerObject.getRegion3());
        apt.setText(mainPageViewPagerObject.getApt());
        //size.setText( mainPageViewPagerObject.getSize());
        //type.setText(mainPageViewPagerObject.getType());

        currentQuotation.setText(String.valueOf(mainPageViewPagerObject.getCurrentQuotation()));

        ongoingSub.setText(mainPageViewPagerObject.getOngoingStatus());

        if(mainPageViewPagerObject.getOngoingStatus().equals("견적접수중")){
            //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_interection_waiting));

            int leftSecond  = mainPageViewPagerObject.getLeftSecond();
            int hour = leftSecond/3600;
            int tmp = leftSecond%3600;
            int minute = tmp/60;
            int second = tmp%60;

            if(leftSecond > 0){
                leftTime.setText(StringUtil.formatNumber2(hour) + "시간 " + StringUtil.formatNumber2(minute)  + "분 " + StringUtil.formatNumber2(second) + "");
            }else{
                mainPageViewPagerObject.setOngoingStatus("선택대기중");
                //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
                leftTime.setText(getString(R.string.step_content2));
                if(timer != null){
                    timer.cancel();
                }
            }

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int leftSecond = mainPageViewPagerObject.getLeftSecond()-1;
                            mainPageViewPagerObject.setLeftSecond(leftSecond);
                            int hour = leftSecond/3600;
                            int tmp = leftSecond%3600;
                            int minute = tmp/60;
                            int second = tmp%60;

                            if(leftSecond > 0){
                                leftTime.setText("마감 " + StringUtil.formatNumber2(hour) + ":"+ StringUtil.formatNumber2(minute)  + ":" + StringUtil.formatNumber2(second) + " 전");
                            }else{
                                mainPageViewPagerObject.setOngoingStatus("선택대기중");
                                //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
                                leftTime.setText(getString(R.string.step_content2));
                                if(timer != null){
                                    timer.cancel();
                                }
                            }
                        }
                    });
                }
            };

            timer = new Timer();
            timer.schedule(timerTask,0,1000);

        }else if(mainPageViewPagerObject.getOngoingStatus().equals("선택대기중")){
            //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            //leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(getString(R.string.step_content2));
            //leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step2_small, 0, 0, 0);
        }else if(mainPageViewPagerObject.getOngoingStatus().equals("상담중")){
            //leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step3_small, 0, 0, 0);
            //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            //leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(getString(R.string.step_content3));
        }else if(mainPageViewPagerObject.getOngoingStatus().equals("심사중")){
            //leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step4_small, 0, 0, 0);
            //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            //leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(getString(R.string.step_content4));
        }else if(mainPageViewPagerObject.getOngoingStatus().equals("승인완료")){
           // leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step5_small, 0, 0, 0);
            //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            //leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(getString(R.string.step_content5));
        }else{
            //leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step6_small, 0, 0, 0);
            //linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_done));
            //leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(getString(R.string.step_content6));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuotationDetailActivity.class);
                intent.putExtra("id",mainPageViewPagerObject.getId());
                intent.putExtra("position",position);
                getActivity().startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer != null){
            timer.cancel();
        }
    }

}
