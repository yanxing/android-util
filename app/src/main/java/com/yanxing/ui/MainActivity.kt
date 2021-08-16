package com.yanxing.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.yanxing.base.BaseActivity
import com.yanxing.util.EventBusUtil
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity() {

    private var mCurrentFragment: Fragment? = null
    private lateinit var mMainFragment: MainFragment

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun afterInstanceView() {
        EventBusUtil.getDefault().register(this)
        mMainFragment = MainFragment()
        mFragmentManager.beginTransaction()
                .add(R.id.main_content, mMainFragment, mMainFragment.TAG)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    @Subscribe
    fun showFragment(fragment: androidx.fragment.app.Fragment) {
        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().remove(mCurrentFragment!!).commit()
            mFragmentManager.beginTransaction().add(R.id.main_content, fragment, fragment.javaClass.name).commit()
        } else {
            mFragmentManager.beginTransaction().add(R.id.main_content, fragment, fragment.javaClass.name).commit()
        }
        mCurrentFragment = fragment
        mFragmentManager.beginTransaction().hide(mMainFragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().remove(mCurrentFragment!!).commit()
            mFragmentManager.beginTransaction().show(mMainFragment).commit()
            mCurrentFragment = null
        } else {
            super.onBackPressed()
        }

    }
}
