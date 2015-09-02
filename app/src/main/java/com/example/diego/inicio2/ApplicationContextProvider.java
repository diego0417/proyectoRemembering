package com.example.diego.inicio2;

import android.app.Application;
import android.content.Context;

/**
 * Created by diego on 02/09/2015.
 */
public class ApplicationContextProvider extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
