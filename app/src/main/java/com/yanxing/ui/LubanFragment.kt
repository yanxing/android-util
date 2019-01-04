package com.yanxing.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider

import com.yanxing.base.BaseFragment
import com.yanxing.util.ConstantValue
import com.yanxing.util.FileUtil
import com.yanxing.util.LogUtil

import java.io.File

import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener

import android.app.Activity.RESULT_OK
import kotlinx.android.synthetic.main.fragment_lu_ban.*

/**
 * Created by 李双祥 on 2017/1/12.
 */
class LubanFragment : BaseFragment() {

    private var mPhotoName = "y.jpg"
    //和清单文件中provider一致
    private val AUTHORITY = "com.yanxing.ui.provider"
    private val TAKE_PHOTO = 100

    override fun getLayoutResID(): Int {
        return R.layout.fragment_lu_ban
    }

    override fun afterInstanceView() {
        button.setOnClickListener {
            takePhoto()
        }
    }

    /**
     * 拍照
     */
    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val tempPhoto = System.currentTimeMillis().toString() + ".png"
        mPhotoName = tempPhoto
        //file文件夹，和filepaths.xml中目录符合，或者是其子文件夹
        val file = File(FileUtil.getStoragePath() + ConstantValue.DCIM_IMAGE, tempPhoto)
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            activity?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    FileProvider.getUriForFile(activity!!, AUTHORITY, file)))
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity!!, AUTHORITY, file))
        } else {
            activity?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }
        startActivityForResult(intent, TAKE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        yaSuo()
        if (resultCode == RESULT_OK) {
            //拍照返回
            if (requestCode == TAKE_PHOTO) {
                yaSuo()
            }
        }
    }

    fun yaSuo() {
        val file = File(FileUtil.getStoragePath() + ConstantValue.DCIM_IMAGE + mPhotoName)
        val tip = "压缩前文件大小为：" + file.length() / 1024 + "KB\n路径为：" + file.absolutePath
        Luban.with(activity)
                .load(file) // 传人要压缩的图片列表
                .ignoreBy(100)// 忽略不压缩图片的大小
                .setTargetDir(file.absolutePath)// 设置压缩后文件存储位置
                .setCompressListener(object : OnCompressListener { //设置回调
                    override fun onStart() {
                        showLoadingDialog("压缩中...")
                    }

                    override fun onSuccess(file: File) {
                        val tag = (tip + "\n" +
                                "压缩后文件大小： " + file.length() / 1024 + "KB"
                                + "\n路径为：" + file.absolutePath)
                        LogUtil.d("LubanFragment", tag)
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        image.setImageBitmap(bitmap)
                        text.text = tag
                        dismissLoadingDialog()
                    }

                    override fun onError(e: Throwable) {}
                }).launch()    //启动压缩
    }
}
