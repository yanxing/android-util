package com.yanxing.baselibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * 基础Fragment
 * @author 李双祥 on 2018/7/9.
 */
abstract class BaseFragment : Fragment(){

    protected var mFragmentManager: FragmentManager?=null
    val TAG: String = javaClass.name

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(getLayoutResID(), container, false)
        mFragmentManager = childFragmentManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        afterInstanceView()
        super.onViewCreated(view, savedInstanceState)
    }

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

    /**
     * 子类布局，例如R.layout.activity_main
     */
    protected abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()

}