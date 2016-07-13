package com.yanxing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * 对话框activity，4.4.系统
 * Created by lishuangxiang on 2016/7/13.
 */
@EActivity(R.layout.activity_dialog)
public class DialogActivity extends BaseActivity {


    @AfterViews
    @Override
    protected void afterInstanceView() {

    }

    /**
     * 重绘使本activity宽度占据手机屏幕1/4
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //获取手机屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //改变activity尺寸
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        lp.width = dm.widthPixels;
        getWindowManager().updateViewLayout(view, lp);
    }

    /**
     * 点击屏幕其他地方，对话框消失
     */
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
