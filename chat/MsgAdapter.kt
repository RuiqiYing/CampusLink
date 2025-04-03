package com.zjgsu.kjr.campus_social_platform

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zjgsu.kjr.campus_social_platform.chat.Msg
import com.zjgsu.kjr.campus_social_platform.chat.other_id
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import com.zjgsu.kjr.campus_social_platform.sign_in_up.SchoolID
import com.zjgsu.kjr.campus_social_platform.sign_in_up.file
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MsgAdapter(val msgList: List<Msg>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leftMsg: TextView = view.findViewById(R.id.left_Msg)
        val time_show_left:TextView=view.findViewById(R.id.time_show_left)
        val left_img:ImageView=view.findViewById(R.id.imageView_left)
    }

    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rightMsg: TextView = view.findViewById(R.id.right_Msg)
        val time_show_right:TextView=view.findViewById(R.id.time_show_right)
        val right_img:ImageView=view.findViewById(R.id.imageView_right)
    }
    //返回当前position相应的消息类型
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }
    //根据不同的ViewType加载不同的布局并创建不同的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        if (viewType == Msg.TYPE_RECEIVED) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item, parent, false)
            LeftViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item, parent, false)
            RightViewHolder(view)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        if(holder is LeftViewHolder ){
            holder.time_show_left.text=msg.time
            holder.leftMsg.text = msg.content
            if (file!=null) {
                val query = BmobQuery<PersonData>()
                query.addWhereEqualTo("ID", other_id)
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
                                                holder.left_img.setImageBitmap(bitmap)
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
        if(holder is RightViewHolder){
            holder.rightMsg.text = msg.content
            holder.time_show_right.text=msg.time
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
                                                val bitmap: Bitmap =
                                                    BitmapFactory.decodeStream(`in`)
                                                holder.right_img.setImageBitmap(bitmap)
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
    }

    override fun getItemCount()=msgList.size
}
