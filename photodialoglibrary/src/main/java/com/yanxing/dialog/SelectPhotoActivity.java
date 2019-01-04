package com.yanxing.dialog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;

/**
 * 选择拍照或者选择图片
 * Created by lishuangxiang on 2016/9/6.
 */
public class SelectPhotoActivity extends FragmentActivity implements View.OnClickListener {

    private PhotoParam mPhotoParam;

    private static final int TAKE_PHOTO = 1;
    private static final int FROM_PICTURE = 2;
    private static final int CUT_PHOTO = 3;
    private String mTempPhoto;
    //和清单文件中provider一致
    private static final String AUTHORITY = "com.yanxing.ui.provider";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mPhotoParam = bundle.getParcelable("photoParam");
        File file = new File(mPhotoParam.getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
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
        mTempPhoto = System.currentTimeMillis() + ".png";
        //针对一些手机需要自己先创建文件夹
        //file文件夹，和filepaths.xml中目录符合，或者是其子文件夹
        File f = new File(mPhotoParam.getPath());
        if (!f.exists()) {
            f.mkdirs();
        }
        File file;
        //经过裁剪
        if (mPhotoParam.isCut()){
            file = new File(mPhotoParam.getPath(), mTempPhoto);
        }else {
            file = new File(mPhotoParam.getPath(), mPhotoParam.getName());
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    FileProvider.getUriForFile(this, AUTHORITY, file)));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, AUTHORITY, file));
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 从图库中选择图片
     */
    private void selectPicture() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        i.putExtra("return-data", true);
        startActivityForResult(i, FROM_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //拍照返回
            if (requestCode == TAKE_PHOTO) {
                //经过裁剪
                if (mPhotoParam.isCut()) {
                    //判读版本是否在7.0以上
                    if (Build.VERSION.SDK_INT >= 24) {
                        cutPhoto(getImageContentUri(new File(mPhotoParam.getPath(), mTempPhoto))
                                , Uri.fromFile(new File(mPhotoParam.getPath() + mPhotoParam.getName())));
                    } else {
                        cutPhoto(Uri.fromFile(new File(mPhotoParam.getPath(), mTempPhoto))
                                , Uri.fromFile(new File(mPhotoParam.getPath(), mPhotoParam.getName())));
                    }
                } else {
                    //不裁减
                    Intent intent=new Intent();
                    intent.putExtra("image", mPhotoParam.getPath() + mPhotoParam.getName());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            } else if (requestCode == CUT_PHOTO) {//裁剪返回
                if (mTempPhoto != null) {
                    File file = new File(mPhotoParam.getPath(), mTempPhoto);
                    file.delete();
                }
                data.putExtra("image", mPhotoParam.getPath() + mPhotoParam.getName());
                setResult(RESULT_OK,data);
                finish();
            } else if (requestCode == FROM_PICTURE) {//图库选择
                Uri selectedImage = data.getData();
                if (selectedImage == null) {
                    return;
                }
                //经过裁剪
                if (mPhotoParam.isCut()) {
                    File file = new File(mPhotoParam.getPath(), mPhotoParam.getName());
                    //android22 bug
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                        selectedImage = Uri.fromFile(new File(ContentUriUtil.getPath(getApplicationContext(), selectedImage)));
                    }
                    cutPhoto(selectedImage, Uri.fromFile(file));
                } else {
                    data.putExtra("image", ContentUriUtil.getPath(getApplicationContext(), selectedImage));
                    setResult(RESULT_OK, data);
                    finish();
                }

            }
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri    图片路径
     * @param newUri 输出的路径 为何用file： 见http://www.open-open.com/lib/view/open1474615893731.html
     */
    public void cutPhoto(Uri uri, Uri newUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", mPhotoParam.getOutputX());
        intent.putExtra("outputY", mPhotoParam.getOutputY());
        intent.putExtra("scale", true);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, newUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CUT_PHOTO);
    }

    /**
     * file转化成content
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

}
