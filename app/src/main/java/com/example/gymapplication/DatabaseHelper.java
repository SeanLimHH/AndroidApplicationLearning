package com.example.gymapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SetsDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SETS = "sets";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SET_VALUE = "setValue";
    private static final String COLUMN_WEIGHT_VALUE = "weightValue";

    // Table creation SQL statement
    private static final String CREATE_TABLE_SETS =
            "CREATE TABLE " + TABLE_SETS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_SET_VALUE + " TEXT," +
                    COLUMN_WEIGHT_VALUE + " REAL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SETS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getNextId() {

        SQLiteDatabase db = getReadableDatabase();

        try {
            String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_SETS;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                int maxId = cursor.getInt(0);
                return maxId + 1;
            }
        } finally {
            db.close();
        }

        // If there is an issue or no records, return a default value
        return 1;
    }
}
