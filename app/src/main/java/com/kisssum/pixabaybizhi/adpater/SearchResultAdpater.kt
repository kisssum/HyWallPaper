package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.R
import org.jsoup.Jsoup
import rxhttp.wrapper.param.RxHttp
import java.lang.Exception

class SearchResultAdpater(private val context: Context) :
    RecyclerView.Adapter<SearchResultAdpater.MyViewHolder>() {
    private var data = arrayListOf<Map<String, String>>()
    private var page = 1
    private val handler: Handler

    init {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                when (msg.what) {
                    1 -> {
                        val list = msg.obj as ArrayList<Map<String, String>>
                        addData(list)
                        page++
                    }
                    2 -> {
                        val list = msg.obj as ArrayList<Map<String, String>>
                        setData(list)
                        page++
                    }
                    3 -> ""
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
            .load(url["src"])
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 3)
            bundel.putString("href", url["href"])
            bundel.putString("lazysrc2x", url["src"])

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_searchResultFragment_to_imgMainFragment, bundel)
        }
    }

    override fun getItemCount() = data.size

    fun getQuery(page: Int = this.page, upgrad: Boolean = false, query: String) {
        val url = "https://www.3gbizhi.com/search/1-${query}/${page}.html"

        // 设置单次加载最大图片数量
        val maxImgCount = 24

        Thread {
            try {
                val doc = Jsoup.connect(url).get().body()
                val list = doc.select("body > div.searchlistw > ul > li")

                val imgs = arrayListOf<Map<String, String>>()
                var map: HashMap<String, String>
                for (i in 0 until 24) {
                    map = HashMap()
                    map["src"] = list[i].select("img").attr("src")
                    map["href"] = list[i].select("a").attr("href")
                    imgs.add(map)
                }

                val message = Message.obtain()
                message.what = when (upgrad) {
                    true -> 1
                    else -> 2
                }
                message.obj = imgs
                handler.sendMessage(message)
            } catch (e: Exception) {
                handler.sendEmptyMessage(3)
            }
        }.start()
    }
}