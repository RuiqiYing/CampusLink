package com.zjgsu.kjr.campus_social_platform

import android.content.Context
import android.widget.Toast
import com.zjgsu.kjr.campus_social_platform.Community.CoDatabaseHelper

class BaseControl() {
    fun deleteData(context: Context, name: String) {
        val dbHelper = CoDatabaseHelper(context, "publish.db", 1)
        val db = dbHelper.writableDatabase
        db.delete("pub", "record=?", arrayOf(name))
        Toast.makeText(context,name.toString(), Toast.LENGTH_SHORT).show()
    }
}