package com.zjgsu.kjr.campus_social_platform.Community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zjgsu.kjr.campus_social_platform.R
import kotlinx.android.synthetic.main.student_information.*

class student_information_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_information)
        val extraData_name = intent.getStringExtra("name")
        val extraData_collage = intent.getStringExtra("collage")
        val extraData_id = intent.getStringExtra("id")
        val extraData_class1=intent.getStringExtra("class1")
        val extraData_phone=intent.getStringExtra("phone")
        val extraData_introduce=intent.getStringExtra("introduce")
        user_name.text=extraData_name
        user_college.text=extraData_collage
        user_id.text=extraData_id
        user_class.text=extraData_class1.toString()
        user_phone.text=extraData_phone
        user1_introduce.text=extraData_introduce
        Log.d("student_information_Activity",extraData_class1.toString())
    }
}