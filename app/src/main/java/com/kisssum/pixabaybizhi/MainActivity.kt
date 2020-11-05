package com.kisssum.pixabaybizhi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        Glide.get(this)
        initUi()
        initNavigationView()
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
    }
}