package com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView;

public class MainValueObject {
    private int id;
    private int type;
    private String pastTime;
    private String region1;
    private String region2;
    private String region3;
    private String apt;
    private int star;
    private String content;
    private int benefit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MainValueObject(int id, int type, String pastTime, String region1, String region2, String region3, String apt, int star, String content, int benefit) {
        this.id = id;

        this.type = type;
        this.pastTime = pastTime;
        this.region1 = region1;
        this.region2 = region2;
        this.region3 = region3;
        this.apt = apt;
        this.star = star;
        this.content = content;
        this.benefit =benefit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getPastTime() {
        return pastTime;
    }

    public void setPastTime(String pastTime) {
        this.pastTime = pastTime;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBenefit() {
        return benefit;
    }

    public void setBenefit(int benefit) {
        this.benefit = benefit;
    }
}

