package com.example.matsuda.testtodo;

import android.app.Application;
import android.content.Context;

/**
 * Created by matsuda on 15/07/16.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
