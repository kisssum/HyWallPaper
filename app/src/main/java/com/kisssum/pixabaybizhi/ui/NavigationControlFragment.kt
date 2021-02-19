package com.kisssum.pixabaybizhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.FragmentNavigationControlBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavigationControlFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentNavigationControlBinding

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
        binding = FragmentNavigationControlBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            val nav = Navigation.findNavController(requireActivity(), R.id.fragment_home)

            when (it.itemId) {
                R.id.Item_master -> {
                    if (nav.currentDestination?.id != R.id.masterFragment) nav.navigate(R.id.masterFragment)
                }
                R.id.Item_types -> {
                    if (nav.currentDestination?.id != R.id.typesFragment) nav.navigate(R.id.typesFragment)
                }
                R.id.Item_bian -> {
                    if (nav.currentDestination?.id != R.id.bianMainFragment) nav.navigate(R.id.bianMainFragment)
                }
                R.id.Item_pix -> {
                    if (nav.currentDestination?.id != R.id.pixabayMainFragment) nav.navigate(R.id.pixabayMainFragment)
                }
                R.id.Item_me -> {
                    if (nav.currentDestination?.id != R.id.meFragment) nav.navigate(R.id.meFragment)
                }
            }

            true
        }
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
            NavigationControlFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}