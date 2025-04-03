package com.zjgsu.kjr.campus_social_platform.main_interface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.zjgsu.kjr.campus_social_platform.*
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PASSWORD
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import com.zjgsu.kjr.campus_social_platform.sign_in_up.SchoolID
import kotlinx.android.synthetic.main.activity_change_password.*

class change_password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        if (F == 1) {
            old_Password.setText(PASSWORD)
            F = 0
        }
        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库
        val p = PersonData()
        p.id = SchoolID
        turn_btn.setOnClickListener {
            val query = BmobQuery<PersonData>()
            query.addWhereEqualTo("ID", SchoolID)
            query.findObjects(object : FindListener<PersonData>() {
                override fun done(p0: MutableList<PersonData>?, p1: BmobException?) {
                    if (p1 == null) {
                        if (p0 != null && p0.size > 0) {
                            for (p in p0) {
                                if (p.password == old_Password.text.toString() && p.password != new_password.text.toString() && new_password.text.toString() == turn_new_password.text.toString()) {
                                    p.password = new_password.text.toString()
                                    p.passwordsure = turn_new_password.text.toString()
                                    PASSWORD = p.password
                                    p.update(object : UpdateListener() {
                                        override fun done(p0: BmobException?) {
                                            if (p0 == null) {
                                                Toast.makeText(
                                                    this@change_password,
                                                    "密码修改成功!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                val intent = Intent(this@change_password,
                                                    Person_interface::class.java)
                                                startActivity(intent)
                                            }
                                            else Log.d("密码修改失败",p0.message.toString())
                                        }

                                    })
                                }
                                else if(p.password != old_Password.text.toString()) Toast.makeText(this@change_password,"旧密码输入错误！",Toast.LENGTH_LONG).show()
                                else if(new_password.text.toString()!=turn_new_password.text.toString()) Toast.makeText(this@change_password,"两次输入密码不一致！",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    else Log.d("123123123!",p1.message.toString())

                }
            })
        }
        forget_btn.setOnClickListener {
            val intent = Intent(this, forget_password::class.java)
            startActivity(intent)
        }
    }
}