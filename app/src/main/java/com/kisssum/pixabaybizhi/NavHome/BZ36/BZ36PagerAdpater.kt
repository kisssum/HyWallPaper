package com.kisssum.pixabaybizhi.NavHome.BZ36

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
import org.jsoup.Jsoup

class BZ36PagerAdpater(private val context: Context, private val typeIndex: Int) :
    RecyclerView.Adapter<BZ36PagerAdpater.MyViewHolder>() {
    private var data = arrayListOf<Map<String, String>>()
    private var page = 1
    private val handler: Handler

    init {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                page++

                val list = msg.obj as ArrayList<Map<String, String>>
                when (msg.what) {
                    1 -> addData(list)
                    2 -> setData(list)
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

    fun getImgUrl(page: Int = this.page, upgrad: Boolean = false, typeIndex: Int = this.typeIndex) {
        val types = arrayOf(
            "sjbz/",
            "wallMV/",
            "wallMX/",
            "wallYS/",
            "wallDM/",
            "wallKT/",
            "wallQC/",
            "wallAQ/",
            "wallYX/",
            "wallTY/",
            "wallCM/",
            "wallQFJ/",
            "wallPP/",
            "wallKA/",
            "wallJR/",
            "wallJZ/",
            "wallZW/",
            "wallDW/",
            "wallCY/",
            "wallQT/",
        )
        val baseUrl = "https://www.3gbizhi.com"
        val url = "${baseUrl}/${types[typeIndex]}" + when (page) {
            1 -> ""
            else -> "index_${page}.html"
        }

        Thread {
            try {
                val doc = Jsoup.connect(url).get()
                val esa = when {
                    (page == 1 && typeIndex == 0) -> doc.select("body > div:nth-child(6) > ul > li > a")
                    (page != 1 && typeIndex == 0) -> doc.select("body > div:nth-child(5) > ul > li > a")
                    else -> doc.select("body > div.contlistw.mtw > ul > li > a")
                }

                val list = arrayListOf<Map<String, String>>()
                var map: HashMap<String, String>
                for (e in esa) {
                    val lazyimg = e.select("img")

                    map = HashMap()
                    map["href"] = e.attr("href")
                    map["lazysrc"] = lazyimg.attr("lazysrc")
                    map["lazysrc2x"] = lazyimg.attr("lazysrc2x").split(" ")[0]
                    list.add(map)
                }

                val message = Message.obtain()
                message.obj = list
                if (upgrad) message.what = 1 else message.what = 2
                handler.sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}