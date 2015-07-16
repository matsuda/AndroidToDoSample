package com.example.matsuda.testtodo.model;

import com.example.matsuda.testtodo.App;
import com.example.matsuda.testtodo.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by matsuda on 15/07/15.
 */
public class Task implements Serializable {

    private static final long serialVersionUID = 3697733285410948107L;

    public enum Priority {
        Low, Normal, High;

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
    }

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

    public static ArrayList<Task> mockTasks(int count) {
        ArrayList<Task> array = new ArrayList<Task>();
        for (int i = 0; i < count; i++) {
            array.add(mock(i));
        }
        return array;
    }

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
