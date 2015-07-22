package com.example.matsuda.testtodo.activity;

import android.os.Bundle;

public class CreateActivity extends EditActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setTitle() {
        setTitle("ToDoの新規作成");
    }
}
