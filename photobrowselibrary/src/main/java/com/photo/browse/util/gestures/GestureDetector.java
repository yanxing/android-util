package com.photo.browse.util.gestures;

import android.view.MotionEvent;

/**
 * Created by Gavin on 2015/8/24.
 */
public interface GestureDetector {

    public boolean onTouchEvent(MotionEvent ev);

    public boolean isScaling();

    public boolean isDragging();

    public void setOnGestureListener(OnGestureListener listener);
}
