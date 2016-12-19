package com.yanxing.ui;


import com.yanxing.base.BaseActivity;
import com.yanxing.view.ProgressBar;

import java.lang.ref.WeakReference;

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
        new MyThread(this).start();
    }

    private static class MyThread extends Thread {

        WeakReference<ProgressBarActivity> mReference;

        private MyThread(ProgressBarActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            while (mReference.get()!=null&&mReference.get().mProgress <= 100) {
                mReference.get().mProgress += 10;
                mReference.get().mProgressBar1.setProgress(mReference.get().mProgress);
                mReference.get().mProgressBar2.setProgress(mReference.get().mProgress);
                mReference.get().mProgressBar3.setProgress(mReference.get().mProgress);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
