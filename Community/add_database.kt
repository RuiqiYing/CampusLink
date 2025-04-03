package com.zjgsu.kjr.campus_social_platform.Community

import android.content.ContentValues
import android.content.Context
import com.zjgsu.kjr.campus_social_platform.chat.MyDatabaseHelper

class add_database {
    fun add_database(context: Context, id:String, name:String, record:String, img:String,time:String){
        val dbHelper= MyDatabaseHelper(context,"publish.db",1)
        val db=dbHelper.writableDatabase
        val valuse=ContentValues().apply {
            put("id",id)
            put("name",name)
            put("record",record)
            put("img", img)
            put("time",time)
        }

        db.insert("pub",null,valuse)
    }
}