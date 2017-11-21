package com.yanxing.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yanxing.base.BaseActivity;
import com.yanxing.dialog.PhotoParam;
import com.yanxing.dialog.SelectPhotoActivity;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.LogUtil;
import com.yanxing.util.PermissionUtil;

import butterknife.BindView;

/**
 * webview中打开相册选择照片返回，拍照返回
 *
 * @author 李双祥 on 2017/11/16.
 */
public class WebOpenPhotoActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    public static final int QUESTION = 1;
    private static final int QUESTION_IMAGE_CODE = 2;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web_open_photo;
    }

    @Override
    protected void afterInstanceView() {
        requestPermission();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl("file:///android_asset/index.html");
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        mWebView.addJavascriptInterface(new Photo(), "photo");

    }

    public void requestPermission() {
        if (PermissionUtil.findNeedRequestPermissions(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}).length > 0) {
            PermissionUtil.requestPermission(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, QUESTION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        if (requestCode == QUESTION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    for (String permission : permissions) {
                        PermissionUtil.getPermissionTip(permission);
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class Photo {

        /**
         * 打开图库
         */
        @JavascriptInterface
        public void selectPhoto() {
            long currentTimeDialog = System.currentTimeMillis();
            Intent intent=new Intent(getApplicationContext(), SelectPhotoActivity.class);
            PhotoParam photoParam = new PhotoParam();
            photoParam.setName(currentTimeDialog+".png");
            photoParam.setPath(FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
            Bundle bundle=new Bundle();
            bundle.putParcelable("photoParam", photoParam);
            intent.putExtras(bundle);
            startActivityForResult(intent, QUESTION_IMAGE_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_IMAGE_CODE) {
                byte file[]=FileUtil.getBytes(data.getStringExtra("image"));
                String image=Base64.encodeToString(file,Base64.DEFAULT);
                javaScript(image);
            }
        }
    }

    /**
     * 调用js方法
     * @param photo
     */
    public void javaScript(String photo){
        photo="yanxing";
        final int version = Build.VERSION.SDK_INT;
        if (version < 19) {
            mWebView.loadUrl("javascript:onPhotoListener('"+photo+"')");
        } else {
            mWebView.evaluateJavascript("javascript:onPhotoListener('"+photo+"')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtil.d(TAG,value);
                }
            });
        }
    }
}
