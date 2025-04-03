package com.zjgsu.kjr.campus_social_platform.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjgsu.kjr.campus_social_platform.Community.CoDatabaseHelper
import com.zjgsu.kjr.campus_social_platform.R

class creat_con : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_con)
        var recordList = ArrayList<String>()
        val dbHelper= CoDatabaseHelper(this,"Contact.db",1)
        dbHelper.writableDatabase
    }
}