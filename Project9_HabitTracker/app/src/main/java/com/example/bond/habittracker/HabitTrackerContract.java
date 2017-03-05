package com.example.bond.habittracker;

import android.provider.BaseColumns;

/**
 * Created by bond on 3/5/2017.
 */

public class HabitTrackerContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private HabitTrackerContract() {}

    /* Inner class that defines the table contents */
    public static class HabitEntry implements BaseColumns {
        // Name of the table
        public static final String TABLE_NAME = "habits";

        // Columns of the table
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_RUN_KM = "run_km";
        public static final String COLUMN_COMMENT = "comment";
    }

}
