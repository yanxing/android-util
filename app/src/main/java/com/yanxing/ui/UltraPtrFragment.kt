package com.yanxing.ui


import com.yanxing.adapterlibrary.CommonAdapter
import com.yanxing.adapterlibrary.ViewHolder
import com.yanxing.base.BaseFragment

import java.util.ArrayList

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import kotlinx.android.synthetic.main.fragment_ultra_ptr.*

/**
 * UltraPtr
 * Created by lishuangxiang on 2016/5/30.
 */
class UltraPtrFragment : BaseFragment() {

    private val mList = ArrayList<String>()
    private var mCommonAdapter: CommonAdapter<String>? = null

    override fun getLayoutResID(): Int {
        return R.layout.fragment_ultra_ptr
    }

    override fun afterInstanceView() {
        ptrClassicFrameLayout.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                ptrClassicFrameLayout.postDelayed({
                    load()
                    ptrClassicFrameLayout.refreshComplete()
                }, 1000)
            }
        })
        ptrClassicFrameLayout.autoRefresh(true)
    }

    //javaScript
    fun load() {
        for (i in 0..9) {
            mList.add(i.toString())
        }
        mCommonAdapter = object : CommonAdapter<String>(mList, R.layout.adapter_content) {
            override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
                viewHolder.setText(R.id.name, mDataList[position])
            }
        }
        listview.adapter = mCommonAdapter
    }
}
