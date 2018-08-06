package com.yanxing.ui

import android.support.v7.widget.LinearLayoutManager

import com.yanxing.adapterlibrary.RecyclerViewAdapter
import com.yanxing.base.BaseFragment
import com.yanxing.util.RecycleViewUtil

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_hide_title_bottom.*

/**
 * RecycleView上滑隐藏标题栏和底部导航栏View，下滑显示
 * Created by lishuangxiang on 2016/11/16.
 */

class HideTitleBottomFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_hide_title_bottom
    }

    override fun afterInstanceView() {
        val strings = ArrayList<String>()
        for (i in 0..79) {
            strings.add(i.toString())
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val recyclerViewAdapter = object : RecyclerViewAdapter<String>(strings, R.layout.adapter_hide_title_bottom) {

            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                holder.setText(R.id.text, mDataList[position])
            }
        }
        recyclerView.adapter = recyclerViewAdapter
        RecycleViewUtil.startHideShowAnimation(recyclerView, top, bottom)
    }
}
