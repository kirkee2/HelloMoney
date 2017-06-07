package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.CounselorDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.PostscriptDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.RequestQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class QuotationDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_SUB = 1;
    public static final int TYPE_HEADER_SUB_SUB = 2;
    public static final int TYPE_HEADER_SUB_SUB_SUB = 3;
    public static final int TYPE_ITEM = 4;

    private Activity activity;

    private ArrayList<QuotationDetailObject> quotationDetailObjectArrayList;
    private QuotationDetailHeaderObject quotationDetailHeaderObject;

    public void addItem(QuotationDetailObject quotationDetailObject) {
        quotationDetailObjectArrayList.add(quotationDetailObject); //아이템 추가
    }

    public void initItem(ArrayList<QuotationDetailObject> quotationDetailObjectArrayList) {
        this.quotationDetailObjectArrayList = quotationDetailObjectArrayList; //아이템 추가
    }

    public QuotationDetailRecyclerViewAdapter(Activity activity,QuotationDetailHeaderObject quotationDetailHeaderObject){
        //변수 초기화
        quotationDetailObjectArrayList = new ArrayList<>();
        this.activity = activity;
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
        TextView bank;
        TextView name;
        TextView loanType;
        TextView interestRate;
        CircleImageView image;
        CardView cardView;

        private QuotationDetailViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            bank = (TextView)itemView.findViewById(R.id.bank);
            name = (TextView)itemView.findViewById(R.id.name);
            loanType = (TextView)itemView.findViewById(R.id.loanType);
            interestRate = (TextView)itemView.findViewById(R.id.interestRate);
            image = (CircleImageView)itemView.findViewById(R.id.image);
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

            BarDataSet dataSet = new BarDataSet(entries, "금리");
            BarDataSet dataSetMin = new BarDataSet(entriesMin, "최저 금리");

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
            ((QuotationDetailSubHeaderViewHolder) holder).barChart.animateY(500);
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
            final QuotationDetailObject valueObject  = quotationDetailObjectArrayList.get(position-4);

            ((QuotationDetailViewHolder) holder).bank.setText(valueObject.getBank());
            ((QuotationDetailViewHolder) holder).name.setText(valueObject.getName());
            ((QuotationDetailViewHolder) holder).interestRate.setText(String.valueOf(valueObject.getInterestRate())+"%");

            if(valueObject.getLoanType() == 0){
                ((QuotationDetailViewHolder) holder).loanType.setText("고정금리");
            }else{
                ((QuotationDetailViewHolder) holder).loanType.setText("변동금리");
            }
            Glide.with(activity)
                    .load(valueObject.getImageUrl())
                    .animate(android.R.anim.slide_in_left)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(((QuotationDetailViewHolder) holder).image);

            ((QuotationDetailViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQuotationDialog requestQuotationDialog = new RequestQuotationDialog(activity);
                    requestQuotationDialog.setInfo(valueObject.getId());

                    requestQuotationDialog.show();
                    //Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"id = " + valueObject.getId(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return quotationDetailObjectArrayList.size()+4;
        //전체 item의 갯수 반환
    }

    private class RequestQuotationDialog extends Dialog {

        private int id;
        private CircleImageView image;
        private TextView bank;
        private TextView name;
        private TextView goCounselor;
        private TextView loanName;
        private TextView finalRegisterDate;
        private TextView loanInterestRate;
        private TextView interestType;
        private TextView monthlyRepayMoney;
        private TextView repayType;
        private TextView interestRateInfo1;
        private TextView interestRateInfo2;
        private TextView feeInfo1;
        private TextView feeInfo2;
        private TextView back;
        private TextView requestCounsel;

        private String imageTmp;
        private String bankTmp;
        private String nameTmp;
        private String loanNameTmp;
        private String finalRegisterDateTmp;
        private String loanInterestRateTmp;
        private String interestTypeTmp;
        private String monthlyRepayMoneyTmp;
        private String repayTypeTmp;
        private String interestRateInfo1Tmp;
        private String interestRateInfo2Tmp;
        private String feeInfo1Tmp;
        private String feeInfo2Tmp;

        public RequestQuotationDialog(@NonNull Context context) {
            super(context);
        }

        public void setInfo(int id){
            this.id = id;
            imageTmp = "http://img.visualdive.co.kr/sites/2/2015/10/gisa2.jpg";
            bankTmp = "외환은행";
            nameTmp = "이건준";
            loanNameTmp = "[KB국민 주택담보 대출]";
            finalRegisterDateTmp = "최종 등록일 : 2017.05.22";
            loanInterestRateTmp = "3.8%";
            interestTypeTmp = "변동금리";
            monthlyRepayMoneyTmp = "890,000원";
            repayTypeTmp = "원리금 균등상환";
            interestRateInfo1Tmp = "90일 미만 : 대출 금리 + 8%";
            interestRateInfo2Tmp = "90일 이상 : 대출 금리 + 9%(최고 연체 이자율 : 16%)";
            feeInfo1Tmp = "조기 상환 원금 X 1.4% X [(3년 대출 경과 일 수 / 3년)]";
            feeInfo2Tmp = "매년 대출 잔액의 10%까지 중도 상환 수수료 면제";
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.request_counsel_dialog);
            image = (CircleImageView)findViewById(R.id.image);
            bank = (TextView)findViewById(R.id.bank);
            goCounselor = (TextView)findViewById(R.id.goCounselor);
            loanName = (TextView)findViewById(R.id.loanName);
            finalRegisterDate = (TextView)findViewById(R.id.finalRegisterDate);
            loanInterestRate = (TextView)findViewById(R.id.loanInterestRate);
            interestType = (TextView)findViewById(R.id.interestType);
            monthlyRepayMoney = (TextView)findViewById(R.id.monthlyRepayMoney);
            repayType = (TextView)findViewById(R.id.repayType);
            interestRateInfo1 = (TextView)findViewById(R.id.interestRateInfo1);
            interestRateInfo2 = (TextView)findViewById(R.id.interestRateInfo2);
            feeInfo1 = (TextView)findViewById(R.id.feeInfo1);
            feeInfo2 = (TextView)findViewById(R.id.feeInfo2);
            back = (TextView)findViewById(R.id.back);
            requestCounsel = (TextView)findViewById(R.id.requestCounsel);

            Glide.with(activity)
                    .load(imageTmp)
                    .animate(android.R.anim.slide_in_left)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(image);

            bank.setText(bankTmp);
            loanName.setText(loanNameTmp);
            finalRegisterDate.setText(finalRegisterDateTmp);
            loanInterestRate.setText(loanInterestRateTmp);
            interestType.setText(interestTypeTmp);
            monthlyRepayMoney.setText(monthlyRepayMoneyTmp);
            repayType.setText(repayTypeTmp);
            interestRateInfo1.setText(interestRateInfo1Tmp);
            interestRateInfo2.setText(interestRateInfo2Tmp);
            feeInfo1.setText(feeInfo1Tmp);
            feeInfo2.setText(feeInfo2Tmp);

            goCounselor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HelloMoneyCustomerApplication.getInstance(),CounselorDetailActivity.class);
                    intent.putExtra("id",id);
                    HelloMoneyCustomerApplication.getInstance().startActivity(intent);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            requestCounsel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"id = "+ id +" 를 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
