package com.zjgsu.kjr.campus_social_platform

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class Myapplication :Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}