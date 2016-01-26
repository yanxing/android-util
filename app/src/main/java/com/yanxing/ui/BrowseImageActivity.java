package com.yanxing.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.photo.browse.widget.CustomPhotoViewPager;
import com.photo.browse.widget.PhotoView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览图片
 * Created by lishuangxiang on 2016/1/26.
 */
@EActivity(R.layout.activity_browse_image)
public class BrowseImageActivity extends FragmentActivity{

    @ViewById(R.id.custom_photo_viewpage)
    CustomPhotoViewPager customPhotoViewPager;

    @ViewById(R.id.number)
    TextView number;//记录当前张数

    private SamplePagerAdapter photoAdapter;

    private List<String> mPathList = new ArrayList<String>();

    @AfterViews
    protected void afterInstanceView() {
        number.getPaint().setFakeBoldText(true);
        Bundle bundle = getIntent().getExtras();
        mPathList = (List<String>) bundle.getSerializable("images");
        int index = bundle.getInt("index", 0);//点击的图片索引
        photoAdapter = new SamplePagerAdapter();
        customPhotoViewPager.setAdapter(photoAdapter);
        if (mPathList.size() == 1) {
            number.setVisibility(View.GONE);
        } else {
            number.setText((index + 1) + "/" + mPathList.size());
        }
        customPhotoViewPager.setCurrentItem(index);
        customPhotoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//设置当前图片第几个
                number.setText((position + 1) + "/" + mPathList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //适配器
    private class SamplePagerAdapter extends PagerAdapter {

        DisplayImageOptions options;
        public SamplePagerAdapter(){
            options=new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .showImageOnLoading(R.anim.loading)
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .cacheInMemory(true)
                    .build();
        }
        @Override
        public int getCount() {
            return mPathList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageLoader.getInstance().displayImage(mPathList.get(position),photoView,options);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BrowseImageActivity.this.finish();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
