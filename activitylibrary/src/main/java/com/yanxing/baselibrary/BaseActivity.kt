package com.yanxing.baselibrary

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 基础Activity
 * @author 李双祥 on 2018/7/5.
 */
abstract class BaseActivity : AppCompatActivity(){

    protected lateinit var mFragmentManager: FragmentManager
    val TAG:String=javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResID())
        mFragmentManager = supportFragmentManager
        afterInstanceView()
    }

    /**
     * 显示toast消息
     */
    fun showToast(tip: String?) {
        tip?.let {
            var toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT)
            if (it.length>30){
                toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
            }
            toast.setText(it)
            toast.show()
        }
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    protected abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()

}