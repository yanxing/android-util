package com.photo.browse.util.gestures;

/**
 * Created by Gavin on 2015/8/24.
 */
public interface OnGestureListener {

    public void onDrag(float dx, float dy);

    public void onFling(float startX, float startY, float velocityX,
                        float velocityY);

    public void onScale(float scaleFactor, float focusX, float focusY);

}
