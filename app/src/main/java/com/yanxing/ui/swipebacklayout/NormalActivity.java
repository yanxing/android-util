package com.yanxing.ui.swipebacklayout;

import com.yanxing.ui.R;
import com.yanxing.util.CommonUtil;
import com.yanxing.view.SwipeBackLayout;

import butterknife.BindView;

/**
 * Created by lishuangxiang on 2016/11/17.
 */

public class NormalActivity extends BaseActivity {

    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_normal;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(true,this);
        mSwipeBackLayout.addOnSlidingFinishListener(new SwipeBackLayout.OnSlidingFinishListener() {

            @Override
            public void onSlidingFinish() {
                finish();
            }
        });
        mSwipeBackLayout.setTouchView(mSwipeBackLayout);

    }
}
