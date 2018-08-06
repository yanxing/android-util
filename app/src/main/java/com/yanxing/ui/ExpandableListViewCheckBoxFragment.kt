package com.yanxing.ui


import com.yanxing.adapter.ExpandableListCheckAdapter
import com.yanxing.base.BaseFragment
import com.yanxing.model.Child
import com.yanxing.model.Parent

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_expandable_listview_checkbox.*

/**
 * ExpandableListView点击一组，全选这个组
 * Created by lishuangxiang on 2016/7/26.
 */
class ExpandableListViewCheckBoxFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.fragment_expandable_listview_checkbox
    }

    override fun afterInstanceView() {
        expandableListView.setGroupIndicator(null)
        val child1 = Child(false, "西瓜1")
        val child2 = Child(false, "西瓜2")
        val child3 = Child(false, "西瓜3")
        val childList1 = ArrayList<Child>()
        childList1.add(child1)
        childList1.add(child2)
        childList1.add(child3)
        val parent1 = Parent(false, "目录一", childList1)

        val childList2 = ArrayList<Child>()
        val child4 = Child(false, "西瓜4")
        val child5 = Child(false, "西瓜5")
        val child6 = Child(false, "西瓜6")
        childList2.add(child4)
        childList2.add(child5)
        childList2.add(child6)
        val parent2 = Parent(false, "目录二", childList2)

        val childList3 = ArrayList<Child>()
        val child7 = Child(false, "西瓜7")
        val child8 = Child(false, "西瓜8")
        childList3.add(child7)
        childList3.add(child8)
        val parent3 = Parent(false, "目录三", childList3)

        val childList4 = ArrayList<Child>()
        val child9 = Child(false, "西瓜9")
        childList4.add(child9)
        val parent4 = Parent(false, "目录四", childList4)

        val parentList = ArrayList<Parent>()
        parentList.add(parent1)
        parentList.add(parent2)
        parentList.add(parent3)
        parentList.add(parent4)
        val expandableListCheckAdapter = ExpandableListCheckAdapter(activity, parentList)
        expandableListView.setAdapter(expandableListCheckAdapter)
    }
}
