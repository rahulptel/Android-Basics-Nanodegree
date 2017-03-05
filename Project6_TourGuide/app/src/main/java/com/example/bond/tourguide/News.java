package com.example.bond.tourguide;

/**
 * Created by bond on 2/27/2017.
 */

public class News {


    private String mTitle;

    private String mDate;

    public News(String title, String date){
        mDate = date;
        mTitle = title;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getDate(){
        return mDate;
    }

}
