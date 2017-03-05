package com.example.bond.tourguide;

/**
 * Created by bond on 2/27/2017.
 */

/**
 * School object in the Ahmedabad University
 */

public class School {

    /** Name of the School */
    private int mName;

    /** Image of the School */
    private int mImage;

    /** Address of the school */
    private int mAddress;

    /** Email of the school*/
    private int mEmail;

    /** Constructor for the School object */
    public School(int name, int image, int address, int email){
        mName = name;
        mImage = image;
        mAddress = address;
        mEmail = email;
    }

    /** Get the name of the School */
    public int getName(){
        return mName;
    }

    /** Get the image of the School */
    public int getImage(){
        return mImage;
    }

    /** Get the address of the School */
    public int getAddress(){
        return mAddress;
    }

    /** Get the email of the school */
    public int getEmail(){
        return mEmail;
    }
}
