package com.yanxing.ui.animation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import com.google.android.material.snackbar.Snackbar

import com.yanxing.base.BaseActivity
import com.yanxing.model.Point
import com.yanxing.ui.R

import kotlinx.android.synthetic.main.activity_object_animation.*

/**
 * 属性动画
 * Created by lishuangxiang on 2016/8/1.
 */
class ObjectAnimationActivity : BaseActivity() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_object_animation
    }

    override fun afterInstanceView() {
        image.setOnClickListener {
            ObjectAnimator
                    .ofFloat(image, "rotationX", 0.0f, 360f)
                    .setDuration(1000)
                    .start()
        }
        image1.setOnClickListener {
            val objectAnimator = ObjectAnimator
                    .ofFloat(image1, "rotationX", 0.0f, 1.0f)
                    .setDuration(1000)
            objectAnimator.start()
            objectAnimator.addUpdateListener { animation ->
                val f = animation.animatedValue as Float
                image1.scaleX = f
                image1.scaleY = f
                image1.alpha = f
            }
            Snackbar.make(image1, "Click", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun paoWuXian() {
        val startPoint = Point(0f, 0f)
        val endPoint = Point(300f, 300f)
        val valueAnimator = ValueAnimator.ofObject(PointTypeEvaluator(), startPoint, endPoint)
        valueAnimator.duration = 500
        valueAnimator.start()
    }
}
