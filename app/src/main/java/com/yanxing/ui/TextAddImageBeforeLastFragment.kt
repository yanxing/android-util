package com.yanxing.ui

import android.graphics.BitmapFactory
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan

import com.yanxing.base.BaseFragment

import kotlinx.android.synthetic.main.activity_text_image.*

/**
 * TextView前后添加图标
 * http://blog.csdn.net/yanzi1225627/article/details/24590029
 * Created by lishuangxiang on 2016/8/25.
 */
class TextAddImageBeforeLastFragment : BaseFragment() {


    override fun getLayoutResID(): Int {
        return R.layout.activity_text_image
    }

    override fun afterInstanceView() {
        //前面,如果保留图片大小，用new ImageSpan（this，bitmap）
        val start = ImageSpan(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
        val str = "TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后" +
                "添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片" +
                "TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片"
        val ss = SpannableString(str)
        ss.setSpan(start, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        text.text = ss
        //后面
        val endImageSpan = ImageSpan(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
        val end = SpannableString(" ")
        end.setSpan(endImageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        text.append(end)
    }
}
