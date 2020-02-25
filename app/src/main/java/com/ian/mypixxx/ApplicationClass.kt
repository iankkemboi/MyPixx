package com.ian.mypixxx

import android.app.Application

import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by brian on 05/10/2016.
 */
class ApplicationClass : Application() {
    // private ObjectGraph mObjectGraph;
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/TruenoRg.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}