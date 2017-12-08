package com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.MyQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.activity.QuotationDetailActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.common.util.StringUtil;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView.QuotationDetailRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.sql.Time;
import java.util.ArrayList;


public class MyQuotationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList;
    private Activity activity;
    private int myPage;

    public MyQuotationRecyclerViewAdapter(Activity activity){
        //변수 초기화
        this.activity = activity;
        mainPageViewPagerObjectArrayList = new ArrayList<>();
    }

    public void init(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList,int myPage){
        this.mainPageViewPagerObjectArrayList = mainPageViewPagerObjectArrayList; //아이템 추가
        this.myPage = myPage;
        notifyDataSetChanged();
    }

    public void update(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList,int myPage){
        this.mainPageViewPagerObjectArrayList = mainPageViewPagerObjectArrayList; //아이템 추가
        this.myPage = myPage;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_quotation_recycler_view_item, parent, false);
        return new MyQuotationRecyclerViewAdapter.MyQuotationViewHolder(view);
    }

    private class MyQuotationViewHolder extends RecyclerView.ViewHolder {
        //TextView region;
        TextView apt;
        //TextView size;
       //TextView type;
        TextView currentQuotation;
        TextView leftTime;
        LinearLayout linearLayout;
        TextView ongoingSub;

        private MyQuotationViewHolder(View itemView) {
            super(itemView);
            //region = (TextView)itemView.findViewById(R.id.region);
            apt = (TextView)itemView.findViewById(R.id.apt);
            //size = (TextView) itemView.findViewById(R.id.size);
            //type = (TextView)itemView.findViewById(R.id.type);
            currentQuotation = (TextView)itemView.findViewById(R.id.currentQuotation);
            leftTime = (TextView)itemView.findViewById(R.id.leftTime);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
            ongoingSub = (TextView)itemView.findViewById(R.id.ongoing_sub);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MainPageViewPagerObject valueObject = mainPageViewPagerObjectArrayList.get(position);

        //((MyQuotationViewHolder) holder).region.setText(valueObject.getRegion1() + " " + valueObject.getRegion2() + " " +  valueObject.getRegion3());

        ((MyQuotationViewHolder) holder).apt.setText(valueObject.getApt());
        //((MyQuotationViewHolder) holder).size.setText(valueObject.getSize());
        //((MyQuotationViewHolder) holder).type.setText(valueObject.getType());

        ((MyQuotationViewHolder) holder).ongoingSub.setText(valueObject.getOngoingStatus());
        ((MyQuotationViewHolder) holder).currentQuotation.setText(String.valueOf(valueObject.getCurrentQuotation()));

        if(valueObject.getOngoingStatus().equals("견적접수중")){
            //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_interection_waiting));
            //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.pointTypo,null));

            int leftSecond  = valueObject.getLeftSecond();
            int hour = leftSecond/3600;
            int tmp = leftSecond%3600;
            int minute = tmp/60;
            int second = tmp%60;

            if(leftSecond > 0){
                ((MyQuotationViewHolder) holder).leftTime.setText("마감 " + StringUtil.formatNumber2(hour) + "시간 " + StringUtil.formatNumber2(minute)  + "분 " + StringUtil.formatNumber2(second) + "초");
            }else{
                //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_ongoing));
                //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
                ((MyQuotationViewHolder) holder).leftTime.setText("00시간 00분 00초");
                valueObject.setOngoingStatus("선택대기중");
            }

        }else if(valueObject.getOngoingStatus().equals("선택대기중")){
            //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_ongoing));
            //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
            ((MyQuotationViewHolder) holder).leftTime.setText("00시간 00분 00초");
            //((MyQuotationViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step2_small, 0, 0, 0);

        }else if(valueObject.getOngoingStatus().equals("상담중")){
            //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_ongoing));
            //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
            ((MyQuotationViewHolder) holder).leftTime.setText("00시간 00분 00초");
            //((MyQuotationViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step3_small, 0, 0, 0);

        }else if(valueObject.getOngoingStatus().equals("심사중")){
            //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_ongoing));
            //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
            ((MyQuotationViewHolder) holder).leftTime.setText("00시간 00분 00초");
            //((MyQuotationViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step4_small, 0, 0, 0);

        }else if(valueObject.getOngoingStatus().equals("승인완료")){
            //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_ongoing));
            //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
            ((MyQuotationViewHolder) holder).leftTime.setText("00시간 00분 00초");
            //((MyQuotationViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step5_small, 0, 0, 0);

        }else{
            //((MyQuotationViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_fixed_done));
            //((MyQuotationViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
            //((MyQuotationViewHolder) holder).leftTime.setText("퍼센트 사용 후기를 남겨주세요.");
            ((MyQuotationViewHolder) holder).leftTime.setText("00시간 00분 00초");

            //((MyQuotationViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step6_small, 0, 0, 0);
        }

        ((MyQuotationViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuotationDetailActivity.class);
                intent.putExtra("id",valueObject.getId());
                intent.putExtra("myPage",myPage);
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainPageViewPagerObjectArrayList.size();
        //전체 item의 갯수 반환
    }
}
