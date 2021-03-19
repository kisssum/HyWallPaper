package com.kisssum.pixabaybizhi.state

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Handler
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import rxhttp.wrapper.param.RxHttp
import java.util.*

class ToolViewModel(application: Application) : AndroidViewModel(application) {
    private val SAVE_OK = 0
    private val SAVE_FAIL = 1
    private val SET_WALLPAPER_OK = 2
    private val SET_WALLPAPER_FAIL = 3
    private val handler = Handler() {
        when (it.what) {
            SAVE_OK -> {
                showToast("已下载")
                true
            }
            SAVE_FAIL -> {
                showToast("下载失败")
                true
            }
            SET_WALLPAPER_OK -> {
                showToast("已设为壁纸")
                true
            }
            SET_WALLPAPER_FAIL -> {
                showToast("设置壁纸失败")
                true
            }
            else -> true
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show()
    }

    fun setWallpaer(url: String) {
        RxHttp.get(url)
            .asBitmap<Bitmap>()
            .subscribe(
                {
                    WallpaperManager.getInstance(getApplication()).setBitmap(it)
                    handler.sendEmptyMessage(SET_WALLPAPER_OK)
                },
                { handler.sendEmptyMessage(SET_WALLPAPER_FAIL) }
            )
    }

    fun downLoad(url: String) {
        showToast("开始下载")

        RxHttp.get(url)
            .asBitmap<Bitmap>()
            .subscribe(
                {
                    MediaStore.Images.Media.insertImage(
                        getApplication<Application>().contentResolver,
                        it,
                        UUID.randomUUID().toString(),
                        "drawing"
                    )

                    handler.sendEmptyMessage(SAVE_OK)
                },
                { handler.sendEmptyMessage(SAVE_FAIL) }
            )
    }
}