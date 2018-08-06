package com.yanxing.ui

import android.os.Handler
import android.view.MotionEvent
import android.view.View

import com.yanxing.base.BaseActivity

import kotlinx.android.synthetic.main.activity_intput_edit_button.*

/**
 * 编辑框下按钮被键盘遮挡问题，Activity注册添加属性windowSoftInputMode="adjustResize"
 * 当使用沉浸式通知栏，布局文件中根节点没有加android:fitsSystemWindows="true"属性，
 * 窗口不会调整大小，即布局不变；而子节点中有android:fitsSystemWindows="true"属性
 * 布局会出现一片空白（颜色为使用了那个属性的View的背景颜色），看不到其他View，
 * 加不加都会影响通知栏着色（通知栏颜色不能和标题栏颜色一致）。
 *
 * 更新：在一个项目中看到，只需要把Button放到ScrollView外下面（层级并列），就可以实现Button不被输入法遮挡，
 * 不需要写代码移动布局，根节点需要添加android:fitsSystemWindows="true"属性。
 * Created by lishuangxiang on 2016/8/23.
 */
class InputEditButtonActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_intput_edit_button
    }

    override fun afterInstanceView() {
        edit.setOnTouchListener (object:View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                changeScrollView();
                return false;
            }

        })
    }

    private fun changeScrollView() {
        //延迟过短过长，都达不到Button在键盘上面效果，由此另外思路监听View大小变化来判断键盘谈起收起（onsizechanged或
        //onLayoutChange）也需要延迟移动View
        Handler().postDelayed({ scrollView.scrollTo(0, scrollView.height) }, 200)
    }
}
