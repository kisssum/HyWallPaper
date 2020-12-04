package com.kisssum.pixabaybizhi.NavHome.Bian

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

class BianPagerAdpater(private val context: Context, private val typeIndex: Int) :
    RecyclerView.Adapter<BianPagerAdpater.MyViewHolder>() {
    private var data = arrayListOf<String>()
    private var page = 1
    private val handler: Handler

    init {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                page++

                val list = msg.obj as ArrayList<String>
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

    private fun setData(data: ArrayList<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun addData(data: ArrayList<String>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bian_pager_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = data[position]

        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 2)
            bundel.putString("imgUrl", url)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_homeFragment_to_imgMainFragment, bundel)
        }
    }

    override fun getItemCount() = data.size

    fun getImgUrl(page: Int = this.page, upgrad: Boolean = false, typeIndex: Int = this.typeIndex) {
        val types = arrayOf(
            "",
            "4kfengjing/",
            "4kmeinv/",
            "4kyouxi/",
            "4kdongman/",
            "4kyingshi/",
            "4kmingxing/",
            "4kqiche/",
            "4kdongwu/",
            "4krenwu/",
            "4kmeishi/",
            "4kzongjiao/",
            "4kbeijing/"
        )
        val baseUrl = "http://pic.netbian.com"
        val url = "${baseUrl}/${types[typeIndex]}" + when (page) {
            1 -> "index.html"
            else -> "index_$page.html"
        }

        Thread {
            try {
                val doc = Jsoup.connect(url).get()
                val es = when {
                    (page == 1 && typeIndex == 0) -> doc.select("#main > div.slist > ul > li > a > span > img")
                    else -> doc.select("#main > div.slist > ul > li > a > img")
                }

                val list = arrayListOf<String>()
                for (e in es)
                    list.add(baseUrl + e.attr("src"))

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