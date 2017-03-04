package com.example.bond.project7_booklisting;

/**
 * Created by bond on 3/3/2017.
 */

/** Object to store the details of a Book*/
public class Book {

    /** Title of the book*/
    private String mTitle;

    /** Author of the book*/
    private String mAuthor;

    /** Book constructor*/
    public Book(String title, String author){
        mTitle = title;
        mAuthor = author;
    }

    /** Get the title of the book*/
    public String getTitle(){
        return mTitle;
    }

    /** Get the author of the book*/
    public String getAuthor(){
        return mAuthor;
    }
}
