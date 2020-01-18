package com.example.zq.remindernote.utils;

import android.text.TextUtils;

import com.example.zq.remindernote.db.MessageContent;

import java.util.ArrayList;
import java.util.List;

public class LevelUtil {

    public static int ContentLevel(String level) {
        int value = 0;
        if (TextUtils.isEmpty(level)) {
            return value;
        }

        switch (level) {

            case "A":
                value = 1;
                break;
            case "B":
                value = 2;
                break;
            case "C":
                value = 3;
                break;
            case "D":
                value = 4;
                break;

        }
        return value;
    }

    public static List<MessageContent> sortContent(List <MessageContent> listData){

        List<MessageContent> listContentTemp = new ArrayList();
        List<MessageContent> listContentSort = new ArrayList();
        List<MessageContent> listContentSortLevel1 = new ArrayList();
        List<MessageContent> listContentSortLevel2 = new ArrayList();
        List<MessageContent> listContentSortLevel3 = new ArrayList();
        List<MessageContent> listContentSortLevel4 = new ArrayList();

        listContentTemp.addAll(listData);
        for(MessageContent messageContent:listContentTemp){
            int level =messageContent.getLevel();
            if(level==1){
                listContentSortLevel1.add(messageContent);
            }else if(level==2){
                listContentSortLevel2.add(messageContent);
            }else if(level==3){
                listContentSortLevel3.add(messageContent);
            }else if(level==4){
                listContentSortLevel4.add(messageContent);
            }
        }

        listContentSort.addAll(listContentSortLevel1);
        listContentSort.addAll(listContentSortLevel2);
        listContentSort.addAll(listContentSortLevel3);
        listContentSort.addAll(listContentSortLevel4);


        return listContentSort;
    }


}
