package com.kisssum.pixabaybizhi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var backTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // 初始化Glide
        Glide.get(this)
        initUi()
    }

    private fun initNavigationView() {
        binding?.navigationView?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Item_about -> {
                    Toast.makeText(applicationContext, "版本号", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }

    private fun initUi() {
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initNavigationView()
    }

    override fun onBackPressed() {
        findNavController(R.id.fragment).let {
            when (it.currentDestination!!.id) {
                R.id.homeFragment -> {
                    if (backTime + 2000 < System.currentTimeMillis()) {
                        backTime = System.currentTimeMillis()

                        val toast = Toast.makeText(this, "在按一次退出程序", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    } else
                        super.onBackPressed()
                }
                else -> it.popBackStack()
            }
        }
    }
}