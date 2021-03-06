package com.kisssum.pixabaybizhi.NavHome.Pixabay

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.random.Random

class PixabayViewModel(application: Application) : AndroidViewModel(application) {
    private var date: MutableLiveData<List<Map<String, String>>>? = null

    private var searchQ: MutableLiveData<String>? = null
    private var page: MutableLiveData<Int>? = null
    private val MAX_PAGE = 25

    fun getData() = when (date == null) {
        false -> date
        else -> {
            date = MutableLiveData()
            date
        }
    }

    fun getSearchQ() = when (searchQ == null) {
        false -> searchQ
        else -> {
            searchQ = MutableLiveData()
            searchQ?.value = ""
            searchQ
        }
    }

    private fun getPage() = when (page == null) {
        false -> page
        else -> {
            page = MutableLiveData()
            page?.value = Random.nextInt(1, MAX_PAGE)
            page
        }
    }

    fun getJson(add: Boolean = false) {
        val txt = getSearchQ()?.value.toString()
        val q = txt.split(" ").joinToString("+")

        getPage()?.value = Random.nextInt(1, MAX_PAGE)
        val url =
            "https://pixabay.com/api/?key=18926294-81b1d0c1dcaa14063b9be84a6&q=${q}&page=${getPage()?.value.toString()}"

        val queue = Volley.newRequestQueue(getApplication())

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                val date = parseJson(it)
                when (date.isEmpty()) {
                    false -> when (add) {
                        true -> getData()?.value = getData()?.value?.plus(date)
                        else -> getData()?.value = date
                    }
                    else -> Toast.makeText(getApplication(), "?????????????????????", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(stringRequest)
    }

    private fun parseJson(str: String): List<Map<String, String>> {
        val list = ArrayList<Map<String, String>>()
        var map: Map<String, String>

        // ????????????
        val jsonObject = JSONObject(str)
        val hitsArray = jsonObject.getJSONArray("hits")

        for (i in 0 until hitsArray.length()) {
            val obj = hitsArray.getJSONObject(i)
            map = HashMap()
            map["id"] = obj.getInt("id").toString()
            map["webformatURL"] = obj.getString("webformatURL")
            map["webformatHeight"] = obj.getInt("webformatHeight").toString()
            map["largeImageURL"] = obj.getString("largeImageURL")
            map["user"] = obj.getString("user")
            map["favorites"] = obj.getInt("favorites").toString()
            map["likes"] = obj.getInt("likes").toString()

            list.add(map)
        }

        return list
    }
}