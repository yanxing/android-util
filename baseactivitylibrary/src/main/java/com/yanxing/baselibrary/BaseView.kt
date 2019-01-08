package com.yanxing.baselibrary

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * View接口的基类,LifecycleProvider 用于取消RxJava订阅
 * @author 李双祥 on 2018/7/5.
 */
interface BaseView : LifecycleProvider<ActivityEvent> {
}