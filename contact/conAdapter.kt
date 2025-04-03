package com.zjgsu.kjr.campus_social_platform.contact

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zjgsu.kjr.campus_social_platform.Community.student_information_Activity
import com.zjgsu.kjr.campus_social_platform.ContactClass
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.chat.chat_interface
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import com.zjgsu.kjr.campus_social_platform.sign_in_up.file
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class conAdapter(val conList: List<ContactClass>, var context:Context) : RecyclerView.Adapter<conAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val conImage: ImageView = view.findViewById(R.id.conImage)
        val conName: TextView = view.findViewById(R.id.conName)
        val college_text:TextView=view.findViewById(R.id.college_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_contact_item, parent, false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            showPopup(view,position)
            false
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val con = conList[position]
        holder.conName.text = con.conName
        holder.college_text.text=con.introduce
        if (file!=null) {
            val query = BmobQuery<PersonData>()
            query.addWhereEqualTo("ID", con.conID)
            query.findObjects(object : FindListener<PersonData>() {
                override fun done(p0: MutableList<PersonData>?, p1: BmobException?) {
                    if (p1 == null) {
                        if (p0 != null && p0.size > 0) {
                            for (p in p0) {
                                object : Thread() {
                                    override fun run() {
                                        try {
                                            Log.d("!图片！！！！！！！！", file.toString())
                                            val url = URL(p!!.picture.url)
                                            val connection: HttpURLConnection =
                                                url.openConnection() as HttpURLConnection
                                            connection.setRequestMethod("GET")
                                            connection.setConnectTimeout(5000)
                                            val `in`: InputStream = connection.getInputStream()
                                            val bitmap: Bitmap =
                                                BitmapFactory.decodeStream(`in`)
                                            holder.conImage.setImageBitmap(bitmap)
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
    override fun getItemCount() = conList.size

    fun showPopup(view: View,temp_num:Int)
    {
        var popup: PopupMenu? = null;
        popup = PopupMenu(context, view)
        popup.inflate(R.menu.my_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.chat_with -> {
                    val intent=Intent(context, chat_interface::class.java)
                    intent.putExtra("Person_name",conList[temp_num].conName)
                    intent.putExtra("Person_id",conList[temp_num].conID)
                    context.startActivity(intent)
                }
                R.id.person_infor -> {
                    val intent=Intent(context, student_information_Activity::class.java)
                    intent.putExtra("name",conList[temp_num].conName.toString())
                    intent.putExtra("collage",conList[temp_num].conCollage)
                    intent.putExtra("id",conList[temp_num].conID)
                    intent.putExtra("class1",conList[temp_num].class1.toString())
                    intent.putExtra("phone",conList[temp_num].phone)
                    intent.putExtra("introduce",conList[temp_num].introduce)
                    Log.d("conAdapter",conList[temp_num].conID)
                    Log.d("conAdapter",conList[temp_num].class1)
                    context.startActivity(intent)
                }
            }
            true
        })
        popup.show()
    }

}
