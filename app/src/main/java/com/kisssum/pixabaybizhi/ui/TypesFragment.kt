package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.adpater.TypesBaseAdpater
import com.kisssum.pixabaybizhi.databinding.FragmentTypesBinding
import com.kisssum.pixabaybizhi.state.TypesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TypesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentTypesBinding
    private lateinit var viewModel: TypesViewModel
    private var baseAdpater: TypesBaseAdpater? = null

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
        binding = FragmentTypesBinding.inflate(inflater)
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
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
            .get(TypesViewModel::class.java)

        viewModel.getBaseData().observe(requireActivity()) {
            baseAdpater?.setData(it)
        }
    }

    private fun initSearch() {
        binding.typesSearch.searchBorder.setOnClickListener {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun initList() {
        binding.typesList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            if (baseAdpater == null) {
                baseAdpater = TypesBaseAdpater(requireContext(), viewModel)
                viewModel.getBaseData().value?.let { baseAdpater?.setData(it) }
            }

            this.adapter = baseAdpater
        }
    }

    private fun initRefresh() {
        binding.typesRefresh.apply {
            setOnRefreshListener {
                viewModel.loadBaseData()
                finishRefresh()
            }

            setOnLoadMoreListener { finishLoadMore() }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TypesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TypesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}