package com.yanxing.ui.animation

import com.yanxing.base.BaseActivity
import com.yanxing.ui.R

import java.lang.ref.WeakReference

import kotlinx.android.synthetic.main.activity_circle_progress_bar.*

/**
 * CircleProgressBar
 * Created by lishuangxiang on 2016/10/21.
 */

class CircleProgressBarActivity : BaseActivity() {


    private var mProgress = 0

    override fun getLayoutResID(): Int {
        return R.layout.activity_circle_progress_bar
    }

    override fun afterInstanceView() {
        circleProgressBar.progressMax = 100
        val myThread = MyThread(this)
        myThread.start()
    }

    class MyThread (activity: CircleProgressBarActivity) : Thread() {

        internal var mReference: WeakReference<CircleProgressBarActivity>

        init {
            mReference = WeakReference(activity)
        }

        override fun run() {
            while (mReference.get() != null && mReference.get()!!.mProgress <= 100) {
                mReference.get()!!.mProgress += 10
                mReference.get()!!.circleProgressBar.progress = mReference.get()!!.mProgress
                try {
                    Thread.sleep(800)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }
}
