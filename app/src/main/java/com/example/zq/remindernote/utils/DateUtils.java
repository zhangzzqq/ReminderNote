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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        return df.format(new Date());
    }
}
