package com.yanxing.ui

import com.tencent.bugly.Bugly.applicationContext
import com.yanxing.baselibrary.BaseFragment
import com.yanxing.model.DouBan
import com.yanxing.networklibrary.AbstractObserver
import com.yanxing.networklibrary.RetrofitManage
import com.yanxing.networklibrary.Transformer
import com.yanxing.ui.retrofit.DouBanAPI
import kotlinx.android.synthetic.main.fragment_networklibrary_kt.*

/**
 * Networklibrary kotlin版例子
 * @author 李双祥 on 2018/3/2.
 */
class NetWorkLibraryKtFragment : BaseFragment() {

    val mBaseUrl = "https://api.douban.com/v2/movie/"

    override fun getLayoutResID(): Int {
        return R.layout.fragment_networklibrary_kt
    }

    override fun afterInstanceView() {
        RetrofitManage.getInstance().init(mBaseUrl, true)
        getData()
    }

    /**
     * 获取电影排行
     */
    fun getData() {
        RetrofitManage.getInstance().retrofit!!.create(DouBanAPI::class.java)
                .getTopMovie(0, 10)
                .compose(Transformer<DouBan>().iOMainHasProgress(this, mFragmentManager, "请稍等..."))
                .subscribe(object : AbstractObserver<DouBan>(applicationContext, mFragmentManager, false) {
                    override fun onCall(douBan: DouBan) {

                    }

                    override fun onNext(douBan: DouBan) {
                        super.onNext(douBan)
                        val stringBuilder = StringBuilder()
                        for (i in 0 until douBan.subjects.size) {
                            stringBuilder.append(i.toString())
                            stringBuilder.append(".")
                            stringBuilder.append(douBan.subjects[i].title)
                            stringBuilder.append("\n")
                        }
                        content.setText(stringBuilder.toString())
                    }
                })
    }
}