package com.zjgsu.kjr.campus_social_platform.chat
import android.content.Context
import com.zjgsu.kjr.campus_social_platform.chat.MyDatabaseHelper
import com.zjgsu.kjr.campus_social_platform.chat.chat

class get_from_database {
    fun getdatabase(context:Context): ArrayList<chat> {
        val dbHelper= MyDatabaseHelper(context,"Chat_Records.db",1)
        val db=dbHelper.writableDatabase
        var recordList = ArrayList<chat>()
        val data=db.query("chat_records", null,null,null,null,null,null)
        if (data.moveToFirst()){
            do {
                val chat = chat()
                val id=data.getString(data.getColumnIndex("id"))
                val name=data.getString(data.getColumnIndex("name"))
                val record=data.getString(data.getColumnIndex("record"))
                val local_id=data.getString(data.getColumnIndex("local_id"))
                val msg_type=data.getString(data.getColumnIndex("msg_type"))
                val time=data.getString(data.getColumnIndex("time"))
                chat.chat_id=id
                chat.local_id=local_id
                chat.chat_name=name
                chat.chat_data=record
                chat.chat_msg_type=msg_type
                chat.chat_time=time
                recordList.add(chat)
            }while (data.moveToNext())
            data.close()
        }
        return recordList
    }
}