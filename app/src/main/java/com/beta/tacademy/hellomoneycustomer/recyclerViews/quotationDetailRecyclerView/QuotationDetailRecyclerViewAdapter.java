package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.CounselorDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.MyQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.activity.PostscriptDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.QuotationDetailActivity;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class QuotationDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_SUB = 1;
    public static final int TYPE_HEADER_SUB_SUB = 2;
    public static final int TYPE_HEADER_SUB_SUB_SUB = 3;
    public static final int TYPE_HEADER_SUB_SUB_SUB_SUB = 4;
    public static final int TYPE_ITEM = 5;
    public static final int TYPE_FOOTER = 6;

    public static final int NO_WRITE_DONE_COMMENT =0;
    public static final int NO_WRITE_ONGOING_COMMENT =1;
    public static final int YES_WRITE_COMMENT =2;
    public static final int NO_WRITE_ONGOING_SELECTED_COMMENT = 3;
    public static final int NO_WRITE_ONGOING_SELECTED_COMMENT_NO_FEED = 4;
    public static final int NO_WRITE_ONGOING_COMMENT_NO_FEED = 5;

    private int type;

    private Activity activity;
    private ArrayList<QuotationDetailObject> quotationDetailObjectArrayList;
    private QuotationDetailHeaderObject quotationDetailHeaderObject;
    private QuotationDetailObject subHeaderQuotationDetailHeaderObject;

    public void addItem(QuotationDetailObject quotationDetailObject) {
        quotationDetailObjectArrayList.add(quotationDetailObject); //아이템 추가
        notifyDataSetChanged();
    }

    public void addSubHeader(QuotationDetailObject quotationDetailObject){
        subHeaderQuotationDetailHeaderObject = quotationDetailObject;
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
        }else if(viewType == TYPE_HEADER_SUB_SUB_SUB_SUB){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_detail_header_sub_sub_sub_sub, parent, false);
            return new QuotationDetailSubSubSubSubHeaderViewHolder(view);
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
            }else if (position == 1) {
                return TYPE_HEADER_SUB_SUB_SUB;
            } else{
                return TYPE_FOOTER;
            }
        }else if(type == NO_WRITE_ONGOING_COMMENT){
            if (position == 0) {
                return TYPE_HEADER;
            }else if (position == 1) {
                return TYPE_HEADER_SUB_SUB_SUB;
            }else if (position == 2) {
                return TYPE_HEADER_SUB_SUB_SUB_SUB;
            }else {
                return TYPE_ITEM;
            }
        }else if(type == NO_WRITE_ONGOING_SELECTED_COMMENT){
            if (position == 0) {
                return TYPE_HEADER;
            }else if (position == 1) {
                return TYPE_HEADER_SUB_SUB_SUB;
            } else if (position == 2) {
                return TYPE_HEADER_SUB;
            }else if (position == 3) {
                return TYPE_HEADER_SUB_SUB_SUB_SUB;
            } else {
                return TYPE_ITEM;
            }
        }else if(type ==NO_WRITE_ONGOING_SELECTED_COMMENT_NO_FEED){
            if (position == 0) {
                return TYPE_HEADER;
            }else if (position == 1) {
                return TYPE_HEADER_SUB_SUB_SUB;
            } else if (position == 2) {
                return TYPE_HEADER_SUB;
            }else {
                return TYPE_ITEM;
            }
        }else if(type == NO_WRITE_ONGOING_COMMENT_NO_FEED ){
            if (position == 0) {
                return TYPE_HEADER;
            }else if (position == 1) {
                return TYPE_HEADER_SUB_SUB_SUB;
            }else {
                return TYPE_ITEM;
            }
        }else{
            if (position == 0) {
                return TYPE_HEADER;
            }else if (position == 1) {
                return TYPE_HEADER_SUB_SUB_SUB;
            }else{
                return TYPE_HEADER_SUB_SUB;
            }
        }
    }


    @Override
    public int getItemCount() {
        if(type == NO_WRITE_ONGOING_COMMENT) {
            return quotationDetailObjectArrayList.size()+3;
        }else if(type == NO_WRITE_ONGOING_SELECTED_COMMENT){
            return quotationDetailObjectArrayList.size()+4;
        }else if(type == YES_WRITE_COMMENT){
            return 3;
        }else if(type ==NO_WRITE_ONGOING_SELECTED_COMMENT_NO_FEED){
            return quotationDetailObjectArrayList.size()+3;
        }else if(type == NO_WRITE_ONGOING_COMMENT_NO_FEED ){
            return quotationDetailObjectArrayList.size()+2;
        }else{
            return 3;
        }
    }

    private class QuotationDetailViewHolder extends RecyclerView.ViewHolder {
        TextView bank;
        TextView name;
        TextView loanType;
        TextView interestRate;
        CircleImageView image;
        CardView cardView;
        //ImageView check;
        LinearLayout tmp;

        private QuotationDetailViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            bank = (TextView)itemView.findViewById(R.id.bank);
            name = (TextView)itemView.findViewById(R.id.name);
            loanType = (TextView)itemView.findViewById(R.id.loanType);
            interestRate = (TextView)itemView.findViewById(R.id.interestRate);
            image = (CircleImageView)itemView.findViewById(R.id.image);
            //check = (ImageView)itemView.findViewById(R.id.check);
            tmp = (LinearLayout)itemView.findViewById(R.id.tmp);
        }
    }

    private class QuotationDetailHeaderViewHolder extends RecyclerView.ViewHolder {
        BarChart barChart;
        TextView finalQuotationCount;
        TextView averageInterestRate;
        RelativeLayout noResult;
        LinearLayout yesResult;

        private QuotationDetailHeaderViewHolder(View itemView) {
            super(itemView);
            barChart = (BarChart) itemView.findViewById(R.id.barChart);
            finalQuotationCount = (TextView)itemView.findViewById(R.id.finalQuotationCount);
            averageInterestRate = (TextView)itemView.findViewById(R.id.averageInterestRate);
            noResult = (RelativeLayout)itemView.findViewById(R.id.noResult);
            yesResult = (LinearLayout)itemView.findViewById(R.id.yesResult);

        }
    }


    private class QuotationDetailSubHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView bank;
        TextView name;
        TextView loanType;
        TextView interestRate;
        CircleImageView image;
        CardView cardView;
        LinearLayout tmp;

        private QuotationDetailSubHeaderViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cardView);
            bank = (TextView)itemView.findViewById(R.id.bank);
            name = (TextView)itemView.findViewById(R.id.name);
            loanType = (TextView)itemView.findViewById(R.id.loanType);
            interestRate = (TextView)itemView.findViewById(R.id.interestRate);
            image = (CircleImageView)itemView.findViewById(R.id.image);
            tmp = (LinearLayout)itemView.findViewById(R.id.tmp);
        }
    }

    private class QuotationDetailSubSubHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView bank;
        TextView name;
        ImageView loanType;
        CircleImageView image;
        TextView region;
        TextView apt;
        RatingBar starRatingBar;
        TextView content;
        TextView goCounselor;
        TextView pastTime;

        private QuotationDetailSubSubHeaderViewHolder(View itemView) {
            super(itemView);
            bank = (TextView)itemView.findViewById(R.id.bank);
            name = (TextView)itemView.findViewById(R.id.name);
            loanType = (ImageView)itemView.findViewById(R.id.loanType);
            image = (CircleImageView)itemView.findViewById(R.id.image);
            region = (TextView)itemView.findViewById(R.id.region);
            apt = (TextView)itemView.findViewById(R.id.apt);
            starRatingBar = (RatingBar)itemView.findViewById(R.id.starRatingBar);
            content = (TextView)itemView.findViewById(R.id.content);
            goCounselor =(TextView)itemView.findViewById(R.id.goCounselor);
            pastTime = (TextView)itemView.findViewById(R.id.pastTime);
        }
    }

    private class QuotationDetailSubSubSubHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView leftTime;
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
            leftTime = (TextView)itemView.findViewById(R.id.leftTime);
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

    private class QuotationDetailSubSubSubSubHeaderViewHolder extends RecyclerView.ViewHolder {
        private QuotationDetailSubSubSubSubHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class QuotationDetailFooterViewHolder extends RecyclerView.ViewHolder {
        Button writeComment;

        private QuotationDetailFooterViewHolder(View itemView) {
            super(itemView);
            writeComment = (Button)itemView.findViewById(R.id.writeComment);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof QuotationDetailHeaderViewHolder){
            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<BarEntry> entriesMin = new ArrayList<>();
            float min = 100;
            int minIndex = 0;

            if(type == NO_WRITE_ONGOING_COMMENT || type == NO_WRITE_ONGOING_COMMENT_NO_FEED){
                for(int i = 0 ; i <quotationDetailObjectArrayList.size() ; i++) {
                    if (quotationDetailObjectArrayList.get(i).getInterestRate() < min) {
                        min = (float) quotationDetailObjectArrayList.get(i).getInterestRate();
                        minIndex = i;
                    }

                    entries.add(new BarEntry(i * 0.5F, (float) quotationDetailObjectArrayList.get(i).getInterestRate()));

                    /*
                    if (type == NO_WRITE_ONGOING_SELECTED_COMMENT || type == YES_WRITE_COMMENT || type == NO_WRITE_DONE_COMMENT) {
                        entries.add(new BarEntry(i * 0.5F + 0.5F, (float) quotationDetailObjectArrayList.get(i).getInterestRate()));
                    } else {
                        entries.add(new BarEntry(i * 0.5F, (float) quotationDetailObjectArrayList.get(i).getInterestRate()));
                    }
                    */
                }
            }else{
                entries.add(new BarEntry(0,(float)subHeaderQuotationDetailHeaderObject.getInterestRate()));
                min = (float)subHeaderQuotationDetailHeaderObject.getInterestRate();
                minIndex = 0;


                for(int i = 0 ; i <quotationDetailObjectArrayList.size() ; i++) {
                    if (quotationDetailObjectArrayList.get(i).getInterestRate() < min) {
                        min = (float) quotationDetailObjectArrayList.get(i).getInterestRate();
                        minIndex = i+1;
                    }

                    entries.add(new BarEntry(i * 0.5F + 0.5F, (float) quotationDetailObjectArrayList.get(i).getInterestRate()));


                    /*
                    if (type == NO_WRITE_ONGOING_SELECTED_COMMENT || type == YES_WRITE_COMMENT || type == NO_WRITE_DONE_COMMENT) {
                        entries.add(new BarEntry(i * 0.5F + 0.5F, (float) quotationDetailObjectArrayList.get(i).getInterestRate()));
                    } else {
                        entries.add(new BarEntry(i * 0.5F, (float) quotationDetailObjectArrayList.get(i).getInterestRate()));
                    }
                    */
                }
            }


            if(quotationDetailObjectArrayList.size() == 0 && (type == NO_WRITE_ONGOING_COMMENT || type == NO_WRITE_ONGOING_COMMENT_NO_FEED)){
                ((QuotationDetailHeaderViewHolder) holder).noResult.setVisibility(View.VISIBLE);
                ((QuotationDetailHeaderViewHolder) holder).yesResult.setVisibility(View.INVISIBLE);
            }else{
                ((QuotationDetailHeaderViewHolder) holder).noResult.setVisibility(View.GONE);
                ((QuotationDetailHeaderViewHolder) holder).yesResult.setVisibility(View.VISIBLE);

                entriesMin.add(entries.get(minIndex));
                entries.remove(minIndex);

                Description d = new Description();
                d.setText("");

                BarDataSet dataSet = new BarDataSet(entries, "금리");
                BarDataSet dataSetMin = new BarDataSet(entriesMin, "최저 금리");

                dataSet.setColor(0xFFBDBDBD);
                dataSet.setHighlightEnabled(false);
                dataSet.setValueTextSize(10);

                dataSetMin.setColor(0xFF00BFA5);
                dataSetMin.setHighlightEnabled(false);
                dataSetMin.setValueTextSize(10);

                BarData data = new BarData(dataSet);
                data.addDataSet(dataSetMin);

                data.setBarWidth(0.15F);

                ((QuotationDetailHeaderViewHolder) holder).barChart.setData(data);
                ((QuotationDetailHeaderViewHolder) holder).barChart.getAxisRight().setEnabled(false);
                ((QuotationDetailHeaderViewHolder) holder).barChart.getXAxis().setEnabled(false);
                ((QuotationDetailHeaderViewHolder) holder).barChart.setDescription(d);
                ((QuotationDetailHeaderViewHolder) holder).barChart.setEnabled(false);
                ((QuotationDetailHeaderViewHolder) holder).barChart.animateY(500);
                ((QuotationDetailHeaderViewHolder) holder).barChart.setDoubleTapToZoomEnabled(false);
                ((QuotationDetailHeaderViewHolder) holder).barChart.setScaleEnabled(false);
                ((QuotationDetailHeaderViewHolder) holder).barChart.invalidate();
            }

            if(type == NO_WRITE_ONGOING_COMMENT  || type == NO_WRITE_ONGOING_COMMENT_NO_FEED){
                double tmp = 0;
                for(int i = 0 ; i < quotationDetailObjectArrayList.size() ; i++){
                    tmp += quotationDetailObjectArrayList.get(i).getInterestRate();
                }

                tmp = tmp/(double) quotationDetailObjectArrayList.size();

                double tmp2 = Double.parseDouble(String.format("%.1f",tmp));
                ((QuotationDetailHeaderViewHolder) holder).averageInterestRate.setText(String.valueOf(tmp2)+"%");
                ((QuotationDetailHeaderViewHolder) holder).finalQuotationCount.setText(String.valueOf(quotationDetailObjectArrayList.size()));
            }else{
                double tmp = subHeaderQuotationDetailHeaderObject.getInterestRate();
                for(int i = 0 ; i < quotationDetailObjectArrayList.size() ; i++){
                    tmp += quotationDetailObjectArrayList.get(i).getInterestRate();
                }

                tmp = tmp/(double) (quotationDetailObjectArrayList.size()+1);

                double tmp2 = Double.parseDouble(String.format("%.1f",tmp));
                ((QuotationDetailHeaderViewHolder) holder).averageInterestRate.setText(String.valueOf(tmp2)+"%");
                ((QuotationDetailHeaderViewHolder) holder).finalQuotationCount.setText(String.valueOf(quotationDetailObjectArrayList.size()+1));
            }

        } else if (holder instanceof QuotationDetailSubHeaderViewHolder) {
            final QuotationDetailObject valueObject = subHeaderQuotationDetailHeaderObject;
            ((QuotationDetailSubHeaderViewHolder) holder).bank.setText(valueObject.getBank());
            ((QuotationDetailSubHeaderViewHolder) holder).name.setText(valueObject.getName());
            ((QuotationDetailSubHeaderViewHolder) holder).interestRate.setText(String.valueOf(valueObject.getInterestRate())+"%");
            ((QuotationDetailSubHeaderViewHolder) holder).loanType.setText(valueObject.getLoanType());

            ((QuotationDetailSubHeaderViewHolder) holder).cardView.setBackgroundResource(R.drawable.border);

            Glide.with(activity)
                    .load(valueObject.getImageUrl())
                    .animate(android.R.anim.slide_in_left)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(((QuotationDetailSubHeaderViewHolder) holder).image);

            ((QuotationDetailSubHeaderViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQuotationDialog requestQuotationDialog = new RequestQuotationDialog(activity);
                    requestQuotationDialog.setInfo(activity, quotationDetailHeaderObject.getId(), valueObject.getId(), quotationDetailHeaderObject.getOngoingStatus());
                    requestQuotationDialog.show();
                }
            });

        }else if (holder instanceof QuotationDetailSubSubHeaderViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ((QuotationDetailSubSubHeaderViewHolder) holder).bank.setText(valueObject.getBank());
            ((QuotationDetailSubSubHeaderViewHolder) holder).name.setText(valueObject.getName());
            if(valueObject.getLoanType().equals("주택담보대출")){
                ((QuotationDetailSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.secured_loan);
            }else{
                ((QuotationDetailSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.lease_loan);
            }

            Glide.with(activity)
                    .load(valueObject.getPhoto())
                    .animate(android.R.anim.slide_in_left)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(((QuotationDetailSubSubHeaderViewHolder) holder).image);

            ((QuotationDetailSubSubHeaderViewHolder) holder).region.setText(valueObject.getRegion1() + " " + valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((QuotationDetailSubSubHeaderViewHolder) holder).apt.setText(valueObject.getApt());
            ((QuotationDetailSubSubHeaderViewHolder) holder).starRatingBar.setEnabled(false);
            ((QuotationDetailSubSubHeaderViewHolder) holder).starRatingBar.setRating((float)valueObject.getScore());
            ((QuotationDetailSubSubHeaderViewHolder) holder).content.setText(valueObject.getContent());
            ((QuotationDetailSubSubHeaderViewHolder) holder).pastTime.setText(CommonClass.timeParsing(valueObject.getReviewRegisterTime()));

            ((QuotationDetailSubSubHeaderViewHolder) holder).goCounselor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity,CounselorDetailActivity.class);
                    intent.putExtra("agentId",valueObject.getAgentId());
                    activity.startActivity(intent);
                }
            });
        }else if (holder instanceof QuotationDetailSubSubSubHeaderViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            if(valueObject.getLoanType().equals("주택담보대출")){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.secured_loan);
            }else{
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanType.setImageResource(R.drawable.lease_loan);
            }

            ((QuotationDetailSubSubSubHeaderViewHolder) holder).region.setText(valueObject.getRegion1() + " " + valueObject.getRegion2() + " "+ valueObject.getRegion3());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).apt.setText(valueObject.getApt());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).size.setText(valueObject.getSize());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanSum.setText(CommonClass.formatMoney(valueObject.getLoanSum())+"만원");
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).rateType.setText(valueObject.getRateType());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).loanDate.setText(CommonClass.timeDashParsing(valueObject.getLoanDate()));
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).jobtype.setText(valueObject.getJobType());
            ((QuotationDetailSubSubSubHeaderViewHolder) holder).telephone.setText(CommonClass.formatPhoneNumber(valueObject.getTelephone()));

            if(valueObject.getOngoingStatus().equals("견적접수중")){
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_interection_waiting));
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step1,0,0,0);
                valueObject.setLeftSecond(CommonClass.timeLeftSecondParsing(valueObject.getLeftTime()));
                int leftSecond  = CommonClass.timeLeftSecondParsing(valueObject.getLeftTime());
                int hour = leftSecond/3600;
                int tmp = leftSecond%3600;
                int minute = tmp/60;
                int second = tmp%60;

                if(leftSecond > 0){
                    ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText("마감 " + CommonClass.formatNumber2(hour) + ":" + CommonClass.formatNumber2(minute)  + ":" + CommonClass.formatNumber2(second) + " 전");;

                }else{
                    ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText("마감 " + "00" + ":" +"00" + ":" +"00"+ " 전");
                    if(((QuotationDetailActivity)activity).timer == null){

                    }else{
                        ((QuotationDetailActivity)activity).timer.cancel();
                    }
                }

                ((QuotationDetailActivity)activity).timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int leftSecond = valueObject.getLeftSecond()-1;
                                valueObject.setLeftSecond(leftSecond);
                                int hour = leftSecond/3600;
                                int tmp = leftSecond%3600;
                                int minute = tmp/60;
                                int second = tmp%60;

                                if(leftSecond > 0){
                                    ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText("마감 " + CommonClass.formatNumber2(hour) + ":" + CommonClass.formatNumber2(minute)  + ":" + CommonClass.formatNumber2(second) + " 전");

                                }else{
                                    ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText("마감 " + "00" + ":" +"00" + ":" +"00"+ " 전");
                                    if(((QuotationDetailActivity)activity).timer == null){

                                    }else{
                                        ((QuotationDetailActivity)activity).timer.cancel();
                                    }
                                }
                            }
                        });
                    }
                };

                ((QuotationDetailActivity)activity).timer = new Timer();
                ((QuotationDetailActivity)activity).timer.schedule(((QuotationDetailActivity)activity).timerTask,0,1000);

            }else if(valueObject.getOngoingStatus().equals("선택대기중")){
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step2,0,0,0);

                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_ongoing));
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText(activity.getString(R.string.step_content2));
            }else if(valueObject.getOngoingStatus().equals("상담중")){
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step3,0,0,0);

                //((QuotationDetailSubSubSubHeaderViewHolder) holder).feedbackList.setText("선택된 견적");
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_ongoing));
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText(activity.getString(R.string.step_content3));
            }else if(valueObject.getOngoingStatus().equals("심사중")){
               // ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step4,0,0,0);

                //((QuotationDetailSubSubSubHeaderViewHolder) holder).feedbackList.setText("선택된 견적");
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_ongoing));
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText(activity.getString(R.string.step_content4));
            }else if(valueObject.getOngoingStatus().equals("승인완료")){
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step5,0,0,0);

                //((QuotationDetailSubSubSubHeaderViewHolder) holder).feedbackList.setText("선택된 견적");
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_ongoing));
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText(activity.getString(R.string.step_content5));
            }else{
               // ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.step6,0,0,0);

                ((QuotationDetailSubSubSubHeaderViewHolder) holder).linearLayout.setBackground(ContextCompat.getDrawable(activity,R.drawable.ongoing_quotation_done));
                //((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setTextColor(ResourcesCompat.getColor(activity.getResources(),R.color.progress,null));
                ((QuotationDetailSubSubSubHeaderViewHolder) holder).leftTime.setText(activity.getString(R.string.step_content6));
            }
        }else if (holder instanceof QuotationDetailFooterViewHolder) {
            final QuotationDetailHeaderObject valueObject  = quotationDetailHeaderObject;

            ((QuotationDetailFooterViewHolder) holder).writeComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WriteCommentDialog writeCommentDialog = new WriteCommentDialog(activity);
                    writeCommentDialog.setInfo(activity,valueObject.getId());

                    writeCommentDialog.show();
                    /*
                    writeCommentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            ((QuotationDetailActivity)activity).update();
                        }
                    });
                    */
                }
            });
        }else if (holder instanceof QuotationDetailSubSubSubSubHeaderViewHolder) {

        }else {
            final QuotationDetailObject valueObject;
            if(type == NO_WRITE_DONE_COMMENT){
                valueObject = quotationDetailObjectArrayList.get(position-4);
            }else if(type == NO_WRITE_ONGOING_SELECTED_COMMENT){
                valueObject = quotationDetailObjectArrayList.get(position-4);
            }else if(type == NO_WRITE_ONGOING_COMMENT){
                valueObject = quotationDetailObjectArrayList.get(position-3);
            }else if(type ==NO_WRITE_ONGOING_SELECTED_COMMENT_NO_FEED){
                valueObject = quotationDetailObjectArrayList.get(position-3);
            }else if(type == NO_WRITE_ONGOING_COMMENT_NO_FEED ){
                valueObject = quotationDetailObjectArrayList.get(position-2);
            }else{
                valueObject = quotationDetailObjectArrayList.get(position-4);
            }


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
                    requestQuotationDialog.setInfo(activity,quotationDetailHeaderObject.getId(),valueObject.getId(),quotationDetailHeaderObject.getOngoingStatus());
                    requestQuotationDialog.show();

/*
                    requestQuotationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            ((QuotationDetailActivity)activity).update();
                        }
                    });
*/
                }
            });

        }
    }

    private class RequestQuotationDialog extends Dialog {
        private Activity activity;

        private int requestId;
        private int estimateId;
        private String ongoingStatus;
        private CircleImageView image;
        private TextView bank;
        private TextView name;
        private TextView goCounselor;
        private TextView finalRegisterDate;
        private TextView loanInterestRate;
        private TextView interestType;
        private TextView monthlyRepayMoney;
        private TextView repayType;
        private TextView interestRateInfo1;
        private TextView interestRateInfo2;
        private TextView interestRateInfo3;
        private TextView feeInfo1;
        private TextView feeInfo2;
        private TextView back;
        private TextView requestCounsel;
        private ProgressBar progressBar;

        private String agentId;
        private String imageInfo;
        private String bankInfo;
        private String nameInfo;
        private String loanNameInfo;
        private String finalRegisterDateInfo;
        private String overDueInfo1;
        private String overDueInfo2;
        private String overDueInfo3;
        private String loanInterestRateInfo;
        private String interestTypeInfo;
        private int monthlyRepayMoneyInfo;
        private String repayTypeInfo;
        private String interestRateInfo1Info;
        private String interestRateInfo2Info;
        private String interestRateInfo3Info;
        private String feeInfo1Info;
        private String feeInfo2Info;

        public RequestQuotationDialog(@NonNull Context context) {
            super(context);
        }

        public void setInfo(Activity activity,int requestId,int estimateId,String ongoingStatus){
            this.activity =activity;
            this.requestId = requestId;
            this.estimateId = estimateId;
            this.ongoingStatus =ongoingStatus;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.request_counsel_dialog);
            image = (CircleImageView)findViewById(R.id.image);
            bank = (TextView)findViewById(R.id.bank);
            name = (TextView)findViewById(R.id.name);
            goCounselor = (TextView)findViewById(R.id.goCounselor);
            finalRegisterDate = (TextView)findViewById(R.id.finalRegisterDate);
            loanInterestRate = (TextView)findViewById(R.id.loanInterestRate);
            interestType = (TextView)findViewById(R.id.interestType);
            monthlyRepayMoney = (TextView)findViewById(R.id.monthlyRepayMoney);
            repayType = (TextView)findViewById(R.id.repayType);
            interestRateInfo1 = (TextView)findViewById(R.id.interestRateInfo1);
            interestRateInfo2 = (TextView)findViewById(R.id.interestRateInfo2);
            interestRateInfo3 = (TextView)findViewById(R.id.interestRateInfo3);
            feeInfo1 = (TextView)findViewById(R.id.feeInfo1);
            feeInfo2 = (TextView)findViewById(R.id.feeInfo2);
            back = (TextView)findViewById(R.id.back);
            requestCounsel = (TextView)findViewById(R.id.requestCounsel);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);

            goCounselor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity,CounselorDetailActivity.class);
                    intent.putExtra("agentId",agentId);
                    activity.startActivity(intent);
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
                    if(ongoingStatus.equals("선택대기중") || ongoingStatus.equals("견적접수중")){
                        new RequestCounsel().execute();
                    }else{
                        Toast.makeText(activity,ongoingStatus + "이므로 상담을 신청하실 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            new QuotationDetail().execute();
        }

        private class QuotationDetail extends AsyncTask<Void, Void, Integer> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //시작 전에 ProgressBar를 보여주어 사용자와 interact
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                boolean flag;
                Response response = null;
                OkHttpClient toServer;

                JSONObject jsonObject = null;

                try{
                    toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                    Request request = new Request.Builder()
                            .url(String.format(activity.getResources().getString(R.string.my_quotation_feedback_detail_url),String.valueOf(estimateId)))
                            .get()
                            .build();

                    //동기 방식
                    response = toServer.newCall(request).execute();

                    flag = response.isSuccessful();

                    String returedJSON;

                    if(flag){ //성공했다면
                        returedJSON = response.body().string();

                        try {
                            jsonObject = new JSONObject(returedJSON);
                        }catch(JSONException jsone){
                            Log.e("json에러", jsone.toString());
                        }
                    }else{
                        return 2;
                    }
                }catch (UnknownHostException une) {
                } catch (UnsupportedEncodingException uee) {
                } catch (Exception e) {
                } finally{
                    if(response != null) {
                        response.close(); //3.* 이상에서는 반드시 닫아 준다.
                    }
                }

                if(jsonObject != null){
                    try {
                        if(jsonObject.get(activity.getResources().getString(R.string.url_message)).equals(activity.getResources().getString(R.string.url_success))){
                            JSONObject data= jsonObject.getJSONObject(activity.getResources().getString(R.string.url_data));

                            agentId = data.getString("agent_id");
                            imageInfo = data.getString("photo");
                            bankInfo = data.getString("item_bank");
                            nameInfo = data.getString("name");
                            finalRegisterDateInfo = data.getString("register_time");
                            loanInterestRateInfo = data.getString("interest_rate");
                            interestTypeInfo = data.getString("interest_rate_type");
                            monthlyRepayMoneyInfo = data.getInt("repayment_amount_per_month");
                            repayTypeInfo = data.getString("repayment_type");
                            /*
                            interestRateInfo1Info = String.valueOf(data.getInt("overdue_interest_rate_1"));
                            interestRateInfo2Info = String.valueOf(data.getInt("overdue_interest_rate_2"));
                            interestRateInfo3Info = String.valueOf(data.getInt("overdue_interest_rate_3"));
                            */
                            /*
                            overDueInfo1 = data.getString("overdue_time_1");
                            overDueInfo2 = data.getString("overdue_time_2");
                            overDueInfo3 = data.getString("overdue_time_3");
                            */
                            feeInfo1Info = "조기 상환 원금 X 1.4% X [(3년 대출 경과 일 수/ 3년)]";
                            feeInfo2Info = "매년 대출 잔액의 10%까지 중도 상환 수수료 면제";

                            return 0;
                        }else if(jsonObject.get(activity.getResources().getString(R.string.url_message)).equals(activity.getResources().getString(R.string.url_no_data))){
                            return 1;
                        }else{
                            return 3;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return 5;
                    }
                }else{
                    return 4;
                }

            }

            @Override
            protected void onPostExecute(Integer result) {
                if(result == 0 || result == 1){
                    Glide.with(activity)
                            .load(imageInfo)
                            .animate(android.R.anim.slide_in_left)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                            .into(image);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                    try {
                        Date date = sdf.parse(finalRegisterDateInfo);
                        finalRegisterDate.setText(sdf.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    bank.setText(bankInfo);
                    name.setText(nameInfo);
                    loanInterestRate.setText(loanInterestRateInfo+"%");
                    interestType.setText(interestTypeInfo);
                    monthlyRepayMoney.setText(CommonClass.formatMoney(monthlyRepayMoneyInfo)+"만원");
                    repayType.setText(repayTypeInfo);
                    //interestRateInfo1.setText(overDueInfo1 + " : 대출 금리 + "+interestRateInfo1Info + "%");
                    //interestRateInfo2.setText(overDueInfo2 + " : 대출 금리 + "+interestRateInfo2Info + "%");
                    //interestRateInfo3.setText(overDueInfo2 + " : 대출 금리 + "+interestRateInfo3Info + "%");
                    interestRateInfo1.setText("30일 이내" + " : 대출 금리 + "+5 + "%");
                    interestRateInfo2.setText("90일 이내" + " : 대출 금리 + "+6 + "%");
                    interestRateInfo3.setText("90일 초과" + " : 대출 금리 + "+7 + "%");
                    feeInfo1.setText(feeInfo1Info);
                    feeInfo2.setText(feeInfo2Info);
                    goCounselor.setEnabled(true);
                    requestCounsel.setEnabled(true);
                }else{
                }
                progressBar.setVisibility(View.GONE);
            }
        }

        private class RequestCounsel extends AsyncTask<Void, Void, Integer>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //시작 전에 ProgressBar를 보여주어 사용자와 interact
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                boolean flag;
                Response response = null;

                OkHttpClient toServer;


                JSONObject jsonObject = null;

                try{
                    toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                    RequestBody postBody = new FormBody.Builder()
                            .add("selectedEstimateId",String.valueOf(estimateId))
                            .build();

                    Request request1 = new Request.Builder()
                            .url(String.format(activity.getResources().getString(R.string.request_counsel_url),String.valueOf(requestId)))
                            .put(postBody)
                            .build();

                    //동기 방식
                    response = toServer.newCall(request1).execute();

                    flag = response.isSuccessful();

                    String returedJSON1;

                    if(flag){ //성공했다면
                        returedJSON1 = response.body().string();

                        try {
                            jsonObject = new JSONObject(returedJSON1);
                        }catch(JSONException jsone){
                            Log.e("json에러", jsone.toString());
                        }
                    }else{
                        return 2;
                    }
                }catch (UnknownHostException une) {
                } catch (UnsupportedEncodingException uee) {
                } catch (Exception e) {
                } finally{
                    if(response != null) {
                        response.close(); //3.* 이상에서는 반드시 닫아 준다.
                    }
                }

                if(jsonObject != null){
                    try {
                        if(jsonObject.get(activity.getResources().getString(R.string.url_message)).equals(activity.getResources().getString(R.string.url_success))){

                            return 0;
                        }else{
                            return 3;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return 5;
                    }
                }else{
                    return 4;
                }
            }

            @Override
            protected void onPostExecute(Integer result) {
                if(result == 0){
                    Toast.makeText(activity,"상담 신청하기 성공",Toast.LENGTH_SHORT).show();
                    dismiss();
                    ((QuotationDetailActivity)activity).update();
                }else{
                }

                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class WriteCommentDialog extends Dialog {

        private int requestId;
        private Activity activity;
        private CircleImageView image;
        private TextView bank;
        private TextView name;
        private RatingBar ratingBar;
        private EditText comment;
        private TextView back;
        private TextView writeComment;
        private ProgressBar progressBar;

        private String imageInfo;
        private String bankInfo;
        private String nameInfo;
        private float ratingBarInfo;
        private String content;

        public WriteCommentDialog(@NonNull Context context) {
            super(context);
        }

        public void setInfo(Activity activity,int requestId){
            this.activity =activity;
            this.requestId = requestId;
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
            progressBar = (ProgressBar)findViewById(R.id.progressBar);


            comment.setImeOptions(EditorInfo.IME_ACTION_DONE);
            comment.setRawInputType(InputType.TYPE_CLASS_TEXT);

            comment.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        content = comment.getText().toString();
                        ratingBarInfo = ratingBar.getRating();
                        new WritePostScript().execute();
                        //Toast.makeText(getContext(),"id = "+ requestId +" 에 평점 " + ratingBar.getRating() +  "점과 " + comment.getText()+ " 라고 댓글을 작성하였습니다.",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(activity,"100자 이상은 작성하실 수 없습니다.",Toast.LENGTH_SHORT).show();
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
                    if(comment.getText().length() < 10){
                        Toast.makeText(activity,"10자 이상 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }else{
                        content = comment.getText().toString();
                        ratingBarInfo = ratingBar.getRating();
                        new WritePostScript().execute();
                    }
                }
            });

            new SelectedFeedbackDetail().execute();
        }

        private class SelectedFeedbackDetail extends AsyncTask<Void, Void, Integer>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //시작 전에 ProgressBar를 보여주어 사용자와 interact
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                boolean flag;
                Response response = null;

                OkHttpClient toServer;


                JSONObject jsonObject = null;

                try{
                    toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                    Request request = new Request.Builder()
                            .url(String.format(activity.getResources().getString(R.string.selected_feedback_detail_url),String.valueOf(requestId)))
                            .get()
                            .build();

                    //동기 방식
                    response = toServer.newCall(request).execute();

                    flag = response.isSuccessful();

                    String returedJSON;

                    if(flag){ //성공했다면
                        returedJSON = response.body().string();

                        try {
                            jsonObject = new JSONObject(returedJSON);
                        }catch(JSONException jsone){
                            Log.e("json에러", jsone.toString());
                        }
                    }else{
                        return 2;
                    }
                }catch (UnknownHostException une) {
                } catch (UnsupportedEncodingException uee) {
                } catch (Exception e) {
                } finally{
                    if(response != null) {
                        response.close(); //3.* 이상에서는 반드시 닫아 준다.
                    }
                }

                if(jsonObject != null){
                    try {
                        if(jsonObject.get(activity.getResources().getString(R.string.url_message)).equals(activity.getResources().getString(R.string.url_success))){
                            JSONObject data= jsonObject.getJSONObject(activity.getResources().getString(R.string.url_data));

                            nameInfo = data.getString("name");
                            imageInfo = data.getString("photo");
                            bankInfo = data.getString("company_name");

                            return 0;
                        }else{
                            return 3;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return 5;
                    }
                }else{
                    return 4;
                }
            }

            @Override
            protected void onPostExecute(Integer result) {
                if(result == 0){
                    name.setText(nameInfo);
                    bank.setText(bankInfo);
                    Glide.with(activity)
                            .load(imageInfo)
                            .animate(android.R.anim.slide_in_left)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                            .into(image);
                }else{
                }

                progressBar.setVisibility(View.GONE);
            }
        }

        private class WritePostScript extends AsyncTask<Void, Void, Integer>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //시작 전에 ProgressBar를 보여주어 사용자와 interact
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                boolean flag;
                Response response = null;

                OkHttpClient toServer;


                JSONObject jsonObject = null;

                try{
                    toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                    RequestBody postBody = new FormBody.Builder()
                            .add("requestId",String.valueOf(requestId))
                            .add("content",content)
                            .add("score",String.valueOf(ratingBarInfo))
                            .build();

                    Request request = new Request.Builder()
                            .url(activity.getResources().getString(R.string.write_post_script_url))
                            .post(postBody)
                            .build();

                    //동기 방식
                    response = toServer.newCall(request).execute();

                    flag = response.isSuccessful();

                    String returedJSON;

                    if(flag){ //성공했다면
                        returedJSON = response.body().string();

                        try {
                            jsonObject = new JSONObject(returedJSON);
                        }catch(JSONException jsone){
                            Log.e("json에러", jsone.toString());
                        }
                    }else{
                        return 2;
                    }
                }catch (UnknownHostException une) {
                } catch (UnsupportedEncodingException uee) {
                } catch (Exception e) {
                } finally{
                    if(response != null) {
                        response.close(); //3.* 이상에서는 반드시 닫아 준다.
                    }
                }

                if(jsonObject != null){
                    try {
                        if(jsonObject.get(activity.getResources().getString(R.string.url_message)).equals(activity.getResources().getString(R.string.url_success))){
                            //JSONArray data1= jsonObject1.getJSONArray("data");
                            //ArrayList<JSONArray> tmp = new ArrayList();
                            //tmp.add(data1);
                            return 0;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return 3;
                    }
                }else{
                    return 1;
                }

                return 4;
            }

            @Override
            protected void onPostExecute(Integer result) {
                if(result == 0){
                    Toast.makeText(activity,"후기를 등록하였습니다.",Toast.LENGTH_SHORT).show();
                    dismiss();
                    ((QuotationDetailActivity)activity).update();
                }else{
                }

                progressBar.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        }
    }



}
