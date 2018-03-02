package com.yanxing.baselibrary.base.kotlin

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxDialogFragment
import com.yanxing.baselibrary.R

/**
 *  kotlin基础DialogFragment
 * @author 李双祥 on 2018/3/2.
 */
abstract class BaseLibraryKtDialogFragment : RxDialogFragment() {

    val TAG: String = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment_style)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutResID(), container)
        dialog.setCanceledOnTouchOutside(false)
        afterInstanceView()
        return view
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    abstract fun afterInstanceView()

    /**
     * 显示toast消息
     */
    fun showToast(tip: String) {
        val toast = Toast.makeText(activity, tip, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}