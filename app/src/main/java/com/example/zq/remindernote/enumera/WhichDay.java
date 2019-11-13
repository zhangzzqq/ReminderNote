package com.example.zq.remindernote.enumera;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/8/20
 */
public enum WhichDay {

    TODADY("today"), YESTERDAY("yesterday"), TOMORROW("tomorrow"), HISTORY("history");

    private String whichDay;


    WhichDay(String whichday) {

        this.whichDay = whichday;
    }

    public String getValue() {

        return whichDay;
    }

    public static int getIntDay(String target) {
        int dayNum=-1;
        switch (target) {
            case "today":
                dayNum =2;
                break;
            case "yesterday":
                dayNum =1;
                break;
            case "tomorrow":
                dayNum =3;
                break;
                case "history":
                 dayNum =4;
                break;
        }
        return dayNum;

    }

}
