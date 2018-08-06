package com.yanxing.ui.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation

import com.yanxing.base.BaseFragment
import com.yanxing.ui.R
import com.yanxing.util.CommonUtil

import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_animation_main.*

/**
 * 动画学习
 * Created by lishuangxiang on 2016/7/7.
 */
class AnimationMainFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.fragment_animation_main
    }

    override fun afterInstanceView() {
        //透明动画
        alpha.setOnClickListener {
            val alphaAnimation = AlphaAnimation(0f, 1f)
            alphaAnimation.duration = 1000
            alpha.startAnimation(alphaAnimation)
        }
        //帧动画
        frame.setOnClickListener {
            frameImg.setBackgroundResource(R.drawable.frame_anim)
            val animationDrawable = frameImg.background as AnimationDrawable
            animationDrawable.start()
            var duration = 0
            for (i in 0 until animationDrawable.numberOfFrames) {
                duration += animationDrawable.getDuration(i)
            }
            val handler = Handler()
            handler.postDelayed({ frameImg.visibility = View.GONE }, duration.toLong())
        }
        layoutAnimation.setOnClickListener {
            val intent = Intent(activity, LayoutAnimationExampleActivity::class.java)
            startActivity(intent)
        }
        ObjectAnimation.setOnClickListener {
            val viewWrapper = ViewWrapper(ObjectAnimation)
            val objectAnimator = ObjectAnimator.ofInt(viewWrapper, "width", CommonUtil.getScreenDisplay(activity)!!.width - 20).setDuration(3000)
            objectAnimator.start()
            objectAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(activity, ObjectAnimationActivity::class.java)
                    startActivity(intent)
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
        }

    }

    @OnClick(R.id.heartBubbleView)
    fun onClickHeartBubbleView() {
        val intent = Intent(activity, HeartBubbleViewActivity::class.java)
        startActivity(intent)
    }


    @OnClick(R.id.health_animation)
    fun onClickHealth() {
        val intent = Intent(activity, QQHealthActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.path_animation)
    fun onClickPath() {
        val intent = Intent(activity, PathExampleActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.progressBar)
    fun onProgressBar() {
        val intent = Intent(activity, ProgressBarActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.circleProgressBar)
    fun onCircleProgressBar() {
        val intent = Intent(activity, CircleProgressBarActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.ripple_sesame)
    fun onRippleSesame() {
        val intent = Intent(activity, RippleLayoutActivity::class.java)
        startActivity(intent)
    }

    private class ViewWrapper internal constructor(private val mView: View) {
        var width: Int
            get() = mView.layoutParams.width
            set(width) {
                mView.layoutParams.width = width
                mView.requestLayout()
            }
    }
}
