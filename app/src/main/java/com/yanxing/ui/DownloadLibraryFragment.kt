package com.yanxing.ui

import com.yanxing.base.BaseFragment
import com.yanxing.downloadlibrary.DownloadConfiguration
import com.yanxing.downloadlibrary.DownloadUtils
import com.yanxing.downloadlibrary.SimpleDownloadListener
import com.yanxing.util.ConstantValue
import com.yanxing.util.FileUtil
import com.yanxing.util.LogUtil

import java.io.File

import kotlinx.android.synthetic.main.fragment_download_library.*

/**
 * downloadlibrary测试
 * Created by lishuangxiang on 2016/9/21.
 */
class DownloadLibraryFragment : BaseFragment() {

    //true停止下载
    private var isStopDownload: Boolean = false

    override fun getLayoutResID(): Int {
        return R.layout.fragment_download_library
    }

    override fun afterInstanceView() {
        val file = File(FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE)
        val downloadConfiguration = DownloadConfiguration.Builder()
                .savePath(file)
                .setLog(true)
                .builder()
        DownloadUtils.getInstance().init(downloadConfiguration)
        val progressInt = DownloadUtils.getInstance().getDownloadProgressByUrl(activity, url.text.toString())
        if (progressInt == -1) {
            showToast(getString(R.string.file_delete_re_down))
        } else if (progressInt >= 0) {
            progress.text = progress.toString() + "%"
            progressBar.max = 100
            progressBar.progress = progressInt
        }
        start.setOnClickListener {
            if (isStopDownload) {
                DownloadUtils.getInstance().resumeDownload()
            } else {
                download()
            }
        }
        stop.setOnClickListener {
            DownloadUtils.getInstance().stopDownload()
            isStopDownload = true
        }
    }

    /**
     * 下载
     */
    fun download() {
        DownloadUtils.getInstance().startDownload(activity, url.text.toString(), object : SimpleDownloadListener() {
            override fun onStart() {
                LogUtil.d("DownloadUtils", getString(R.string.start_download1))
            }

            override fun onProgress(progressInt: Int, totalSize: Int) {
                progressBar.max = totalSize
                progressBar.progress = progressInt
                progress.text = (progressInt * 1.0 / totalSize * 100).toInt().toString() + "%"
            }

            override fun onError(state: Int, message: String) {
                super.onError(state, message)
                if (state == 404) {
                    showToast(getString(R.string.file_exist))
                } else {
                    showToast(getString(R.string.error_code) + state + "  " + message)
                }
            }

            override fun onFinish() {
                LogUtil.d("DownloadUtils", getString(R.string.download_finish))
            }
        })
    }
}
