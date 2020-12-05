package com.kisssum.pixabaybizhi.NavHome.BZ36

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kisssum.pixabaybizhi.databinding.BasePagerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BZ36PagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BZ36PagerFragment(private val typeIndex: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: BasePagerBinding
    private var adpater: BZ36PagerAdpater? = null

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
        binding = BasePagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.let {
            it.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

            if (adpater == null) {
                adpater = BZ36PagerAdpater(requireContext(), typeIndex)
                adpater?.getImgUrl()
            }

            it.adapter = adpater
        }

        binding.smartRefreshLayout.let {
            it.setOnRefreshListener {
                adpater?.getImgUrl()
                it.finishRefresh()
            }

            it.setOnLoadMoreListener {
                adpater?.getImgUrl(upgrad = true)
                it.finishLoadMore()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BZ36PagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BZ36PagerFragment(0).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}