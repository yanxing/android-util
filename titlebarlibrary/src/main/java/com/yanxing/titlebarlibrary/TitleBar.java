package com.yanxing.titlebarlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 标题栏
 * Created by lishuangxiang on 2016/1/13.
 */
public class TitleBar extends LinearLayout {

    //返回
    private LinearLayout mBack;
    //标题
    private TextView mTitle;
    //右文字菜单
    private TextView mRightTitle;
    //标题栏背景色
    private LinearLayout mBackground;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public TitleBar(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.my_title_bar, this);
        mBack = (LinearLayout) findViewById(R.id.back_layout);
        mTitle = (TextView) findViewById(R.id.title);
        mRightTitle = (TextView) findViewById(R.id.right_title);
        mBackground = (LinearLayout) findViewById(R.id.background);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        //标题
        if (a.hasValue(0)){
            mTitle.setText(a.getText(0));
        }
        //左菜单
        if (a.hasValue(1)){
            mRightTitle.setText(a.getText(1));
        }
        //做菜单可见性
        if (a.hasValue(2)){
            mRightTitle.setVisibility(VISIBLE);
        }
        //背景颜色
        if (a.hasValue(R.styleable.MyTitleBar_backgroundColor)){
            Drawable background = a.getDrawable(R.styleable.MyTitleBar_backgroundColor);
            mBackground.setBackgroundDrawable(background);
        }
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }

    /**
     * 点击返回
     *
     * @param onClickListener
     */
    private void setOnClickBackLayout(OnClickListener onClickListener) {
        mBack.setOnClickListener(onClickListener);
    }

    /**
     * 设置标题文字
     */
    private void setTitle(String text) {
        mTitle.setText(text);
    }

    /**
     * 设置标题文字大小
     */
    private void setTitleSize(float size) {
        mTitle.setTextSize(size);
    }

    /**
     * 设置标题居中，默认左边
     */
    private void setTitleCenter() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        mTitle.setLayoutParams(params);
        mTitle.setGravity(Gravity.CENTER);
    }

    /**
     * 设置右菜单文字
     *
     * @param text
     */
    private void setRightTitle(String text) {
        mRightTitle.setText(text);
    }

    /**
     * 设置右菜单文字可见，默认不可见
     *
     * @param visibility
     */
    private void setRightTitleVisibility(int visibility) {
        mRightTitle.setVisibility(visibility);
    }

    /**
     * 右菜单文字点击事件
     *
     * @param onClickListener
     */
    private void setRightTitleClick(OnClickListener onClickListener) {
        mRightTitle.setOnClickListener(onClickListener);
    }
}
