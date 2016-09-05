package com.yanxing.ui.animation;


import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.yanxing.base.BaseActivity;
import com.yanxing.ui.R;
import com.yanxing.util.CountDownTimer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类似于直播送心效果
 * Created by lishuangxiang on 2016/9/5.
 */
public class HeartBubbleViewActivity extends BaseActivity {

    @BindView(R.id.heartBubbleView)
    com.yanxing.HeartBubbleView mHeartBubbleView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_heart_bubble_view;
    }

    @Override
    protected void afterInstanceView() {
        Drawable drawable[] = new Drawable[5];
        drawable[0] = getResources().getDrawable(R.mipmap.blue);
        drawable[1] = getResources().getDrawable(R.mipmap.pink);
        drawable[2] = getResources().getDrawable(R.mipmap.yellow);
        drawable[3] = getResources().getDrawable(R.mipmap.green);
        drawable[4] = getResources().getDrawable(R.mipmap.red);
        mHeartBubbleView.init(drawable);
        new CountDownTimer(20000,200){

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing()){
                    this.cancel();
                }
                   mHeartBubbleView.start();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @OnClick(R.id.heartBubbleView)
    public void onClick() {
        mHeartBubbleView.start();
    }
}
