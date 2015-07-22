package com.example.matsuda.testtodo.activity;

import android.os.Bundle;

public class UpdateActivity extends EditActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setTitle() {
        String name = this.task.name;
        if (name == null || name.isEmpty()) {
            setTitle("ToDoの編集");
        } else  {
            setTitle(this.task.name + "の編集");
        }
    }
}
