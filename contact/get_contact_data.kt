package com.zjgsu.kjr.campus_social_platform.contact

import android.content.Context
import com.zjgsu.kjr.campus_social_platform.contact.contactDatabase
import com.zjgsu.kjr.campus_social_platform.contact.contact_msg

class get_contact_data {
    fun getcondata(context: Context): ArrayList<contact_msg> {
        val dbHelper= contactDatabase(context,"Contact.db",1)
        val db=dbHelper.writableDatabase
        val recordList = ArrayList<contact_msg>()
        val data=db.query("con",null,null,null,null,null,null)
        if (data.moveToFirst()){
            do {
                val contact_msg= contact_msg()
                val id=data.getString(data.getColumnIndex("id"))
                val name=data.getString(data.getColumnIndex("name"))
                val img=data.getString(data.getColumnIndex("img"))
                val collage=data.getString(data.getColumnIndex("collage"))
                val introduce=data.getString(data.getColumnIndex("introduce"))
                val class1=data.getString(data.getColumnIndex("class1"))
                contact_msg.con_id=id
                contact_msg.con_name=name
                contact_msg.con_collage=collage
                contact_msg.con_image=img
                contact_msg.con_introduce=introduce
                contact_msg.con_class1=class1

                recordList.add(contact_msg)

            }while (data.moveToNext())
            data.close()
        }
        return recordList
    }
}