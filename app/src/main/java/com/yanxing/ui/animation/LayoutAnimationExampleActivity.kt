package com.yanxing.ui.animation


import com.yanxing.base.BaseActivity
import com.yanxing.commonlibrary.adapter.CommonAdapter
import com.yanxing.commonlibrary.adapter.ViewHolder
import com.yanxing.ui.R

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_layout_animation_example.*

/**
 * LayoutAnimation
 * Created by lishuangxiang on 2016/7/7.
 */
class LayoutAnimationExampleActivity : BaseActivity() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_layout_animation_example
    }

    override fun afterInstanceView() {
        val list = ArrayList<Int>()
        for (i in 0..19) {
            list.add(i)

        }
        val adapter = object : CommonAdapter<Int>(list, R.layout.list_dialog_textview) {
            override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
                viewHolder.setText(R.id.text, mDataList[position].toString())
            }
        }
        listview.adapter = adapter
    }
}
