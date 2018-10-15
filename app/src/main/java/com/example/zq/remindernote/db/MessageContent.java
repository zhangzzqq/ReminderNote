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
    private int  isFinish;//0没有完成 1完成

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


}
