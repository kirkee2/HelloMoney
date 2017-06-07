package com.beta.tacademy.hellomoneycustomer.recyclerViews.FAQRecyclerView;

/**
 * Created by kirkee on 2017. 6. 7..
 */

public class FAQValueObject {
    String title;
    String content;
    boolean isOpen;


    public FAQValueObject(String title, String content) {
        this.title = title;
        this.content = content;
        isOpen = false;

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
