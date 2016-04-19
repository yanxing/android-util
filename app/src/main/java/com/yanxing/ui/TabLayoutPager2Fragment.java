package com.yanxing.ui;

import android.widget.TextView;

import com.yanxing.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

/**
 * Created by lishuangxiang on 2016/3/14.
 */
@EFragment(R.layout.fragment_tablayoutpager2)
public class TabLayoutPager2Fragment extends BaseFragment {

    @ViewById(R.id.text)
    TextView mTextView;

    @AfterViews
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
