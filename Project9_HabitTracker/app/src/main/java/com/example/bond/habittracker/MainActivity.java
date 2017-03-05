package com.example.bond.habittracker;

import com.example.bond.habittracker.HabitTrackerContract.HabitEntry;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitTrackerDbHelper dbHelper = new HabitTrackerDbHelper(this);

        //Get current date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        dbHelper.insertHabit(dateString,
                5,
                getString(R.string.dummy_comment_1));

        dbHelper.insertHabit(dateString,
                2,
                getString(R.string.dummy_comment_2));

        Cursor cursor = dbHelper.readHabit();
        while(cursor.moveToNext()){
            Log.v(LOG_TAG, "HabitEntry :" +
                    cursor.getInt(cursor.getColumnIndex(HabitEntry._ID)) + " " +
                    cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_DATE)) + " " +
                    cursor.getInt(cursor.getColumnIndex(HabitEntry.COLUMN_RUN_KM)) + " " +
                    cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_COMMENT)));
        }
    }
}
