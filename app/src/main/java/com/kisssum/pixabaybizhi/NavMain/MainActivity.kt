package com.kisssum.pixabaybizhi.NavMain

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化Glide
        Glide.get(this)

        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onBackPressed() {
        val list = arrayOf(R.id.homeFragment, R.id.bianMainFragment)

        findNavController(R.id.fragment_main).let {
            if (it.currentDestination!!.id in list)
                if (backTime + 2000 < System.currentTimeMillis()) {
                    backTime = System.currentTimeMillis()

                    val toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                } else {
                    super.onBackPressed()
                    finish()
                }
            else it.navigateUp()
        }
    }
}