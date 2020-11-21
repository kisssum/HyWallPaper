package com.kisssum.pixabaybizhi.Bian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.FragmentBianMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BianFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BianFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentBianMainBinding

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
        binding = FragmentBianMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.let {
            it.setNavigationOnClickListener {
                requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
                    .openDrawer(GravityCompat.START)
            }
        }

        binding.viewPage.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 13
            override fun createFragment(position: Int) = BianAllFragment(position)
        }

        TabLayoutMediator(binding.tablayout, binding.viewPage) { tab: TabLayout.Tab, i: Int ->
            tab.text = when (i) {
                0 -> "全部"
                1 -> "风景"
                2 -> "美女"
                3 -> "游戏"
                4 -> "动漫"
                5 -> "影视"
                6 -> "明星"
                7 -> "汽车"
                8 -> "动物"
                9 -> "人物"
                10 -> "美食"
                11 -> "宗教"
                12 -> "背景"
                else -> "其它"
            }
        }.attach()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BianFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BianFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}