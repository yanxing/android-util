package com.yanxing.ui.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.yanxing.base.BaseActivity;
import com.yanxing.model.Point;
import com.yanxing.ui.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 属性动画
 * Created by lishuangxiang on 2016/8/1.
 */
public class ObjectAnimationActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mImage;

    @BindView(R.id.image1)
    ImageView mImage1;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_object_animation;
    }

    @Override
    protected void afterInstanceView() {

    }

    @OnClick({R.id.image,R.id.image1})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image:
                ObjectAnimator
                .ofFloat(mImage,"rotationX",0.0f,360f)
                .setDuration(1000)
                .start();
                break;
            case R.id.image1:
                ObjectAnimator objectAnimator=ObjectAnimator
                        .setDuration(1000);
                objectAnimator.start();
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float f= (float) animation.getAnimatedValue();
                        mImage1.setScaleX(f);
                        mImage1.setScaleY(f);
                        mImage1.setAlpha(f);
                    }
                });
                Snackbar.make(mImage1, "Click", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    public void paoWuXian(){
        Point startPoint=new Point(0,0);
        Point endPoint=new Point(300,300);
        ValueAnimator valueAnimator=ValueAnimator.ofObject(new PointTypeEvaluator(),startPoint,endPoint);
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }
}
