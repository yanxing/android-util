package com.yanxing.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.provider.MediaStore
import android.view.SurfaceHolder
import android.view.View

import com.yanxing.base.BaseActivity
import com.yanxing.util.CommonUtil
import com.yanxing.util.FileUtil

import java.io.File

import kotlinx.android.synthetic.main.activity_surfaceview_media_player.*


/**
 * SurfaceView+MediaPlayer播放视频
 * Created by lishuangxiang on 2016/10/10.
 */

class SurfaceViewMediaPlayerActivity : BaseActivity(), SurfaceHolder.Callback {

    private lateinit var mSurfaceHolder: SurfaceHolder
    private lateinit var mMediaPlayer: MediaPlayer
    var URL = FileUtil.getStoragePath() + "DCIM/Camera/11.mp4"

    override fun surfaceCreated(holder: SurfaceHolder) {
        val file = File(URL)
        if (!file.exists()) {
            showToast(getString(R.string.no_found_file))
        }
        //必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer.setDisplay(mSurfaceHolder)
        try {
            mMediaPlayer.setDataSource(URL)
            mMediaPlayer.prepare()
            val bitmap = CommonUtil.getVideoThumbnail(URL, CommonUtil.getScreenDisplay(this)!!.width, surface.height, MediaStore.Images.Thumbnails.MINI_KIND)
            firstFrame.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        play.setOnClickListener {
            firstFrame.visibility = View.GONE
            mMediaPlayer.start()
        }
        pause.setOnClickListener {
            mMediaPlayer.pause()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_surfaceview_media_player
    }

    override fun afterInstanceView() {
        mSurfaceHolder = surface.holder
        mSurfaceHolder.addCallback(this)
    }
}
