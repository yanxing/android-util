package com.yanxing.baselibrary

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.fragment.app.DialogFragment
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import com.trello.rxlifecycle2.components.support.RxDialogFragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * 基础DialogFragment
 * @author 李双祥 on 2018/7/10.
 */
abstract class BaseDialogFragment : DialogFragment(), LifecycleProvider<ActivityEvent> {

    /**
     * rxlifecycle2用于取消Rxjava订阅，防止内存泄露
     */
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    val TAG: String = javaClass.name

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
        setStyle(RxDialogFragment.STYLE_NO_TITLE, R.style.dialog_fragment_style)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutResID(), container)
        dialog.setCanceledOnTouchOutside(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInstanceView()
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    protected abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()

    /**
     * 显示toast消息
     */
    open fun showToast(tip: String?) {
        if (TextUtils.isEmpty(tip)){
            return
        }
        var toast = Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
        if (tip!!.length>30){
            toast = Toast.makeText(activity, tip, Toast.LENGTH_LONG)
        }
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

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