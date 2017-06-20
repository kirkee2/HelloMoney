package com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.MainActivity;
import com.beta.tacademy.hellomoneycustomer.activity.PostscriptDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.RequestQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.listener.EndlessScrollListener;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_SUB = 1;
    public static final int TYPE_HEADER_SUB_SUB = 2;
    public static final int TYPE_ITEM = 3;

    public static final int NO_MY_QUOTATION =0;
    public static final int YES_MY_QUOTATION =1;

    private MainFragmentPagerAdapter pagerAdapter;
    private Activity activity;
    private int position;

    private ArrayList<MainValueObject> mainValueObjectArrayList;
    private int type;
    EndlessScrollListener endlessScrollListener;

    public void initItem(ArrayList<MainValueObject> mainValueObjectArrayList){
        this.mainValueObjectArrayList = mainValueObjectArrayList; //아이템 추가
        notifyDataSetChanged();
    }

    public void updateItem(ArrayList<MainValueObject> mainValueObject){
        this.mainValueObjectArrayList.addAll(mainValueObject); //아이템 추가
        notifyDataSetChanged();
    }

    public void initHeader(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList, int position){
        pagerAdapter.init(mainPageViewPagerObjectArrayList); //헤더 아이템 추가
        this.position = position;
        notifyDataSetChanged();
    }

    public MainRecyclerViewAdapter(Activity activity,FragmentManager fragmentManager,int type){
        //변수 초기화
        this.type = type;
        this.activity = activity;
        this.endlessScrollListener = (MainActivity)activity;
        mainValueObjectArrayList = new ArrayList<>();
        position = 0;
        pagerAdapter = new MainFragmentPagerAdapter(fragmentManager);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //Header와 Item를 구분하여 view 설정

        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_header, parent, false);
            return new MainHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_header_sub, parent, false);
            return new MainSubHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_header_sub_sub, parent, false);
            return new MainSubSubHeaderViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_items, parent, false);
            return new MainViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //position의 type 반환
        if(type == NO_MY_QUOTATION){
            if (position == 0){
                return TYPE_HEADER;
            }else if (position == 1){
                return TYPE_HEADER_SUB_SUB;
            }else{
                return TYPE_ITEM;
            }
        }else{
            if (position == 0){
                return TYPE_HEADER;
            }else if (position == 1){
                return TYPE_HEADER_SUB;
            }else if (position == 2){
                return TYPE_HEADER_SUB_SUB;
            }else{
                return TYPE_ITEM;
            }
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
            typeImage = (ImageView) itemView.findViewById(R.id.typeImage);
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

    private class MainSubHeaderViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TabLayout tabLayout;
        private MainSubHeaderViewHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.pager);
            tabLayout = (TabLayout) itemView.findViewById(R.id.tabDots);
        }
    }

    private class MainSubSubHeaderViewHolder extends RecyclerView.ViewHolder {

        private MainSubSubHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MainHeaderViewHolder){
            ((MainHeaderViewHolder) holder).requestQuotation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(activity, RequestQuotationActivity.class);

                    activity.startActivityForResult(intent,1);
                    //Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"견적 요청 페이지로 이동",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof MainViewHolder) {
            final MainValueObject valueObject;
            if(type == NO_MY_QUOTATION){
                valueObject = mainValueObjectArrayList.get(position-2);
            }else{
                valueObject = mainValueObjectArrayList.get(position-3);
            }

            if(valueObject.getLoanType().equals("주택담보대출")){
                ((MainViewHolder) holder).typeImage.setImageResource(R.drawable.secured_loan);
            }else{
                ((MainViewHolder) holder).typeImage.setImageResource(R.drawable.lease_loan);
            }

            ((MainViewHolder) holder).region.setText(valueObject.getRegion1() +" "+ valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((MainViewHolder) holder).apt.setText(valueObject.getApt());
            ((MainViewHolder) holder).starRatingBar.setRating(valueObject.getStar());
            ((MainViewHolder) holder).starRatingBar.setEnabled(false);
            ((MainViewHolder) holder).content.setText(valueObject.getContent());

            ((MainViewHolder) holder).pastTime.setText(CommonClass.timeParsing(valueObject.getPastTime()));


            if( valueObject.getBenefit() < 0){
                String benefitString = CommonClass.formatMoney(valueObject.getBenefit()) + "만원 대출 조건 보기";
                final String tmp = valueObject.getBenefit() + "";
                SpannableStringBuilder builder = new SpannableStringBuilder(benefitString);

                builder.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(activity.getResources(),R.color.colorAccent,null)), 0, tmp.length()+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ((MainViewHolder) holder).benefit.setText(builder);
            }else{
                String benefitString = "+" + CommonClass.formatMoney(valueObject.getBenefit()) + "만원 대출 조건 보기";
                final String tmp = "+"+valueObject.getBenefit() + "";
                SpannableStringBuilder builder = new SpannableStringBuilder(benefitString);

                builder.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(activity.getResources(),R.color.colorPrimaryDark,null)), 0, tmp.length()+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ((MainViewHolder) holder).benefit.setText(builder);
            }

            ((MainViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, PostscriptDetailActivity.class);
                    intent.putExtra("id",valueObject.getId());
                    activity.startActivityForResult(intent,2);
                    //Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"id = " +valueObject.getId(),Toast.LENGTH_SHORT).show();
                }
            });
        }else if (holder instanceof MainSubSubHeaderViewHolder) {
        }else {
            ((MainSubHeaderViewHolder) holder).viewPager.setAdapter(pagerAdapter);
            ((MainSubHeaderViewHolder) holder).tabLayout.setupWithViewPager(((MainSubHeaderViewHolder) holder).viewPager, true);
            ((MainSubHeaderViewHolder) holder).viewPager.setCurrentItem(this.position);
            pagerAdapter.notifyDataSetChanged();
        }

        if(position == getItemCount() - 1) {
            if(endlessScrollListener != null) {
                endlessScrollListener.onLoadMore(position+1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(type == NO_MY_QUOTATION){
            return mainValueObjectArrayList.size()+2;
        }else{
            return mainValueObjectArrayList.size()+3;
        }
 //전체 item의 갯수 반환
    }
}
