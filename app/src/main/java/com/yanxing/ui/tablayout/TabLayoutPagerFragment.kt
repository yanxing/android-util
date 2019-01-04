package com.yanxing.ui.tablayout

import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment

import com.yanxing.base.BaseFragment
import com.yanxing.ui.R

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_tablayoutpager.*

/**
 * tablayoutpagerlibrary例子
 * Created by lishuangxiang on 2016/3/14.
 */
class TabLayoutPagerFragment : BaseFragment() {


    private val mFragmentList = ArrayList<androidx.fragment.app.Fragment>()
    private val mStringList = ArrayList<String>()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_tablayoutpager
    }

    override fun afterInstanceView() {
        mFragmentList.add(TabLayoutPager1Fragment())
        mFragmentList.add(TabLayoutPager2Fragment())
        mFragmentList.add(TabLayoutPager3Fragment())
        mStringList.add(getString(R.string.menu1))
        mStringList.add(getString(R.string.menu2))
        mStringList.add(getString(R.string.menu3))
        tabLayoutPager.addTab(fragmentManager, mFragmentList, mStringList)
        tabLayoutPager.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabLayoutPager.viewPager.currentItem = tab.position
                showToast(getString(R.string.di) + (tab.position + 1) + getString(R.string.ge))

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}
