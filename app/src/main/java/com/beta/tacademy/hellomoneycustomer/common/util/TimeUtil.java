package com.beta.tacademy.hellomoneycustomer.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by len on 2017. 6. 15..
 */

public class TimeUtil {
    public static String timeParsing(String time){
        TimeZone utc = TimeZone.getTimeZone("UTC");

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일");
        transFormat.setTimeZone(utc);
        dateFormat.setTimeZone(utc);


        try {
            Date toDate = transFormat.parse(time);
            String pastTimeInfo = dateFormat.format(toDate.getTime());
            return pastTimeInfo;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String timeDashParsing(String time){
        TimeZone utc = TimeZone.getTimeZone("UTC");

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        transFormat.setTimeZone(utc);
        dateFormat.setTimeZone(utc);


        try {
            Date toDate = transFormat.parse(time);
            String pastTimeInfo = dateFormat.format(toDate.getTime());
            return pastTimeInfo;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int timeLeftSecondParsing(String time){
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
        transFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date endDate = transFormat.parse(time);

            long endTime = endDate.getTime(); //현재의 시간 설정

            Calendar cal = Calendar.getInstance();
            Date startDate=cal.getTime();

            long startTime=startDate.getTime();

            long mills=endTime-startTime; //분으로 변환

            //long hour=mills/3600000;
            //long min=mills/60000;
            int second= (int)mills/1000;

            return second;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
