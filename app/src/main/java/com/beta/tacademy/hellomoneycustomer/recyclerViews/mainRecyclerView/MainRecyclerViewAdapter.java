package com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_SUB = 1;
    public static final int TYPE_HEADER_SUB_SUB = 2;
    public static final int TYPE_ITEM = 3;

    private ArrayList<MainValueObject> mainList;

    public void addMember(MainValueObject mainValueObject){
        mainList.add(mainValueObject); //아이템 추가
    }

    public void addHeader(String valueObject){
        //pagerAdapter.add(valueObject); //헤더 아이템 추가
    }

    public void updateImage(){
        notifyDataSetChanged(); //데이터 변경 사실 알림
    }

    public MainRecyclerViewAdapter(FragmentManager fragmentManager){
        //변수 초기화

        mainList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //Header와 Item를 구분하여 view 설정

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_items, parent, false);
            return new MainViewHolder(view);
        }else if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_header, parent, false);
            return new MainHeaderSubSubViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_header_sub_sub, parent, false);
            return new MainHeaderSubSubViewHolder(view);
        }else{
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //position의 type 반환
        if (position == 0){
            return TYPE_HEADER;
        }else if (position == 1){
            return TYPE_HEADER_SUB_SUB;
        }else{
            return TYPE_ITEM;
        }
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ImageView typeImage;
        TextView region;
        TextView apt;
        RatingBar starRatingBar;
        TextView pastTime;
        TextView content;
        TextView benefit;


        private MainViewHolder(View itemView) {
            super(itemView);
            typeImage = (ImageView) itemView.findViewById(R.id.typeImage);;
            region = (TextView)itemView.findViewById(R.id.region);
            apt = (TextView)itemView.findViewById(R.id.apt);
            starRatingBar = (RatingBar)itemView.findViewById(R.id.starRatingBar);
            pastTime = (TextView)itemView.findViewById(R.id.pastTime);
            content = (TextView)itemView.findViewById(R.id.content);
            benefit = (TextView)itemView.findViewById(R.id.benefit);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }

    private class MainHeaderViewHolder extends RecyclerView.ViewHolder {

        Button requestQuotation;
        private MainHeaderViewHolder(View itemView) {
            super(itemView);
            requestQuotation = (Button)itemView.findViewById(R.id.requestQuotation);
        }
    }


    private class MainHeaderSubSubViewHolder extends RecyclerView.ViewHolder {

        private MainHeaderSubSubViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //final MainValueObject valueObject = mainList.get(position-1);

        if(holder instanceof MainHeaderViewHolder){
            ((MainHeaderViewHolder) holder).requestQuotation.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"견적 요청 페이지로 이동",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof MainViewHolder) {
            final MainValueObject valueObject = mainList.get(position-2);

            if(valueObject.getType() == 0){
                ((MainViewHolder) holder).typeImage.setImageResource(R.drawable.lease_loan);
            }else{
                ((MainViewHolder) holder).typeImage.setImageResource(R.drawable.secured_loan);
            }

            ((MainViewHolder) holder).region.setText(valueObject.getRegion1() +" "+ valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((MainViewHolder) holder).apt.setText(valueObject.getApt());
            ((MainViewHolder) holder).starRatingBar.setRating(valueObject.getStar());
            ((MainViewHolder) holder).pastTime.setText(valueObject.getPastTime());
            ((MainViewHolder) holder).content.setText(valueObject.getContent());

            String benefitString = "+ " + valueObject.getBenefit() + "만원 대출 조건 보기";
            final String tmp = valueObject.getBenefit() + "";
            SpannableStringBuilder builder = new SpannableStringBuilder(benefitString);

            builder.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(HelloMoneyCustomerApplication.getInstance().getResources(),R.color.colorPrimaryDark,null)), 0, tmp.length()+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //builder.setSpan(new AbsoluteSizeSpan(20,true), 0, textsize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((MainViewHolder) holder).benefit.setText(builder);

            ((MainViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"id = " +valueObject.getId(),Toast.LENGTH_SHORT).show();
                }
            });
        }else if (holder instanceof MainHeaderSubSubViewHolder) {
        }else {

        }

    }

    @Override
    public int getItemCount() {
        return mainList.size()+2; //전체 item의 갯수 반환
    }
}
