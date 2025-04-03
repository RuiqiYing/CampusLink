package com.zjgsu.kjr.campus_social_platform.Community

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class CoDatabaseHelper(val context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    private val createrecords ="create table pub (" +
            "num integer primary key autoincrement, " +
            "id String," +
            "name STRING," +
            "record STRING," +
            "img STRING," +
            "time STRING)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createrecords)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}



