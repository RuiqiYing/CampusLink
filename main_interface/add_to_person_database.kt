package com.zjgsu.kjr.campus_social_platform.main_interface

import android.content.ContentValues
import android.content.Context
import com.zjgsu.kjr.campus_social_platform.chat.MyDatabaseHelper

class add_to_person_database {
    fun add_to_database(
            context: Context,name:String,image:Int,last_record:String,
            id:String
            ){
            val dbHelper= MyDatabaseHelper(context,"Chat_Records.db",1)
            val db=dbHelper.writableDatabase
            val valuse= ContentValues().apply {
                put("name",name)
                put("image",image)
                put("last_record",last_record)
                put("id",id)
            }
            db.insert("chat_person",null,valuse)
        }
    }
