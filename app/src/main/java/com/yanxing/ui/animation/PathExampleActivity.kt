package com.yanxing.ui.animation

import com.photo.util.AppUtil
import com.yanxing.base.BaseActivity
import com.yanxing.ui.R

/**
 * Path类
 * Created by lishuangxiang on 2016/8/2.
 */
class PathExampleActivity : BaseActivity() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_path_example
    }

    override fun afterInstanceView() {
        AppUtil.setStatusBarDarkMode(true, this)

    }
}
