package com.zjgsu.kjr.campus_social_platform.Community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.get_time

class creat_db : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_creat)
        var recordList = ArrayList<String>()
        val dbHelper= CoDatabaseHelper(this,"Publish.db",1)
        dbHelper.writableDatabase
        val time= get_time().gettime()
       // add_database().add_database(this,"1935010205","小明","你好啊，你叫什么名字","aaa",time)
        //recordList=get_database().getdatabase(this)
    }
}