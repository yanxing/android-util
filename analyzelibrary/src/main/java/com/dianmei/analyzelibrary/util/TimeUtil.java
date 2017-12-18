package com.dianmei.analyzelibrary.util;

import java.util.Calendar;

/**
 * @author 李双祥 on 2017/12/8.
 */
public class TimeUtil {

    /**
     * 获取今天日期
     * @return 格式2017-09-09
     */
    public static String getToday(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);
        if (month<10){
            monthStr="0"+month;
        }
        if (day<10){
            dayStr="0"+day;
        }
        return year+"-"+monthStr+"-"+dayStr;
    }

    /**
     * 获取当前时间
     * @return 格式 2017-09-09 09:09:09
     */
    public static String getTime(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        int s=calendar.get(Calendar.SECOND);

        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);
        String hourStr=String.valueOf(hour);
        String minStr=String.valueOf(min);
        String sStr=String.valueOf(s);
        if (month<10){
            monthStr="0"+month;
        }
        if (day<10){
            dayStr="0"+day;
        }
        if (hour<10){
            hourStr="0"+hour;
        }
        if (min<10){
            minStr="0"+min;
        }
        if (s<10){
            sStr="0"+s;
        }
        return year+"-"+monthStr+"-"+dayStr+" "+hourStr+":"+minStr+":"+sStr;

    }
}
