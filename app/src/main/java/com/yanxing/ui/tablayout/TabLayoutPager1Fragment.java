package com.yanxing.ui.tablayout;

import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager1Fragment extends BaseFragment {

    @BindView(R.id.text)
    TextView mTextView;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tablayoutpager1;
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
