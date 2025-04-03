package com.zjgsu.kjr.campus_social_platform.Community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.bmob.v3.BmobObject

class invitation :  BmobObject(){
    var invitation_id: String? = null
    var invitation_name: String? = null
    var invitation_time:String?=null
    var invitation_image:String?=null
    var invitation_record:String?=null
}