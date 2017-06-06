package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 6. 5..
 */

public class QuotationDetailHeaderObject {
    private int id;
    private ArrayList<Double> rate;
    private String remainTime;
    private int loanType;
    private String region1;
    private String region2;
    private String region3;
    private String apt;
    private String size;
    private int loanSum;
    private int rateType;
    private String loanDate;
    private int jobType;
    private String telephone;

    public QuotationDetailHeaderObject(int id, ArrayList<Double> rate, String remainTime, int loanType, String region1, String region2, String region3, String apt, String size, int loanSum, int rateType, String loanDate, int jobType, String telephone) {
        this.id = id;
        this.rate = rate;
        this.remainTime = remainTime;
        this.loanType = loanType;
        this.region1 = region1;
        this.region2 = region2;
        this.region3 = region3;
        this.apt = apt;
        this.size = size;
        this.loanSum = loanSum;
        this.rateType = rateType;
        this.loanDate = loanDate;
        this.jobType = jobType;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
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

    public int getRateType() {
        return rateType;
    }

    public void setRateType(int rateType) {
        this.rateType = rateType;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public ArrayList<Double> getRate() {
        return rate;
    }

    public void setRate(ArrayList<Double> rate) {
        this.rate = rate;
    }
}
