package com.beta.tacademy.hellomoneycustomer.common;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kirkee on 2017. 6. 8..
 */

public class CommonClass {
    public static String getUUID() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getString("UUID",null);
    }
}
