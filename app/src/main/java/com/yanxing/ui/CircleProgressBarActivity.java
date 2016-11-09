package com.yanxing.ui;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.view.CircleDotProgressBar;

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
        CommonUtil.setStatusBarDarkMode(true,this);
        mCircleDotProgressBar.setProgressMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress <= 100) {
                    mProgress += 10;
                    mCircleDotProgressBar.setProgress(mProgress);
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
