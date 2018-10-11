package com.photo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.photo.R;
import com.photo.atapter.GirdItemAdapter;
import com.photo.atapter.ImageFloderAdapter;
import com.photo.model.ImageFloder;
import com.photo.util.AppUtil;
import com.photo.util.ConstantUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * 拍照、从系统相册选择上传
 */
public class PhotoUtilsActivity extends Activity implements OnClickListener {

    private GridView photoGrid;// 图片列表

    private TextView titleName;// 头部的标题

    private ImageView titleIcon;// 头部的三角图标

    private ProgressDialog mProgressDialog;// 加载Dialog

    private HashSet<String> mDirPaths = new HashSet<String>();// 临时的辅助类，用于防止同一个文件夹的多次扫描

    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();// 扫描拿到所有的图片文件夹

    int totalCount = 0;

    private File mImgDir = new File("");// 图片数量最多的文件夹

    private int mPicsSize;// 存储文件夹中的图片数量

    private List<String> mImgs = new ArrayList<String>();// 所有的图片

    private List<String> camerList = new ArrayList<String>();//拍照文件下图片

    private TextView backTv;// 取消

    private ListView dirListView;

    private RelativeLayout headLayout;// titleLayout

    private int mScreenWidth;// 用来获取屏幕的宽度

    private int mScreenHeight;// 用来获取屏幕的高度

    private ImageFloderAdapter floderAdapter;

    private GirdItemAdapter girdItemAdapter;

    private PopupWindow popupWindow;

    private View dirview;

    private String path;

    private File dirFile;

    private String imagename;// 用来为拍到的图片命名

    private Uri fromFile;

    private String mPath;

    private String mName;

    private boolean flag = false;//是否要剪切图片

