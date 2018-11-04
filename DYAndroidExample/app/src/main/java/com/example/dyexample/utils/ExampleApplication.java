package com.example.dyexample.utils;

import android.app.Application;

import com.dynamicyield.dyapi.DYApi;

public class ExampleApplication extends Application {

    private final String SECRET_KEY = YOUR_SECRET_HERE;

    @Override
    public void onCreate() {
        super.onCreate();

        DYApi.setContextAndSecret(getApplicationContext(),SECRET_KEY);

    }


}
