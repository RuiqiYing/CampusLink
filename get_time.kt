package com.zjgsu.kjr.campus_social_platform;

import java.text.SimpleDateFormat
import java.util.*

class get_time {
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var date = Date(System.currentTimeMillis())
    var Time = simpleDateFormat.format(date)
    fun gettime(): String {
        return Time
    }
    fun getyear():String{
        val simpleDateFormat = SimpleDateFormat("yyyy")
        val date = Date(System.currentTimeMillis())
        val Time = simpleDateFormat.format(date)
        return Time
    }
    fun getmonth():String{
        val simpleDateFormat = SimpleDateFormat("MM")
        val date = Date(System.currentTimeMillis())
        val Time = simpleDateFormat.format(date)
        return Time
    }
    fun getday():String{
        val simpleDateFormat = SimpleDateFormat("dd")
        val date = Date(System.currentTimeMillis())
        val Time = simpleDateFormat.format(date)
        return Time
    }
    fun get_now_time():String{
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val date = Date(System.currentTimeMillis())
        val Time = simpleDateFormat.format(date)
        return Time
    }

}
