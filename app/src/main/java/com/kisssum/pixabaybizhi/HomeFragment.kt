package com.kisssum.pixabaybizhi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kisssum.pixabaybizhi.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHomeBinding
    private var adpater: RvAdpater? = null
    private var viewModel: MyViewModel? = null

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
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolBar()
        initSwipeRefresh()
        initRecyclerView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(activity?.application!!)
        ).get(MyViewModel::class.java)
    }

    private fun initSwipeRefresh() {
        binding.smartRefreshLayout.setOnRefreshListener {
            viewModel?.getJson()
            binding.smartRefreshLayout.finishRefresh()
        }

        binding.smartRefreshLayout.setOnLoadMoreListener {
            viewModel?.getJson(true)
            binding.smartRefreshLayout.finishLoadMore()
        }
    }

    private fun initRecyclerView() {
        // 观察者(layoutManager)
//        viewModel?.getItemLayoutManager()?.observe(viewLifecycleOwner) {
//            when (it) {
//                0 -> binding.recyclerView.layoutManager =
//                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                1 -> binding.recyclerView.layoutManager = LinearLayoutManager(context)
//                else -> ""
//            }
//        }

        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // 观察者(adpater)
        viewModel?.getData()?.observe(viewLifecycleOwner) {
            adpater?.setData(it)
        }

        // 添加数据
        if (adpater?.itemCount == null) {
            adpater = RvAdpater(activity?.applicationContext!!)
            viewModel?.getJson()
        }

        binding.recyclerView.adapter = adpater
    }

    private fun initToolBar() {
        binding.toolBar.let {
            // 导航
            it.setNavigationOnClickListener {
                activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
                    ?.openDrawer(GravityCompat.START)
            }

            // 搜索
            val searchView = it.findViewById<SearchView>(R.id.Item_search)
            searchView.let {
                it.isSubmitButtonEnabled = true
                it.queryHint = "搜索"

                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query.let {
                            viewModel?.let {
                                it.getSearchQ()?.value = searchView.query.toString()
                                it.getJson()
                            }
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?) = true
                })
            }

            // 项目
            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_refresh -> {
                        viewModel?.getJson()
                        true
                    }
//                    R.id.Item_change -> {
//                        val v = viewModel?.getItemLayoutManager()
//                        when (v?.value) {
//                            0 -> v.value = 1
//                            1 -> v.value = 0
//                            else -> v?.value = 0
//                        }
//                        true
//                    }
                    else -> true
                }
            }
        }
    }
}