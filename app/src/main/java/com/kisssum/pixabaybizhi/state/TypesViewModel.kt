package com.kisssum.pixabaybizhi.state

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
    private val allData = MutableLiveData<ArrayList<Map<String, String>>>()
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
    private val RESET_ALLDATA = 0
    private val RESET_MVDATA = 1
    private val RESET_MXDATA = 2
    private val RESET_YSDATA = 3
    private val RESET_DMDATA = 4
    private val RESET_KTDATA = 5
    private val RESET_QCDATA = 6
    private val RESET_AQDATA = 7
    private val RESET_YXDATA = 8
    private val RESET_TYDATA = 9
    private val RESET_CMDATA = 10
    private val RESET_QFJDATA = 11
    private val RESET_PPDATA = 12
    private val RESET_KADATA = 13
    private val RESET_JRDATA = 14
    private val RESET_JZDATA = 15
    private val RESET_ZWDATA = 16
    private val RESET_DWDATA = 17
    private val RESET_CYDATA = 18
    private val RESET_QTDATA = 19
    private val ADD_ALLDATA = RESET_ALLDATA + SPEED
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
        1, 1, 1, 1)

    init {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val list = msg.obj as ArrayList<Map<String, String>>

                when (msg.what) {
                    RESET_BASEDATA -> baseData.value = list

                    RESET_ALLDATA -> allData.value = list
                    ADD_ALLDATA -> mergeData(allData, list)

                    RESET_MVDATA -> mvData.value = list
                    ADD_MVDATA -> mergeData(mvData, list)

                    RESET_MXDATA -> mxData.value = list
                    ADD_MXDATA -> mergeData(mxData, list)

                    RESET_YSDATA -> ysData.value = list
                    ADD_YSDATA -> mergeData(ysData, list)

                    RESET_DMDATA -> dmData.value = list
                    ADD_DMDATA -> mergeData(dmData, list)

                    RESET_KTDATA -> ktData.value = list
                    ADD_KTDATA -> mergeData(ktData, list)

                    RESET_QCDATA -> qcData.value = list
                    ADD_QCDATA -> mergeData(qcData, list)

                    RESET_AQDATA -> aqData.value = list
                    ADD_AQDATA -> mergeData(aqData, list)

                    RESET_YXDATA -> yxData.value = list
                    ADD_YXDATA -> mergeData(yxData, list)

                    RESET_TYDATA -> tyData.value = list
                    ADD_TYDATA -> mergeData(tyData, list)

                    RESET_CMDATA -> cmData.value = list
                    ADD_CMDATA -> mergeData(cmData, list)

                    RESET_QFJDATA -> qfjData.value = list
                    ADD_QFJDATA -> mergeData(qfjData, list)

                    RESET_PPDATA -> ppData.value = list
                    ADD_PPDATA -> mergeData(ppData, list)

                    RESET_KADATA -> kaData.value = list
                    ADD_KADATA -> mergeData(kaData, list)

                    RESET_JRDATA -> jrData.value = list
                    ADD_JRDATA -> mergeData(jrData, list)

                    RESET_JZDATA -> jzData.value = list
                    ADD_JZDATA -> mergeData(jzData, list)

                    RESET_ZWDATA -> zwData.value = list
                    ADD_ZWDATA -> mergeData(zwData, list)

                    RESET_DWDATA -> dwData.value = list
                    ADD_DWDATA -> mergeData(dwData, list)

                    RESET_CYDATA -> cyData.value = list
                    ADD_CYDATA -> mergeData(cyData, list)

                    RESET_QTDATA -> qtData.value = list
                    ADD_QTDATA -> mergeData(qtData, list)
                }
            }
        }

        loadBaseData()
    }

    private fun mergeData(
        firstData: MutableLiveData<ArrayList<Map<String, String>>>,
        lastData: ArrayList<Map<String, String>>,
    ) {
        val allData = ArrayList<Map<String, String>>()
        allData.addAll(firstData.value!!)
        allData.addAll(lastData)
        firstData.value = allData
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
        RESET_ALLDATA -> allData
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
        var url = when (index) {
            0 -> "https://www.3gbizhi.com/sjbz/"
            else -> baseData.value?.get(index - 1)?.get("url").toString()
        }

        if (pages[index] > 1)
            url += "index_${pages[index]}.html"

        val maxImgCount = if (index == 0) 24 else 18

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