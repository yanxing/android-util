package com.yanxing.ui.animation;

import android.animation.TypeEvaluator;

import com.yanxing.model.Point;

/**
 * Created by lishuangxiang on 2016/8/2.
 */
public class PointTypeEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint= (Point) startValue;
        Point endPoint= (Point) endValue;
        float x=startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
        float y=endPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());
        Point point=new Point(x,y);
        return point;
    }
}
