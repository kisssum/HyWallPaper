package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import com.kisssum.pixabaybizhi.adpater.TypesPagerListAdpater
import com.kisssum.pixabaybizhi.databinding.ModelListBinding
import com.kisssum.pixabaybizhi.state.TypesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BZ36PagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TypesPagerListFragment(private val typeIndex: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: ModelListBinding
    private lateinit var listAdpater: TypesPagerListAdpater
    private lateinit var viewModel: TypesViewModel

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
        binding = ModelListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initList()
        initRefresh()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            AndroidViewModelFactory(requireActivity().application)
        ).get(TypesViewModel::class.java)


        viewModel.getPictureData(typeIndex).observe(requireActivity()) {
            listAdpater = TypesPagerListAdpater(requireContext(), typeIndex)
            listAdpater.setData(it)
        }
    }

    private fun initList() {
        binding.list.let {
            it.layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

            it.adapter = listAdpater
        }
    }

    private fun initRefresh() {
        binding.smartRefresh.let {
            it.setOnRefreshListener {
                viewModel.resetPictureData(typeIndex)
                it.finishRefresh()
            }

            it.setOnLoadMoreListener {
                viewModel.upPictureData(typeIndex)
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
            TypesPagerListFragment(0).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}