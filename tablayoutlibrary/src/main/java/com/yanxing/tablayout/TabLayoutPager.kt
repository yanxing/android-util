package com.yanxing.tablayout

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
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

    /**
     * 初始化设置选中位置
     */
    private var currentItem = 0

    /**
     * 选中字体大小
     */
    private var selectTextSize: Int? = null

    /**
     * 未选中字体大小
     */
    private var normalTextSize: Int? = null

    /**
     * 是否需要加粗，默认false
     */
    private var bold = false

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        viewPager.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 1) {
            throw Exception("need child view com.google.android.material.tabs.TabLayout")
        }
        if (getChildAt(0) is TabLayout) {
            tabLayout = getChildAt(0) as TabLayout
        } else {
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
            val builder = SpannableStringBuilder()
            builder.append(tabTexts[position])
            if (position == currentItem) {
                selectTextSize?.let {
                    builder.setSpan(AbsoluteSizeSpan(it, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    if (bold) {
                        builder.setSpan(StyleSpan(Typeface.BOLD), 0, builder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                    viewPager.currentItem = currentItem
                }
            } else {
                normalTextSize?.let {
                    builder.setSpan(AbsoluteSizeSpan(it, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    if (bold) {
                        builder.setSpan(StyleSpan(Typeface.NORMAL), 0, builder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                }
            }
            tab.text = builder
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
            val builder = SpannableStringBuilder()
            builder.append(tabTexts[position])
            if (position == currentItem) {
                selectTextSize?.let {
                    builder.setSpan(AbsoluteSizeSpan(it, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    if (bold) {
                        builder.setSpan(StyleSpan(Typeface.BOLD), 0, builder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                    viewPager.currentItem = currentItem
                }
            } else {
                normalTextSize?.let {
                    builder.setSpan(AbsoluteSizeSpan(it, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    if (bold) {
                        builder.setSpan(StyleSpan(Typeface.NORMAL), 0, builder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                }
            }
            tab.text = builder
        }.attach()
    }

    fun setViewPagerScroll(scroll: Boolean) {
        viewPager.isUserInputEnabled = scroll
    }

    fun getViewPager(): ViewPager2 {
        return viewPager
    }

    fun getTabLayout(): TabLayout {
        return tabLayout
    }

    /**
     * 设置滑动时改变选中和非选中字体大小，需要先调用initTextSize方法设置字体大小
     */
    fun changeTabTextSize(onTabSelectedListener: TabLayout.OnTabSelectedListener?) {
        getTabLayout().addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (selectTextSize != null && normalTextSize != null) {
                    setTextSize(getTabLayout(), tab.position, selectTextSize!!, normalTextSize!!, bold)
                }
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
     * 选中和非选中字体大小（适用于选中和非选中字体大小不一样的情况），该方法需要在addTab方法之前调用，否则首次进去界面选中的tab字体没有改变（滑动才会改变）
     * @param selectTextSize 选中字体大小
     * @param normalTextSize 未选中字体大小
     * @param bold true加粗选中的tab文字
     */
    fun initTextSize(selectTextSize: Int, normalTextSize: Int, bold: Boolean) {
        this.selectTextSize = selectTextSize
        this.normalTextSize = normalTextSize
        this.bold = bold
    }

    /**
     * 需要在tab方法之前调用，首次初始化时候（选中和非选中字体大小不一样情况）
     */
    fun setCurrentItem(currentItem: Int) {
        this.currentItem = currentItem
    }


    /**
     * 动态变更tabLayout的字体大小
     * @param tabLayout tabLayout布局
     * @param selectPosition 位置
     * @param selectTextSize 选中字体大小
     * @param normalTextSize 未选中字体大小
     * @param bold true加粗选中的tab文字
     */
    private fun setTextSize(tabLayout: TabLayout, selectPosition: Int, selectTextSize: Int, normalTextSize: Int, bold: Boolean) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.apply {
                val builder = SpannableStringBuilder()
                text = if (selectPosition == i) {
                    builder.append(text ?: "")
                    builder.setSpan(AbsoluteSizeSpan(selectTextSize, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    if (bold) {
                        builder.setSpan(StyleSpan(Typeface.BOLD), 0, builder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                    builder
                } else {
                    builder.append(text ?: "")
                    builder.setSpan(AbsoluteSizeSpan(normalTextSize, true), 0, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    if (bold) {
                        builder.setSpan(StyleSpan(Typeface.NORMAL), 0, builder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                    builder
                }
            }
        }
    }

}