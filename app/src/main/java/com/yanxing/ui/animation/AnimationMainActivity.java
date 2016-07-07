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

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 动画学习
 * Created by lishuangxiang on 2016/7/7.
 */
@EActivity(R.layout.activity_animation_main)
public class AnimationMainActivity extends BaseActivity{

    @ViewById(R.id.alpha)
    Button mAlpha;

    @ViewById(R.id.frame)
    Button mFrame;

    @ViewById(R.id.frame_img)
    ImageView mFrameImg;

    @Override
    protected void afterInstanceView() {

    }

    //透明动画
    @Click(R.id.alpha)
    public void onClickAlpha(){
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        mAlpha.startAnimation(alphaAnimation);
    }

    //帧动画
    @Click(R.id.frame)
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

    @Click(R.id.layout_animation)
    public void onClickLayoutAnimation(){
        Intent intent=new Intent(getApplicationContext(),LayoutAnimationExampleActivity_.class);
        startActivity(intent);
    }
}
