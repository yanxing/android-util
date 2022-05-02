package com.yanxing.tablayout

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yanxing.adapter.MyFragmentStateAdapter


/**
 * 封装TabLayout+viewpager2，fragment懒加载,可见onResume中
 */
class TabLayoutPager(context: Context, attrs: AttributeSet?, defStyle: Int) :
    LinearLayout(context, attrs, defStyle) {

    private var viewPager: ViewPager2 = ViewPager2(context)
    private lateinit var tabLayout: TabLayout

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        viewPager.layoutParams=LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount!=1){
            throw Exception("need child view com.google.android.material.tabs.TabLayout")
        }
        if (getChildAt(0) is TabLayout){
            tabLayout=getChildAt(0) as TabLayout
        }else{
            throw Exception("need child view com.google.android.material.tabs.TabLayout")
        }

        addView(viewPager)
    }

    fun addTab(activity: FragmentActivity, fragments: List<Fragment>, tabTexts: List<String>) {
        for (element in tabTexts) {
            tabLayout.addTab(tabLayout.newTab())
        }
        val tabLayoutPager = MyFragmentStateAdapter(fragments, activity.supportFragmentManager,
            LifecycleRegistry(activity).apply {
                currentState = Lifecycle.Event.ON_RESUME.targetState
            })
        viewPager.adapter = tabLayoutPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTexts[position]
        }.attach()
    }

    fun addTab(fragment: Fragment, fragments: List<Fragment>, tabTexts: List<String>) {
        for (element in tabTexts) {
            tabLayout.addTab(tabLayout.newTab())
        }
        val tabLayoutPager = MyFragmentStateAdapter(fragments, fragment.childFragmentManager,
            LifecycleRegistry(fragment).apply {
                currentState = Lifecycle.Event.ON_RESUME.targetState
            })
        viewPager.adapter = tabLayoutPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTexts[position]
        }.attach()
    }

    fun setViewPagerScroll(scroll: Boolean) {
        viewPager.isUserInputEnabled = scroll
    }

    fun getViewPager(): ViewPager2 {
        return viewPager
    }

    fun getTabLayout():TabLayout{
        return tabLayout
    }

    /**
     * 设置选中和非选中字体大小
     */
    fun setTextStyle(selectTextSize: Int, normalTextSize: Int, onTabSelectedListener: TabLayout.OnTabSelectedListener?) {
        getTabLayout().addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setTextSize(getTabLayout(),tab.position,selectTextSize, normalTextSize)
                onTabSelectedListener?.onTabSelected(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                onTabSelectedListener?.onTabUnselected(tab)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelectedListener?.onTabReselected(tab)
            }
        })
    }

    /**
     * 动态变更tabLayout的字体大小
     * @param tabLayout tabLayout布局
     * @param selectPosition 位置
     * @param selectTextSize 选中字体大小
     * @param normalTextSize 未选中字体大小
     */
    private fun setTextSize(tabLayout: TabLayout, selectPosition: Int, selectTextSize: Int, normalTextSize: Int) {
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            if (tab != null) {
                val builder = SpannableStringBuilder()
                val text = if (tab.text == null) "" else tab.text.toString()
                if (selectPosition == i) {
                    builder.append(text)
                    builder.setSpan(AbsoluteSizeSpan(selectTextSize, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    tab.text = builder
                } else {
                    builder.append(text)
                    builder.setSpan(AbsoluteSizeSpan(normalTextSize, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    tab.text = builder
                }
            }
        }
    }

}