package com.kisssum.pixabaybizhi.NavHome.BZ36

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.pixabaybizhi.NavHome.Bian.BianPagerFragment
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.FragmentBZ36MainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BZ36MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BZ36MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentBZ36MainBinding
    private val list = arrayOf(
        "全部",
//        "美女",
        "明星",
        "影视",
        "动漫",
        "卡通",
        "汽车",
        "爱情",
        "游戏",
        "体育",
        "车模",
        "风景",
        "品牌",
        "可爱",
        "节日",
        "建筑",
        "植物",
        "动物",
        "创意",
        "精美"
    )

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
    ): View {
        binding = FragmentBZ36MainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = list.size
            override fun createFragment(position: Int) = BZ36PagerFragment(position)
        }

        TabLayoutMediator(
            binding.tablayout,
            binding.viewpager
        ) { tab: TabLayout.Tab, i: Int -> tab.text = list[i] }.attach()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BZ36MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BZ36MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}