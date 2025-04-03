package com.zjgsu.kjr.campus_social_platform.chat

import android.util.Log

class judge_show_time(){
    private var result:String=""
    fun  judge(show_time:String,result_time: String): String {
        if(show_time.length==result_time.length){
                if(show_time.substring(3,5).toInt()-result_time.substring(3,5).toInt()>3){
                    result=result_time
            }
        }
        return result
    }
}