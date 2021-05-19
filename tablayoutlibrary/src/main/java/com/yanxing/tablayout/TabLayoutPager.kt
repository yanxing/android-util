package com.yanxing.tablayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yanxing.adapter.MyFragmentStateAdapter
import com.yanxing.tablayoutlibrary.R

/**
 * 封装TabLayout+viewpager2，fragment懒加载,可见onResume中
 */
class TabLayoutPager(context: Context, attrs: AttributeSet?, defStyle: Int) :
    FrameLayout(context, attrs, defStyle) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        LayoutInflater.from(context).inflate(R.layout.tablayoutpager, this)
            .apply {
                viewPager = findViewById(R.id.viewPager)
                tabLayout = findViewById(R.id.tabLayout)
            }
        context.obtainStyledAttributes(attrs, R.styleable.TabLayoutPager)
            .apply {
                tabLayout.setSelectedTabIndicatorColor(
                    getColor(R.styleable.TabLayoutPager_tabLayoutIndicatorColor,
                        resources.getColor(R.color.tablayout_black)
                    ))
                //选项卡文字颜色
                val noSelected = getColor(
                    R.styleable.TabLayoutPager_tabLayoutTextColor,
                    resources.getColor(R.color.tablayout_colorGray)
                )
                val selected = getColor(
                    R.styleable.TabLayoutPager_tabLayoutSelectedTextColor,
                    resources.getColor(R.color.tablayout_black)
                )
                tabLayout.setTabTextColors(noSelected, selected)

                if (hasValue(R.styleable.TabLayoutPager_tabLayoutBackground)) {
                    tabLayout.setBackgroundResource(
                        getResourceId(R.styleable.TabLayoutPager_tabLayoutBackground,
                            resources.getColor(R.color.tablayout_white)))
                }
                if (hasValue(R.styleable.TabLayoutPager_tabLayoutHigh)) {
                    tabLayout.layoutParams.height =
                        getDimension(R.styleable.TabLayoutPager_tabLayoutHigh, 40f).toInt()
                }
            }.recycle()
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
}