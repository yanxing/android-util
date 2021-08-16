package com.yanxing.ui.work

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * @author 李双祥 on 2019/6/12.
 */
class TaskWork(@NonNull  context: Context, @NonNull workerParams: WorkerParameters) : Worker(context,workerParams) {

    override fun doWork(): Result {
        //执行任务
        val data=inputData.getString("data")
        Log.d("TaskWork接收到的数据为：",data!!)
        return Result.success()
    }
}