package com.alia.sisdiary;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class SisdiaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
}
