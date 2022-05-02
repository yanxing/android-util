package com.lsx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>(private var layoutId: Int) : Fragment() {

    protected var viewBinding: T? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInstanceView()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        viewBinding=DataBindingUtil.inflate(inflater,layoutId,container,false)
        return viewBinding!!.root
    }

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}