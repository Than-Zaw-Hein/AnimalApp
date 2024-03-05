package com.tzh.animal

import android.app.Application
import com.tzh.animal.ulti.UnsplashPhotoPicker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimalApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        // initializing the picker library
        UnsplashPhotoPicker.init(
            this,
            "xxxxxxxxxxxx",//<-Replace with your
            "xxxxxxxxxxxx"//<-Replace with your
            /* optional page size (number of photos per page) */
        )
        /* .setLoggingEnabled(true) // if you want to see the http requests */
    }
}