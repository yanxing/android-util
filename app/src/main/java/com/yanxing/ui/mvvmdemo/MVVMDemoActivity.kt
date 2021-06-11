package com.yanxing.ui.mvvmdemo

import androidx.lifecycle.ViewModelProvider
import com.lsx.base.BaseActivity
import com.yanxing.ui.R
import com.yanxing.ui.databinding.ActivityMvvmDemoBinding
import com.yanxing.ui.mvvmdemo.viewmodel.UserViewModel
import com.yanxing.util.LogUtil
import com.yanxing.util.ToastUtil

class MVVMDemoActivity :BaseActivity<ActivityMvvmDemoBinding>(){

    override fun getLayoutResID(): Int {
        return R.layout.activity_mvvm_demo
    }

    override fun afterInstanceView() {
        val userViewModel=ViewModelProvider(this).get(UserViewModel::class.java)
        viewBinding.viewModel=userViewModel
        viewBinding.lifecycleOwner=this
        userViewModel.apply {
            getData()
            userLiveData.observe(this@MVVMDemoActivity,{
                //获取界面输入框改变后的数据
                  LogUtil.d("测试",it.name)
                  ToastUtil.showToast(applicationContext,it.name)
            })

        }
    }

}