package com.beta.tacademy.hellomoneycustomer.recyclerViews.counselorDetailRecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.PostscriptDetailActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CounselorDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private Activity activity;
    private CounselorDetailHeaderObject counselorDetailHeaderObject;
    private ArrayList<MainValueObject> mainValueObjectArrayList;

    public void addItem(MainValueObject mainValueObject){
        mainValueObjectArrayList.add(mainValueObject); //아이템 추가
    }

    public CounselorDetailRecyclerViewAdapter(Activity activity,CounselorDetailHeaderObject counselorDetailHeaderObject){
        //변수 초기화
        this.activity = activity;
        mainValueObjectArrayList = new ArrayList<>();
        this.counselorDetailHeaderObject = counselorDetailHeaderObject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //Header와 Item를 구분하여 view 설정

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_items, parent, false);
            return new CounselorDetailViewHolder(view);
        }else if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.counselor_detail_recycler_view_header, parent, false);
            return new CounselorDetailHeaderViewHolder(view);
        }else{
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //position의 type 반환
        if (position == 0){
            return TYPE_HEADER;
        }else{
            return TYPE_ITEM;
        }
    }

    private class CounselorDetailHeaderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView bank;
        TextView name;
        TextView introductionMent;

        private CounselorDetailHeaderViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView)itemView.findViewById(R.id.image);
            bank = (TextView)itemView.findViewById(R.id.bank);
            name = (TextView)itemView.findViewById(R.id.name);
            introductionMent = (TextView)itemView.findViewById(R.id.introductionMent);
        }
    }

    private class CounselorDetailViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView typeImage;
        TextView region;
        TextView apt;
        RatingBar starRatingBar;
        TextView pastTime;
        TextView content;
        TextView benefit;

        private CounselorDetailViewHolder(View itemView) {
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof CounselorDetailHeaderViewHolder){
            final CounselorDetailHeaderObject valueObject = counselorDetailHeaderObject;

            Glide.with(activity)
                    .load(valueObject.getImageUrl())
                    .animate(android.R.anim.slide_in_left)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(((CounselorDetailHeaderViewHolder) holder).image);

            ((CounselorDetailHeaderViewHolder) holder).bank.setText(valueObject.getBank());
            ((CounselorDetailHeaderViewHolder) holder).name.setText(valueObject.getName());
            ((CounselorDetailHeaderViewHolder) holder).introductionMent.setText(valueObject.getIntroductionMent());
        } else{
            final MainValueObject valueObject = mainValueObjectArrayList.get(position-1);

            if(valueObject.getLoanType() == 0){
                ((CounselorDetailViewHolder) holder).typeImage.setImageResource(R.drawable.lease_loan);
            }else{
                ((CounselorDetailViewHolder) holder).typeImage.setImageResource(R.drawable.secured_loan);
            }

            ((CounselorDetailViewHolder) holder).region.setText(valueObject.getRegion1() +" "+ valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((CounselorDetailViewHolder) holder).apt.setText(valueObject.getApt());
            ((CounselorDetailViewHolder) holder).starRatingBar.setRating(valueObject.getStar());
            ((CounselorDetailViewHolder) holder).starRatingBar.setEnabled(false);
            ((CounselorDetailViewHolder) holder).pastTime.setText(valueObject.getPastTime());
            ((CounselorDetailViewHolder) holder).content.setText(valueObject.getContent());

            String benefitString = "+ " + valueObject.getBenefit() + "만원 대출 조건 보기";
            final String tmp = valueObject.getBenefit() + "";
            SpannableStringBuilder builder = new SpannableStringBuilder(benefitString);

            builder.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(HelloMoneyCustomerApplication.getInstance().getResources(),R.color.colorPrimaryDark,null)), 0, tmp.length()+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //builder.setSpan(new AbsoluteSizeSpan(20,true), 0, textsize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((CounselorDetailViewHolder) holder).benefit.setText(builder);

            ((CounselorDetailViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HelloMoneyCustomerApplication.getInstance(), PostscriptDetailActivity.class);
                    intent.putExtra("id",valueObject.getId());
                    HelloMoneyCustomerApplication.getInstance().startActivity(intent);
                    //Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"id = " +valueObject.getId(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mainValueObjectArrayList.size()+1;
        //전체 item의 갯수 반환
    }
}