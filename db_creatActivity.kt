package com.zjgsu.kjr.campus_social_platform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjgsu.kjr.campus_social_platform.chat.MyDatabaseHelper

class db_creatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_db_creat)
        var recordList = ArrayList<String>()
        val dbHelper= MyDatabaseHelper(this,"ChatRecords.db",1)
        dbHelper.writableDatabase
        val time=get_time().gettime()
        //add_to_database().add_to_database(this,1935010205,"小明","你好啊，你叫什么名字",time)
        //recordList=get_from_database().getdatabase(this)
    }
}