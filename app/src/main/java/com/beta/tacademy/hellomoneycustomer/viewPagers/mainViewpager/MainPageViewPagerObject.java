package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;

public class MainPageViewPagerObject {
    private int id;
    private int ongoingStatus;
    private int type;
    private String leftTime;
    private String region1;
    private String region2;
    private String region3;
    private String apt;
    private String size;
    private int currentQuotation;

    public MainPageViewPagerObject(int id, int ongoingStatus, int type, String leftTime, String region1, String region2, String region3, String apt, String size, int currentQuotation) {
        this.id = id;
        this.ongoingStatus = ongoingStatus;
        this.type = type;
        this.leftTime = leftTime;
        this.region1 = region1;
        this.region2 = region2;
        this.region3 = region3;
        this.apt = apt;
        this.size = size;
        this.currentQuotation = currentQuotation;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOngoingStatus() {
        return ongoingStatus;
    }

    public void setOngoingStatus(int ongoingStatus) {
        this.ongoingStatus = ongoingStatus;
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
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

    public int getCurrentQuotation() {
        return currentQuotation;
    }

    public void setCurrentQuotation(int currentQuotation) {
        this.currentQuotation = currentQuotation;
    }
}
