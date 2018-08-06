package com.yanxing.ui

import com.yanxing.adapter.RecyclerViewLoadMoreAdapter
import com.yanxing.base.BaseFragment

import java.util.ArrayList

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import kotlinx.android.synthetic.main.fragment_recyclerview_load_more.*

/**
 * RecyclerviewLoadMore
 * Created by 李双祥 on 2017/4/9.
 */
class RecyclerviewLoadMoreFragment : BaseFragment() {


    private val mList = ArrayList<Int>()
    private lateinit var mIntegerRecyclerViewLoadMoreAdapter: RecyclerViewLoadMoreAdapter<Int>
    private var mIndex = 4


    override fun getLayoutResID(): Int {
        return R.layout.fragment_recyclerview_load_more
    }

    override fun afterInstanceView() {
        ptrFrameLayout.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                mList.add(1)
                mList.add(2)
                mList.add(3)
                mIntegerRecyclerViewLoadMoreAdapter = object : RecyclerViewLoadMoreAdapter<Int>(mList, R.layout.adapter_recyclerview_load_more) {
                    override fun onBindViewHolder(holder: RecyclerViewLoadMoreAdapter.MyViewHolder, position: Int) {
                        holder.setText(R.id.text, mList[position].toString())
                    }
                }
                recyclerViewLoadMore.adapter = mIntegerRecyclerViewLoadMoreAdapter
                ptrFrameLayout.refreshComplete()
            }
        })
        ptrFrameLayout.autoRefresh(true)

        /////////////////////下拉刷新
        recyclerViewLoadMore.addOnLoadMoreListener { loadMore() }
    }

    fun loadMore() {
        for (i in mIndex until mIndex + 4) {
            mList.add(i)
        }
        mIndex = mIndex + 4
    }
}
