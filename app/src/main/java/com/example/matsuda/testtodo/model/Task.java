package com.example.matsuda.testtodo.model;

import java.io.Serializable;

/**
 * Created by matsuda on 15/07/15.
 */
public class Task implements Serializable {

    private static final long serialVersionUID = 3697733285410948107L;

    enum Priority {
        High, Normal, Row
    }
    public String name;
    public String memo;
    public String date;
    public Priority priority;

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
}
