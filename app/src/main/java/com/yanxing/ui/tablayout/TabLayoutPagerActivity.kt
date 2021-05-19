package com.yanxing.ui.tablayout

import androidx.fragment.app.Fragment
import com.yanxing.base.BaseActivity

import com.yanxing.ui.R
import kotlinx.android.synthetic.main.activity_tablayoutpager.*

import java.util.ArrayList


/**
 * tablayoutpagerlibrary例子
 * Created by lishuangxiang on 2016/3/14.
 */
class TabLayoutPagerActivity : BaseActivity() {


    private val mFragmentList = ArrayList<Fragment>()
    private val mStringList = ArrayList<String>()

    override fun getLayoutResID(): Int {
        return R.layout.activity_tablayoutpager
    }

    override fun afterInstanceView() {
        mFragmentList.add(TabLayoutPager1Fragment())
        mFragmentList.add(TabLayoutPager2Fragment())
        mFragmentList.add(TabLayoutPager3Fragment())
        mStringList.add(getString(R.string.menu1))
        mStringList.add(getString(R.string.menu2))
        mStringList.add(getString(R.string.menu3))
        tabLayoutPager.addTab(this, mFragmentList, mStringList)
    }
}
