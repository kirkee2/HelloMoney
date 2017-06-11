package com.beta.tacademy.hellomoneycustomer.recyclerViews.quotationDetailRecyclerView;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 6. 5..
 */

public class QuotationDetailObject {
    private int id;
    private String bank;
    private String name;
    private String loanType;
    private double interestRate;
    private String imageUrl;

    public QuotationDetailObject(int id, String bank, String name, String loanType, double interestRate, String imageUrl) {
        this.id = id;
        this.bank = bank;
        this.name = name;
        this.loanType = loanType;
        this.interestRate = interestRate;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
