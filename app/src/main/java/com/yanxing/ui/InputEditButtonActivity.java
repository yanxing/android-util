package com.yanxing.ui;

import android.view.View;
import android.widget.Button;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.LogUtil;

import butterknife.BindView;

/**
 * Created by lishuangxiang on 2016/8/23.
 */
public class InputEditButtonActivity extends BaseActivity
        implements View.OnLayoutChangeListener {

    @BindView(R.id.next)
    Button mNext;

    @BindView(R.id.root)
    View mRoot;

    @BindView(R.id.edit_layout)
    View mEditLayout;

    private int mKeyHigh = 30;


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_intput_edit_button;
    }

    @Override
    protected void afterInstanceView() {
        mTintManager.setStatusBarTintEnabled(false);
        mRoot.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        LogUtil.d(TAG,bottom+"  "+oldBottom);
        //软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyHigh)) {
            mEditLayout.scrollBy(0,400);
            LogUtil.d(TAG,"键盘谈起");

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyHigh)) {
            LogUtil.d(TAG,"键盘收起");
            mEditLayout.scrollBy(0,-400);
        }
    }
}
