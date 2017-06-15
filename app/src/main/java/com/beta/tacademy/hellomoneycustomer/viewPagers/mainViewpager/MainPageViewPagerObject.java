package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;

import android.os.Parcel;
import android.os.Parcelable;

import com.beta.tacademy.hellomoneycustomer.common.CommonClass;

public class MainPageViewPagerObject implements Parcelable {
    private int id;
    private String ongoingStatus;
    private String type;
    private String leftTime;
    private String region1;
    private String region2;
    private String region3;
    private String apt;
    private String size;
    private int currentQuotation;
    private int leftSecond;

    public MainPageViewPagerObject(Parcel in) {
        readFromParcel(in);
    }

    public MainPageViewPagerObject(int id, String ongoingStatus, String type, String leftTime, String region1, String region2, String region3, String apt, String size, int currentQuotation) {
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

    public String getType() {

        return type;
    }

    public void setType(String type) {
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

    public int getLeftSecond() {
        return leftSecond;
    }

    public void setLeftSecond(int leftSecond) {
        this.leftSecond = leftSecond;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.ongoingStatus);
        dest.writeString(this.type);
        dest.writeString(this.leftTime);
        dest.writeString(this.region1);
        dest.writeString(this.region2);
        dest.writeString(this.region3);
        dest.writeString(this.apt);
        dest.writeString(this.size);
        dest.writeInt(this.currentQuotation);
    }


    private void readFromParcel(Parcel in){
        this.id = in.readInt();
        this.ongoingStatus = in.readString();
        this.type = in.readString();
        this.leftTime = in.readString();
        this.region1 = in.readString();
        this.region2 = in.readString();
        this.region3 = in.readString();
        this.apt = in.readString();
        this.size = in.readString();
        this.currentQuotation = in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MainPageViewPagerObject createFromParcel(Parcel in) {
            return new MainPageViewPagerObject(in);
        }

        @Override
        public MainPageViewPagerObject[] newArray(int size) {
            return new MainPageViewPagerObject[size];
        }
    };
}
