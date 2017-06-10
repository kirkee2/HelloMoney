package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.widget.ArrayAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kirkee on 2017. 6. 5..
 */

public class QuotationDetailHeaderObject {
    private int id;
    private String ongoingStatus;
    //private ArrayList<Float> rate;
    private String remainTime;
    private String loanType;
    private String region1;
    private String region2;
    private String region3;
    private String apt;
    private String size;
    private int loanSum;
    private String rateType;
    private String loanDate;
    private String jobType;
    private String telephone;

    public QuotationDetailHeaderObject(int id, String ongoingStatus, String remainTime, String loanType, String region1, String region2, String region3, String apt, String size, int loanSum, String rateType, String loanDate, String jobType, String telephone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        try {
            Date date = sdf.parse(remainTime);
            this.remainTime = sdf.format(date);
            date = sdf.parse(loanDate);
            this.loanDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.id = id;
        this.ongoingStatus = ongoingStatus;
        this.loanType = loanType;
        this.region1 = region1;
        this.region2 = region2;
        this.region3 = region3;
        this.apt = apt;
        this.size = size;
        this.loanSum = loanSum;
        this.rateType = rateType;
        this.jobType = jobType;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOngoingStatus() {
        return ongoingStatus;
    }

    public void setOngoingStatus(String ongoingStatus) {
        this.ongoingStatus = ongoingStatus;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public String getRegion3() {
        return region3;
    }

    public void setRegion3(String region3) {
        this.region3 = region3;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getLoanSum() {
        return loanSum;
    }

    public void setLoanSum(int loanSum) {
        this.loanSum = loanSum;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
