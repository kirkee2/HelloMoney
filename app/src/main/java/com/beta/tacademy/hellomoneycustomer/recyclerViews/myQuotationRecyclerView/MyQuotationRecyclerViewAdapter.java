package com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.beta.tacademy.hellomoneycustomer.activity.QuotationDetailActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 5. 31..
 */

public class MyQuotationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MainPageViewPagerObject> mainPageViewPagerObjects;

    public MyQuotationRecyclerViewAdapter(){
        //변수 초기화
        mainPageViewPagerObjects = new ArrayList<>();
    }

    public void init(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList){
        mainPageViewPagerObjects = mainPageViewPagerObjectArrayList; //아이템 추가
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_quotation_recycler_view_item, parent, false);
        return new MyQuotationRecyclerViewAdapter.MyQuotationViewHolder(view);
    }

    private class MyQuotationViewHolder extends RecyclerView.ViewHolder {
        TextView region;
        TextView aptSize;
        TextView type;
        TextView currentQuotation;
        TextView leftTime;
        LinearLayout linearLayout;

        private MyQuotationViewHolder(View itemView) {
            super(itemView);
            region = (TextView)itemView.findViewById(R.id.region);
            aptSize = (TextView)itemView.findViewById(R.id.aptSize);
            type = (TextView)itemView.findViewById(R.id.type);
            currentQuotation = (TextView)itemView.findViewById(R.id.currentQuotation);
            leftTime = (TextView)itemView.findViewById(R.id.leftTime);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MainPageViewPagerObject valueObject = mainPageViewPagerObjects.get(position);


        ((MyQuotationViewHolder) holder).region.setText(valueObject.getRegion1() + " " + valueObject.getRegion2() + " " +  valueObject.getRegion3());

        ((MyQuotationViewHolder) holder).aptSize.setText(valueObject.getApt() + " / " + valueObject.getSize());
        ((MyQuotationViewHolder) holder).type.setText(valueObject.getType());

        ((MyQuotationViewHolder) holder).currentQuotation.setText(String.valueOf(valueObject.getCurrentQuotation()));
        ((MyQuotationViewHolder) holder).leftTime.setText("마감까지 " + valueObject.getLeftTime() + " 남았습니다.");

        ((MyQuotationViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"id = " + valueObject.getId() + " 상세로 이동.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HelloMoneyCustomerApplication.getInstance(), QuotationDetailActivity.class);
                intent.putExtra("id",valueObject.getId());
                HelloMoneyCustomerApplication.getInstance().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mainPageViewPagerObjects.size();
        //전체 item의 갯수 반환
    }
}
