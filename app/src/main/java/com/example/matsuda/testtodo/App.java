package com.example.matsuda.testtodo;

import android.app.Application;
import android.content.Context;

import com.example.matsuda.testtodo.model.DBOpenHelper;
import com.example.matsuda.testtodo.model.Task;

/**
 * Created by matsuda on 15/07/16.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        DBOpenHelper helper = new DBOpenHelper(this);
        Task.prepareTasks(helper, 20);
    }

    public static Context getContext() {
        return context;
    }
}
