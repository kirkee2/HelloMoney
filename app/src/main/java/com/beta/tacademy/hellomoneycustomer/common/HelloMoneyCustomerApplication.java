package com.beta.tacademy.hellomoneycustomer.common;

import android.app.Application;
import android.content.res.Configuration;

public class HelloMoneyCustomerApplication extends Application {
    private static HelloMoneyCustomerApplication helloMoneyCustomerApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        helloMoneyCustomerApplication = this;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }


    public static HelloMoneyCustomerApplication getInstance(){
        return helloMoneyCustomerApplication;
    }
}
