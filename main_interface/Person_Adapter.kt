package com.zjgsu.kjr.campus_social_platform.main_interface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.chat.chat_interface
import com.zjgsu.kjr.campus_social_platform.sign_in_up.file
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.ArrayList

class Person_Adapter(val PersonList: ArrayList<Person>) : RecyclerView.Adapter<Person_Adapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Person_mage: ImageView = view.findViewById(R.id.Person_imageView)
        val Person_name: TextView = view.findViewById(R.id.Person_name)
        val Person_records:TextView=view.findViewById(R.id.Person_records)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {//zhushidifang
        val Person = PersonList[position]
        holder.Person_name.text =Person.person_name
        holder.Person_records.text=Person.chat_records
        if (file !=null) {
            val query = BmobQuery<PersonData>()
            query.addWhereEqualTo("ID", Person.person_id)
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
                                            val bitmap: Bitmap =
                                                BitmapFactory.decodeStream(`in`)
                                              holder.Person_mage.setImageBitmap(bitmap)
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
        holder.Person_mage.setOnClickListener {
            val intent=Intent(holder.itemView.context, chat_interface::class.java)
            intent.putExtra("Person_id",Person.person_id)
            intent.putExtra("Person_name",Person.person_name)
            startActivity(holder.itemView.context,intent,null)
        }
        holder.Person_name.setOnClickListener {
            val intent=Intent(holder.itemView.context,chat_interface::class.java)
            intent.putExtra("Person_id",Person.person_id)
            intent.putExtra("Person_name",Person.person_name)
            startActivity(holder.itemView.context,intent,null)
        }
        holder.Person_records.setOnClickListener {
            val intent=Intent(holder.itemView.context,chat_interface::class.java)
            intent.putExtra("Person_id",Person.person_id)
            intent.putExtra("Person_name",Person.person_name)
            startActivity(holder.itemView.context,intent,null)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_chat_interface_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = PersonList.size

}

