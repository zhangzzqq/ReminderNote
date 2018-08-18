package com.example.zq.remindernote.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/7/2
 */
public class DateUtils {

    public static String  getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }


    public static String  getCurrentDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
    }



    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
        return days;
    }




}
