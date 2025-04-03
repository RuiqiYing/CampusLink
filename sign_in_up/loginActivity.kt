 package com.zjgsu.kjr.campus_social_platform.sign_in_up
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener

import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.main_interface.Person_interface
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.nav_header.*

 var SchoolID = ""
var NAME = ""
var COLLEGE = ""
var CLASS = ""
var PHONE = ""
var CONTACT = ""
var INTRODUCE = ""
var PASSWORD = ""
var file: BmobFile? = null //图片地址

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isremember = prefs.getBoolean("remember_password",false) //判断是否记住密码，默认false
        if(isremember) {
            val ID = prefs.getString("ID","")
            val Password = prefs.getString("password","")
            schoolID.setText(ID)
            password.setText(Password)
            rememberPassword.isChecked = true
        }
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener {
            var id = schoolID.text.toString()
            var Password = password.text.toString()
            var flag = 0 //标志位 判断是否能在后端找到数据
            val bmobQuery = BmobQuery<PersonData>()
            bmobQuery.findObjects(object : FindListener<PersonData>() {
                override fun done(list: List<PersonData>, e: BmobException?) {
                    if (e == null) {
                        val n = list.size
                        for (i in 0 until n) {
                            if (list[i].id == id && list[i].password == Password) {
                                val editor = prefs.edit()
                                if(rememberPassword.isChecked){//复选框选中
                                    editor.putBoolean("remember_password",true) //记住密码
                                    editor.putString("ID",id)
                                    editor.putString("password",Password)
                                }
                                else editor.clear()//清除Sharedpreferences的数据
                                editor.apply()
                                flag = 1
                                SchoolID = id
                                NAME = list[i].name
                                CLASS = list[i].class1
                                COLLEGE = list[i].college
                                CONTACT = list[i].contact
                                PHONE = list[i].phone
                                if (list[i].introduce!=null) INTRODUCE = list[i].introduce
                                file = list[i].picture
                                Toast.makeText(this@LoginActivity, "登录成功！", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@LoginActivity, Person_interface::class.java)
                                startActivity(intent)
                                break
                            }
                        }
                        if (flag==0) Toast.makeText(this@LoginActivity, "学号或密码错误！", Toast.LENGTH_LONG).show()

                    } else {
                        Log.d("error", "error")
                    }
                }
            })
        }
    }

}