package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.MyQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.activity.QuotationDetailActivity;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainPageFragment extends Fragment {

    MainPageViewPagerObject mainPageViewPagerObject;
    TextView region;
    TextView aptSize;
    TextView type;
    TextView currentQuotation;
    TextView leftTime;
    LinearLayout linearLayout;
    Timer timer;
    TimerTask timerTask;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance(MainPageViewPagerObject mainPageViewPagerObject) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putParcelable("mainPageViewPagerObject", mainPageViewPagerObject);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mainPageViewPagerObject = getArguments().getParcelable("mainPageViewPagerObject");
        }else{
            this.mainPageViewPagerObject = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        region = (TextView)view.findViewById(R.id.region);
        aptSize = (TextView)view.findViewById(R.id.aptSize);
        type = (TextView)view.findViewById(R.id.type);
        currentQuotation = (TextView)view.findViewById(R.id.currentQuotation);
        leftTime = (TextView)view.findViewById(R.id.leftTime);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);

        region.setText(mainPageViewPagerObject.getRegion1() + " " + mainPageViewPagerObject.getRegion2() + " " +  mainPageViewPagerObject.getRegion3());
        aptSize.setText(mainPageViewPagerObject.getApt() + " / " + mainPageViewPagerObject.getSize());
        type.setText(mainPageViewPagerObject.getType());

        currentQuotation.setText(String.valueOf(mainPageViewPagerObject.getCurrentQuotation()));

        if(mainPageViewPagerObject.getOngoingStatus().equals("견적접수중")){
            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_interection_waiting));

            mainPageViewPagerObject.setLeftSecond(CommonClass.timeLeftSecondParsing(mainPageViewPagerObject.getLeftTime()));
            int leftSecond  = CommonClass.timeLeftSecondParsing(mainPageViewPagerObject.getLeftTime());
            int hour = leftSecond/3600;
            int minute = leftSecond%3600;
            minute = minute/60;

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int leftSecond = mainPageViewPagerObject.getLeftSecond()-1;
                            mainPageViewPagerObject.setLeftSecond(leftSecond);
                            int hour = leftSecond/3600;
                            int minute = leftSecond%3600;
                            minute = minute/60;

                            if(leftSecond > 0){
                                if(hour<10 && minute<10){
                                    leftTime.setText("마감까지 " + "0"+ hour + ":" +"0"+ minute + " 남았습니다.");
                                } else if(hour<10){
                                    leftTime.setText("마감까지 " + "0" + hour + ":" + minute + " 남았습니다.");
                                }else if(minute<10){
                                    leftTime.setText("마감까지 " + hour + ":" + "0" +minute + " 남았습니다.");
                                }else{
                                    leftTime.setText("마감까지 " + hour + ":" + minute + " 남았습니다.");
                                }
                            }else{
                                leftTime.setText("마감까지 " + "00" + ":" +"00" + " 남았습니다.");
                            }
                        }
                    });
                }
            };

            timer = new Timer();
            timer.schedule(timerTask,1000,1000);

            if(leftSecond > 0){
                if(hour<10 && minute<10){
                    leftTime.setText("마감까지 " + "0"+ hour + ":" +"0"+ minute + " 남았습니다.");
                } else if(hour<10){
                    leftTime.setText("마감까지 " + "0" + hour + ":" + minute + " 남았습니다.");
                }else if(minute<10){
                    leftTime.setText("마감까지 " + hour + ":" + "0" +minute + " 남았습니다.");
                }else{
                    leftTime.setText("마감까지 " + hour + ":" + minute + " 남았습니다.");
                }
            }else{
                leftTime.setText("마감까지 " + "00" + ":" +"00" + " 남았습니다.");
                timer.cancel();
            }

        }else if(mainPageViewPagerObject.getOngoingStatus().equals("선택대기중")){
            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(mainPageViewPagerObject.getOngoingStatus() + "입니다.");
        }else if(mainPageViewPagerObject.getOngoingStatus().equals("상담중")){
            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(mainPageViewPagerObject.getOngoingStatus() + "입니다.");
        }else if(mainPageViewPagerObject.getOngoingStatus().equals("심사중")){
            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(mainPageViewPagerObject.getOngoingStatus() + "입니다.");
        }else if(mainPageViewPagerObject.getOngoingStatus().equals("승인완료")){
            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_ongoing));
            leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(mainPageViewPagerObject.getOngoingStatus() + " 되었습니다.");
        }else{
            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ongoing_quotation_fixed_done));
            leftTime.setTextColor(ResourcesCompat.getColor(getActivity().getResources(),R.color.progress,null));
            leftTime.setText(mainPageViewPagerObject.getOngoingStatus() + " 되었습니다.");
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuotationDetailActivity.class);
                intent.putExtra("id",mainPageViewPagerObject.getId());
                getActivity().startActivity(intent);
                Toast.makeText(getActivity(),"id = " + mainPageViewPagerObject.getId() + " 상세로 이동.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        if(timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }

}
