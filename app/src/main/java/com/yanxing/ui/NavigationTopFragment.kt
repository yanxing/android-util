package com.yanxing.ui

import android.support.v7.widget.LinearLayoutManager

import com.yanxing.adapterlibrary.RecyclerViewAdapter
import com.yanxing.base.BaseFragment
import com.yanxing.util.CommonUtil

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_navigation_top.*

/**
 * 类似今日头条导航菜单效果
 * Created by 李双祥 on 2017/4/25.
 */
class NavigationTopFragment : BaseFragment() {


    private var mLinearManager: LinearLayoutManager? = null
    private var mWidth = 1080

    override fun getLayoutResID(): Int {
        return R.layout.fragment_navigation_top
    }

    override fun afterInstanceView() {
        val displayMetrics = CommonUtil.getDisplayMetrics(activity)
        if (displayMetrics != null) {
            mWidth = displayMetrics.widthPixels
        }
        mLinearManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLinearManager

        //javaScript
        val list = ArrayList<Int>()
        for (i in 0..9) {
            list.add(i)
        }
        recyclerView.adapter = object : RecyclerViewAdapter<Int>(list, R.layout.adapter_nav) {
            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                holder.setText(R.id.menu, "菜单" + list[position])
                holder.itemView.setOnClickListener {
                    val firstItem = mLinearManager!!.findFirstVisibleItemPosition()
                    //此项距离左边距离
                    val left = recyclerView.getChildAt(position - firstItem).left
                    if (left >= mWidth / 2) {
                        recyclerView.smoothScrollBy(holder.itemView.width, 0)
                    } else {
                        recyclerView.smoothScrollBy(-holder.itemView.width, 0)
                    }
                }
            }
        }
    }
}
