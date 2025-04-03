package com.zjgsu.kjr.campus_social_platform.chat

class Msg(val content: String,val type:Int,val time:String?=null) {
    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}
