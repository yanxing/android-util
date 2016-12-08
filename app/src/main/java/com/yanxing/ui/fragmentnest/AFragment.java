package com.yanxing.ui.fragmentnest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;
import com.yanxing.util.LogUtil;

/**
 * Created by lishuangxiang on 2016/9/23.
 */

public class AFragment extends BaseFragment {
    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_a;
    }

    @Override
    protected void afterInstanceView() {

    }
}
