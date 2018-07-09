package com.yanxing.ui.swipebacklayout;

import android.widget.ScrollView;

import com.yanxing.ui.R;
import com.yanxing.view.SwipeBackLayout;

import butterknife.BindView;

/**
 * Created by lishuangxiang on 2016/11/17.
 */
public class ScrollViewActivity extends BaseActivity {

    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_scroll_view;
    }

    @Override
    protected void afterInstanceView() {
        mSwipeBackLayout.addOnSlidingFinishListener(new SwipeBackLayout.OnSlidingFinishListener() {
            @Override
            public void onSlidingFinish() {
                finish();
            }
        });
        mSwipeBackLayout.setTouchView(mScrollView);
    }
}
