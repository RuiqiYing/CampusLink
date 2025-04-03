package com.zjgsu.kjr.campus_social_platform.main_interface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.main_interface.change_password
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PHONE
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_register.*

var F = 0
class forget_password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        shoujihao.setText(PHONE)
        button2.setOnClickListener { //发送验证码
            button2.isEnabled = false
            BmobSMS.requestSMSCode(shoujihao.text.toString(), "Ti", object :
                QueryListener<Int>() {
                override fun done(p0: Int?, e: BmobException?) {
                    if(e == null) {//验证码成功发送
                        Toast.makeText(this@forget_password,"验证码已发送！", Toast.LENGTH_LONG).show()
                    }
                    else Log.d("213123213!!!", e.message.toString())
                }
            })
        }
        queren.setOnClickListener {//确认验证码
            val V = yanzhengma.text.toString()
            BmobSMS.verifySmsCode(shoujihao.text.toString(), V, object : UpdateListener() {
                override fun done(p0: BmobException?) {
                    if (p0 == null) { // 短信验证码已验证成功
                        F = 1
                        Toast.makeText(this@forget_password,"验证码输入正确！",Toast.LENGTH_LONG).show()
                        val intent = Intent(this@forget_password, change_password::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@forget_password,"验证码错误！",Toast.LENGTH_LONG).show()
                    }
                }
            })

        }
    }
}