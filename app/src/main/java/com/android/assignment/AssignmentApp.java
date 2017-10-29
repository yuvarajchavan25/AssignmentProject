package com.android.assignment;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class AssignmentApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
