package com.zjgsu.kjr.campus_social_platform

import cn.bmob.v3.BmobObject

class Person_test : BmobObject() {
    var person_name: String? = null
    var person_address: String? = null
    fun setName(person_test_name: String) {
        person_name=person_test_name
    }
    fun setAddress(person_test_address: String) {
        person_address=person_test_address
    }

}