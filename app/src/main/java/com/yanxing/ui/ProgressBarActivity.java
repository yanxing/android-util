package com.yanxing.ui;

import com.yanxing.base.BaseActivity;
import com.yanxing.view.ProgressBar;

import butterknife.BindView;

/**
 * ProgressBar使用
 * Created by lishuangxiang on 2016/10/18.
 */

public class ProgressBarActivity extends BaseActivity {

    @BindView(R.id.progressBar1)
    ProgressBar mProgressBar1;

    @BindView(R.id.progressBar2)
    ProgressBar mProgressBar2;

    @BindView(R.id.progressBar3)
    ProgressBar mProgressBar3;

    private int mProgress = 0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_progress_bar;
    }

    @Override
    protected void afterInstanceView() {
        mProgressBar1.setMax(100);
        mProgressBar2.setMax(100);
        mProgressBar3.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress <= 100) {
                    mProgress += 10;
                    mProgressBar1.setProgress(mProgress);
                    mProgressBar2.setProgress(mProgress);
                    mProgressBar3.setProgress(mProgress);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
