package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.R
import rxhttp.wrapper.param.RxHttp

class MasterAdpater(private val context: Context) :
    RecyclerView.Adapter<MasterAdpater.MyViewHolder>() {
    private var data = arrayListOf<Map<String, String>>()
    private var page = 1
    private val handler: Handler

    init {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val list = msg.obj as ArrayList<Map<String, String>>
                when (msg.what) {
                    1 -> {
                        addData(list)
                        page++
                    }
                    2 -> {
                        setData(list)
                        page++
                    }
                    else -> ""
                }
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.img)
    }

    private fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun addData(data: ArrayList<Map<String, String>>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.base_pager_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = data[position]

        Glide.with(context)
            .load(url["lazysrc"])
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 3)
            bundel.putString("href", url["href"])
            bundel.putString("lazysrc2x", url["lazysrc2x"])

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_homeFragment_to_imgMainFragment, bundel)
        }
    }

    override fun getItemCount() = data.size

    fun reLoad() {
        page = 1
        getImgUrl()
    }

    fun getImgUrl(page: Int = this.page, upgrad: Boolean = false) {
        val type = "sjbz/"
        val baseUrl = "https://www.3gbizhi.com"
        val url = "${baseUrl}/${type}" + when (page) {
            1 -> ""
            else -> "index_${page}.html"
        }
        // 设置单次加载最大图片数量
        val maxImgCount = 24

        RxHttp.get(url)
            .add("User-Agent",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.142 Safari/537.36")
            .asString()
            .subscribe({
                val href = """<a href="(.+?)".*?imgw.*?>""".toRegex().findAll(it)
                val lazysrc = """lazysrc="(.+?)"""".toRegex().findAll(it)
                val lazysrc2x = """lazysrc2x="(.+?) 2x"""".toRegex().findAll(it)

                val list = arrayListOf<Map<String, String>>()
                if (href.count() != 0) {
                    for (i in 0 until maxImgCount) {
                        val map = hashMapOf<String, String>()
                        map["href"] = href.toList()[i].destructured.component1()
                        map["lazysrc"] = lazysrc.toList()[i].destructured.component1()
                        map["lazysrc2x"] = lazysrc2x.toList()[i].destructured.component1()
                        list.add(map)
                    }
                }

                val message = Message.obtain()
                message.what = when {
                    upgrad -> 1
                    else -> 2
                }
                message.obj = list
                handler.sendMessage(message)
            }, {})
    }
}