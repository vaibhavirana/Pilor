package com.barodacoder.pilor;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Workstation01 on 7/20/2016.
 */
public class MyApplication extends Application {

    public static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