    private final static String CAMERA = Environment.getExternalStorageDirectory() + "/DCIM/Camera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_select_view);
        GirdItemAdapter.mSelectedImage.clear();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScreenWidth = outMetrics.widthPixels;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mPath = extras.getString("path") == null ? "" : extras.getString("path");
            mName = extras.getString("name") == null ? "" : extras.getString("name");
            flag = extras.getBoolean("cut");
        }
        initImmersionStatus();
        initView();
        initListener();
        getImages();
    }

    /**
     * 沉浸式布局
     */
    public void initImmersionStatus(){
        AppUtil.setTranslucentStatus(this);
        AppUtil.setStatusBarDarkMode(true,this);
    }

    private void initView() {
        photoGrid = (GridView) findViewById(R.id.gird_photo_list);
        titleName = (TextView) this.findViewById(R.id.selected_photo_name_text);
        backTv = (TextView) findViewById(R.id.quxiao_btn);
        titleIcon = (ImageView) findViewById(R.id.selected_photo_icon);
        headLayout = (RelativeLayout) findViewById(R.id.selected_photo_header_layout);
        titleIcon.setBackgroundResource(R.drawable.navigationbar_arrow_down);
    }

    private void initListener() {
        backTv.setOnClickListener(this);
        titleName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.quxiao_btn) {
            finish();

        } else if (i == R.id.selected_photo_name_text) {// 初始化展示文件夹的popupWindw
            initListDirPopupWindw();

        }
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstImage = null;
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PhotoUtilsActivity.this
                        .getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    final File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    if (parentFile.list() == null)
                        continue;
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg")) {
                                if (dir.getAbsolutePath().equals(CAMERA)) {
                                    camerList.add(filename);
                                }
                                return true;
                            }
                            return false;
                        }
                    }).length;
                    totalCount += picSize;
                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
            }
            setDataView();// 为View绑定数据
        }
    };

    /**
     * 为View绑定数据
     */
    public void setDataView() {
        backTv.setText("取消");
        path = mPath;
        dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "未扫描到图片",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Collections.sort(camerList);
        Collections.reverse(camerList);//时间逆序显示
        girdItemAdapter = new GirdItemAdapter(this, camerList,
                CAMERA);


        photoGrid.setAdapter(girdItemAdapter);
        girdItemAdapter.setOnPhotoSelectedListener(new GirdItemAdapter.OnPhotoSelectedListener() {
            @Override
            public void takePhoto() {
                imagename = getTimeName(System.currentTimeMillis())
                        + ".jpg";
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);// 用于拍照的Activity
                    // 需要返回照片图像数据,
                    File file = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), imagename);
                    if (!file.exists()) {
                        file.delete();
                    }
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fromFile = Uri.fromFile(file);
                    intent.putExtra(
                            MediaStore.Images.Media.ORIENTATION, 0);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fromFile);
                    startActivityForResult(intent,
                            ConstantUtils.CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(PhotoUtilsActivity.this, "请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void photoClick(List<String> number) {

            }

            @Override
            public void photoPath(String path) {
                decodeUriAsBitmap(Uri.fromFile(new File(path)), getCacheUri());
            }

        });
    }

    private Uri getCacheUri() {
        String path = mPath;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File cacheFile = new File(file, mName);
        if (cacheFile.exists()) {
            cacheFile.delete();
        }
        try {
            cacheFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(cacheFile);
    }

    /**
     * 用popupWindw来展示相册列表
     */
    @SuppressLint("InflateParams")
    private void initListDirPopupWindw() {
        if (popupWindow == null) {
            dirview = LayoutInflater.from(PhotoUtilsActivity.this).inflate(
                    R.layout.image_select_dirlist, null);
            dirListView = (ListView) dirview.findViewById(R.id.photo_file_list);
            Rect frame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            popupWindow = new PopupWindow(dirview, mScreenWidth
                    , mScreenHeight - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48
                    , getResources().getDisplayMetrics()) - frame.top);
            // 适配数据
            floderAdapter = new ImageFloderAdapter(PhotoUtilsActivity.this,
                    mImageFloders);
            dirListView.setAdapter(floderAdapter);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        titleIcon.setBackgroundResource(R.drawable.navigationbar_arrow_up);
        popupWindow.showAsDropDown(headLayout, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                titleIcon
                        .setBackgroundResource(R.drawable.navigationbar_arrow_down);
            }
        });
        dirListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                titleName.setText(mImageFloders.get(position).getName());
                mImgDir = new File(mImageFloders.get(position).getDir());
                mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg")
                                || filename.endsWith(".png")
                                || filename.endsWith(".jpeg"))
                            return true;
                        return false;
                    }
                }));
                for (int i = 0; i < mImageFloders.size(); i++) {
                    mImageFloders.get(i).setSelected(false);
                }
                mImageFloders.get(position).setSelected(true);
                floderAdapter.changeData(mImageFloders);
                girdItemAdapter.changeData(mImgs, mImageFloders.get(position)
                        .getDir());
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    public static String getTimeName(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(time);
        return formatter.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == ConstantUtils.CAMERA_REQUEST_CODE) {// 拍照得到的照片
            decodeUriAsBitmap(fromFile, getCacheUri());
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(fromFile);
            sendBroadcast(intent);
        } else if (requestCode == ConstantUtils.PHOTO_REQUEST_CUT) {
            if (data != null) {
                //解决有时文件没有写入完就返回bug
                final File file = new File(mPath + mName);
                mProgressDialog.setMessage("图片裁剪中...");
                mProgressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (file.length() != 0) {
                                setResult(RESULT_OK, data);
                                mProgressDialog.dismiss();
                                finish();
                                break;
                            }
                        }
                    }
                }).start();
            }
        }
    }

    // 裁剪图片方法
    private void decodeUriAsBitmap(Uri uri, Uri cacheUri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            //进行裁剪
            if (flag) {
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 480);
                intent.putExtra("outputY", 480);
                intent.putExtra("scale", true);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cacheUri);
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, ConstantUtils.PHOTO_REQUEST_CUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
