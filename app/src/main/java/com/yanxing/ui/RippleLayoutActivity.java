package com.yanxing.ui;

import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.view.SesameView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 点击波纹+芝麻分学习
 * Created by lishuangxiang on 2016/12/5.
 */

public class RippleLayoutActivity extends BaseActivity {

    @BindView(R.id.sesameView)
    SesameView mSesameView;

    @BindView(R.id.edit)
    EditText mEditText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_ripple_layout;
    }

    @Override
    protected void afterInstanceView() {
    }

    @OnClick(R.id.button)
    public void onClick() {
        String score=mEditText.getText().toString().trim();
        mSesameView.setCurrentNum(Integer.parseInt(score.isEmpty()?"0":score));
    }
}
