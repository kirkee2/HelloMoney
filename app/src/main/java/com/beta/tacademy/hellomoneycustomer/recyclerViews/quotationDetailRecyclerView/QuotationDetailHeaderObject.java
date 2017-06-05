package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 6. 5..
 */

public class QuotationDetailHeaderObject {
    private int id;
    private int finalQuotationCount;
    private double averageInterestRate;
    private ArrayList<Integer> rate;
    private String remainTime;
    private int loanType;
    private String region1;
    private String region2;
    private String region3;
    private String apt;
    private String size;
    private int loanSum;
    private int rateType;
    private int loanDate;
    private int jobType;
    private String telephone;

    public QuotationDetailHeaderObject(int id, int finalQuotationCount, double averageInterestRate, ArrayList<Integer> rate, String remainTime, int loanType, String region1, String region2, String region3, String apt, String size, int loanSum, int rateType, int loanDate, int jobType, String telephone) {
        this.id = id;
        this.finalQuotationCount = finalQuotationCount;
        this.averageInterestRate = averageInterestRate;
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

    public int getFinalQuotationCount() {
        return finalQuotationCount;
    }

    public void setFinalQuotationCount(int finalQuotationCount) {
        this.finalQuotationCount = finalQuotationCount;
    }

    public double getAverageInterestRate() {
        return averageInterestRate;
    }

    public void setAverageInterestRate(double averageInterestRate) {
        this.averageInterestRate = averageInterestRate;
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

    public int getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(int loanDate) {
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

    public ArrayList<Integer> getRate() {
        return rate;
    }

    public void setRate(ArrayList<Integer> rate) {
        this.rate = rate;
    }
}
