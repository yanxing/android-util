package com.yanxing.ui.tablayout;


import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;
import com.yanxing.util.EventBusUtil;
import com.yanxing.util.LogUtil;


/**
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager1Fragment extends BaseFragment {


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tablayoutpager1;
    }

    @Override
    protected void afterInstanceView() {
    }


    @Override
    public void onResume() {
        LogUtil.d("TabLayoutPager","0可见");
        super.onResume();
    }

}
