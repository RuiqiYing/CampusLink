package com.zjgsu.kjr.campus_social_platform

import cn.bmob.v3.BmobObject

class chat_information : BmobObject() {
    var id: Int? = null
    var information: String? = null
    var chat_object_ID: String? = null
    fun information(): String? {
        return information
    }

    fun setinformation(information: String?) {
        this.information = information
    }

    fun chat_object_ID(): String? {
        return chat_object_ID
    }

    fun setchat_object_ID(chat_object_ID: String?) {
        this.chat_object_ID = chat_object_ID
    }
}