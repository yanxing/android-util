package com.yanxing.ui.swipebacklayout;

import android.content.Intent;
import android.view.View;

import com.yanxing.ui.R;
import com.yanxing.util.CommonUtil;

import butterknife.OnClick;

/**
 * SwipeBackLayout学习
 * Created by lishuangxiang on 2016/11/17.
 */

public class SwipeBackLayoutActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_swipe_back_layout;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(true,this);
    }

    @OnClick({R.id.normal, R.id.absListview, R.id.scrollView})
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.normal:
                intent.setClass(getApplicationContext(),NormalActivity.class);
                startActivity(intent);
                break;
            case R.id.absListview:
                intent.setClass(getApplicationContext(),AbsListViewActivity.class);
                startActivity(intent);

                break;
            case R.id.scrollView:
                intent.setClass(getApplicationContext(),ScrollViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
