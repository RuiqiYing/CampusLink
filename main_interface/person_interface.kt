package com.zjgsu.kjr.campus_social_platform.main_interface

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.google.android.material.navigation.NavigationView
import com.zjgsu.kjr.campus_social_platform.Community.CommunityActivity
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.change_student_informationActivity
import com.zjgsu.kjr.campus_social_platform.chat.get_from_database
import com.zjgsu.kjr.campus_social_platform.contact.contact
import com.zjgsu.kjr.campus_social_platform.main_interface.*
import com.zjgsu.kjr.campus_social_platform.sign_in_up.*
import kotlinx.android.synthetic.main.main_chat_interface.*
import kotlinx.android.synthetic.main.main_chat_interface.recyclerView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set
import kotlin.concurrent.thread
var remind_num = 0
var remind_id =""
class Person_interface : AppCompatActivity() {
    private val PersonList=ArrayList<Person>()
    var run = false
    val handler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_chat_interface)

        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f")
        initPersons()
        setSupportActionBar(tooolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(android.R.drawable.ic_dialog_dialer)
        }
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = Person_Adapter(PersonList)
        recyclerView.adapter = adapter
        swipeRefresh.setColorSchemeResources(R.color.black)//设置下拉刷新时的进度条转动的时候的进度条的颜色
        swipeRefresh.setOnRefreshListener {
            refreshPerson(adapter)
        } //设置menu的点击事件
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.setting_information -> {
                    val intent = Intent(this, change_student_informationActivity::class.java)
                    startActivity(intent)
                }
                R.id.change_my_information -> {
                    val intent = Intent(this, change_password::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        val navview=findViewById<NavigationView>(R.id.navView)
        if (navview.getHeaderCount() > 0) {
            val header: View = navView.getHeaderView(0)
            var self_id=header.findViewById<TextView>(R.id.show_id)
            var self_name = header.findViewById<TextView>(R.id.show_name)
            var self_college = header.findViewById<TextView>(R.id.show_xyuan)
            var self_class = header.findViewById<TextView>(R.id.show_class)
            var self_phone = header.findViewById<TextView>(R.id.show_phone)
            var self_contact = header.findViewById<TextView>(R.id.show_another)
            var self_introduce = header.findViewById<TextView>(R.id.show_myself)
            var self_image = header.findViewById<ImageView>(R.id.imageView3)
            self_id.text= SchoolID
            self_name.text = NAME
            self_college.text = COLLEGE
            self_class.text = CLASS
            self_phone.text = PHONE
            self_contact.text = CONTACT
            if (INTRODUCE!=null) self_introduce.text = INTRODUCE

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
                                                val connection: HttpURLConnection=
                                                    url.openConnection() as HttpURLConnection
                                                connection.setRequestMethod("GET")
                                                connection.setConnectTimeout(5000)
                                                val `in`: InputStream = connection.getInputStream()
                                                val bitmap: Bitmap =
                                                    BitmapFactory.decodeStream(`in`)
                                                runOnUiThread { self_image.setImageBitmap(bitmap) }
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
        }
        
       socity_btn.setOnClickListener {
           val intent=Intent(this, CommunityActivity::class.java)
           startActivity(intent)
           this.finish()
       }
        contacks_btn.setOnClickListener {
            val intent=Intent(this, contact::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    private fun initPersons() {
        PersonList.clear()//要清空之前的数据库，不然会不停的生成聊天界面
                          //用hashmap的方式来解决
        var test_num=0
        var data_map=HashMap<String,String?>()
        val recordList= get_from_database().getdatabase(this)//从本地数据库得到聊天记录
        for(i in recordList){                    //找出同一个学号的最后一条的聊天记录
            //因为数据取出来的时候就是时间最近的在最下面，所以用字典的方法来写比较符合和方便
            //学号等于==list_data[0].substring(1,11)
            if(data_map.containsKey(i.chat_id)){
                data_map[i.chat_id.toString()]=i.chat_data.toString()
            }else{
                    data_map.put(i.chat_id.toString(),i.chat_data.toString())
            }
        }//取值成功，以字典的方式保存
        //最后初始化的时候拿着学号与联系人的数据库取交集
        //PersonList.add(Person("柯俊锐", data_map["1910080303"].toString(),"1910080303"))
        val bmobQuery = BmobQuery<temporary_session>()
        bmobQuery.findObjects(object : FindListener<temporary_session>() {
            override fun done(list: List<temporary_session>, e: BmobException?) {
                if (e == null) {
                    for (i in list){
                          if (i.temporary_id.toString()!=SchoolID){
                              for(j in PersonList){
                                  if (j.person_id==i.temporary_id){
                                      test_num=1
                                  }
                              }
                              if (test_num==0){
                                PersonList.add(Person(i.temporary_name_demo.toString(), data_map[i.temporary_id.toString()].toString(),i.temporary_id.toString()))
                              val adapter = Person_Adapter(PersonList)
                              recyclerView.adapter = adapter
                          }}
                    test_num=0
                    }
                }
                else {
                    Log.d("error","error")}
            }
        })
    }
    private fun refreshPerson(adapter: Person_Adapter){//重新刷新函数
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initPersons()
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing=false
                //Toast.makeText(this,"刷新成功！",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}
