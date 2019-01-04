package com.yanxing.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView

import com.yanxing.base.BaseActivity
import com.yanxing.dialog.PhotoParam
import com.yanxing.dialog.SelectPhotoActivity
import com.yanxing.util.ConstantValue
import com.yanxing.util.FileUtil
import com.yanxing.util.LogUtil
import com.yanxing.util.PermissionUtil

import java.io.File

import kotlinx.android.synthetic.main.activity_web_open_photo.*

/**
 * webview中打开相册选择照片返回，拍照返回
 *
 * @author 李双祥 on 2017/11/16.
 */
class WebOpenPhotoActivity : BaseActivity() {


    private var mValueCallback: ValueCallback<Array<Uri>>? = null
    private val QUESTION = 1
    private val QUESTION_IMAGE_CODE = 2

    override fun getLayoutResID(): Int {
        return R.layout.activity_web_open_photo
    }

    override fun afterInstanceView() {
        requestPermission()
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.domStorageEnabled = true
        webview.webChromeClient = object : WebChromeClient() {

            override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: WebChromeClient.FileChooserParams): Boolean {
                mValueCallback = filePathCallback
                val currentTimeDialog = System.currentTimeMillis()
                val intent = Intent(applicationContext, SelectPhotoActivity::class.java)
                val photoParam = PhotoParam()
                photoParam.name = currentTimeDialog.toString() + ".png"
                photoParam.path = FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE
                val bundle = Bundle()
                bundle.putParcelable("photoParam", photoParam)
                intent.putExtras(bundle)
                startActivityForResult(intent, QUESTION_IMAGE_CODE)
                return true
            }
        }
        webview.loadUrl("file:///android_asset/index.html")
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        webview.addJavascriptInterface(Photo(), "photo")

    }

    fun requestPermission() {
        if (PermissionUtil.findNeedRequestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS)).size > 0) {
            PermissionUtil.requestPermission(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS), QUESTION)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == QUESTION) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    for (permission in permissions) {
                        PermissionUtil.getPermissionTip(permission)
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private inner class Photo {

        /**
         * 打开图库
         */
        @JavascriptInterface
        fun selectPhoto() {
            val currentTimeDialog = System.currentTimeMillis()
            val intent = Intent(applicationContext, SelectPhotoActivity::class.java)
            val photoParam = PhotoParam()
            photoParam.name = currentTimeDialog.toString() + ".png"
            photoParam.path = FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE
            val bundle = Bundle()
            bundle.putParcelable("photoParam", photoParam)
            intent.putExtras(bundle)
            startActivityForResult(intent, QUESTION_IMAGE_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_IMAGE_CODE) {
                val path = data?.getStringExtra("image")
                val uri = Uri.fromFile(File(path))
                mValueCallback!!.onReceiveValue(arrayOf(uri))
            }
        }
    }

    /**
     * 调用js方法
     * @param photo
     */
    fun javaScript(photo: String) {
        var photo = photo
        photo = "yanxing"
        val version = Build.VERSION.SDK_INT
        if (version < 19) {
            webview.loadUrl("javascript:onPhotoListener('$photo')")
        } else {
            webview.evaluateJavascript("javascript:onPhotoListener('$photo')") { value -> LogUtil.d("WebOpenPhotoActivity", value) }
        }
    }
}
