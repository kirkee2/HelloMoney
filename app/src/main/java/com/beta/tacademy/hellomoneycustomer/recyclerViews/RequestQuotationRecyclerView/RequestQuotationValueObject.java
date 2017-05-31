package com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView;

/**
 * Created by kirkee on 2017. 5. 31..
 */

public class RequestQuotationValueObject {
    private int type;
    private int step;
    private String text;

    public RequestQuotationValueObject(int type, int step, String text) {
        this.type = type;
        this.step = step;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
