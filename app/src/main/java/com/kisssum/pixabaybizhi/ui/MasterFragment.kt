package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.adpater.MasterAdpater
import com.kisssum.pixabaybizhi.databinding.FragmentMasterBinding
import com.kisssum.pixabaybizhi.state.MasterViewModel

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
    private lateinit var viewModel: MasterViewModel
    private var adpater: MasterAdpater? = null

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

        initViewModel()
        initSearch()
        initList()
        initRefresh()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            AndroidViewModelFactory(requireActivity().application))
            .get(MasterViewModel::class.java)

        viewModel.getData().observe(requireActivity()) {
            adpater?.setData(it)
        }
    }

    private fun initSearch() {
        binding.masterSearch.searchBorder.setOnClickListener {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun initList() {
        binding.masterRefresh.list.apply {
            this.layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

            if (adpater == null) {
                adpater = MasterAdpater(requireContext(), 0)
                viewModel.getData().value?.let { adpater?.setData(it) }
            }

            this.adapter = adpater
        }
    }

    private fun initRefresh() {
        binding.masterRefresh.smartRefresh.apply {
            setOnRefreshListener {
                viewModel.reLoad()
                finishRefresh()
            }

            setOnLoadMoreListener {
                viewModel.loadData(upgrad = true)
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