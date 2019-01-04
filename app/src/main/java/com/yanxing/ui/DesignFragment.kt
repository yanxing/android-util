package com.yanxing.ui

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager

import com.yanxing.base.BaseFragment
import com.yanxing.commonlibrary.adapter.RecyclerViewAdapter

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_design.*

/**
 * Design中控件用法
 * Created by lishuangxiang on 2016/11/18.
 */
class DesignFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.fragment_design
    }

    override fun afterInstanceView() {
        collapsingToolbarLayout.title = "CollapsingToolbarLayout"
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE)
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN)

        val strings = ArrayList<String>()
        for (i in 0..79) {
            strings.add(i.toString())
        }
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val recyclerViewAdapter = object : RecyclerViewAdapter<String>(strings, R.layout.adapter_recycler_view) {

            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                holder.setText(R.id.text, mDataList[position])
            }
        }
        recyclerView.adapter = recyclerViewAdapter
    }
}
