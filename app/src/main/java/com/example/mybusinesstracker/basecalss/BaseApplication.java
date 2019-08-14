package com.example.mybusinesstracker.basecalss;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseApplication extends Application {

    public static Context mContext;
    public Locale mLocale;
    public ApplicationLifecycleHandler handler;
    private AppCompatActivity mCurrentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setmCurrentActivity(AppCompatActivity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    public AppCompatActivity getmCurrentActivity() {
        return mCurrentActivity;
    }
}
