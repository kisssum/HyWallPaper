package com.kisssum.pixabaybizhi.state

import android.app.Application
import android.os.Handler
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kisssum.pixabaybizhi.R
import rxhttp.wrapper.param.RxHttp
import java.util.ArrayList

class MasterViewModel(application: Application) : AndroidViewModel(application) {
    private var data = MutableLiveData<ArrayList<Map<String, String>>>()
    private val handler: Handler
    private var page = 1
    private val typeIndex = 0

    init {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val list = msg.obj as ArrayList<Map<String, String>>
                when (msg.what) {
                    1 -> {
                        addData(list)
                    }
                    2 -> {
                        setData(list)
                    }
                    else -> ""
                }
            }
        }

        loadData()
    }

    fun reLoad() {
        page = 1
        loadData()
    }

    fun getData() = data

    private fun setData(data: ArrayList<Map<String, String>>) {
        this.data.value = data
        page++
    }

    private fun addData(data: ArrayList<Map<String, String>>) {
        this.data.value?.addAll(data)
        page++
    }

    fun loadData(upgrad: Boolean = false) {
        val stringArray =
            getApplication<Application>().resources.getStringArray(R.array.types_list_url)
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