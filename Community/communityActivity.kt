package com.zjgsu.kjr.campus_social_platform.Community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zjgsu.kjr.campus_social_platform.Community.add_database
import com.zjgsu.kjr.campus_social_platform.Community.invitation
import com.zjgsu.kjr.campus_social_platform.Fruit
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.contact.contact
import com.zjgsu.kjr.campus_social_platform.main_interface.Person_interface
import kotlinx.android.synthetic.main.activity_comain.*
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_community.floatingActionButton
import kotlin.concurrent.thread

internal var onLongClickFlag = false
internal var removeName = ""
class CommunityActivity : AppCompatActivity() {
    private var fruitList = ArrayList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comain)
        initFruits() //初始化
        val layoutManager = LinearLayoutManager(this)

        con_recyclerView.layoutManager = layoutManager
        val adapter = pubAdapter(fruitList)
        con_recyclerView.adapter = adapter
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, publish::class.java)
            startActivity(intent)
            this.finish()
        }
        CoswipeRefresh.setOnRefreshListener {
            refreshPerson(adapter)
        }


        msg_in_btn.setOnClickListener {
            val intent=Intent(this, Person_interface::class.java)
            startActivity(intent)
            this.finish()
        }

        contact_in_btn.setOnClickListener {
            val intent=Intent(this, contact::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    private fun initFruits() {
        fruitList.clear()
        val recordList = get_database().getdatabase(this)
        load_invitation_data()
        for (i in recordList) {
            val str_id=i.invitation_id.toString()
            val str_name =i.invitation_name.toString()
            val str_text=i.invitation_record
            val str_time=i.invitation_time
            fruitList.add(0,Fruit(str_id,str_name.toString(), R.drawable.a1,
                str_text.toString(),str_time.toString().substring(5,19)))

        }

    }

    companion object{
        const val HANDLELONGCLIECK = "handlelongclick"
        lateinit var  mainActivity : AppCompatActivity   //静态对象，用于适配器调用activity的相关操作
        @JvmStatic
        fun mainActivityTodo(event: String, Name: String){
            when (event){
                /* 处理长按删除item事件 */
                HANDLELONGCLIECK -> {
                    onLongClickFlag = true
                    removeName = Name
                }
            }
        }
    }
    private fun refreshPerson(adapter: pubAdapter){//重新刷新函数
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                CoswipeRefresh.isRefreshing=false
                //Toast.makeText(this,"刷新成功！",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun load_invitation_data(){
        val bmobQuery_demo = BmobQuery<invitation>()
        //val bmobList = ArrayList<invitation>()
        //bmobList.clear()
        val temp=0;
        bmobQuery_demo.findObjects(object : FindListener<invitation>() {
            override fun done(list: List<invitation>, e: BmobException?) {
                if (e == null) {
                    val recordList=get_database().getdatabase(this@CommunityActivity)
                    for(i in list){
                        val invitation_demo= invitation()
                        invitation_demo.invitation_id=i.invitation_id
                        invitation_demo.invitation_record=i.invitation_record
                        invitation_demo.invitation_image=i.invitation_image
                        invitation_demo.invitation_time=i.invitation_time
                        invitation_demo.invitation_name=i.invitation_name
                        var temp=0
                        for (j in recordList){
                            if (j.invitation_time.toString()==invitation_demo.invitation_time.toString()){
                                temp=1
                            }
                        }

                        if(temp==1){

                        }else{
                            add_database().add_database(this@CommunityActivity, i.invitation_id.toString(), i.invitation_name.toString(),
                                i.invitation_record.toString(),"111",i.invitation_time.toString())
                        }
                    }//取到云端的值和本地的值，现在进行对比
                } else {
                    Log.d("error","error")
                }
            }
        })
    }
}