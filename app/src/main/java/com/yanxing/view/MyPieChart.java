package com.yanxing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.PieChart;

/**
 *
 * @author 李双祥 on 2019/8/26.
 */
public class MyPieChart extends PieChart {

    int lastX;
    int lastY;

    public MyPieChart(Context context) {
        super(context);
    }

    public MyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        lastX = (int) event.getRawX();
        lastY = (int) event.getRawY();

        //获取控件在屏幕的位置
        int[] location = new int[2];
        getLocationOnScreen(location);

        //控件相对于屏幕的x与y坐标
        int x = location[0];
        int y = location[1];

        //圆半径 通过左右坐标计算获得getLeft
        int r = (getRight()-getLeft())/2;

        //圆心坐标
        int vCenterX = x+r;
        int vCenterY = y+r;

        //点击位置x坐标与圆心的x坐标的距离
        int distanceX = Math.abs(vCenterX-lastX);
        //点击位置y坐标与圆心的y坐标的距离
        int distanceY = Math.abs(vCenterY-lastY);
        //点击位置与圆心的直线距离
        int distanceZ = (int) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));

        //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
        if(distanceZ > r){
            return false;
        }
        return super.dispatchTouchEvent(event);
    }
}
