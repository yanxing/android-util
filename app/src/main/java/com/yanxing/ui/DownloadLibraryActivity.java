package com.yanxing.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yanxing.base.BaseActivity;
import com.yanxing.downloadlibrary.DownloadConfiguration;
import com.yanxing.downloadlibrary.DownloadUtils;
import com.yanxing.downloadlibrary.SimpleDownloadListener;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * downloadlibrary测试
 * Created by lishuangxiang on 2016/9/21.
 */

public class DownloadLibraryActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.url)
    TextView mUrl;

    @BindView(R.id.start)
    Button mStart;

    @BindView(R.id.progress)
    TextView mProgress;

    //true停止下载
    private boolean isStopDownload;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_download_library;
    }

    @Override
    protected void afterInstanceView() {
        File file = new File(FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
        DownloadConfiguration downloadConfiguration = new DownloadConfiguration.Builder()
                .savePath(file)
                .builder();
        DownloadUtils.getInstance().init(downloadConfiguration);
    }

    @OnClick({R.id.start, R.id.stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (isStopDownload) {
                    DownloadUtils.getInstance().resumeDownload();
                } else {
                    download();
                }
                break;
            case R.id.stop:
                DownloadUtils.getInstance().stopDownload();
                isStopDownload = true;
                break;
        }
    }

    /**
     * 下载
     */
    public void download() {
        DownloadUtils.getInstance().startDownload(this, mUrl.getText().toString(), new SimpleDownloadListener() {
            @Override
            public void onStart() {
                LogUtil.d("DownloadUtils", "下载开始...");
            }

            @Override
            public void onProgress(int progress, int totalSize) {
                mProgressBar.setMax(totalSize);
                mProgressBar.setProgress(progress);
                LogUtil.d("DownloadUtils", progress + "  " + totalSize);
                mProgress.setText((int) ((progress * 1.0 / totalSize) * 100) + "%");
            }

            @Override
            public void onError(int state) {
                super.onError(state);
                if (state == 404) {
                    showToast("文件不存在");
                } else {
                    showToast("错误代码" + state);
                }
            }

            @Override
            public void onFinish() {
                LogUtil.d("DownloadUtils", "下载完成...");
            }
        });
    }
}
