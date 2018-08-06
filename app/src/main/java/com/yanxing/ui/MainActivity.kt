package com.yanxing.ui

import com.yanxing.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun afterInstanceView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, MainFragment(), MainFragment::class.java.name)
                .commit()
    }
}
