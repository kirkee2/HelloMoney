package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public static final int TYPE_FOOTER = 5;

    public static final int NO_WRITE_COMMENT =0;
    public static final int YES_WRITE_COMMENT =1;

    private int type;

    private Activity activity;

    private ArrayList<QuotationDetailObject> quotationDetailObjectArrayList;
    private QuotationDetailHeaderObject quotationDetailHeaderObject;

    public void addItem(QuotationDetailObject quotationDetailObject) {
        quotationDetailObjectArrayList.add(quotationDetailObject); //아이템 추가
        notifyDataSetChanged();
    }

    public void initItem(ArrayList<QuotationDetailObject> quotationDetailObjectArrayList) {
        this.quotationDetailObjectArrayList = quotationDetailObjectArrayList; //아이템 추가
        notifyDataSetChanged();
    }

    public QuotationDetailRecyclerViewAdapter(Activity activity,int type,QuotationDetailHeaderObject quotationDetailHeaderObject){
        //변수 초기화
        this.type = type;
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
        }else if(viewType == TYPE_FOOTER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_footer_write_comment, parent, false);
            return new QuotationDetailFooterViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_items, parent, false);
            return new QuotationDetailViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(type == YES_WRITE_COMMENT) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == 1) {
                return TYPE_HEADER_SUB;
            } else if (position == 2) {
                return TYPE_HEADER_SUB_SUB;
            } else if (position == 3) {
                return TYPE_HEADER_SUB_SUB_SUB;
            } else if (position == quotationDetailObjectArrayList.size() + 4) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }else{
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == 1) {
                return TYPE_HEADER_SUB;
            } else if (position == 2) {
                return TYPE_HEADER_SUB_SUB;
            } else if (position == 3) {
                return TYPE_HEADER_SUB_SUB_SUB;
            } else {
                return TYPE_ITEM;
            }
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

    private class QuotationDetailFooterViewHolder extends RecyclerView.ViewHolder {
        Button writeComment;

        private QuotationDetailFooterViewHolder(View itemView) {
            super(itemView);
            writeComment = (Button)itemView.findViewById(R.id.writeComment);
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
        LinearLayout linearLayout;

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
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof QuotationDetailHeaderViewHolder){
        } else if (holder instanceof QuotationDetailSubHeaderViewHolder) {

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<BarEntry> entriesMin = new ArrayList<>();
            float min = 100;
            int minIndex = 0;
            for(int i = 0 ; i <quotationDetailObjectArrayList.size() ; i++){
                if(quotationDetailObjectArrayList.get(i).getInterestRate() < min){
                    min = (float) quotationDetailObjectArrayList.get(i).getInterestRate();
                    minIndex = i;
                }
                entries.add(new BarEntry(i*0.5F,(float)quotationDetailObjectArrayList.get(i).getInterestRate()));
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

            ((QuotationDetailSubSubHeaderViewHolder) holder).finalQuotationCount.setText(String.valueOf(quotationDetailObjectArrayList.size()));
            double tmp = 0;
            for(int i = 0 ; i < quotationDetailObjectArrayList.size() ; i++){
                tmp += quotationDetailObjectArrayList.get(i).getInterestRate();
            }

            tmp = tmp/(double) quotationDetailObjectArrayList.size();
            ((QuotationDetailSubSubHeaderViewHolder) holder).averageInterestRate.setText(String.valueOf(tmp));


        }else if (holder instanceof QuotationDetailSubSubSubHeaderViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ((QuotationDetailSubSubSubHeaderViewHolder) holder).remainTime.setText("마감까지 " + valueObject.getRemainTime()+ " 남았습니다.");

            if(valueObject.getLoanType().equals("주택담보대출")){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.secured_loan);
            }else{
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.lease_loan);
            }

            ((QuotationDetailSubSubSubHeaderViewHolder) holder).region.setText(valueObject.getRegion1() + " " + valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).apt.setText(valueObject.getApt());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).size.setText(valueObject.getSize());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanSum.setText(String.valueOf(valueObject.getLoanSum()));
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).rateType.setText(valueObject.getRateType());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanDate.setText(valueObject.getLoanDate());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).jobtype.setText(valueObject.getJobType());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).telephone.setText(valueObject.getTelephone());

            if(valueObject.getOngoingStatus().equals("견적접수중")){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_interection_waiting));
            }else if(valueObject.getOngoingStatus().equals("선택대기중") || valueObject.getOngoingStatus().equals("상담중") || valueObject.getOngoingStatus().equals("심사중") || valueObject.getOngoingStatus().equals("승인완료")){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_ongoing));
            }else{
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_done));
            }
        }else if (holder instanceof QuotationDetailFooterViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ((QuotationDetailFooterViewHolder) holder).writeComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WriteCommentDialog writeCommentDialog = new WriteCommentDialog(activity);
                    writeCommentDialog.setInfo(valueObject.getId());

                    writeCommentDialog.show();
                }
            });
        }else {
            final QuotationDetailObject valueObject = quotationDetailObjectArrayList.get(position-4);

            ((QuotationDetailViewHolder) holder).bank.setText(valueObject.getBank());
            ((QuotationDetailViewHolder) holder).name.setText(valueObject.getName());
            ((QuotationDetailViewHolder) holder).interestRate.setText(String.valueOf(valueObject.getInterestRate())+"%");
            ((QuotationDetailViewHolder) holder).loanType.setText(valueObject.getLoanType());

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
        if(type == YES_WRITE_COMMENT) {
            return quotationDetailObjectArrayList.size()+5;
        }else{
            return quotationDetailObjectArrayList.size()+4;
        }
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
            name = (TextView)findViewById(R.id.name);
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
            name.setText(nameTmp);
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

    private class WriteCommentDialog extends Dialog {

        private int id;
        private CircleImageView image;
        private TextView bank;
        private TextView name;
        private RatingBar ratingBar;
        private EditText comment;
        private TextView back;
        private TextView writeComment;

        private String imageTmp;
        private String bankTmp;
        private String nameTmp;

        public WriteCommentDialog(@NonNull Context context) {
            super(context);
        }

        public void setInfo(int id){
            this.id = id;
            imageTmp = "http://img.visualdive.co.kr/sites/2/2015/10/gisa2.jpg";
            bankTmp = "외환은행";
            nameTmp = "이건준";
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.write_comment_dialog);
            image = (CircleImageView)findViewById(R.id.image);
            bank = (TextView)findViewById(R.id.bank);
            name = (TextView)findViewById(R.id.name);
            back = (TextView)findViewById(R.id.back);
            writeComment = (TextView)findViewById(R.id.writeComment);
            ratingBar = (RatingBar)findViewById(R.id.starRatingBar);
            comment = (EditText)findViewById(R.id.comment);

            Glide.with(activity)
                    .load(imageTmp)
                    .animate(android.R.anim.slide_in_left)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(image);

            bank.setText(bankTmp);
            name.setText(nameTmp);
            ratingBar.setRating(4);

            comment.setImeOptions(EditorInfo.IME_ACTION_DONE);
            comment.setRawInputType(InputType.TYPE_CLASS_TEXT);

            comment.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        Toast.makeText(getContext(),"id = "+ id +" 에 평점 " + ratingBar.getRating() +  "점과 " + comment.getText()+ " 라고 댓글을 작성하였습니다.",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });

            comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(comment.getText().length() >= 100){
                        Toast.makeText(HelloMoneyCustomerApplication.getInstance(),"100자 이상은 작성하실 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            writeComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"id = "+ id +" 에 평점 " + ratingBar.getRating() +  "점과 " + comment.getText()+ " 라고 댓글을 작성하였습니다.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
