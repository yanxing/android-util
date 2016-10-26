package com.yanxing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by lishuangxiang on 2016/10/26.
 */

public class MyListView extends ListView {

    private int mLastInterceptX;
    private int mLastInterCeptY;
    private static final int BRAKE=1;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted=false;
        int x= (int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted=false;
                break;
            case MotionEvent.ACTION_MOVE:
                //拦截
                if (x==mLastInterceptX||y==mLastInterCeptY){
                    intercepted=true;
                }else {
                    intercepted=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted=false;
                break;
        }
        mLastInterceptX=x;
        mLastInterCeptY=y;
        return intercepted;
    }
}
