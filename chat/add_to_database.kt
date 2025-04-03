package com.zjgsu.kjr.campus_social_platform.chat

import android.content.ContentValues
import android.content.Context

class add_to_database {
    fun add_to_database(
        context: Context, id: String, name:String, record:String,local_id:String,
        msg_type:String,
        time:String){
        val dbHelper= MyDatabaseHelper(context,"Chat_Records.db",1)
        val db=dbHelper.writableDatabase
        val valuse=ContentValues().apply {
            put("id",id)
            put("name",name)
            put("record",record)
            put("local_id",local_id)
            put("msg_type",msg_type)
            put("time",time)
        }
        db.insert("chat_records",null,valuse)
    }
}