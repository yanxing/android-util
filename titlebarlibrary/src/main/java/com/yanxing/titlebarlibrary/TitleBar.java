package com.yanxing.titlebarlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 标题栏
 * Created by lishuangxiang on 2016/1/13.
 */
public class TitleBar extends LinearLayout {

    //返回
    private LinearLayout mBack;
    private ImageView mBackImg;
    //标题
    private TextView mTitle;
    //右文字菜单
    private TextView mRightTitle;
    //标题栏背景色
    private LinearLayout mBackground;
    //右菜单图标
    private ImageView mRightIcon;

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
        mBackImg= (ImageView) findViewById(R.id.back_img);
        mRightIcon= (ImageView) findViewById(R.id.right_icon);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        //标题
        if (a.hasValue(R.styleable.MyTitleBar_title_main)){
            mTitle.setText(a.getText(R.styleable.MyTitleBar_title_main));
        }
        //标题居中true
        if (a.getBoolean(R.styleable.MyTitleBar_title_main_center,false)){
            setTitleCenter();
        }
        //标题颜色
        if (a.hasValue(R.styleable.MyTitleBar_title_main_color)){
            mTitle.setTextColor(a.getColor(R.styleable.MyTitleBar_title_main_color,0));
        }
        //右菜单
        if (a.hasValue(R.styleable.MyTitleBar_title_right)){
            mRightTitle.setText(a.getText(R.styleable.MyTitleBar_title_right));
            mRightTitle.setVisibility(VISIBLE);
        }
        //右菜单文字颜色
        if (a.hasValue(R.styleable.MyTitleBar_titleRightColor)){
            mTitle.setTextColor(a.getColor(R.styleable.MyTitleBar_titleRightColor,0));
        }
        //背景颜色
        if (a.hasValue(R.styleable.MyTitleBar_backgroundColor)){
            Drawable background = a.getDrawable(R.styleable.MyTitleBar_backgroundColor);
            mBackground.setBackgroundDrawable(background);
        }
        //返回图片不可见false,默认可见true
        if (!a.getBoolean(R.styleable.MyTitleBar_back_img_visible,true)){
            mBackImg.setVisibility(INVISIBLE);
        }

        //返回图片资源
        if (a.hasValue(R.styleable.MyTitleBar_back_img_resource)){
            Drawable background = a.getDrawable(R.styleable.MyTitleBar_back_img_resource);
            mBackImg.setBackgroundDrawable(background);
        }

        //右菜单图片
        if (a.hasValue(R.styleable.MyTitleBar_right_icon)){
            Drawable background = a.getDrawable(R.styleable.MyTitleBar_right_icon);
            mRightIcon.setVisibility(VISIBLE);
            mRightIcon.setBackgroundDrawable(background);
        }

        //返回图片可见，才有点击事件
        if (a.getBoolean(R.styleable.MyTitleBar_back_img_visible,true)){
            mBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
        }
    }

    /**
     * 点击返回
     *
     * @param onClickListener
     */
    public void setOnClickBackLayout(OnClickListener onClickListener) {
        mBack.setOnClickListener(onClickListener);
    }

    /**
     * 设置标题文字
     */
    public void setTitle(String text) {
        mTitle.setText(text);
    }

    /**
     * 设置标题文字颜色
     */
    public void setTitleColor(int  color) {
        mTitle.setTextColor(color);
    }

    /**
     * 设置标题文字大小
     */
    public void setTitleSize(float size) {
        mTitle.setTextSize(size);
    }

    /**
     * 设置标题居中，默认左边
     */
    public void setTitleCenter() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 0, 0);
        mTitle.setLayoutParams(lp);
        mTitle.setGravity(Gravity.CENTER);
    }

    /**
     * 设置右菜单文字
     *
     * @param text
     */
    public void setRightTitle(String text) {
        mRightTitle.setText(text);
    }

    /**
     * 设置右菜单文字可见，默认不可见
     *
     * @param visibility
     */
    public void setRightTitleVisibility(int visibility) {
        mRightTitle.setVisibility(visibility);
    }

    /**
     * 右菜单文字点击事件
     *
     * @param onClickListener
     */
    public void setRightTitleClick(OnClickListener onClickListener) {
        mRightTitle.setOnClickListener(onClickListener);
    }

    /**
     * 点击右图片
     *
     * @param onClickListener
     */
    public void setRightIconClick(OnClickListener onClickListener) {
        mRightIcon.setOnClickListener(onClickListener);
    }
}
