package com.yanxing.baselibrary

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * 基础DialogFragment
 * @author 李双祥 on 2018/7/10.
 */
abstract class BaseDialogFragment : DialogFragment(){

    val TAG: String = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment_style)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutResID(), container)
        dialog?.setCanceledOnTouchOutside(false)
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
        tip?.let {
            var toast = Toast.makeText(requireActivity(), "", Toast.LENGTH_SHORT)
            if (it.length>30){
                toast = Toast.makeText(requireActivity(), "", Toast.LENGTH_LONG)
            }
            toast.setText(it)
            toast.show()
        }
    }

}