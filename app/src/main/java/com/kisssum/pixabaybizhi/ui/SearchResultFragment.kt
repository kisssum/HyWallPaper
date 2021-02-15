package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.adpater.SearchResultAdpater
import com.kisssum.pixabaybizhi.databinding.FragmentSearchResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSearchResultBinding
    private var adpater: SearchResultAdpater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater)
        return binding.root
    }

    private var query = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            query = requireArguments().getString("query", "")

        binding.searchResultToolbar.apply {
            title = "关于“${query}”的图片"

            setNavigationOnClickListener {
                val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
                controller.popBackStack();
            }
        }

        binding.searchResultList.apply {
            this.list.layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

            if (adpater == null) {
                adpater = SearchResultAdpater(requireContext())
                adpater?.getQuery(query = query)
            }

            this.list.adapter = adpater
            this.smartRefresh.apply {
                setOnRefreshListener {
                    adpater?.getQuery(query = query)
                    finishRefresh()
                }

                setOnLoadMoreListener {
                    adpater?.getQuery(upgrad = true, query = query)
                    finishLoadMore()
                }
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
         * @return A new instance of fragment SearchResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}