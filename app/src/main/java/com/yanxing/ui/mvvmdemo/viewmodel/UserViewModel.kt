package com.yanxing.ui.mvvmdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanxing.ui.mvvmdemo.model.User

class UserViewModel : ViewModel() {

    val userLiveData = MutableLiveData<User>()

    fun getData(){
        //模拟获取数据
       userLiveData.value = User("张三")
    }

}