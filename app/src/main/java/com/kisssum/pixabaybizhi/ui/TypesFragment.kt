package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.adpater.MasterAdpater
import com.kisssum.pixabaybizhi.adpater.TypesAdpater
import com.kisssum.pixabaybizhi.databinding.FragmentTypesBinding

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
    private var adpater: TypesAdpater? = null

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

        binding.typesSearch.searchBorder.setOnClickListener {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.typesList.apply {
            this.list.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            if (adpater == null) {
                adpater = TypesAdpater(requireContext())
//                adpater?.getImgUrl()
            }

            this.list.adapter = adpater
            this.smartRefresh.apply {
                setOnRefreshListener {
//                    adpater?.getImgUrl()
                    finishRefresh()
                }

                setOnLoadMoreListener {
//                    adpater?.getImgUrl(upgrad = true)
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