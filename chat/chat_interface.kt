package com.zjgsu.kjr.campus_social_platform.chat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.zjgsu.kjr.campus_social_platform.*
import com.zjgsu.kjr.campus_social_platform.main_interface.remind_id
import com.zjgsu.kjr.campus_social_platform.main_interface.remind_num
import com.zjgsu.kjr.campus_social_platform.main_interface.temporary_session
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import com.zjgsu.kjr.campus_social_platform.sign_in_up.SchoolID
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.change_student_information.*
import kotlinx.android.synthetic.main.msg_right_item.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

import kotlin.collections.ArrayList
var other_id=""
class chat_interface: AppCompatActivity(), View.OnClickListener{
    private val msgList = ArrayList<Msg>()
    private var adapter: MsgAdapter? = null
    var run = false
    val handler: Handler = Handler()
    var id:String=""
    var name:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        Bmob.initialize(this, "3ef45cbaebfff5cc05e559f8206b523f") //连接后端数据库
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        remind_num=0
        remind_id=""
        val dbHelper=MyDatabaseHelper(this,"Chat_Records.db",1)
        dbHelper.writableDatabase
        val student_id=intent.getStringExtra("Person_id")
        val person_name=intent.getStringExtra("Person_name")
        id= student_id.toString()
        name=person_name.toString()
        other_id=id
        initMsg()
        //构建RecyclerView，指定一个LayoutManager和一个适配器
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        recyclerView.adapter = adapter
        send.setOnClickListener(this)
        setSupportActionBar(tooolbar_chat)
        person_name_show.text=person_name
        adapter?.notifyItemInserted(msgList.size- 1)
        //将RecyclerView显示的数据定位至最后一行
        recyclerView.scrollToPosition(msgList.size- 1)
        //清空输入框内容
        input_text.setText("")
        var num=0
        val bmobQuery = BmobQuery<temporary_session>()
        bmobQuery.findObjects(object : FindListener<temporary_session>() {
            override fun done(list: List<temporary_session>, e: BmobException?) {
                if (e == null) {
                    for (i in list){
                          if(i.temporary_id.toString()==id&&i.local_id.toString()==SchoolID){
                              num=1
                    }}
                    if (num==0){
                         try {
                            val temporary_session= temporary_session()
                                temporary_session.temporary_id=id
                                temporary_session.local_id=SchoolID
                                temporary_session.temporary_name_demo=name
                                temporary_session.save(object : SaveListener<String>() {
                                    override fun done(objectId: String?, e: BmobException?) {
                                        if(e == null) {
                                        } else {
                                            Toast.makeText(this@chat_interface,e.toString(), Toast.LENGTH_LONG).show()
                                        }
                                    }
                                })
                        }catch (e: SecurityException) {
                            e.printStackTrace()
                        }
                    }
                }
                else {
                    Log.d("error","error")}
            }
        })
        val query = BmobQuery<PersonData>()
        query.addWhereEqualTo("ID", SchoolID)
        query.findObjects(object : FindListener<PersonData>() {
            override fun done(p0: MutableList<PersonData>?, p1: BmobException?) {
                        for (p in p0!!) {
                            try {
                                val url = URL(p!!.picture.url)
                                val connection: HttpURLConnection =
                                    url.openConnection() as HttpURLConnection
                                connection.setRequestMethod("GET")
                                connection.setConnectTimeout(5000)
                                val `in`: InputStream = connection.getInputStream()
                                val bitmap: Bitmap = BitmapFactory.decodeStream(`in`)
                                val ly=layoutInflater.inflate(R.layout.msg_right_item,null)
                                val image_ringt=ly.findViewById<ImageView>(R.id.imageView)
                                runOnUiThread {
                                    image_ringt.setImageBitmap(bitmap)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                }
        })
        run = true
        handler.postDelayed(task, 3000)//三秒刷新
    }
    private val task: Runnable = object : Runnable {
        override fun run() {
            if (run) {
                loadDataa()
                handler.postDelayed(this, 3000)
            }
        }
    }
    override fun onClick(v: View?) {
        when (v) {
            send -> {
                val content = input_text.text.toString()
                if (content.isNotEmpty()) {
                    //如果不为空，则新建Msg将其添加至msgList列表中
                    val msg = Msg(content,Msg.TYPE_SENT, get_time().gettime().substring(11,16))//.substring(11,16)
                    msgList.add(msg)
                    val time=get_time().gettime()
                    add_to_database().add_to_database(this,id,name,content, SchoolID,"send",time)
                    //notifyItemInserted通知列表有新数据插入，显示出来，刷新RecyclerView中的显示
                    adapter?.notifyItemInserted(msgList.size- 1)
                    //将RecyclerView显示的数据定位至最后一行
                    recyclerView.scrollToPosition(msgList.size- 1)
                    try {
                        val chat = chat()
                        chat.chat_id=id
                        chat.local_id= SchoolID
                        chat.chat_name=name
                        chat.chat_data=content
                        chat.chat_msg_type="send"
                        chat.chat_time=time
                        chat.save(object :SaveListener<String>() {
                            override fun done(objectId: String?, e: BmobException?) {
                                if(e == null) {
                                } else {
                                    Toast.makeText(this@chat_interface,e.toString(),Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    }catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                    //清空输入框内容
                    input_text.setText("")//那我们刚刚新生成的聊天记录就放进数据库，时间就不作为当场显示，再次退出和进入的时候再进行初始化的显示
                }
            }
        }
    }
    private fun initMsg(){//初始话聊天内容
        msgList.clear()
        var show_time: String =""
        val recordList= get_from_database().getdatabase(this)//从本地数据库得到聊天记录
        for(i in recordList){//对时间进行一个筛选，不要每条消息记录上面都有一个时间显示？
            val year=(i.chat_time.toString()).substring(0,4)
            val month=(i.chat_time.toString()).substring(5,7)
            val day=(i.chat_time.toString()).substring(8,10)
            val time=(i.chat_time.toString()).substring(11,16)
            var result_time=""
            if (year!=get_time().getyear()){
                result_time+=year+" "
            }
            if (day!=get_time().getday()){
                result_time+=month+"-"
                result_time+=day+" "
            }
            result_time+=time
            if (show_time==""){
                show_time=result_time
            }else{
                show_time= judge_show_time().judge(show_time,result_time)
            }
            if(i.chat_id==id){
                val msg_type=i.chat_msg_type//取出聊天对象的发送消息的类型，和某个人聊天的时候所有的聊天消息都放在一起，然后根据
                //消息的类型进行分类然后放到消息界面里
                if (msg_type=="send"){
                    val msg1=Msg(i.chat_data.toString(),Msg.TYPE_SENT,show_time)
                    msgList.add(msg1)

                }
            else{
                val msg2=Msg(i.chat_data.toString(),Msg.TYPE_RECEIVED,show_time)
                msgList.add(msg2)

            }}
        }}
    fun loadDataa(){
            val bmobQuery = BmobQuery<chat>()
            var num=0
            bmobQuery.findObjects(object : FindListener<chat>() {
                override fun done(list: List<chat>, e: BmobException?) {
                    if (e == null) {
                        val recordList= get_from_database().getdatabase(this@chat_interface)
                        for(i in list){
                            val chat_demo=chat()
                            if (i.local_id==id){
                                if (i.chat_id==SchoolID){
                                    chat_demo.local_id=i.chat_id
                                    chat_demo.chat_id=i.local_id
                                    chat_demo.chat_data=i.chat_data
                                    chat_demo.chat_msg_type="receiver"
                                    chat_demo.chat_time=i.chat_time
                                    chat_demo.chat_name=i.chat_name
                                    for(i in recordList){
                                        if(chat_demo.local_id.toString()==i.local_id.toString()&&chat_demo.chat_id.toString()==i.chat_id.toString()&&
                                            chat_demo.chat_data.toString()==i.chat_data.toString()&&chat_demo.chat_msg_type.toString()==i.chat_msg_type.toString()){
                                            num=1
                                            Log.d("456",i.chat_data.toString())
                                        }
                                    }
                                    if(num==0){
                                        add_to_database().add_to_database(this@chat_interface,chat_demo.chat_id.toString(),chat_demo.chat_name.toString(),
                                        chat_demo.chat_data.toString(),chat_demo.local_id.toString(),"receiver",chat_demo.chat_time.toString())
                                        initMsg()
                                        adapter = MsgAdapter(msgList)
                                        recyclerView.adapter = adapter
                                        adapter?.notifyItemInserted(msgList.size- 1)
                                        //将RecyclerView显示的数据定位至最后一行
                                        recyclerView.scrollToPosition(msgList.size- 1)
                                        val intent=Intent(Myapplication.context,chat_interface::class.java)
                                        val pi=PendingIntent.getActivity(Myapplication.context,0,intent,0)
                                        intent.putExtra("Person_id",id)
                                        intent.putExtra("Person_name",name)
                                        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                                            val  channel=NotificationChannel("normal","Normal",NotificationManager.IMPORTANCE_DEFAULT)
                                            manager.createNotificationChannel(channel)
                                        }
                                        val notification = NotificationCompat.Builder(this@chat_interface, "normal")
                                            .setContentTitle("您有新的消息")
                                            .setContentText("用户 $name 给您发来一条新消息")
                                            .setSmallIcon(R.drawable.ic_comment)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                                R.drawable.nav_friends
                                            ))
                                            .setContentIntent(pi)
                                            .setAutoCancel(true)
                                            .build()
                                        manager.notify(1, notification)
//                                        remind_num=1
//                                        remind_id=id
                                    }
                                }
                            }
                            num=0
                       }
                    }
                     else {
                        Log.d("error","error")
                    }
                }
            })
    }
}

