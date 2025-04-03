package com.zjgsu.kjr.campus_social_platform.contact

import android.content.ContentValues
import android.content.Context

class add_contact {
    fun add_database(context: Context, id:String, name:String, Img:String, collage:String ,introduce:String?,class1:String,phone:String){
        val dbHelper= contactDatabase(context,"Contact.db",1)
        val db=dbHelper.writableDatabase
        val valuse= ContentValues().apply {
            put("id",id)
            put("name",name)
            put("img",Img)
            put("collage",collage)
            put("phone",phone)
            put("introduce",introduce)
            put("class1",class1)
        }
        db.insert("con",null,valuse)
    }
}