package com.yanxing.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.yanxing.base.BaseFragment
import com.yanxing.commonlibrary.adapter.RecyclerViewAdapter
import com.yanxing.view.CustomLinearLayoutManager

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_extend_recyclerview.*

/**
 * 扩展RecyclerView,增加禁止上下滑动和滑动到指定位置(参考http://blog.csdn.net/tyzlmjj/article/details/49227601)
 * Created by lishuangxiang on 2016/11/11.
 */
class ExtendRecyclerViewFragment : BaseFragment() {

    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter<String>
    private lateinit var mLayoutManager: CustomLinearLayoutManager
    private var mNeedScroll: Boolean = false
    private val mStrings = ArrayList<String>()
    private var mPosition = 20

    override fun getLayoutResID(): Int {
        return R.layout.activity_extend_recyclerview
    }

    override fun afterInstanceView() {
        for (i in 0..79) {
            mStrings.add(i.toString())
        }
        mLayoutManager = CustomLinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        mRecyclerViewAdapter = object : RecyclerViewAdapter<String>(mStrings, R.layout.adapter_recycler_view) {

            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                val textView = holder.findViewById<View>(R.id.text) as TextView
                textView.text = mDataList[position]
                holder.itemView.setOnClickListener { showToast(getString(R.string.you_click) + (position + 1) + getString(R.string.ge)) }
                holder.itemView.setOnClickListener { showToast(getString(R.string.long_an) + (position + 1) + getString(R.string.ge)) }
            }
        }
        recyclerView.adapter = mRecyclerViewAdapter
        recyclerView.addOnScrollListener(RecyclerViewListener())
        canScroll.setOnClickListener {
            mLayoutManager.setScrollEnable(true)
            recyclerView.layoutManager = mLayoutManager
        }
        noScroll.setOnClickListener {
            mLayoutManager.setScrollEnable(false)
            recyclerView.layoutManager = mLayoutManager
        }
        scrollPosition.setOnClickListener {
            mPosition = 20
            moveToPosition(mPosition)
        }
    }

    /**
     * 滑动监听，当指定位置位于最后可见项的后面时，移动最后的距离
     */
    private inner class RecyclerViewListener : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (mNeedScroll) {
                mNeedScroll = false
                val top = recyclerView.getChildAt(mPosition - mLayoutManager.findFirstVisibleItemPosition()).top
                recyclerView.scrollBy(0, top)
            }
        }
    }

    /**
     * 指定位置置顶
     *
     * @param n
     */
    private fun moveToPosition(n: Int) {
        val firstItem = mLayoutManager.findFirstVisibleItemPosition()
        val lastItem = mLayoutManager.findLastVisibleItemPosition()
        //当要置顶的项在当前显示的第一个项的前面时
        if (n <= firstItem) {
            recyclerView.scrollToPosition(n)
        } else if (n <= lastItem) {//当要置顶的项已经在屏幕上显示时
            val top = recyclerView.getChildAt(n - firstItem).top
            recyclerView.scrollBy(0, top)
        } else {
            //当要置顶的项在当前显示的最后一项的后面时,先显示出来
            recyclerView.scrollToPosition(n)
            mNeedScroll = true
        }
    }
}
