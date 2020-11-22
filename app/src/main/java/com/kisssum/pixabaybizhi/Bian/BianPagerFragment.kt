package com.kisssum.pixabaybizhi.Bian

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kisssum.pixabaybizhi.databinding.FragmentBianPagerBinding
import org.jsoup.Jsoup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BianAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BianAllFragment(val tag: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val BIAN_ALL = 0
    val BIAN_FENGJING = 1
    val BIAN_MEIYU = 2
    val BIAN_YOUXI = 3
    val BIAN_DONGMAN = 4
    val BIAN_YINGSHI = 5
    val BIAN_MINGXING = 6
    val BIAN_QICHE = 7
    val BIAN_DONGWU = 8
    val BIAN_RENWU = 9
    val BIAN_NEISHI = 10
    val BIAN_ZONGJIAO = 11
    val BIAN_BEIJING = 12

    private lateinit var binding: FragmentBianPagerBinding
    private var adpater: BianPagerAdpater? = null
    private lateinit var handler: Handler
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBianPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHandler()
        initRecyclerView()
        initRefreshLayout()
    }

    private fun initHandler() {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val list = msg.obj as ArrayList<String>
                when (msg.what) {
                    1 -> adpater?.addData(list)
                    2 -> adpater?.setData(list)
                    else -> ""
                }
            }
        }
    }

    private fun initRefreshLayout() {
        binding.smartRefreshLayout.setOnRefreshListener {
            getImgUrl(page++)
            binding.smartRefreshLayout.finishRefresh()
        }

        binding.smartRefreshLayout.setOnLoadMoreListener {
            getImgUrl(page++, true)
            binding.smartRefreshLayout.finishLoadMore()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.let {
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            adpater = BianPagerAdpater(requireContext())
            getImgUrl(page++)

            it.adapter = adpater
        }
    }

    private fun getImgUrl(page: Int, up: Boolean = false) {
        val type = arrayOf(
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
        val url = "${baseUrl}/${type[tag]}" + when (page) {
            1 -> "index.html"
            else -> "index_$page.html"
        }

        Thread {
            try {
                val doc = Jsoup.connect(url).get()
                val es = when {
                    (page == 1 && tag == 0) -> doc.select("#main > div.slist > ul > li > a > span > img")
                    else -> doc.select("#main > div.slist > ul > li > a > img")
                }

                val list = arrayListOf<String>()
                for (e in es)
                    list.add(baseUrl + e.attr("src"))

                val message = Message.obtain()
                message.obj = list
                if (up) message.what = 1 else message.what = 2
                handler.sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BianAllFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BianAllFragment(0).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}