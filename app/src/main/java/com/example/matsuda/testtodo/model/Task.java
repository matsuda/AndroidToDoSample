package com.example.matsuda.testtodo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.matsuda.testtodo.App;
import com.example.matsuda.testtodo.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by matsuda on 15/07/15.
 */
public class Task implements Serializable, BaseColumns {
    private static final String TAG = Task.class.getSimpleName();
    private static final long serialVersionUID = 3697733285410948107L;

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_MEMO = "memo";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_PRIORITY = "priority";

    public enum Priority {
        Low(0), Normal(1), High(2);

        private int value;

        Priority(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            switch (this) {
                case Low:
                    return App.getContext().getResources().getString(R.string.task_priority_low);
                case High:
                    return App.getContext().getResources().getString(R.string.task_priority_high);
                default:
                    return App.getContext().getResources().getString(R.string.task_priority_normal);
            }
        }

        public int toInt() {
            return this.value;
        }

        public static Priority getEnum(int i) {
            Priority[] values = Priority.values();
            for (Priority p : values) {
                if (p.toInt() == i) {
                    return p;
                }
            }
            return Normal;
        }
    }

    public long id;
    public String name;
    public String memo;
    public String date;
    public Priority priority = Priority.Normal;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public static void truncateTasks(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLE_NAME + ";");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.execSQL("VACUUM");
            db.close();
        }
    }

    public static void prepareTasks(Context context, int count) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int i = 1; i <= count; i++) {
            Task task = mock(i);
            ContentValues values = task.mapValues();
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public static ArrayList<Task> findAll(Context context, String limit) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, limit);
        ArrayList<Task> array = mapObjects(c);
        c.close();
        db.close();
        return array;
    }

    public static Task findById(Context context, long id) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = _ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        Cursor c = db.query(TABLE_NAME, null, where, whereArgs, null, null, null);
        ArrayList<Task> array = mapObjects(c);
        c.close();
        db.close();
        if (array.size() > 0) {
            return array.get(0);
        }
        return null;
    }

    private static ArrayList<Task> mapObjects(Cursor c) {
        ArrayList<Task> array = new ArrayList<Task>();
        if (c.moveToFirst()) {
            do {
                Task task = new Task();
                task.name = c.getString(c.getColumnIndex(COLUMN_NAME_NAME));
                task.memo = c.getString(c.getColumnIndex(COLUMN_NAME_MEMO));
                task.date = c.getString(c.getColumnIndex(COLUMN_NAME_DATE));
                int p = c.getInt(c.getColumnIndex(COLUMN_NAME_PRIORITY));
                Priority priority = Priority.getEnum(p);
                task.priority = priority;
                task.id = c.getLong(c.getColumnIndex(_ID));
                array.add(task);
            } while (c.moveToNext());
        }
        return array;
    }

    public boolean save(Context context) {
        if (this.id > 0) {
            return update(context);
        } else {
            return insert(context);
        }
    }

    public boolean insert(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = mapValues();
        long rowId = db.insert(TABLE_NAME, null, values);
        this.id = rowId;
        db.close();
        return rowId != -1;
    }

    public boolean update(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = mapValues();
        String where = _ID + " = ?";
        String[] whereArgs = { String.valueOf(this.id) };
        int count = db.update(TABLE_NAME, values, where, whereArgs);
        db.close();
        return count > 0;
    }

    private ContentValues mapValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, this.name);
        values.put(COLUMN_NAME_MEMO, this.memo);
        values.put(COLUMN_NAME_DATE, this.date);
        values.put(COLUMN_NAME_PRIORITY, Integer.valueOf(this.priority.toInt()));
        return values;
    }
    /*
    public static ArrayList<Task> mockTasks(int count) {
        ArrayList<Task> array = new ArrayList<Task>();
        for (int i = 1; i <= count; i++) {
            array.add(mock(i));
        }
        return array;
    }
    */
    public static Task mock(int i) {
        Task task = new Task();
        task.name = "task" + i;
        task.date = "2015/01/23 18:25";
        // int p = (int)Math.random() * 3;
        Random r = new Random();
        int p = r.nextInt(3);
        switch (p) {
            case 0:
                task.priority = Priority.High;
                break;
            case 1:
                task.priority = Priority.Low;
                break;
            default:
                task.priority = Priority.Normal;
        }
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < i + 1; j++) {
            for (int k = 0; k < j + 1; k++) {
                sb.append("memo ");
            }
            if (j < i) sb.append("\n");
            if (j % 3 == 0) sb.append("\n");
        }
        task.memo = sb.toString();
        return task;
    }
}
