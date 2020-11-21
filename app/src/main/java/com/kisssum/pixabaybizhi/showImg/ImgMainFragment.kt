package com.kisssum.pixabaybizhi.showImg

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kisssum.pixabaybizhi.Pixabay.PixabayViewModel
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.FragmentImgMainBinding
import rxhttp.wrapper.param.RxHttp
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

class DetailFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentImgMainBinding
    private lateinit var handler: Handler
    private var viewModel: PixabayViewModel? = null
    val SAVE_OK = 0
    val SAVE_FAIL = 1

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
        binding = FragmentImgMainBinding.inflate(inflater)
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

        handler = Handler() {
            when (it.what) {
                SAVE_OK -> {
                    showToast("下载成功")
                    true
                }
                SAVE_FAIL -> {
                    showToast("下载失败")
                    true
                }
                else -> true
            }
        }

        when (requireArguments().getInt("type")) {
            1 -> {
                initViewModel()
                initPxiUi()
            }
            2 -> initBianUi()
            else -> ""
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(activity?.application!!)
        ).get(PixabayViewModel::class.java)
    }

    private fun initBianUi() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 1

            override fun createFragment(position: Int): Fragment {
                // 获取图片地址
                val url = requireArguments().getString("imgUrl")!!
                return ImageFragment(url)
            }
        }

        binding.toolbar.let {
            it.setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fragment).navigateUp()
            }

            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_download -> {
                        val url = requireArguments().getString("imgUrl")!!
                        downLoadDialog(url)
                        true
                    }
                    else -> true
                }
            }
        }

    }

    private fun initPxiUi() {
        // 传入的图片位置
        var index = requireArguments().getInt("indexImg")
        // 图片总数
        var size = viewModel?.getData()?.value?.size
        var flag = false

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = size!!

            override fun createFragment(position: Int): Fragment {
                // 获取图片地址
                val url = viewModel?.getData()?.value?.get(index)?.get("webformatURL")

                // 如果后面没有图片了则添加图片
                if (++index >= size!!) {
                    viewModel?.getJson(true)
                    size = viewModel?.getData()?.value?.size
                }

                // 更新显示数字(第一次初始化要-1显示)
                binding.toolbar.title = when (flag) {
                    true -> "${index - 1}/${size!!}"
                    else -> {
                        flag = !flag
                        "${index}/${size!!}"
                    }
                }

                return ImageFragment(url!!)
            }
        }

        binding.toolbar.let {
            it.setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fragment).navigateUp()
            }

            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_download -> {
                        if (index > 2) {
                            val url =
                                viewModel?.getData()?.value?.get(index - 2)?.get("largeImageURL")!!
                            downLoadDialog(url)
                        } else {
                            val url =
                                viewModel?.getData()?.value?.get(index - 2)?.get("largeImageURL")!!
                            downLoadDialog(url)
                        }

                        true
                    }
                    else -> true
                }
            }
        }
    }

    private fun downLoadDialog(url: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("下载图片")
            .setMessage("你确定要下载此图片吗?")
            .setCancelable(true)
            .setPositiveButton("确定") { dialogInterface: DialogInterface, i: Int ->
                downLoadImage(url)
            }
            .setNegativeButton("取消", null)
            .create()

        dialog.show()
    }

    private fun downLoadImage(url: String) {
        showToast("开始下载")

        RxHttp.get(url)
            .asBitmap<Bitmap>()
            .subscribe(
                {
                    saveImage(it)
                    handler.sendEmptyMessage(SAVE_OK)
                },
                { handler.sendEmptyMessage(SAVE_FAIL) }
            )
    }

    private fun showToast(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
    }

    private fun saveImage(bitmap: Bitmap) {
        MediaStore.Images.Media.insertImage(
            context?.contentResolver,
            bitmap,
            UUID.randomUUID().toString(),
            "drawing"
        )
    }
}