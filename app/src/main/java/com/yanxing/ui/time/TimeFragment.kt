package com.yanxing.ui.time

import android.support.v7.widget.LinearLayoutManager

import com.yanxing.base.BaseFragment
import com.yanxing.commonlibrary.adapter.RecyclerViewAdapter
import com.yanxing.ui.R
import kotlinx.android.synthetic.main.fragment_time.*

import java.util.ArrayList
import java.util.Random


/**
 * 列表定时器一种实现方式
 * Created by lishuangxiang on 2016/12/7.
 */
class TimeFragment : BaseFragment() {

    private var mRecyclerViewAdapter: RecyclerViewAdapter<Order>? = null
    private val mList = ArrayList<Order>()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_time
    }

    override fun afterInstanceView() {
        addTestData()
        recyclerview.layoutManager = LinearLayoutManager(activity)
        mRecyclerViewAdapter = object : RecyclerViewAdapter<Order>(mList, R.layout.adapter_recycler_view) {
            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                holder.setText(R.id.text,mDataList[position].payRemaindSecond.toString())
            }
        }
        recyclerview.adapter = mRecyclerViewAdapter
        OrderCountDown.getInstance().start(mList, mRecyclerViewAdapter)
        refresh.setOnClickListener {
            mList[0].payRemaindSecond=4
            mList[3].payRemaindSecond=3
            mList[6].payRemaindSecond=9
            mList[5].payRemaindSecond=12
            mRecyclerViewAdapter?.notifyDataSetChanged()
        }
    }


    fun addTestData() {
        val random = Random(500L)
        for (i in 0..10) {
            val time = random.nextInt(100)
            val order = Order()
            order.payRemaindSecond = time
            mList.add(order)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        OrderCountDown.getInstance().close()
    }
}
