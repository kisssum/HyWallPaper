package com.kisssum.pixabaybizhi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
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

        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initNavigationView()
    }

    private fun initNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Item_Pixabay -> {
                    val nav = Navigation.findNavController(this, R.id.fragment)

                    if (nav.currentDestination?.id != R.id.Item_Pixabay) {
                        nav.navigateUp()
                        nav.navigate(R.id.homeFragment)
                    }

                    true
                }
                R.id.Item_bian -> {
                    val nav = Navigation.findNavController(this, R.id.fragment)

                    if (nav.currentDestination?.id != R.id.Item_bian) {
                        nav.navigateUp()
                        nav.navigate(R.id.bianFragment)
                    }

                    true
                }
                R.id.Item_about -> {
                    Toast.makeText(applicationContext, "版本号", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }

    override fun onBackPressed() {
        findNavController(R.id.fragment).let {
            when (it.currentDestination!!.id) {
                R.id.homeFragment -> {
                    if (backTime + 2000 < System.currentTimeMillis()) {
                        backTime = System.currentTimeMillis()

                        val toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    } else
                        super.onBackPressed()
                }
                else -> it.navigateUp()
            }
        }
    }
}