package com.zjgsu.kjr.campus_social_platform.contact

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class contactDatabase(val context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    private val createrecords = "create table con (" +
            "id integer primary key autoincrement, " +
            "img STRING, " +
            "name STRING," +
            "introduce STRING,"+
            "class1 STRING,"+
            "phone STRING, "+
            "collage STRING)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createrecords)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}