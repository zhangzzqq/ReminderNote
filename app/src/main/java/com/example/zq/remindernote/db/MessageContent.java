package com.example.zq.remindernote.db;

import org.litepal.crud.LitePalSupport;
/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/6/29
 */
public class MessageContent extends LitePalSupport {
    private String dailyDate; //每天
    private String content ;//内容
    private String contentDate;//内容日期   详细日期
    private String contentId; //内容id
    private String contentTitle; //内容title
    private int  isFinish;// 1完成 0没有完成
    private int whichDay; //  前天 今天 明天 1 2 3
    private int level;//文件内容的重要性



    public int getWhichDay() {
        return whichDay;
    }

    public void setWhichDay(int whichDay) {
        this.whichDay = whichDay;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getDailyDate() {
        return dailyDate;
    }

    public void setDailyDate(String dailyDate) {
        this.dailyDate = dailyDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentDate() {
        return contentDate;
    }

    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
