package com.cimcitech.myrecyclerview;

import android.app.Application;

import com.cimcitech.myrecyclerview.utils.Config;

/**
 * Created by dapineapple on 2018/1/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Config.CONTEXT = getApplicationContext();
    }
}
