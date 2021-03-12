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
import com.kisssum.pixabaybizhi.databinding.ModelListItem3Binding
import rxhttp.wrapper.param.RxHttp
import java.util.*

class TypesPagerListAdpater(private val context: Context, private val typeIndex: Int) :
    RecyclerView.Adapter<TypesPagerListAdpater.MyViewHolder>() {
    private val handler: Handler
    private var data = arrayListOf<Map<String, String>>()
    private var page = 1

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

        loadData()
    }

    class MyViewHolder(binding: ModelListItem3Binding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.img
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ModelListItem3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    private fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun addData(data: ArrayList<Map<String, String>>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data[position]

        Glide.with(context)
            .load(obj["lazysrc"])
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 3)
            bundel.putString("href", obj["href"])
            bundel.putString("lazysrc2x", obj["lazysrc2x"])

            if (typeIndex == 0) {
                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
            } else {
                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_typesPagerFragment_to_imgMainFragment, bundel)
            }
        }
    }

    override fun getItemCount() = data.size

    fun reLoad() {
        page = 1
        loadData()
    }

    fun loadData(upgrad: Boolean = false) {
        val stringArray = context.resources.getStringArray(R.array.types_list_url)
        val url = when (page) {
            1 -> stringArray[typeIndex]
            else -> "${stringArray[typeIndex]}index_${page}.html"
        }

        // 设置单次加载最大图片数量
        val maxImgCount = if (typeIndex == 0) 24 else 18

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