package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.ModelListItem2Binding
import rxhttp.wrapper.param.RxHttp

class TypesListAdpater(private val context: Context, private val typeIndex: Int) :
    RecyclerView.Adapter<TypesListAdpater.MyViewHolder>() {

    private var data = arrayListOf<Map<String, String>>()
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val list = msg.obj as ArrayList<Map<String, String>>
            when (msg.what) {
                1 -> {
                    setData(list)
                }
                else -> ""
            }
        }
    }
    private val types = arrayOf(
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
    private val baseUrl = "https://www.3gbizhi.com"


    init {
        loadData()
    }

    class MyViewHolder(binding: ModelListItem2Binding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.img
    }

    private fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ModelListItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
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
                .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
        }
    }

    override fun getItemCount() = data.size

    private fun loadData() {
        val url = "${baseUrl}/${types[typeIndex]}"

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
                    for (i in 0 until href.count()) {
                        val map = hashMapOf<String, String>()
                        map["href"] = href.toList()[i].destructured.component1()
                        map["lazysrc"] = lazysrc.toList()[i].destructured.component1()
                        map["lazysrc2x"] = lazysrc2x.toList()[i].destructured.component1()
                        list.add(map)
                    }
                }

                val message = Message.obtain()
                message.what = 1
                message.obj = list
                handler.sendMessage(message)
            }, {})
    }
}