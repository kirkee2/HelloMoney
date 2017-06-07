package com.beta.tacademy.hellomoneycustomer.recyclerViews.counselorDetailRecyclerView;

/**
 * Created by kirkee on 2017. 6. 7..
 */

public class CounselorDetailHeaderObject {
    private int counselorId;
    private String imageUrl;
    private String bank;
    private String name;
    private String introductionMent;

    public CounselorDetailHeaderObject(int counselorId, String imageUrl, String bank, String name, String introductionMent) {
        this.counselorId = counselorId;
        this.imageUrl = imageUrl;
        this.bank = bank;
        this.name = name;
        this.introductionMent = introductionMent;
    }

    public int getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(int counselorId) {
        this.counselorId = counselorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getIntroductionMent() {
        return introductionMent;
    }

    public void setIntroductionMent(String introductionMent) {
        this.introductionMent = introductionMent;
    }
}
