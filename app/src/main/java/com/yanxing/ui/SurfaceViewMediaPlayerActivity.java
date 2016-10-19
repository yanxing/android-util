package com.yanxing.ui;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.util.FileUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * SurfaceView+MediaPlayer播放视频
 * Created by lishuangxiang on 2016/10/10.
 */

public class SurfaceViewMediaPlayerActivity extends BaseActivity
        implements SurfaceHolder.Callback {

    @BindView(R.id.surface)
    SurfaceView mSurface;

    @BindView(R.id.first_frame)
    ImageView mFirstFrame;

    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;

    public static String URL=FileUtil.getStoragePath()+"DCIM/Camera/11.mp4";

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        File file=new File(URL);
        if (!file.exists()){
            showToast(getString(R.string.no_found_file));
        }
        //必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        mMediaPlayer=new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mMediaPlayer.setDataSource(URL);
            mMediaPlayer.prepare();
            Bitmap bitmap=CommonUtil.getVideoThumbnail(URL,CommonUtil.getScreenDisplay(this).getWidth()
                    ,mSurface.getHeight(), MediaStore.Images.Thumbnails.MINI_KIND);
            mFirstFrame.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_surfaceview_media_player;
    }

    @Override
    protected void afterInstanceView() {
        mSurfaceHolder=mSurface.getHolder();
        mSurfaceHolder.addCallback(this);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @OnClick({R.id.play, R.id.pause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                mFirstFrame.setVisibility(View.GONE);
                mMediaPlayer.start();
                break;
            case R.id.pause:
                mMediaPlayer.pause();
                break;
        }
    }
}
