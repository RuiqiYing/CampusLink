package com.zjgsu.kjr.campus_social_platform

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import com.zjgsu.kjr.campus_social_platform.main_interface.Person_interface
import com.zjgsu.kjr.campus_social_platform.sign_in_up.*
import kotlinx.android.synthetic.main.change_student_information.*
import kotlinx.android.synthetic.main.main_chat_interface.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class change_student_informationActivity : AppCompatActivity() {
    private val TAG = "change_student_informat"
    val fromalbum = 1
    var common_file:File?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_student_information)
        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库
        change_user_id.setText(SchoolID)
        change_user_college.setText(COLLEGE)
        change_user_class.setText(CLASS)
        change_user_phone.setText(PHONE)
        change_user_other1.setText(CONTACT)
        change_user_name.setText(NAME)
        if (file!=null) {
            val query = BmobQuery<PersonData>()
            query.addWhereEqualTo("ID", SchoolID)
            query.findObjects(object : FindListener<PersonData>() {
                override fun done(p0: MutableList<PersonData>?, p1: BmobException?) {
                    if (p1 == null) {
                        if (p0 != null && p0.size > 0) {
                            for (p in p0) {
                                object : Thread() {
                                    override fun run() {
                                        try {
                                            val url = URL(p!!.picture.url)
                                            val connection: HttpURLConnection =
                                                url.openConnection() as HttpURLConnection
                                            connection.setRequestMethod("GET")
                                            connection.setConnectTimeout(5000)
                                            val `in`: InputStream = connection.getInputStream()
                                            val bitmap: Bitmap = BitmapFactory.decodeStream(`in`)
                                            runOnUiThread { imageView.setImageBitmap(bitmap) }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                }.start()
                            }
                        }
                    }
                }
            })
        }


        if (INTRODUCE != null) {
            user_introduce.setText(INTRODUCE)
        }
        imageView.setOnClickListener { //点击更换头像
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, fromalbum)

        }
        save.setOnClickListener {
            val query = BmobQuery<PersonData>()
            query.addWhereEqualTo("ID", SchoolID)
            query.findObjects(object : FindListener<PersonData>(){
                override fun done(p0: MutableList<PersonData>?, p1: BmobException?) {
                    if(p1==null) {
                        if(p0!=null&&p0.size>0){
                            for (p in p0) {
//                                Log.d(TAG, imagePath)
//                                Log.d(TAG, File(imagePath).exists().toString())
//                                file = BmobFile(File(imagePath))
                                if (common_file?.exists() == true) { //修改头像
                                    file = BmobFile(common_file)
                                    file!!.uploadblock(object : UploadFileListener() {
                                        override fun done(p2: BmobException?) {
                                            if (p2 == null) {
                                                p.picture = file
                                                p.name = change_user_name.text.toString()
                                                p.phone = change_user_phone.text.toString()
                                                p.class1 = change_user_class.text.toString()
                                                p.college = change_user_college.text.toString()
                                                p.contact = change_user_other1.text.toString()
                                                p.introduce = user_introduce.text.toString()
                                                p.update(object : UpdateListener() {
                                                    override fun done(p0: BmobException?) {
                                                        if (p0 == null) {
                                                            COLLEGE = p.college
                                                            CONTACT = p.contact
                                                            CLASS = p.class1
                                                            PHONE = p.phone
                                                            INTRODUCE = p.introduce
                                                            Toast.makeText(
                                                                this@change_student_informationActivity,
                                                                "更新成功！",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                            common_file = null
                                                            val intent = Intent(
                                                                this@change_student_informationActivity,
                                                                Person_interface::class.java
                                                            )
                                                            startActivity(intent)
                                                        } else {
                                                            Log.d(
                                                                "保存数据！！！！！！！！！！！！！！！！！！",
                                                                p0.message.toString()
                                                            )
                                                        }
                                                    }
                                                })
                                                Log.d("!!!picture!!!", "上传成功！")

                                            } else {
                                                Log.d(
                                                    TAG,
                                                    p2.message.toString()
                                                )
                                            }
                                        }
                                    })
                                }
                                else{ //未修改头像
                                    p.name = change_user_name.text.toString()
                                    p.phone = change_user_phone.text.toString()
                                    p.class1 = change_user_class.text.toString()
                                    p.college = change_user_college.text.toString()
                                    p.contact = change_user_other1.text.toString()
                                    p.introduce = user_introduce.text.toString()
                                    p.update(object : UpdateListener() {
                                        override fun done(p0: BmobException?) {
                                            if (p0 == null) {
                                                COLLEGE = p.college
                                                CONTACT = p.contact
                                                CLASS = p.class1
                                                PHONE = p.phone
                                                INTRODUCE = p.introduce
                                                Toast.makeText(
                                                    this@change_student_informationActivity,
                                                    "更新成功！",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                common_file = null
                                                val intent = Intent(
                                                    this@change_student_informationActivity,
                                                    Person_interface::class.java
                                                )
                                                startActivity(intent)
                                            } else {
                                                Log.d(
                                                    "保存数据！！！！！！！！！！！！！！！！！！",
                                                    p0.message.toString()
                                                )
                                            }
                                        }
                                    })

                                }
                            }
                        }
                    }
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            fromalbum->{
                if (resultCode==Activity.RESULT_OK&&data!=null) {
                    data.data?.let {
                            uri->
                        val bitmap = getBitMapFromUri(uri)
                        imageView.setImageBitmap(bitmap)
                        common_file = uriToFileQ(this,uri)
                    }
                }
            }
        }
    }
    private fun getBitMapFromUri(uri: Uri)=contentResolver.openFileDescriptor(uri,"r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uriToFileQ(context: Context, uri: Uri): File? =
        if (uri.scheme == ContentResolver.SCHEME_FILE)
            File(requireNotNull(uri.path))
        else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //把文件保存到沙盒
            val contentResolver = context.contentResolver
            val displayName = "${System.currentTimeMillis()}${Random.nextInt(0, 9999)}.${
                MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(uri))}"
            val ios = contentResolver.openInputStream(uri)
            if (ios != null) {
                File("${context.cacheDir.absolutePath}/$displayName")
                    .apply {
                        val fos = FileOutputStream(this)
                        FileUtils.copy(ios, fos)
                        fos.close()
                        ios.close()
                    }
            } else null
        } else null

}