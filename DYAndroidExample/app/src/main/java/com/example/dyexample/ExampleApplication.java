package com.example.dyexample;

import android.app.Application;
import android.content.res.Configuration;

import com.dynamicyield.dyapi.DYApi;

public class ExampleApplication extends Application {

    private String mSecretKey = YOUR_SECRET_KEY;

    @Override
    public void onCreate() {
        super.onCreate();

        DYApi.setContextAndSecret(getApplicationContext(),mSecretKey);

    }


}
