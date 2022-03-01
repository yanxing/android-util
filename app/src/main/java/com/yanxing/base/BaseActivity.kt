package com.yanxing.base

import androidx.fragment.app.Fragment
import android.os.Bundle
import com.yanxing.util.CommonUtil

import com.yanxing.view.LoadDialog
import com.yanxing.util.StatusBarColorUtil

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
abstract class BaseActivity : com.yanxing.baselibrary.BaseActivity() {

    private var mCurrentFragment: Fragment? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //StatusBarColorUtil.setStatusBarDarkIcon(this,true)
    }

    /**
     * 显示加载框
     */
    protected fun showLoadingDialog() {
        showLoadingDialog(null)
    }

    /**
     * 显示加载框,带文字提示
     */
    fun showLoadingDialog(msg: String?) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit()
        } else {
            val loadDialog = LoadDialog()
            if (msg != null) {
                val bundle = Bundle()
                bundle.putString("tip", msg)
                loadDialog.arguments = bundle
            }
            loadDialog.show(fragmentTransaction, LoadDialog.TAG)
        }
    }

    /**
     * 添加新的Fragment
     */
    protected fun add(fragment: androidx.fragment.app.Fragment, id: Int) {
        val tag = fragment.javaClass.toString()
        mFragmentManager
            .beginTransaction()
            .add(id, fragment, tag)
            .commitAllowingStateLoss()
    }

    /**
     * 切换Fragment 不销毁，隐藏显示
     * @param to
     * @param ViewId
     */
    protected  fun switchFragment(to: Fragment, ViewId: Int) {
        if (mCurrentFragment !== to) {
            val transaction = mFragmentManager.beginTransaction()
            if (mCurrentFragment != null) {
                val fragmentLit = mFragmentManager.fragments
                if (!CommonUtil.isEmpty(fragmentLit)) {
                    //共用一个fragment情况下bug问题
                    for (i in fragmentLit.indices) {
                        if (fragmentLit[i] != null) {
                            transaction.hide(fragmentLit[i]!!)
                        }
                    }
                }
            }
            if (!to.isAdded) {
                transaction.add(ViewId, to)
            } else {
                transaction.show(to)
            }
            mCurrentFragment = to
            transaction.commitNowAllowingStateLoss()
        }
    }


    /**
     * 隐藏加载框
     */
    fun dismissLoadingDialog() {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            //移除正在显示的对话框
            fragmentTransaction.remove(fragment).commitNow()
        }
    }
}
