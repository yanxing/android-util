package com.yanxing.ui.work

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

/**
 * @author 李双祥 on 2019/6/13.
 */
class TaskJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d("TaskJobService：","onCreate")
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("TaskJobService：","执行完毕")
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        //在任务开始执行时触发。返回false表示执行完毕，返回true表示需要开发者自己调用jobFinished方法通知系统已执行完成
        Log.d("TaskJobService：","执行开始")
        return false
    }
}