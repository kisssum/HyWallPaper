package com.kisssum.pixabaybizhi.NavMain.showImg

import android.app.Application
import android.graphics.Bitmap
import android.os.Handler
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import rxhttp.wrapper.param.RxHttp
import java.util.*

class DownLoadViewModel(application: Application) : AndroidViewModel(application) {
    private val SAVE_OK = 0
    private val SAVE_FAIL = 1
    private val handler = Handler() {
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

    private fun showToast(str: String) {
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show()
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