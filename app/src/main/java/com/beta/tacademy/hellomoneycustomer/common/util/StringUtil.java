package com.beta.tacademy.hellomoneycustomer.common.util;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Created by len on 2017. 6. 19..
 */

public class StringUtil {
    public static String formatNumber2(int num){
        return String.format("%02d",num);
    }

    public static String formatMoney(int num){
        return String.format("%,d", num);
    }

    public static String formatPhoneNumber(String phoneNumber) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

        if(!Pattern.matches(regEx, phoneNumber)) return null;

        return phoneNumber.replaceAll(regEx, "$1-$2-$3");

    }
}
