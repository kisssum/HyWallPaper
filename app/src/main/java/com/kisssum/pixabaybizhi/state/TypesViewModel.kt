package com.kisssum.pixabaybizhi.state

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.jsoup.Jsoup
import rxhttp.wrapper.param.RxHttp
import java.lang.Exception
import java.util.ArrayList

class TypesViewModel(application: Application) : AndroidViewModel(application) {
    private val baseData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val mvData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val mxData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val ysData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val dmData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val ktData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val qcData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val aqData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val yxData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val tyData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val cmData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val qfjData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val ppData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val kaData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val jrData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val jzData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val zwData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val dwData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val cyData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val qtData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val handler: Handler

    init {
        handler = object : Handler() {
            @SuppressLint("HandlerLeak")
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val list = msg.obj as ArrayList<Map<String, String>>

                when (msg.what) {
                    -1 -> {
                        baseData.value = list
                    }
                    0 -> {
                        mvData.value = list
                    }
                    1 -> {
                        mxData.value = list
                    }
                    2 -> {
                        ysData.value = list
                    }
                    3 -> {
                        dmData.value = list
                    }
                    4 -> {
                        ktData.value = list
                    }
                    5 -> {
                        qcData.value = list
                    }
                    6 -> {
                        aqData.value = list
                    }
                    7 -> {
                        yxData.value = list
                    }
                    8 -> {
                        tyData.value = list
                    }
                    9 -> {
                        cmData.value = list
                    }
                    10 -> {
                        qfjData.value = list
                    }
                    11 -> {
                        ppData.value = list
                    }
                    12 -> {
                        kaData.value = list
                    }
                    13 -> {
                        jrData.value = list
                    }
                    14 -> {
                        jzData.value = list
                    }
                    15 -> {
                        zwData.value = list
                    }
                    16 -> {
                        dwData.value = list
                    }
                    17 -> {
                        cyData.value = list
                    }
                    18 -> {
                        qtData.value = list
                    }
                }
            }
        }

        loadBaseData()
    }

    fun getBaseData() = baseData

    fun loadBaseData() {
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

                    map["url"] = doc[i].attr("href")

                    list.add(map)
                }

                val message = Message.obtain()
                message.what = -1
                message.obj = list
                handler.sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun getPictureData(index: Int) = when (index) {
        0 -> mvData
        1 -> mxData
        2 -> ysData
        3 -> dmData
        4 -> ktData
        5 -> qcData
        6 -> aqData
        7 -> yxData
        8 -> tyData
        9 -> cmData
        10 -> qfjData
        11 -> ppData
        12 -> kaData
        13 -> jrData
        14 -> jzData
        15 -> zwData
        16 -> dwData
        17 -> cyData
        18 -> qtData
        else -> MutableLiveData()
    }

    fun loadPictureData(index: Int) {
        val url = baseData.value?.get(index)?.get("url").toString()

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
                message.what = index
                message.obj = list
                handler.sendMessage(message)
            }, {})
    }
}