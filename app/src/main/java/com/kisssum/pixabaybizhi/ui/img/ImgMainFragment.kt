package com.kisssum.pixabaybizhi.ui.img

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.kisssum.pixabaybizhi.NavHome.Pixabay.PixabayViewModel
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.FragmentImgMainBinding
import com.kisssum.pixabaybizhi.state.ToolViewModel
import com.kisssum.pixabaybizhi.state.TypesViewModel
import org.jsoup.Jsoup


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImgMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ImgMainFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentImgMainBinding

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
            ImgMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.addFlags(FLAG_LAYOUT_NO_LIMITS)

        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.clearFlags(FLAG_LAYOUT_NO_LIMITS)

        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (requireArguments().getInt("type")) {
            1 -> initPxiUi()
            2 -> initBianUi()
            3 -> initTypes()
            else -> initBZ()
        }
    }

    private fun initTypes() {
        val DOWNLOAD = 1
        val SET_WALLPAPER = 2
        val TOOLBAR = 3

        val downloadViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ToolViewModel::class.java)

        val hBZ = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val url = msg.obj as String

                when (msg.what) {
                    DOWNLOAD -> downloadViewModel.downLoad(url)
                    SET_WALLPAPER -> downloadViewModel.setWallpaer(url)
                    TOOLBAR -> downLoadDialog(url)
                }
            }
        }

        val viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
            .get(TypesViewModel::class.java)

        val index = requireArguments().getInt("index", 0)
        var cposition = requireArguments().getInt("position", 0)

        binding.viewPager.apply {
            this.orientation = ViewPager2.ORIENTATION_VERTICAL

            this.adapter = object : FragmentStateAdapter(requireActivity()) {
                override fun getItemCount() = viewModel.getPictureData(index).value?.count()!!
                override fun createFragment(position: Int): Fragment {
                    // add picture
                    if (cposition >= viewModel.getPictureData(index).value?.size!! - 5) {
                        viewModel.upPictureData(index)
                    }

                    return ImageFragment(viewModel.getPictureData(index).value?.get(position)
                        ?.get("lazysrc2x")!!, 1)
                }
            }

            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    cposition = position

                    binding.imgPx.text = "@" +
                            viewModel.getPictureData(index).value?.get(cposition)?.get("px")
                }
            })

            this.setCurrentItem(requireArguments().getInt("position", 0), false)
        }

        binding.imgRightBar.apply {
            this.rDownLoad.setOnClickListener {
                val href =
                    viewModel.getPictureData(index).value?.get(cposition)?.get("href")

                Thread {
                    val doc = Jsoup.connect(href).get()
                    val url =
                        doc.select("body > div.showtitle > div.morew > a")
                            .attr("href")

                    val msg = Message()
                    msg.what = DOWNLOAD
                    msg.obj = url
                    hBZ.sendMessage(msg)
                }.start()
            }
        }

        binding.imgButton.button.setOnClickListener {
            val href =
                viewModel.getPictureData(index).value?.get(cposition)?.get("href")

            Thread {
                val doc = Jsoup.connect(href).get()
                val url =
                    doc.select("body > div.showtitle > div.morew > a")
                        .attr("href")

                val msg = Message()
                msg.what = SET_WALLPAPER
                msg.obj = url
                hBZ.sendMessage(msg)
            }.start()
        }

        binding.toolbar.let {
            it.setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
                requireActivity().window.clearFlags(FLAG_LAYOUT_NO_LIMITS)
            }

            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_More -> {
                        val href =
                            viewModel.getPictureData(index).value?.get(cposition)?.get("href")

                        Thread {
                            val doc = Jsoup.connect(href).get()
                            val url =
                                doc.select("body > div.showtitle > div.morew > a")
                                    .attr("href")

                            val message = Message()
                            message.what = TOOLBAR
                            message.obj = url
                            hBZ.sendMessage(message)
                        }.start()
                        true
                    }
                    else -> true
                }
            }
        }
    }

    private fun initBZ() {
        val hBZ = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val url = msg.obj as String
                downLoadDialog(url)
            }
        }

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 1
            override fun createFragment(position: Int) =
                ImageFragment(requireArguments().getString("lazysrc2x")!!, 1)
        }

        binding.toolbar.let {
            it.setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
            }

            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_More -> {
                        val href = requireArguments().getString("href")!!
                        Thread {
                            val doc = Jsoup.connect(href).get()
                            val url =
                                doc.select("body > div.showtitle > div.morew > a")
                                    .attr("href")

                            val message = Message.obtain()
                            message.obj = url
                            hBZ.sendMessage(message)
                        }.start()
                        true
                    }
                    else -> true
                }
            }
        }
    }

    private fun initBianUi() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 1
            override fun createFragment(position: Int) =
                ImageFragment(requireArguments().getString("imgUrl")!!)
        }

        binding.toolbar.let {
            it.setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
            }

            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_More -> {
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
        val viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(activity?.application!!)
        ).get(PixabayViewModel::class.java)

        // 传入的图片位置
        var index = requireArguments().getInt("indexImg")
        // 图片总数
        var size = viewModel.getData()?.value?.size
        var flag = false

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = size!!

            override fun createFragment(position: Int): Fragment {
                // 获取图片地址
                val url = viewModel.getData()?.value?.get(index)?.get("webformatURL")

                // 如果后面没有图片了则添加图片
                if (++index >= size!!) {
                    viewModel.getJson(true)
                    size = viewModel.getData()?.value?.size
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
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
            }

            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.Item_More -> {
                        val url =
                            viewModel.getData()?.value?.get(index - 1)?.get("largeImageURL")!!
                        downLoadDialog(url)
                        true
                    }
                    else -> true
                }
            }
        }
    }

    private fun downLoadDialog(url: String) {
        val downloadViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ToolViewModel::class.java)

        val items = arrayOf("收藏", "下载", "分享", "设为壁纸", "制作日签", "反馈图片问题")

        AlertDialog.Builder(requireContext())
            .setTitle("工具")
            .setItems(items) { dialogInterface: DialogInterface, i: Int ->
                when (i) {
                    1 -> {
                        downloadViewModel.downLoad(url)
                    }
                    3 -> {
                        downloadViewModel.setWallpaer(url)
                    }
                    else -> {
                    }
                }
            }
            .setNegativeButton("取消") { dialogInterface: DialogInterface, i: Int -> }
            .create()
            .show()
    }
}
