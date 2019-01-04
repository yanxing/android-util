package com.yanxing.baselibrary

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * 基础Activity
 * @author 李双祥 on 2018/7/5.
 */
abstract class BaseActivity : AppCompatActivity(), LifecycleProvider<ActivityEvent> {

    /**
     * rxlifecycle2用于取消Rxjava订阅，防止内存泄露
     */
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    protected lateinit var mFragmentManager: FragmentManager
    val TAG:String=javaClass.name

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        setContentView(getLayoutResID())
        mFragmentManager = supportFragmentManager
        afterInstanceView()
    }

    /**
     * 显示toast消息
     */
    open fun showToast(tip: String?) {
        if (TextUtils.isEmpty(tip)){
            return
        }
        var toast = Toast.makeText(applicationContext, tip, Toast.LENGTH_SHORT)
        if (tip!!.length>30){
            toast = Toast.makeText(applicationContext, tip, Toast.LENGTH_LONG)
        }
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    protected abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }
}