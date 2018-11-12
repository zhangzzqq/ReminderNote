package com.example.zq.remindernote.Base;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.example.zq.remindernote.common.Constant;
import com.example.zq.remindernote.utils.ACache;
import com.example.zq.remindernote.utils.DateUtils;
import com.tencent.bugly.Bugly;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stevenZhang on 2016/9/25.
 */
public class App extends Application {

    public static ACache aCache;
    private static final String TAG = "App";
    private  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate() {
        super.onCreate();

        aCache = ACache.get(this);
        LitePal.initialize(this);
        Bugly.init(getApplicationContext(), "bafd08ebde", false);

//        /**
//         *
//         * 时间在变动，所以大于一天之后清除掉对应保存的缓存
//         * 这种做法需要处理当前缓存的时间和，以及处理缓存不断变化的情况
//         */
//        String cacheCurrentDay = aCache.getAsString(Constant.saveCurrentDay);
//        Log.e(TAG, "cacheCurrentDay==" + cacheCurrentDay);
//        if (cacheCurrentDay != null) {
//            Date currentDay = null;
//            Date cacheDay = null;
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//            //缓存天
//            try {
//                cacheDay = formatter.parse(cacheCurrentDay);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            //当前天
//            try {
//                currentDay = formatter.parse(DateUtils.getCurrentDay());
//                Log.e(TAG, "DateUtils.getCurrentDay()==" + DateUtils.getCurrentDay());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            int days = DateUtils.differentDaysByMillisecond(currentDay, cacheDay);
//
//            if (days < 1) {
//
//            } else if (days == 1) {
//                aCache.remove(Constant.yesterdaymap);
//                Log.e(TAG, "移除昨天的缓存");
//            } else if (days > 1) {
//                aCache.remove(Constant.yesterdaymap);
//                aCache.remove(Constant.todaymap);
//                Log.e(TAG, "移除今天和昨天的缓存");
//            }
//
//        } else {
//            aCache.put(Constant.saveCurrentDay, DateUtils.getCurrentDay());
//            Log.e(TAG, "saveCurrentDay==" + DateUtils.getCurrentDay());
//        }


        /**
         *
         * 当前时间与map中的时间进行比较，如果在当前时间范围内则继续保存数据
         * 否则清除对应的数据
         *
         */

        HashMap todayMap = (HashMap) aCache.getAsObject(Constant.todaymap);
        if(todayMap!=null){
            String strTodyKey =getKeyOrNull(todayMap);
            if(strTodyKey!=null){
                String subResult = strTodyKey.substring(0,10);
                Log.e(TAG,"今天缓存=="+subResult);
                try {
                    Date cacheDay = format.parse(subResult);
                    Date  currentDay = format.parse(DateUtils.getCurrentDay());
                    int days = DateUtils.differentDaysByMillisecond(currentDay, cacheDay);
                    if(days>=1){
                        aCache.remove(Constant.todaymap);
                        Log.e(TAG,"移除今天的缓存");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        HashMap yesterdayMap = (HashMap) aCache.getAsObject(Constant.yesterdaymap);
        if(yesterdayMap!=null){
            String strYesterdayMap =getKeyOrNull(yesterdayMap);
            if(!TextUtils.isEmpty(strYesterdayMap)){
                String subResult = strYesterdayMap.substring(0,10);
                Log.e(TAG,"昨天缓存=="+subResult);
                try {
                    Date cacheDay = format.parse(subResult);
                    Date  currentDay = format.parse(DateUtils.getCurrentDay());
                    int days = DateUtils.differentDaysByMillisecond(cacheDay,currentDay );
                    if(days>=1){
                        aCache.remove(Constant.yesterdaymap);
                        Log.e(TAG,"移除昨天的缓存");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 获取map中第一个key值
     *
     * @param map 数据源
     * @return
     */
    private static String getKeyOrNull(Map<String, Object> map) {
        String obj = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

}
