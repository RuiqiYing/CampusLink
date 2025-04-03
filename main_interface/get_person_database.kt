package com.zjgsu.kjr.campus_social_platform.main_interface

import android.content.Context
import com.zjgsu.kjr.campus_social_platform.chat.MyDatabaseHelper

class get_person_database {
    fun getdatabase(context: Context): ArrayList<String> {
        val dbHelper= MyDatabaseHelper(context,"Chat_Records.db",1)
        val db=dbHelper.writableDatabase
        var recordList = ArrayList<String>()
        val resultList = ArrayList<String>()
        val data=db.query("chat_person",null,null,null,null,null,null)
        if (data.moveToFirst()){
            do {
                val id=data.getString(data.getColumnIndex("name"))
                val name=data.getString(data.getColumnIndex("image"))
                val msg_type=data.getString(data.getColumnIndex("last_records"))
                val time=data.getString(data.getColumnIndex("id"))
                recordList.add(id.toString())
                recordList.add(name)
                recordList.add(msg_type)
                recordList.add(time)
                resultList.add(recordList.toString())
                recordList.clear()
            }while (data.moveToNext())
            data.close()
        }
        return resultList
    }
}