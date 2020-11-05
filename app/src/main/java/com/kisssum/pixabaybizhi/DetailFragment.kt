package com.kisssum.pixabaybizhi

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.databinding.FragmentDetailBinding
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentDetailBinding

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
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        val webUrl = arguments?.getString("webformatURL")
        val lastUrl = arguments?.getString("largeImageURL")

        // 加载图片
        Glide.with(context?.applicationContext!!)
            .load(webUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView)

        binding.toolbar.let {
            it.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)

            it.setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fragment).popBackStack()
            }
        }

        binding.btnDownload.setOnClickListener {
            downLoadVolley(lastUrl!!)
        }
    }

    private fun downLoadVolley(url: String) {
        val queue = Volley.newRequestQueue(context)

        val imageRequest = ImageRequest(
            url,
            {
                MediaStore.Images.Media.insertImage(
                    context?.contentResolver,
                    it,
                    UUID.randomUUID().toString() + ".png",
                    "drawing"
                )

                Toast.makeText(activity, "下载成功", Toast.LENGTH_SHORT).show()
            }, 0, 0, Bitmap.Config.RGB_565,
            { Toast.makeText(activity, "下载失败", Toast.LENGTH_SHORT).show() })

        queue.add(imageRequest)
    }
}
