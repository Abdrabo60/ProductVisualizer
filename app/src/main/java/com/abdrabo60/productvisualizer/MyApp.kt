package com.abdrabo60.productvisualizer

import android.app.Application

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}