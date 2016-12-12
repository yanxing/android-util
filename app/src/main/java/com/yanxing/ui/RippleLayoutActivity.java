package com.yanxing.ui;

import android.widget.EditText;

import com.yanxing.base.BaseActivity;
import com.yanxing.view.CircleProgressView;
import com.yanxing.view.SesameView;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 点击波纹+芝麻分学习
 * Created by lishuangxiang on 2016/12/5.
 */

public class RippleLayoutActivity extends BaseActivity {

    @BindView(R.id.sesameView)
    SesameView mSesameView;

    @BindView(R.id.circle_progress_view)
    CircleProgressView mCircleProgressView;

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
        Random random=new Random();
        mCircleProgressView.setProgressNotInUiThread(random.nextInt(100));
    }
}
