package com.example.zq.remindernote.enumera;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/8/20
 */
public enum  WhichDay {

    TODADY ("today"),YESTERDAY("yesterday"),TOMORROW("tomorrow"),HISTORY("history");

    private String whichDay;


    WhichDay(String whichday) {

        this.whichDay= whichday;
    }

    public String getValue(){

        return whichDay;
    }



}
