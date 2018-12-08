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

    private static final String TAG = "App";
    private  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);
        Bugly.init(getApplicationContext(), "bafd08ebde", false);

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
