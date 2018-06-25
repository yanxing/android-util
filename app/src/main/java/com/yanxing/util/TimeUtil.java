package com.yanxing.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 * Created by 李双祥 on 2017/3/30.
 */
public class TimeUtil {


    /**
     * 获取本年，截止到当日
     * @return  str[0] 2017-01-01  str[1] 2017-12-31
     */
    public static String[] getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        String str[]=new String[2];
        str[0]=year+"-01-"+"01";
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);
        if (month<10){
            monthStr="0"+monthStr;
        }
        if (day<10){
            dayStr="0"+dayStr;
        }
        str[1]=year+"-"+monthStr+"-"+dayStr;
        return str;
    }

    /**
     * 获取本月
     * @return  str[0] 2017-01-01  str[1] 2017-01-31
     */
    public static String[] getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        String str[]=new String[2];
        int month=calendar.get(Calendar.MONTH)+1;
        String monthStr=String.valueOf(month);
        if (month<10){
            monthStr="0"+monthStr;
        }
        str[0]=year+"-"+monthStr+"-"+"01";
        str[1]=year+"-"+monthStr+"-"+calendar.getActualMaximum(Calendar.DATE);
        return str;
    }

    /**
     * 获取本日
     * @return  str[0] 2017-01-01  str[1] 2017-01-01
     */
    public static String[] getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        String str[]=new String[2];
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);
        if (month<10){
            monthStr="0"+monthStr;
        }
        if (day<10){
            dayStr="0"+dayStr;
        }
        str[0]=year+"-"+monthStr+"-"+dayStr;
        str[1]=str[0];
        return str;
    }

    /**
     * 获取本日
     * @return 2017-01-01
     */
    public static String getToday(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String months=String.valueOf(month);
        String days=String.valueOf(day);
        if (month<10){
            months="0"+month;
        }
        if (day<10){
            days="0"+day;
        }
        return year+"-"+months+"-"+days;
    }
    /**
     * 获取本月
     * @return 2017-01
     */
    public static String getMonth(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        String months=String.valueOf(month);
        if (month<10){
            months="0"+month;
        }
        return year+"-"+months;
    }


    /**
     * 获取今日 小时 分钟 秒
     * @return 09:23:10
     */
    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        int s=calendar.get(Calendar.SECOND);
        String hourStr=String.valueOf(hour);
        String minStr=String.valueOf(min);
        String sStr=String.valueOf(s);
        if (hour<10){
            hourStr="0"+hour;
        }
        if (min<10){
            minStr="0"+min;
        }
        if (s<10){
            sStr="0"+s;
        }
        return hourStr+":"+minStr+":"+sStr;
    }

    /**
     * 获取今日 小时 分钟
     * @return 09:23
     */
    public static String getCurrentTime1(){
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        String hourStr=String.valueOf(hour);
        String minStr=String.valueOf(min);
        if (hour<10){
            hourStr="0"+hour;
        }
        if (min<10){
            minStr="0"+min;
        }
        return hourStr+":"+minStr;
    }


    /**
     * 获取本月到本日，如果今天是1号，则返回上个月
     * @return 2017-01-01  2017-01-22
     */
    public static String[] getDateStartToday(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String months=String.valueOf(month);
        String days=String.valueOf(day);
        String str[]=new String[2];
        //如果是月初的1号，则返回上个月
        if (day==1){
            //如果这一天是1月1号，则返回上一年的12月01号-12:31号
            if (month==1){
                str[0]=(year-1)+"-12-01";
                str[1]=(year-1)+"-12-31";
                return str;
            }else if (month==3){
                //上个月是2月份，判断是否是瑞年
                if (year%4==0&&year%100!=0||year%400==0){
                    str[0]=year+"-02-01";
                    str[1]=year+"-02-29";
                }else {
                    str[0]=year+"-02-01";
                    str[1]=year+"-02-28";
                }
                return str;
            }else {
                month--;
                if (month<10){
                    months="0"+month;
                }else {
                    months=String.valueOf(month);
                }
                if (month==1||month==3||month==5||month==7||month==8||month==10){
                    str[0]=year+"-"+months+"-"+"01";
                    str[1]=year+"-"+months+"-"+"31";
                }else {
                    str[0]=year+"-"+months+"-"+"01";
                    str[1]=year+"-"+months+"-"+"30";
                }
                return str;
            }
        }else {
            if (month<10){
                months="0"+month;
            }
            if (day<10){
                days="0"+day;
            }
            str[0]=year+"-"+months+"-"+"01";
            str[1]=year+"-"+months+"-"+days;
            return str;
        }
    }

    /**
     * 根据美国东部时间获取Calendar
     * @return 2017-01-01
     */
    public static String format(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);
        if (month<10){
            monthStr="0"+monthStr;
        }
        if (day<10){
            dayStr="0"+dayStr;
        }
        return year+"-"+monthStr+"-"+dayStr;
    }

    /**
     * 根据美国东部时间获取Calendar
     * @return 2017-01-01
     */
    public static String format4(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        if (month<10){
            monthStr="0"+monthStr;
        }
        return year+"-"+monthStr;
    }

    /**
     * app时间选择，最早的时间
     * @return
     */
    public static Calendar getStartTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1993, 2, 4);
        return calendar;
    }

    /**
     * 根据美国东部时间获取Calendar
     * @return 2017-01-01
     */
    public static String format1(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);

        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        String hourStr=String.valueOf(hour);
        String minuteStr=String.valueOf(minute);
        if (month<10){
            monthStr="0"+monthStr;
        }
        if (day<10){
            dayStr="0"+dayStr;
        }
        if (hour<10){
            hourStr="0"+hourStr;
        }
        if (minute<10){
            minuteStr="0"+minuteStr;
        }
        return year+"-"+monthStr+"-"+dayStr+" "+hourStr+":"+minuteStr;
    }

    /**
     * 根据美国东部时间获取Calendar 用于发布公告定时时间
     * @return 2017-01-01
     */
    public static String format2(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr=String.valueOf(month);
        String dayStr=String.valueOf(day);

        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        String hourStr=String.valueOf(hour);
        String minuteStr=String.valueOf(minute);
        if (month<10){
            monthStr="0"+monthStr;
        }
        if (day<10){
            dayStr="0"+dayStr;
        }
        if (hour<10){
            hourStr="0"+hourStr;
        }
        if (minute<10){
            minuteStr="0"+minuteStr;
        }
        return "01:"+minuteStr+":"+hourStr+" "+dayStr+"-"+monthStr+"-"+year;
    }

    /**
     * 根据美国东部时间获取Calendar
     * @return 08:00
     */
    public static String format3(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        String hourStr=String.valueOf(hour);
        String minuteStr=String.valueOf(minute);
        if (hour<10){
            hourStr="0"+hourStr;
        }
        if (minute<10){
            minuteStr="0"+minuteStr;
        }
        return hourStr+":"+minuteStr;
    }

    /**
     * 格式化时间，04-05 13:19
     *
     * @param time 2017-04-05 13:19:51
     * @return
     */
    public static String formatTime(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            throw new IllegalArgumentException("time is Illegal");
        }
        String tem[]=time.split(" ");
        String left[]=tem[0].split("-");
        String right[]=tem[1].split(":");
        return left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
    }

    /**
     * 格式化时间，2017-04-05
     *
     * @param time 2017-04-05 13:19:51
     * @return 2017-04-05
     */
    public static String formatTime1(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            throw new IllegalArgumentException("time is Illegal");
        }
        String tem[]=time.split(" ");
        String left[]=tem[0].split("-");
        return left[0]+"-"+left[1]+"-"+left[2];
    }

    /**
     * 格式化时间，2017-04-05 13:19
     *
     * @param time 2017-04-05 13:19:51
     * @return
     */
    public static String formatTime2(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            throw new IllegalArgumentException("time is Illegal");
        }
        String tem[]=time.split(" ");
        String left[]=tem[0].split("-");
        String right[]=tem[1].split(":");
        return left[0]+"-"+left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
    }

    /**
     * 格式化时间，13:19
     *
     * @param time 13:19:51
     * @return
     */
    public static String formatTime3(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            return time;
        }
        String tem[]=time.split(":");
        return tem[0]+":"+tem[1];
    }

    /**
     * 格式化时间，2017-04-05 13:19
     *
     * @param time 2017-04-05 13:19:51
     * @return
     */
    public static String formatTime6(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            throw new IllegalArgumentException("time is Illegal");
        }
        String tem[]=time.split(" ");
        String left[]=tem[0].split("-");
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String right[]=tem[1].split(":");
        if (year==Integer.parseInt(left[0])&&month==Integer.parseInt(left[1])&&day==Integer.parseInt(left[2])){
            return "今天 "+right[0]+":"+right[1];
        }else if (year!=Integer.parseInt(left[1])){
            return left[0]+"-"+left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
        }else {
            return left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
        }
    }

    /**
     * 格式化时间，今天、昨天、 月-日 时：分、 年-月-日 时：分
     *
     * @param time 2017-04-05 13:19:51
     * @return
     */
    public static String formatTime7(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            return  "";
        }
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        String tem[]=time.split(" ");
        String left[]=tem[0].split("-");
        String right[]=tem[1].split(":");
        //今年
        if (year==Integer.parseInt(left[0])){
            //本月
            if (month==Integer.parseInt(left[1])){
                //今天
                if (day==Integer.parseInt(left[2])){
                    return "今天 "+right[0]+":"+right[1];
                }else if (day==Integer.parseInt(left[2])+1){
                    //昨天
                    return "昨天 "+right[0]+":"+right[1];
                }else {
                    //对于上个月最后一天，显示月-日 时：分
                    return left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
                }
            }else {
                return left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
            }
        }else {
            return left[0]+"-"+left[1]+"-"+left[2]+" "+right[0]+":"+right[1];
        }
    }


    /**
     * 格式化时间，04-05
     *
     * @param time 2017-04-05 13:19:51
     * @return 04月05日
     */
    public static String formatTime4(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            throw new IllegalArgumentException("time is Illegal");
        }
        String tem[]=time.split(" ");
        String left[]=tem[0].split("-");
        return left[1]+"月"+left[2]+"日";
    }

    /**
     * 格式化时间，04-05 13:19
     *
     * @param time 2017-04-05 13:19:51
     * @return
     */
    public static String formatTime5(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2} (\\d){2}(:(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            return time;
        }
        String tem[]=time.split(" ");
        String right[]=tem[1].split(":");
        return right[0]+":"+right[1];
    }

    /**
     * 格式化时间，04-05
     *
     * @param time 2017-04-05
     * @return
     */
    public static String getMonthDate(String time) {
        if (TextUtils.isEmpty(time)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()){
            return time;
        }
        String tem[]=time.split("-");
        return tem[1]+"-"+tem[2];
    }

    /**
     * 是否下班
     * @param offTime 18:00
     * @return true下班
     */
    public static boolean isOffDuty(String offTime){
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        String hourStr=String.valueOf(hour);
        String minuteStr=String.valueOf(minute);
        if (hour<10){
            hourStr="0"+hourStr;
        }
        if (minute<10){
            minuteStr="0"+minuteStr;
        }
        return (hourStr+":"+minuteStr).compareTo(offTime)>=0;
    }

    /**
     * 比较时间大小 大于true
     * @param time1 格式2017-08-14 16:30:14
     * @param time2 格式2017-08-14 16:30:14
     * @return
     */
    public static boolean compartTo(String time1,String time2){
        if (TextUtils.isEmpty(time1)){
            return false;
        }
        if (TextUtils.isEmpty(time2)){
            return true;
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=simpleDateFormat.parse(time1);
            Date date2=simpleDateFormat.parse(time2);
            Calendar c1=Calendar.getInstance();
            c1.setTime(date1);
            Calendar c2=Calendar.getInstance();
            c2.setTime(date2);
            return c1.compareTo(c2) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 指定日期获取星期几
     * @param date yyyy-MM-dd
     * @return
     */
    public static String getWeekday(String date){
        if (TextUtils.isEmpty(date)){
            return "";
        }
        String regex = "^(\\d){4}(-(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()){
            return date;
        }
        final String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int dayForWeek = 0;
        try {
            c.setTime(format.parse(date));
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ){
                dayForWeek = 7;
            }else{
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weeks[dayForWeek-1];
    }

}
