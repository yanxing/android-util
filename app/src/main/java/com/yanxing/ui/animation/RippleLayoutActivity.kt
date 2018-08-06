package com.yanxing.ui.animation


import com.yanxing.base.BaseActivity
import com.yanxing.ui.R

import java.util.Random

import kotlinx.android.synthetic.main.activity_ripple_layout.*

/**
 * 点击波纹+芝麻分学习
 * Created by lishuangxiang on 2016/12/5.
 */

class RippleLayoutActivity : BaseActivity() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_ripple_layout
    }

    override fun afterInstanceView() {
        button.setOnClickListener {
            val score = edit.text.toString().trim { it <= ' ' }
            sesameView.currentNum = Integer.parseInt(if (score.isEmpty()) "0" else score)
            val random = Random()
            circleProgressView.setProgressNotInUiThread(random.nextInt(100))
        }
    }
}
