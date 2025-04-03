package com.zjgsu.kjr.campus_social_platform.Community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.get_time
import com.zjgsu.kjr.campus_social_platform.sign_in_up.NAME
import com.zjgsu.kjr.campus_social_platform.sign_in_up.SchoolID

class publish : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
        val pub_btn:Button=findViewById(R.id.pub_btn)
        val publish_text: EditText =findViewById(R.id.publish_text)
        val  inputText=publish_text.getText();
        pub_btn.setOnClickListener{
            val dbHelper= CoDatabaseHelper(this,"publish.db",1)
            dbHelper.writableDatabase
            val time= get_time().gettime()
            //Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()
            //测试发布内容获取
            add_database().add_database(this, SchoolID, NAME,inputText.toString(),"111",time)
            try {
                val invitation = invitation()
                invitation.invitation_id= SchoolID;
                invitation.invitation_name= NAME
                invitation.invitation_time=time
                invitation.invitation_image="qqq"
                invitation.invitation_record=inputText.toString()
                invitation.save(object : SaveListener<String>(){
                    override fun done(objectId: String?, e: BmobException?) {
                        if(e == null) {
                        } else {
                            //Toast.makeText(this@publish,e.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                    }
                )
            }catch (e: SecurityException){
                e.printStackTrace()
            }
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

}
