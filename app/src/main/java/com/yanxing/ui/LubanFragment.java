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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

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
        String tip="压缩前文件大小为："+file.length()/1024+"KB\n路径为："+file.getAbsolutePath();
        Luban.get(getActivity())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingDialog("压缩中...");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                    @Override
                    public Observable<? extends File> call(Throwable throwable) {
                        return Observable.empty();
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        String tag=tip+"\n"+
                                "压缩后文件大小： "+file.length() / 1024 + "KB"
                                +"\n路径为："+file.getAbsolutePath();
                        LogUtil.d(TAG,tag);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        mImage.setImageBitmap(bitmap);
                        mText.setText(tag);
                        dismissLoadingDialog();
                    }
                });
    }

    @OnClick(R.id.button)
    public void onClick() {
        takePhoto();
    }
}
