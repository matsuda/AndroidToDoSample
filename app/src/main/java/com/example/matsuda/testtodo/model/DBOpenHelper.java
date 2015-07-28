package com.example.matsuda.testtodo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matsuda on 15/07/21.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";

    private static final String TASK_TABLE_CREATE =
            "CREATE TABLE " + Task.TABLE_NAME + " ( " +
                    Task._ID + " INTEGER PRIMARY KEY, " +
                    Task.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                    Task.COLUMN_NAME_MEMO + " TEXT, " +
                    Task.COLUMN_NAME_DATE + " INTEGER, " +
                    Task.COLUMN_NAME_PRIORITY + " INTEGER NOT NULL);";

    private static final String TASK_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + Task.TABLE_NAME + ";";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TASK_TABLE_DELETE);
        onCreate(db);
    }
}
