package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.adpater.TypesPagerListAdpater
import com.kisssum.pixabaybizhi.databinding.FragmentMasterBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MasterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MasterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMasterBinding
    private var adpater: TypesPagerListAdpater? = null

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
        binding = FragmentMasterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.masterSearch.searchBorder.setOnClickListener {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.masterRefresh.list.apply {
            this.layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

            if (adpater == null) {
                adpater = TypesPagerListAdpater(requireContext(), 0)
            }

            this.adapter = adpater
        }

        binding.masterRefresh.smartRefresh.apply {
            setOnRefreshListener {
                adpater?.reLoad()
                finishRefresh()
            }

            setOnLoadMoreListener {
                adpater?.loadData(upgrad = true)
                finishLoadMore()
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
         * @return A new instance of fragment MasterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MasterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}