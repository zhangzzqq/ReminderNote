package com.example.zq.remindernote.Base;

import android.app.Application;

import com.example.zq.remindernote.utils.ACache;

/**
 * Created by stevenZhang on 2016/9/25.
 */
public class App  extends Application{

    public static ACache aCache;
    @Override
    public void onCreate() {
        super.onCreate();

        aCache = ACache.get(this);

    }
}
