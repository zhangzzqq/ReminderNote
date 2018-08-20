package com.example.zq.remindernote.Base;

import android.app.Application;
import android.util.Log;

import com.example.zq.remindernote.common.Constant;
import com.example.zq.remindernote.utils.ACache;
import com.example.zq.remindernote.utils.DateUtils;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stevenZhang on 2016/9/25.
 */
public class App  extends Application{

    public static ACache aCache;
    private static final String TAG ="App";
    @Override
    public void onCreate() {
        super.onCreate();

        aCache = ACache.get(this);
        LitePal.initialize(this);


        /**
         *
         * 时间在变动，所以大于一天之后清除掉对应保存的缓存
         */
        String cacheCurrentDay = aCache.getAsString(Constant.saveCurrentDay);
       if(cacheCurrentDay!=null){
           Date currentDay=null;
           Date cacheDay=null;
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

           //缓存天
           try {
                cacheDay = formatter.parse(cacheCurrentDay);
           } catch (ParseException e) {
               e.printStackTrace();
           }

           //当前天
           try {
               currentDay = formatter.parse(DateUtils.getCurrentDay());
           } catch (ParseException e) {
               e.printStackTrace();
           }

            int  days = DateUtils. differentDaysByMillisecond(currentDay,cacheDay);

           if(days<1){

           }else if(days==1){
               aCache.remove(Constant.yesterdaymap);
               Log.e(TAG,"移除昨天的缓存");
           }else if(days>1){
               aCache.remove(Constant.yesterdaymap);
               aCache.remove(Constant.todaymap);
               Log.e(TAG,"移除今天和昨天的缓存");
           }

       }else {

           aCache.put(Constant.saveCurrentDay,DateUtils.getCurrentDay());
       }


    }



}
