package com.example.bond.tourguide;

/**
 * Created by bond on 2/27/2017.
 */

/**
 * School object in the Ahmedabad University
 */

public class Center {

    /** Name of the School */
    private int mName;

    /** Image of the School */
    private int mImage;

    /** Description of the school */
    private int mDescription;

    /** Constructor for the School object */
    public Center(int name, int image, int description){
        mName = name;
        mImage = image;
        mDescription = description;
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
    public int getDescription(){
        return mDescription;
    }
}
