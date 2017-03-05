package com.example.bond.tourguide;

/**
 * Created by bond on 2/28/2017.
 */

public class Event {

    /** Name of the Event */
    private int mName;

    /** Image of the Event */
    private int mImage;

    /** Event date of the school */
    private String mDate;

    /** Constructor for the School object */
    public Event(int name, int image, String date){
        mName = name;
        mImage = image;
        mDate = date;
    }

    /** Get the name of the School */
    public int getName(){
        return mName;
    }

    /** Get the image of the School */
    public int getImage(){
        return mImage;
    }

    /** Get the description of the School */
    public String getDate(){
        return mDate;
    }
}
