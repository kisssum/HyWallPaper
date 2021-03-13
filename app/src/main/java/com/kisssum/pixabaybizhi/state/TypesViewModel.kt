package com.kisssum.pixabaybizhi.state

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Message
import android.util.Log
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

    private val SPEED = 20
    private val RESET_BASEDATA = -1
    private val RESET_MVDATA = 0
    private val RESET_MXDATA = 1
    private val RESET_YSDATA = 2
    private val RESET_DMDATA = 3
    private val RESET_KTDATA = 4
    private val RESET_QCDATA = 5
    private val RESET_AQDATA = 6
    private val RESET_YXDATA = 7
    private val RESET_TYDATA = 8
    private val RESET_CMDATA = 9
    private val RESET_QFJDATA = 10
    private val RESET_PPDATA = 11
    private val RESET_KADATA = 12
    private val RESET_JRDATA = 13
    private val RESET_JZDATA = 14
    private val RESET_ZWDATA = 15
    private val RESET_DWDATA = 16
    private val RESET_CYDATA = 17
    private val RESET_QTDATA = 18
    private val ADD_MVDATA = RESET_MVDATA + SPEED
    private val ADD_MXDATA = RESET_MXDATA + SPEED
    private val ADD_YSDATA = RESET_YSDATA + SPEED
    private val ADD_DMDATA = RESET_DMDATA + SPEED
    private val ADD_KTDATA = RESET_KTDATA + SPEED
    private val ADD_QCDATA = RESET_QCDATA + SPEED
    private val ADD_AQDATA = RESET_AQDATA + SPEED
    private val ADD_YXDATA = RESET_YXDATA + SPEED
    private val ADD_TYDATA = RESET_TYDATA + SPEED
    private val ADD_CMDATA = RESET_CMDATA + SPEED
    private val ADD_QFJDATA = RESET_QFJDATA + SPEED
    private val ADD_PPDATA = RESET_PPDATA + SPEED
    private val ADD_KADATA = RESET_KADATA + SPEED
    private val ADD_JRDATA = RESET_JRDATA + SPEED
    private val ADD_JZDATA = RESET_JZDATA + SPEED
    private val ADD_ZWDATA = RESET_ZWDATA + SPEED
    private val ADD_DWDATA = RESET_DWDATA + SPEED
    private val ADD_CYDATA = RESET_CYDATA + SPEED
    private val ADD_QTDATA = RESET_QTDATA + SPEED

    private var pages = arrayListOf(
        1, 1, 1, 1,
        1, 1, 1, 1,
        1, 1, 1, 1,
        1, 1, 1, 1,
        1, 1, 1)

    init {
        handler = object : Handler() {
            @SuppressLint("HandlerLeak")
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val list = msg.obj as ArrayList<Map<String, String>>

                when (msg.what) {
                    RESET_BASEDATA -> baseData.value = list

                    RESET_MVDATA -> mvData.value = list
                    ADD_MVDATA -> mvData.value?.addAll(list)

                    RESET_MXDATA -> mxData.value = list
                    ADD_MXDATA -> mxData.value?.addAll(list)

                    RESET_YSDATA -> ysData.value = list
                    ADD_YSDATA -> ysData.value?.addAll(list)

                    RESET_DMDATA -> dmData.value = list
                    ADD_DMDATA -> dmData.value?.addAll(list)

                    RESET_KTDATA -> ktData.value = list
                    ADD_KTDATA -> ktData.value?.addAll(list)

                    RESET_QCDATA -> qcData.value = list
                    ADD_QCDATA -> qcData.value?.addAll(list)

                    RESET_AQDATA -> aqData.value = list
                    ADD_AQDATA -> aqData.value?.addAll(list)

                    RESET_YXDATA -> yxData.value = list
                    ADD_YXDATA -> yxData.value?.addAll(list)

                    RESET_TYDATA -> tyData.value = list
                    ADD_TYDATA -> tyData.value?.addAll(list)

                    RESET_CMDATA -> cmData.value = list
                    ADD_CMDATA -> cmData.value?.addAll(list)

                    RESET_QFJDATA -> qfjData.value = list
                    ADD_QFJDATA -> qfjData.value?.addAll(list)

                    RESET_PPDATA -> ppData.value = list
                    ADD_PPDATA -> ppData.value?.addAll(list)

                    RESET_KADATA -> kaData.value = list
                    ADD_KADATA -> kaData.value?.addAll(list)

                    RESET_JRDATA -> jrData.value = list
                    ADD_JRDATA -> jrData.value?.addAll(list)

                    RESET_JZDATA -> jzData.value = list
                    ADD_JZDATA -> jzData.value?.addAll(list)

                    RESET_ZWDATA -> zwData.value = list
                    ADD_ZWDATA -> zwData.value?.addAll(list)

                    RESET_DWDATA -> dwData.value = list
                    ADD_DWDATA -> dwData.value?.addAll(list)

                    RESET_CYDATA -> cyData.value = list
                    ADD_CYDATA -> cyData.value?.addAll(list)

                    RESET_QTDATA -> qtData.value = list
                    ADD_QTDATA -> qtData.value?.addAll(list)
                }

                Log.d("BIAN", list.count().toString() + " " + mvData.value?.count().toString())
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
                message.what = RESET_BASEDATA
                message.obj = list
                handler.sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun getPictureData(index: Int) = when (index) {
        RESET_MVDATA -> mvData
        RESET_MXDATA -> mxData
        RESET_YSDATA -> ysData
        RESET_DMDATA -> dmData
        RESET_KTDATA -> ktData
        RESET_QCDATA -> qcData
        RESET_AQDATA -> aqData
        RESET_YXDATA -> yxData
        RESET_TYDATA -> tyData
        RESET_CMDATA -> cmData
        RESET_QFJDATA -> qfjData
        RESET_PPDATA -> ppData
        RESET_KADATA -> kaData
        RESET_JRDATA -> jrData
        RESET_JZDATA -> jzData
        RESET_ZWDATA -> zwData
        RESET_DWDATA -> dwData
        RESET_CYDATA -> cyData
        RESET_QTDATA -> qtData
        else -> MutableLiveData()
    }

    fun resetPictureData(index: Int) {
        pages[index] = 1
        loadPictureData(index)
    }

    fun upPictureData(index: Int) {
        pages[index]++
        loadPictureData(index, true)
    }

    private fun loadPictureData(index: Int, isAdd: Boolean = false) {
        val url = when (pages[index]) {
            1 -> baseData.value?.get(index)?.get("url").toString()
            else -> baseData.value?.get(index)?.get("url").toString() + "index_${pages[index]}.html"
        }

//        val maxImgCount = if (index == 0) 24 else 18
        val maxImgCount = 18

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
                message.what = when (isAdd) {
                    true -> index + SPEED
                    else -> index
                }
                message.obj = list
                handler.sendMessage(message)
            }, {})
    }
}