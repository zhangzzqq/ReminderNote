package com.example.zq.remindernote.utils;

import android.text.TextUtils;

public class  TextUtilManager {

    public static String  removeNullString(String target){
        if(!TextUtils.isEmpty(target)){
            return target.replaceAll(" ","");
        }
        return null;
    }
}
