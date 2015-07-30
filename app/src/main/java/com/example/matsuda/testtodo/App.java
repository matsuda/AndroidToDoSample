package com.example.matsuda.testtodo;

import android.app.Application;
import android.content.Context;

import com.example.matsuda.testtodo.model.Observer;
import com.example.matsuda.testtodo.model.Task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by matsuda on 15/07/16.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static Context context;
    private static List<Observer> observers = new CopyOnWriteArrayList<Observer>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Task.truncateTasks(context);
        Task.prepareTasks(context, 10);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * Observer
     */
    public static void notifyObservers(Object object) {
        for (Observer observer: observers) {
            observer.update(object);
        }
    }

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
