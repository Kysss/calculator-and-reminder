package com.yingying.calculator;

/**
 * Created by Yingying Xia on 2016/6/1.
 */
public class Reminder {
    private String Date;
    private String content;



    public Reminder(String Date, String content){
        this.Date = Date;
        this.content=content;

    }

    public String getDate(){
        return this.Date;
    }
    public String getContent(){
        return this.content;
    }

}
