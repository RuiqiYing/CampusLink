package com.zjgsu.kjr.campus_social_platform.chat

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    private val createrchat_ecords = "create table chat_records (" +
            "person_id integer primary key autoincrement," +
            "id STRING," +
            "name STRING," +
            "record STRING," +
            "local_id STRING,"+
            "msg_type STRING,"+
            "time STRING)"
    private val createrchat_person = "create table chat_person (" +
            "person_test_id integer primary key autoincrement," +
            "name STRING,"+
            "image INTEGER,"+
            "last_records STRING,"+
            "id STRING)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createrchat_ecords)
        db.execSQL(createrchat_person)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}


