package com.yanxing.ui.animation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.yanxing.base.BaseActivity;
import com.yanxing.ui.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 动画学习
 * Created by lishuangxiang on 2016/7/7.
 */
public class AnimationMainActivity extends BaseActivity{

    @BindView(R.id.alpha)
    Button mAlpha;

    @BindView(R.id.frame)
    Button mFrame;

    @BindView(R.id.frame_img)
    ImageView mFrameImg;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_animation_main;
    }

    @Override
    protected void afterInstanceView() {

    }

    //透明动画
    @OnClick(R.id.alpha)
    public void onClickAlpha(){
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        mAlpha.startAnimation(alphaAnimation);
    }

    //帧动画
    @OnClick(R.id.frame)
    public void onClickFrame(){
        mFrameImg.setBackgroundResource(R.drawable.frame_anim);
        AnimationDrawable animationDrawable= (AnimationDrawable) mFrameImg.getBackground();
        animationDrawable.start();
        int duration = 0;
        for(int i=0;i<animationDrawable.getNumberOfFrames();i++){
            duration += animationDrawable.getDuration(i);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                mFrameImg.setVisibility(View.GONE);
            }
        }, duration);
    }

    @OnClick(R.id.layout_animation)
    public void onClickLayoutAnimation(){
        Intent intent=new Intent(getApplicationContext(),LayoutAnimationExampleActivity.class);
        startActivity(intent);
    }
}
