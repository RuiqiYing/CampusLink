package com.zjgsu.kjr.campus_social_platform.sign_in_up

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import cn.bmob.sms.listener.VerifySMSCodeListener
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.zjgsu.kjr.campus_social_platform.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.student_information.*

var flag = 0
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Vbutton.setOnClickListener {
            Vbutton.isEnabled = false
            BmobSMS.requestSMSCode(PhoneNumber.text.toString(), "Ti", object :QueryListener<Int>() {
                override fun done(p0: Int?, e: BmobException?) {
                    if(e == null) {//验证码成功发送
                        Toast.makeText(this@RegisterActivity,"验证码已发送！",Toast.LENGTH_LONG).show()
                    }
                    else Log.d("213123213!!!", e.message.toString())
                }
            })


        }
        registerbtn.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            }
            else{
                sign_in()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            1->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sign_in()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    private fun sign_in(){
        try {
            val p = PersonData()
            p.ID = ID.text.toString()
            p.name = Name.text.toString()
            p.college = College.selectedItem.toString() //获取Spinner显示的内容
            p.class1 = Class.text.toString()
            p.phone = PhoneNumber.text.toString()
            p.password = Password.text.toString()
            p.passwordsure = PasswordSure.text.toString()
            p.contact = Contact.text.toString()
            val V = Verification.text.toString() //输入的验证码
            BmobSMS.verifySmsCode(PhoneNumber.text.toString(), V, object : UpdateListener() {
                override fun done(p0: BmobException?) {
                    if (p0 == null) { // 短信验证码已验证成功
                        flag = 1
                    } else {
                        Toast.makeText(this@RegisterActivity,"验证码错误！",Toast.LENGTH_LONG).show()
                    }
                }
            })

            if(p.ID!=""&&p.name!=""&&p.college!=""&&p.phone!=""&&p.password!=""&&p.contact!=""&&p.password==p.passwordsure &&flag==1){
                p.save(object :SaveListener<String>() {
                    override fun done(objectId: String?, e: BmobException?) {
                        if (e == null) {
                            flag = 0
                            Toast.makeText(this@RegisterActivity,"注册成功！",Toast.LENGTH_LONG).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)//跳转到登录界面
                        } else {
                            Toast.makeText(this@RegisterActivity,"该学号/工号已注册！",Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
            else if (p.ID==""){
                Toast.makeText(this@RegisterActivity,"请输入学号！",Toast.LENGTH_LONG).show()
            }
            else if(p.name==""){
                Toast.makeText(this@RegisterActivity,"请输入姓名！",Toast.LENGTH_LONG).show()
            }
            else if(p.college==""){
                Toast.makeText(this@RegisterActivity,"请输入学院！",Toast.LENGTH_LONG).show()
            }
            else if (p.phone==""){
                Toast.makeText(this@RegisterActivity,"请输入手机号！",Toast.LENGTH_LONG).show()
            }
            else if (p.password==""){
                Toast.makeText(this@RegisterActivity,"请输入密码！",Toast.LENGTH_LONG).show()
            }
            else if (p.password!=p.passwordsure){
                Toast.makeText(this@RegisterActivity,"两次输入密码不一致！",Toast.LENGTH_LONG).show()
            }
            else if(p.contact==""){
                Toast.makeText(this@RegisterActivity,"请输入联系方式！",Toast.LENGTH_LONG).show()
            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}


