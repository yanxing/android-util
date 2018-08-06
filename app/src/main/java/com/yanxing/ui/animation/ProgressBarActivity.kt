package com.yanxing.ui.animation


import com.yanxing.base.BaseActivity
import com.yanxing.ui.R

import java.lang.ref.WeakReference

import kotlinx.android.synthetic.main.activity_progress_bar.*

/**
 * ProgressBar使用
 * Created by lishuangxiang on 2016/10/18.
 */

class ProgressBarActivity : BaseActivity() {

    private var mProgress = 0

    override fun getLayoutResID(): Int {
        return R.layout.activity_progress_bar
    }

    override fun afterInstanceView() {
        progressBar1.max = 100
        progressBar2.max = 100
        progressBar3.max = 100
        MyThread(this).start()
    }

    class MyThread (activity: ProgressBarActivity) : Thread() {

        internal var mReference: WeakReference<ProgressBarActivity>

        init {
            mReference = WeakReference(activity)
        }

        override fun run() {
            while (mReference.get() != null && mReference.get()!!.mProgress <= 100) {
                mReference.get()!!.mProgress += 10
                mReference.get()!!.progressBar1.progress = mReference.get()!!.mProgress
                mReference.get()!!.progressBar2.progress = mReference.get()!!.mProgress
                mReference.get()!!.progressBar3.progress = mReference.get()!!.mProgress
                try {
                    Thread.sleep(800)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }
}
