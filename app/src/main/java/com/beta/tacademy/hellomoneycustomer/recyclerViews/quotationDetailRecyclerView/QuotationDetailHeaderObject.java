package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import android.widget.ArrayAdapter;

import com.beta.tacademy.hellomoneycustomer.common.CommonClass;

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
    private String leftTime;
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
    private boolean isReviewed;
    private int selectedEstimateId;
    private String content;
    private double score;
    private String reviewRegisterTime;
    private String name;
    private String photo;
    private String bank;
    private String agentId;
    private int leftSecond;

    public QuotationDetailHeaderObject(int id, String ongoingStatus, String leftTime, String loanType, String region1, String region2, String region3, String apt, String size, int loanSum, String rateType, String loanDate, String jobType, String telephone, boolean isReviewed, int selectedEstimateId, String content, double score, String reviewRegisterTime, String name, String photo, String bank,String agentId) {
        this.id = id;
        this.ongoingStatus = ongoingStatus;
        this.leftTime = leftTime;
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
        this.isReviewed = isReviewed;
        this.selectedEstimateId = selectedEstimateId;
        this.content = content;
        this.score = score;
        this.reviewRegisterTime = reviewRegisterTime;
        this.name = name;
        this.photo = photo;
        this.bank = bank;
        this.agentId = agentId;
        this.leftSecond = CommonClass.timeLeftSecondParsing(leftTime);
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

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
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

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public int getSelectedEstimateId() {
        return selectedEstimateId;
    }

    public void setSelectedEstimateId(int selectedEstimateId) {
        this.selectedEstimateId = selectedEstimateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getReviewRegisterTime() {
        return reviewRegisterTime;
    }

    public void setReviewRegisterTime(String reviewRegisterTime) {
        this.reviewRegisterTime = reviewRegisterTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public int getLeftSecond() {
        return leftSecond;
    }

    public void setLeftSecond(int leftSecond) {
        this.leftSecond = leftSecond;
    }
}
