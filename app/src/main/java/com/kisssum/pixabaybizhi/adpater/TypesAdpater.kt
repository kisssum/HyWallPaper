package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.ModelListItemBinding
import org.jsoup.Jsoup
import java.lang.Exception

class TypesAdpater(private val context: Context) :
    RecyclerView.Adapter<TypesAdpater.MyViewHolder>() {

    private var data = arrayListOf<Map<String, String>>()
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when (msg.what) {
                SUCCESFUL -> {
                    val list = msg.obj as ArrayList<Map<String, String>>
                    setData(list)
                }
                else -> ""
            }
        }
    }
    private val SUCCESFUL = 1
    private val FAIL = 2

    init {
        loadData()
    }

    class MyViewHolder(binding: ModelListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val type = binding.type
        val count = binding.count
        val enter = binding.enter
        val list = binding.list
        val topTitle = binding.topTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ModelListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data[position]
        val activity = context as Activity

        holder.type.text = obj["name"]
        holder.count.text = obj["count"]
        holder.topTitle.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", position)

            Navigation.findNavController(activity, R.id.fragment_main)
//                .navigate(R.id.action_homeFragment_to_BZ36MainFragment2, bundle)
                .navigate(R.id.action_navigationControlFragment_to_typesPagerFragment, bundle)
        }
        holder.list.apply {
            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = TypesListAdpater(context, position)
        }
    }

    override fun getItemCount() = data.size

    private fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun loadData() {
        val url = "https://www.3gbizhi.com/sjbz/"

        Thread {
            try {
                val client = Jsoup.connect(url).get().body()
                val doc = client.select("body > div.menuw.mtm > div > ul > li > a")

                val list = arrayListOf<Map<String, String>>()
                var map: HashMap<String, String>

                for (i in 1 until doc.size) {
                    map = HashMap()
                    map["name"] = doc[i].select("em:nth-child(1)").text()

                    val c1 = doc[i].select("em:nth-child(2)").text()
                    val c2 = """\d+""".toRegex().find(c1)?.value
                    map["count"] = c2!!

                    list.add(map)
                }

                val message = Message.obtain()
                message.what = SUCCESFUL
                message.obj = list
                handler.sendMessage(message)
            } catch (e: Exception) {
                handler.sendEmptyMessage(FAIL)
            }
        }.start()
    }
}