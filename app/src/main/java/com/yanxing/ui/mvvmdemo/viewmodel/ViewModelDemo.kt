package com.yanxing.ui.mvvmdemo.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.yanxing.ui.BR

class ViewModelDemo :BaseObservable() {

    private var content:String=""

    @Bindable
    fun getContent()=content

    fun setContent(content:String){
        this.content=content
        notifyPropertyChanged(BR.content)
    }

}