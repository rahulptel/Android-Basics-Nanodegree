package com.example.bond.newsreportapp;

import java.util.Date;

/**
 * Created by bond on 2/19/2017.
 */

public class News {

    /** Required data about news */
    private String mTitle;
    private String mSection;
    private String mUrl;

    /** Optional data about news */
    /*private String mAuthor;
    private Date mDate;
    private Boolean mPublished;*/

    /** Constructor to create a new News object */
    public News(String title, String section, String url){
        mTitle = title;
        mSection = section;
        mUrl = url;
        /*mAuthor = null;
        mDate = null;
        mPublished = false;*/
    }

    /** Return the title of the news */
    public String getTitle(){ return mTitle; }

    /** Return the section of the news */
    public String getSection(){ return mSection; }

    /** Return the url of the news */
    public String getUrl(){ return mUrl; }
}
