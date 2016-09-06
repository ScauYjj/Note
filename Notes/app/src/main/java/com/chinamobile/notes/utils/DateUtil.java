package com.chinamobile.notes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yjj on 2016/8/24.
 */
public class DateUtil {

    /**
     * String 转换 Date
     *
     * @param str
     * @param format
     * @return
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static int daysOfTwo(Date originalDate, Date compareDateDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(originalDate);
        int originalDay = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(compareDateDate);
        int compareDay = aCalendar.get(Calendar.DAY_OF_YEAR);

        return originalDay - compareDay;
    }

    public static String FriendlyDate(Date compareDate) {
        Date nowDate = new Date();
        int dayDiff = daysOfTwo(nowDate, compareDate);

        if (dayDiff == 0) {
            return "今日";
        }else if (dayDiff == 1){
            return "昨日";
        }else if (dayDiff == 2){
            return "前日";
        }else if (dayDiff<0){
            return "未来";
        }else{
            return new SimpleDateFormat("M月d日 E").format(compareDate);
        }
    }

    public static String getCurrentTime(){
        //SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

}