package com.yanxing.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 李双祥 on 2017/1/12.
 */
public class LubanFragment extends BaseFragment {

    @BindView(R.id.text)
    TextView mText;

    @BindView(R.id.image)
    ImageView mImage;

    //和清单文件中provider一致
    private static final String AUTHORITY = "com.yanxing.ui.provider";
    private static final int TAKE_PHOTO = 100;
    private String mPhotoName="y.jpg";

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_lu_ban;
    }

    @Override
    protected void afterInstanceView() {
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String tempPhoto = System.currentTimeMillis() + ".png";
        mPhotoName = tempPhoto;
        //file文件夹，和filepaths.xml中目录符合，或者是其子文件夹
        File file = new File(FileUtil.getStoragePath()+ConstantValue.DCIM_IMAGE, tempPhoto);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    FileProvider.getUriForFile(getActivity(), AUTHORITY, file)));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), AUTHORITY, file));
        } else {
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        yaSuo();
        if (resultCode == RESULT_OK) {
            //拍照返回
            if (requestCode == TAKE_PHOTO) {
                yaSuo();
            }
        }
    }

    public void yaSuo() {
        File file = new File(FileUtil.getStoragePath()+ConstantValue.DCIM_IMAGE + mPhotoName);
        final String tip="压缩前文件大小为："+file.length()/1024+"KB\n路径为："+file.getAbsolutePath();
        Luban.with(getActivity())
                .load(file)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(file.getAbsolutePath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        showLoadingDialog("压缩中...");
                    }

                    @Override
                    public void onSuccess(File file) {
                        String tag=tip+"\n"+
                                "压缩后文件大小： "+file.length() / 1024 + "KB"
                                +"\n路径为："+file.getAbsolutePath();
                        LogUtil.d(TAG,tag);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        mImage.setImageBitmap(bitmap);
                        mText.setText(tag);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();    //启动压缩
    }

    @OnClick(R.id.button)
    public void onClick() {
        takePhoto();
    }
}
