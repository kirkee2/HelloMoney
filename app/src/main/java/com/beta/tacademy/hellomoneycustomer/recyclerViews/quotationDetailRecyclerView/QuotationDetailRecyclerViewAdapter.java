package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;

import java.util.ArrayList;


public class QuotationDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_SUB = 1;
    public static final int TYPE_HEADER_SUB_SUB = 2;
    public static final int TYPE_HEADER_SUB_SUB_SUB = 3;
    public static final int TYPE_ITEM = 4;

    int type;

    private ArrayList<QuotationDetailObject> quotationDetailObjectArrayList;

    public void addMember(QuotationDetailObject quotationDetailObject) {
        quotationDetailObjectArrayList.add(quotationDetailObject); //아이템 추가
    }

    public QuotationDetailRecyclerViewAdapter(int type){
        //변수 초기화
        quotationDetailObjectArrayList = new ArrayList<>();
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //Header와 Item를 구분하여 view 설정

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_items, parent, false);
            return new QuotationDetailViewHolder(view);
        }else if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header, parent, false);
            return new QuotationDetailSubHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub, parent, false);
            return new QuotationDetailHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub_sub, parent, false);
            return new QuotationDetailSubSubHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub_sub_sub, parent, false);
            return new QuotationDetailSubSubSubHeaderViewHolder(view);
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
            return TYPE_HEADER_SUB;
        }else if (position == 2){
            return TYPE_HEADER_SUB_SUB;
        }else if (position == 3){
            return TYPE_HEADER_SUB_SUB_SUB;
        }else{
            return TYPE_ITEM;
        }
    }

    private class QuotationDetailViewHolder extends RecyclerView.ViewHolder {

        private QuotationDetailViewHolder(View itemView) {
            super(itemView);

        }
    }

    private class QuotationDetailHeaderViewHolder extends RecyclerView.ViewHolder {

        private QuotationDetailHeaderViewHolder(View itemView) {
            super(itemView);

        }
    }

    private class QuotationDetailSubHeaderViewHolder extends RecyclerView.ViewHolder {

        private QuotationDetailSubHeaderViewHolder(View itemView) {
            super(itemView);

        }
    }

    private class QuotationDetailSubSubHeaderViewHolder extends RecyclerView.ViewHolder {

        private QuotationDetailSubSubHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class QuotationDetailSubSubSubHeaderViewHolder extends RecyclerView.ViewHolder {

        private QuotationDetailSubSubSubHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof QuotationDetailHeaderViewHolder){

        } else if (holder instanceof QuotationDetailSubHeaderViewHolder) {
            //final MainValueObject valueObject;

        }else if (holder instanceof QuotationDetailSubSubHeaderViewHolder) {
        }else if (holder instanceof QuotationDetailSubSubSubHeaderViewHolder) {
        }else {

        }

    }

    @Override
    public int getItemCount() {
        return quotationDetailObjectArrayList.size()+4;

        //전체 item의 갯수 반환
    }
}
