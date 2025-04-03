package com.zjgsu.kjr.campus_social_platform.chat
import cn.bmob.v3.BmobObject
class chat : BmobObject() {
    var chat_name: String? = null
    var chat_id: String? = null
    var chat_data:String?=null
    var chat_time:String?=null
    var chat_msg_type:String?=null
    var local_id:String?=null
}