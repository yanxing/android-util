package com.yanxing.ui.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;
import com.yanxing.util.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 动画学习
 * Created by lishuangxiang on 2016/7/7.
 */
public class AnimationMainFragment extends BaseFragment {

    @BindView(R.id.alpha)
    Button mAlpha;

    @BindView(R.id.frame)
    Button mFrame;

    @BindView(R.id.frame_img)
    ImageView mFrameImg;

    @BindView(R.id.object_animation)
    Button mObjectAnimation;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_animation_main;
    }

    @Override
    protected void afterInstanceView() {

    }

    //透明动画
    @OnClick(R.id.alpha)
    public void onClickAlpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        mAlpha.startAnimation(alphaAnimation);
    }

    //帧动画
    @OnClick(R.id.frame)
    public void onClickFrame() {
        mFrameImg.setBackgroundResource(R.drawable.frame_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) mFrameImg.getBackground();
        animationDrawable.start();
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
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
    public void onClickLayoutAnimation() {
        Intent intent = new Intent(getActivity(), LayoutAnimationExampleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.object_animation)
    public void onClick() {
        ViewWrapper viewWrapper = new ViewWrapper(mObjectAnimation);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(viewWrapper, "width"
                , CommonUtil.getScreenDisplay(getActivity()).getWidth() - 20).setDuration(3000);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(getActivity(), ObjectAnimationActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @OnClick(R.id.heartBubbleView)
    public void onClickHeartBubbleView() {
        Intent intent = new Intent(getActivity(), HeartBubbleViewActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.health_animation)
    public void onClickHealth() {
        Intent intent = new Intent(getActivity(), QQHealthActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.path_animation)
    public void onClickPath() {
        Intent intent = new Intent(getActivity(), PathExampleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.progressBar)
    public void onProgressBar() {
        Intent intent = new Intent(getActivity(), ProgressBarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.circleProgressBar)
    public void onCircleProgressBar() {
        Intent intent = new Intent(getActivity(), CircleProgressBarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ripple_sesame)
    public void onRippleSesame() {
        Intent intent = new Intent(getActivity(), RippleLayoutActivity.class);
        startActivity(intent);
    }

    private static class ViewWrapper {
        private View mView;

        ViewWrapper(View view) {
            this.mView = view;
        }

        public int getWidth() {
            return mView.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mView.getLayoutParams().width = width;
            mView.requestLayout();
        }
    }
}
