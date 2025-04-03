package com.zjgsu.kjr.campus_social_platform.Community
import android.content.Context
import com.zjgsu.kjr.campus_social_platform.Community.CoDatabaseHelper
import com.zjgsu.kjr.campus_social_platform.Community.invitation

class get_database {
    fun getdatabase(context:Context): ArrayList<invitation> {
        val dbHelper= CoDatabaseHelper(context,"publish.db",1)
        val db=dbHelper.writableDatabase
        val recordList = ArrayList<invitation>()
        //val recordList = ArrayList<String>()
        val data=db.query("pub",null,null,null,null,null,null)
        if (data.moveToFirst()){
            do {
                val invitation= invitation()
                val id=data.getString(data.getColumnIndex("id"))
                val name=data.getString(data.getColumnIndex("name"))
                val record=data.getString(data.getColumnIndex("record"))
                val time=data.getString(data.getColumnIndex("time"))
                val img=data.getString(data.getColumnIndex("img")).toString()
                invitation.invitation_id=id
                invitation.invitation_name=name
                invitation.invitation_time=time
                invitation.invitation_image=img
                invitation.invitation_record=record
                recordList.add(invitation)
            }while (data.moveToNext())
            data.close()
        }
        return recordList
    }
}
//            val img=data.getString(data.getColumnIndex("img"))

//                invitation.invitation_id=id
//                invitation.invitation_name=name
//                invitation.invitation_time=time
//                invitation.invitation_image=img
//                invitation.invitation_record=record
//                recordList.add(invitation)