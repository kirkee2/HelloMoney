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
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class QuotationDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_SUB = 1;
    public static final int TYPE_HEADER_SUB_SUB = 2;
    public static final int TYPE_HEADER_SUB_SUB_SUB = 3;
    public static final int TYPE_ITEM = 4;

    private ArrayList<QuotationDetailObject> quotationDetailObjectArrayList;
    private QuotationDetailHeaderObject quotationDetailHeaderObject;

    public void addMember(QuotationDetailObject quotationDetailObject) {
        quotationDetailObjectArrayList.add(quotationDetailObject); //아이템 추가
    }

    public QuotationDetailRecyclerViewAdapter(QuotationDetailHeaderObject quotationDetailHeaderObject){
        //변수 초기화
        quotationDetailObjectArrayList = new ArrayList<>();
        this.quotationDetailHeaderObject = quotationDetailHeaderObject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header, parent, false);
            return new QuotationDetailHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub, parent, false);
            return new QuotationDetailSubHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub_sub, parent, false);
            return new QuotationDetailSubSubHeaderViewHolder(view);
        }else if(viewType == TYPE_HEADER_SUB_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub_sub_sub, parent, false);
            return new QuotationDetailSubSubSubHeaderViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_items, parent, false);
            return new QuotationDetailViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
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
        BarChart barChart;
        private QuotationDetailSubHeaderViewHolder(View itemView) {
            super(itemView);
            barChart = (BarChart) itemView.findViewById(R.id.barChart);
        }
    }

    private class QuotationDetailSubSubHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView finalQuotationCount;
        TextView averageInterestRate;
        private QuotationDetailSubSubHeaderViewHolder(View itemView) {
            super(itemView);
            finalQuotationCount = (TextView)itemView.findViewById(R.id.finalQuotationCount);
            averageInterestRate = (TextView)itemView.findViewById(R.id.averageInterestRate);
        }
    }
    private class QuotationDetailSubSubSubHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView remainTime;
        ImageView loanType;
        TextView region;
        TextView apt;
        TextView size;
        TextView loanSum;
        TextView rateType;
        TextView loanDate;
        TextView jobtype;
        TextView telephone;

        private QuotationDetailSubSubSubHeaderViewHolder(View itemView) {
            super(itemView);
            remainTime = (TextView)itemView.findViewById(R.id.remainTime);
            loanType = (ImageView) itemView.findViewById(R.id.loanType);
            region = (TextView)itemView.findViewById(R.id.region);
            apt = (TextView)itemView.findViewById(R.id.apt);
            size = (TextView)itemView.findViewById(R.id.size);
            loanSum = (TextView)itemView.findViewById(R.id.loanSum);
            rateType = (TextView)itemView.findViewById(R.id.rateType);
            loanDate = (TextView)itemView.findViewById(R.id.loanDate);
            jobtype = (TextView)itemView.findViewById(R.id.jobType);
            telephone = (TextView)itemView.findViewById(R.id.telephone);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof QuotationDetailHeaderViewHolder){
        } else if (holder instanceof QuotationDetailSubHeaderViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<BarEntry> entriesMin = new ArrayList<>();
            float min = 100;
            int minIndex = 0;
            for(int i = 0 ; i <valueObject.getRate().size() ; i++){
                if(valueObject.getRate().get(i) < min){
                    min = valueObject.getRate().get(i);
                    minIndex = i;
                }
                entries.add(new BarEntry(i*0.5F,valueObject.getRate().get(i)));
            }

            entriesMin.add(entries.get(minIndex));
            entries.remove(minIndex);

            Description d = new Description();
            d.setText("");

            BarDataSet dataSet = new BarDataSet(entries, "이자율");
            BarDataSet dataSetMin = new BarDataSet(entriesMin, "최저");

            dataSet.setColor(0xFF00BFA5);
            dataSet.setHighlightEnabled(false);
            dataSet.setValueTextSize(10);

            dataSetMin.setColor(0xFFFF4081);
            dataSetMin.setHighlightEnabled(false);
            dataSetMin.setValueTextSize(10);

            BarData data = new BarData(dataSet);
            data.addDataSet(dataSetMin);

            data.setBarWidth(0.15F);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.setData(data);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.getAxisRight().setEnabled(false);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.getXAxis().setEnabled(false);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.setDescription(d);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.setEnabled(false);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.animateXY(2000, 2000);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.setDoubleTapToZoomEnabled(false);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.setScaleEnabled(false);
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.invalidate();

        }else if (holder instanceof QuotationDetailSubSubHeaderViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ((QuotationDetailSubSubHeaderViewHolder) holder).finalQuotationCount.setText(String.valueOf(valueObject.getRate().size()));
            double tmp = 0;
            for(int i = 0 ; i < valueObject.getRate().size() ; i++){
                tmp += valueObject.getRate().get(i);
            }

            tmp = tmp/(double)valueObject.getRate().size();
            ((QuotationDetailSubSubHeaderViewHolder) holder).averageInterestRate.setText(String.valueOf(tmp));

        }else if (holder instanceof QuotationDetailSubSubSubHeaderViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ((QuotationDetailSubSubSubHeaderViewHolder) holder).remainTime.setText("마감까지 " + valueObject.getRemainTime()+ " 남았습니다.");
            if(valueObject.getLoanType() == 0 ){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.lease_loan);
            }else{
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.secured_loan);
            }

            ((QuotationDetailSubSubSubHeaderViewHolder) holder).region.setText(valueObject.getRegion1() + " " + valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).apt.setText(valueObject.getApt());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).size.setText(valueObject.getSize());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanSum.setText(String.valueOf(valueObject.getLoanSum()));

            if(valueObject.getRateType() == 0){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).rateType.setText("고정");
            }else{
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).rateType.setText("변동");
            }

            ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanDate.setText(valueObject.getLoanDate());

            if(valueObject.getJobType() == 0){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).jobtype.setText("직장근로자");
            }else{
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).jobtype.setText("자영업자");
            }

           ((QuotationDetailSubSubSubHeaderViewHolder) holder).telephone.setText(valueObject.getTelephone());
        }else {

        }
    }

    @Override
    public int getItemCount() {
        return quotationDetailObjectArrayList.size()+4;
        //전체 item의 갯수 반환
    }
}
