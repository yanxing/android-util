package com.yanxing.dialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import java.io.File;

/**
 * 选择拍照或者选择图片
 * Created by lishuangxiang on 2016/9/6.
 */
public class SelectPhotoActivity extends FragmentActivity implements View.OnClickListener {

    private PhotoParam mPhotoParam;

    private static final int TAKE_PHOTO = 1;
    private static final int FROM_PICTURE = 2;
    private static final int CUT_PHOTO = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mPhotoParam = bundle.getParcelable("photoParam");
        TextView takePhoto = (TextView) findViewById(R.id.take_photo);
        TextView fromPicture = (TextView) findViewById(R.id.from_picture);
        takePhoto.setOnClickListener(this);
        fromPicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.take_photo) {
            takePhoto();
        } else if (v.getId() == R.id.from_picture) {
            selectPicture();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mPhotoParam.getPath(), mPhotoParam.getName());
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 从图库中选择图片并裁剪，与拍照并裁剪配置crop不同，不设置不保存图片
     */
    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (mPhotoParam.isCut()){
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", mPhotoParam.getOutputX());
            intent.putExtra("outputY", mPhotoParam.getOutputY());
            intent.putExtra("scale", true);
        }else {
            intent.putExtra("crop", "false");
        }
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        File file = new File(mPhotoParam.getPath(), mPhotoParam.getName());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, FROM_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File file = new File(mPhotoParam.getPath(), mPhotoParam.getName());
            //拍照返回
            if (requestCode == TAKE_PHOTO) {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(file)));
                cutPhoto(Uri.fromFile(file));
                //裁剪返回
            } else if (requestCode == CUT_PHOTO) {
                setResult(RESULT_OK);
                finish();
            } else if (requestCode == FROM_PICTURE) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    /**
     * 裁剪图片
     */
    public void cutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //进行裁剪
        if (mPhotoParam.isCut()) {
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", mPhotoParam.getOutputX());
            intent.putExtra("outputY", mPhotoParam.getOutputY());
            intent.putExtra("scale", true);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CUT_PHOTO);
    }

    /**
     * 重绘使本activity宽度占据手机屏幕1/4
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //获取手机屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //改变activity尺寸
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = dm.widthPixels * 3 / 4;
        getWindowManager().updateViewLayout(view, lp);
    }

    /**
     * 点击屏幕其他地方，对话框消失
     */
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

}
