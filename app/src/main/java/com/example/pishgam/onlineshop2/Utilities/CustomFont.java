package com.example.pishgam.onlineshop2.Utilities;

import android.app.Application;

import com.example.pishgam.onlineshop2.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Babak on 9/10/2017.
 */

public class CustomFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Fonts/Sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
