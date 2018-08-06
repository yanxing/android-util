package com.yanxing.ui

import android.graphics.drawable.Animatable
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.yanxing.adapterlibrary.RecyclerViewAdapter
import com.yanxing.base.BaseFragment

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_fresco_example.*
import me.relex.photodraweeview.PhotoDraweeView

/**
 * fresco+PhotoDraweeView使用
 * Created by lishuangxiang on 2016/1/28.
 */
class FrescoFragment : BaseFragment() {


    private var mCommonAdapter: RecyclerViewAdapter<String>? = null
    private val mList = ArrayList<String>()

    override fun getLayoutResID(): Int {
        return R.layout.activity_fresco_example
    }

    override fun afterInstanceView() {
        mList.add("http://moviepic.manmankan.com/yybpic/xinwen/2015/201512/02/14490288511739759.gif")
        mCommonAdapter = object : RecyclerViewAdapter<String>(mList, R.layout.gridview_adapter) {
            override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
                val url = mDataList[position]
                val uri = Uri.parse(url)
                val photoDraweeView = holder.findViewById<View>(R.id.simple_drawee_view) as PhotoDraweeView
                val controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setOldController(photoDraweeView.controller)
                        .setAutoPlayAnimations(true)
                controller.setControllerListener(object : BaseControllerListener<ImageInfo>() {
                    override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                        super.onFinalImageSet(id, imageInfo, animatable)
                        if (imageInfo == null) {
                            return
                        }
                        photoDraweeView.update(imageInfo.width, imageInfo.height)
                    }
                })
                photoDraweeView.controller = controller.build()
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mCommonAdapter
    }
}
