package com.yanxing.ui

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.TextView

import com.yanxing.base.BaseFragment
import com.yanxing.adapter.RecyclerViewAdapter

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_common_adapter.*

/**
 * adapterlibrary测试
 * Created by lishuangxiang on 2016/1/20.
 */
class CommonAdapterFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.fragment_common_adapter
    }

    override fun afterInstanceView() {
        val list = ArrayList<Int>()
        for (i in 0..6) {
            list.add(i)

        }
        //RecycleView
        recyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        val recyclerViewAdapter = object : RecyclerViewAdapter<Int>(list, R.layout.adapter_recycler_view) {

            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val textView = holder.findViewById<View>(R.id.text) as TextView
                //瀑布流，动态设置item大小
                val lp = textView.layoutParams
                lp.height = (50 + Math.random() * 300).toInt()
                textView.layoutParams = lp
                textView.text = list[position].toString()
            }
        }
        recyclerViewAdapter.setOnItemClick { viewHolder, position ->
            showToast(getString(R.string.you_click) + (position + 1) + getString(
                    R.string.ge))
        }
        recyclerView.adapter = recyclerViewAdapter
    }
}
