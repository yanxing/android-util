package com.yanxing.ui;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanxing.base.BaseActivity;


import butterknife.BindView;

/**
 * 本地图片选择
 * Created by lishuangxiang on 2016/5/9.
 */
public class ShowImageActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mImageView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void afterInstanceView() {
        String imageName=getIntent().getStringExtra("name");
        ImageLoader.getInstance().displayImage("file://"+imageName,mImageView);
    }
}
