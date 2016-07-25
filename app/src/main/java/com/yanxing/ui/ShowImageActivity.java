package com.yanxing.ui;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.ConstantValue;

import butterknife.BindView;

/**
 * 本地图片选择
 * Created by lishuangxiang on 2016/5/9.
 */
public class ShowImageActivity extends BaseActivity {

    @BindView(R.id.simple_drawee_view)
    SimpleDraweeView mSimpleDraweeView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void afterInstanceView() {
        String imageName=getIntent().getStringExtra("name");
        Uri uri = Uri.parse(ConstantValue.FILE_CACHE_IMAGE + imageName);
        mSimpleDraweeView.setVisibility(View.VISIBLE);
        mSimpleDraweeView.setImageURI(uri);
    }
}
