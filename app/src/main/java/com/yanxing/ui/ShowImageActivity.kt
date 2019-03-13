package com.yanxing.ui


import com.yanxing.base.BaseActivity
import kotlinx.android.synthetic.main.activity_show_image.*


/**
 * 本地图片选择
 * Created by lishuangxiang on 2016/5/9.
 */
class ShowImageActivity : BaseActivity() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_show_image
    }

    override fun afterInstanceView() {
        val imageName = intent.getStringExtra("name")
        image.setImageURI("file://$imageName")
    }
}
