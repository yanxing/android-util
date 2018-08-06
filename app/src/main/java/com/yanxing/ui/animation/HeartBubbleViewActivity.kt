package com.yanxing.ui.animation


import android.graphics.drawable.Drawable

import com.yanxing.base.BaseActivity
import com.yanxing.ui.R
import com.yanxing.util.CountDownTimer

import kotlinx.android.synthetic.main.activity_heart_bubble_view.*

/**
 * 类似于直播送心效果
 * Created by lishuangxiang on 2016/9/5.
 */
class HeartBubbleViewActivity : BaseActivity() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_heart_bubble_view
    }

    override fun afterInstanceView() {
        val drawable = arrayOfNulls<Drawable>(5)
        drawable[0] = resources.getDrawable(R.mipmap.blue)
        drawable[1] = resources.getDrawable(R.mipmap.pink)
        drawable[2] = resources.getDrawable(R.mipmap.yellow)
        drawable[3] = resources.getDrawable(R.mipmap.green)
        drawable[4] = resources.getDrawable(R.mipmap.red)
        heartBubbleView.init(drawable)
        object : CountDownTimer(20000, 200) {

            override fun onTick(millisUntilFinished: Long) {
                if (isFinishing) {
                    this.cancel()
                }
                heartBubbleView.start()
            }

            override fun onFinish() {

            }
        }.start()
        heartBubbleView.setOnClickListener {
            heartBubbleView.start()
        }
    }
}
