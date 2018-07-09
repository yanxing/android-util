package com.yanxing.ui.animation;

import com.yanxing.base.BaseActivity;
import com.yanxing.ui.R;
import com.yanxing.view.CircleDotProgressBar;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * CircleProgressBar
 * Created by lishuangxiang on 2016/10/21.
 */

public class CircleProgressBarActivity extends BaseActivity {

    @BindView(R.id.circleProgressBar)
    CircleDotProgressBar mCircleDotProgressBar;

    private int mProgress = 0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_circle_progress_bar;
    }

    @Override
    protected void afterInstanceView() {
        mCircleDotProgressBar.setProgressMax(100);
        MyThread myThread = new MyThread(this);
        myThread.start();
    }

    private static class MyThread extends Thread {

        WeakReference<CircleProgressBarActivity> mReference;

        private MyThread(CircleProgressBarActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            while (mReference.get()!=null&&mReference.get().mProgress <= 100) {
                mReference.get().mProgress += 10;
                mReference.get().mCircleDotProgressBar.setProgress(mReference.get().mProgress);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
