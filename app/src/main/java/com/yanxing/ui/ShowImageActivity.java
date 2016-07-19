package com.yanxing.ui;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.ConstantValue;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 本地图片选择
 * Created by lishuangxiang on 2016/5/9.
 */
@EActivity(R.layout.activity_show_image)
public class ShowImageActivity extends BaseActivity {

    @ViewById(R.id.simple_drawee_view)
    SimpleDraweeView mSimpleDraweeView;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        String imageName=getIntent().getStringExtra("name");
        Uri uri = Uri.parse(ConstantValue.FILE_CACHE_IMAGE + imageName);
        mSimpleDraweeView.setVisibility(View.VISIBLE);
        mSimpleDraweeView.setImageURI(uri);

    }
}
