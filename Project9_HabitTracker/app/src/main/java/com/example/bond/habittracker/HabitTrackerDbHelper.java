package com.example.bond.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bond.habittracker.HabitTrackerContract.HabitEntry;

/**
 * Created by bond on 3/5/2017.
 */

public class HabitTrackerDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "habit.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /** Constructor */
    public HabitTrackerDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_HABIT_TABLE =  "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_RUN_KM + " INT NOT NULL, "
                + HabitEntry.COLUMN_COMMENT + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Insert habit entry into the database
    public void insertHabit(String date, int run, String comment){
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_DATE, date);
        values.put(HabitEntry.COLUMN_RUN_KM, run);
        values.put(HabitEntry.COLUMN_COMMENT, comment);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    // Read habit entry from the database
    public Cursor readHabit(){
        SQLiteDatabase db = getReadableDatabase();

        // Set the projection
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_RUN_KM,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_COMMENT
        };

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }
}
