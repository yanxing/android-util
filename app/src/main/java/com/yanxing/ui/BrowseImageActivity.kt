package com.yanxing.ui

import android.graphics.drawable.Animatable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.yanxing.base.BaseActivity
import com.yanxing.util.ConstantValue

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_browse_image.*
import me.relex.photodraweeview.OnPhotoTapListener
import me.relex.photodraweeview.PhotoDraweeView

/**
 * 类似微博图片浏览（Fresco+photodraweeview）
 * Created by lishuangxiang on 2017/1/5.
 */
class BrowseImageActivity : BaseActivity() {


    private var mImageUrls: List<String> = ArrayList()

    override fun getLayoutResID(): Int {
        return R.layout.activity_browse_image
    }

    override fun afterInstanceView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val bundle = intent.extras
        if (bundle != null) {
            mImageUrls = bundle.getStringArrayList("imageUrl")
            val index = bundle.getInt("currentIndex")
            if (index >= mImageUrls.size) {
                throw IndexOutOfBoundsException("currentIndex must to <imageUrl.size")
            } else {
                if (mImageUrls.size == 1) {
                    currentPage.visibility = View.INVISIBLE
                } else {
                    currentPage.text = (index + 1).toString() + "/" + mImageUrls!!.size
                }
                viewPager.adapter = DraweePagerAdapter()
                viewPager.currentItem = index
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                    override fun onPageSelected(position: Int) {
                        currentPage.text = (position + 1).toString() + "/" + mImageUrls!!.size
                    }

                    override fun onPageScrollStateChanged(state: Int) {}
                })
            }
        }
        menu.setOnClickListener {
            showToast(getString(R.string.this_is_menu))
        }
    }

    inner class DraweePagerAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return mImageUrls.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(viewGroup: ViewGroup, position: Int): Any {
            val photoDraweeView = PhotoDraweeView(viewGroup.context)
            val controller = Fresco.newDraweeControllerBuilder()
                    //先加载中等图，再加载原图
                    .setLowResImageRequest(ImageRequest.fromUri(mImageUrls[position]
                            .replace(ConstantValue.THUMBNAIL_PIC.toRegex(), ConstantValue.BMIDDLE_PIC)))
                    .setImageRequest(ImageRequest.fromUri(mImageUrls[position]
                            .replace(ConstantValue.THUMBNAIL_PIC.toRegex(), ConstantValue.ORIGINAL_PIC)))
                    .setAutoPlayAnimations(true)
                    .setOldController(photoDraweeView.controller)
            controller.controllerListener = object : BaseControllerListener<ImageInfo>() {
                override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                    super.onFinalImageSet(id, imageInfo, animatable)
                    if (imageInfo == null) {
                        return
                    }
                    photoDraweeView.update(imageInfo.width, imageInfo.height)
                }
            }
            photoDraweeView.controller = controller.build()
            photoDraweeView.onPhotoTapListener = OnPhotoTapListener { view, x, y -> finish() }

            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return photoDraweeView
        }
    }
}
