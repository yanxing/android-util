package com.yanxing.ui;

import android.widget.TextView;

import com.yanxing.base.BaseFragment;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager3Fragment extends BaseFragment {

    @BindView(R.id.text)
    TextView mTextView;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tablayoutpager3;
    }

    @Override
    protected void afterInstanceView() {
        EventBus.getDefault().register(this);

    }

    public void onEvent(String content){
        mTextView.setText(content);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
