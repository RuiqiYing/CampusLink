package com.zjgsu.kjr.campus_social_platform.Community

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zjgsu.kjr.campus_social_platform.BaseControl
import com.zjgsu.kjr.campus_social_platform.Fruit
import com.zjgsu.kjr.campus_social_platform.R
import com.zjgsu.kjr.campus_social_platform.sign_in_up.NAME
import com.zjgsu.kjr.campus_social_platform.sign_in_up.PersonData
import com.zjgsu.kjr.campus_social_platform.sign_in_up.file
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class pubAdapter(val fruitList: ArrayList<Fruit>) : RecyclerView.Adapter<pubAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val Name: TextView = view.findViewById(R.id.fruitName)
        val user_id: TextView=view.findViewById(R.id.user_id)
        val time_pub: TextView=view.findViewById(R.id.time_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_item, parent, false)

        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnLongClickListener {

            val position = viewHolder.adapterPosition
            /* 将长按item对应的学生姓名发送至MainActivity */

            if(fruitList[position].name== NAME){
            CommunityActivity.mainActivityTodo(
                CommunityActivity.HANDLELONGCLIECK,
                fruitList[position].name)
//            /* 在ArrayList中移除此股 */
            BaseControl().deleteData(parent.context,fruitList[position].name.substring(0,fruitList[position].name.length))
//            Toast.makeText(parent.context, fruitList[position].name, Toast.LENGTH_SHORT).show()
            fruitList.remove(fruitList[position])
//            /* 通知移除该item */
            notifyItemRemoved(position)
//            /* 通知调制ArrayList顺序(此句删除也无影响) */
            notifyItemRangeChanged(position, fruitList.size)
            }
            else{

            }
            //maketext决定Toast显示内容
            //BaseControl().deleteData(parent.context,fruitList[position].text)
            false
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.user_id.text = fruit.name
        holder.Name.text=fruit.text
        holder.time_pub.text=fruit.pub_time
        if (file!=null) {
            val query = BmobQuery<PersonData>()
            query.addWhereEqualTo("ID", fruit.id)
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
                                            holder.fruitImage.setImageBitmap(bitmap)
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


    override fun getItemCount() = fruitList.size

}
