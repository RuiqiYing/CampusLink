package com.zjgsu.kjr.campus_social_platform.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zjgsu.kjr.campus_social_platform.Community.CommunityActivity
import com.zjgsu.kjr.campus_social_platform.ContactClass
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.main_interface.Person_interface
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_contact_item.*


class contact : AppCompatActivity() {

    private val conList = ArrayList<ContactClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        initContacts() // 初始化联系人数据
        val layoutManager = LinearLayoutManager(this)
        con_recyclerView.layoutManager = layoutManager
        val adapter = conAdapter(conList,this)
        con_recyclerView.adapter = adapter
        cn_socity_in_btn.setOnClickListener {
            val intent= Intent(this, CommunityActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        cn_msg_in_btn.setOnClickListener {
            val intent= Intent(this, Person_interface::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun initContacts() {
        conList.clear()
        var recordList = ArrayList<contact_msg>()
        add_contact().add_database(this@contact,"1910080221","应瑞琦","111","计算机与信息工程学院","好好学习，天天向上","计科1902","13454970716")
        add_contact().add_database(this@contact,"1910080303","柯俊锐","111","计算机与信息工程学院","人生就是一场永远也无法回放的绝版影片。","计科1901","15381170456")
        add_contact().add_database(this@contact,"1935010205","易靖凯","111","计算机与信息工程学院","有钱的人更有钱，这往往是一种必然。","计科1902","13634827346")
       load_invitation_data()
        recordList= get_condata().getcondata(this)

        for (i in recordList) {
            var str_name = ""
            var str_col = ""
            var str_intr=""
            var str_id=""
            var str_class1=""
            var str_phone=""
            var str_img=""

            str_id=i.con_id.toString()
            str_name = i.con_name.toString()
            str_col=i.con_collage.toString()
            str_intr=i.con_introduce.toString()
            str_class1=i.con_class1.toString()
            str_phone=i.con_phone.toString()
            str_img=i.con_image.toString()
            conList.add(ContactClass(str_id,str_name, str_img,str_col,str_intr,str_class1,str_phone))
        }
        var temp= ContactClass("1","1","1","1","1","1","1")
        var next=0
        var i=0;
        for (i in 0..conList.size){
            next=i+1;
            for (j in i+1..conList.size-1){
                if (conList[i].conCollage==conList[j].conCollage){
                    temp=conList[j]
                    conList.add(j,conList[next])
                    conList.removeAt(j+1)
                    conList.add(next,temp)
                    conList.removeAt(next+1)
                    next=next+1
                }
            }
        }
        for ( i in 0..conList.size-2){
            if(conList[i].conCollage!=conList[i+1].conCollage&&conList[i].conImageId!=" "){
                conList.add(i+1,
                    ContactClass(" "," "," ","以下是"+conList[i+1].conCollage,"以下是"+conList[i+1].conCollage," "," ")
                )
                i.and(1)
            }
        }
        conList.add(0, ContactClass(" "," "," ","以下是"+conList[1].conCollage,"以下是"+conList[i+1].conCollage," "," "))
//        if(conList[conList.size-1].conCollage!=conList[conList.size-2].conCollage){
//            conList.add(conList.size-1,ContactClass(" "," "," ","以下是"+conList[conList.size-1].conCollage,"以下是"+conList[conList.size-1].conCollage," "," "))
//        }
    }

    fun load_invitation_data(){
        val bmobQuery_demo = BmobQuery<PersonData>()
        //val bmobList = ArrayList<invitation>()
        //bmobList.clear()
        bmobQuery_demo.findObjects(object : FindListener<PersonData>() {
            override fun done(list: List<PersonData>, e: BmobException?) {
                if (e == null) {
                    val recordList= get_condata().getcondata(this@contact)
                    for(i in list){
                        val contact_demo= contact_msg()
                        contact_demo.con_id=i.id
                        contact_demo.con_introduce=i.introduce
                       // contact_demo.con_image=i.picture
                        contact_demo.con_collage=i.college
                        contact_demo.con_name=i.name
                        contact_demo.con_class1=i.class1
                        contact_demo.con_phone=i.phone
//                        Log.d("contact",contact_demo.con_introduce.toString())
//                        Log.d("contact",contact_demo.con_class1.toString())
                        //bmobList.add(invitation_demo)
                        if(recordList.contains(contact_demo)){
                        }else{
                            add_contact().add_database(this@contact,i.id,i.name,"111",i.college,i.introduce,i.class1,i.phone)
                        }
                    }//取到云端的值和本地的值，现在进行对比
                } else {
                    Log.d("error","error")
                }
            }
        })
    }
}