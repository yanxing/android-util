package com.yanxing.amap.event;


import com.amap.api.maps2d.model.Marker;

/**
 * Marker（覆盖物）拖拽监听回调
 * Created by lishuangxiang on 2016/1/27.
 */
public interface OnMarkerDragListener {

    /**
     * 开始拖拽 Marker，这个marker的位置可以通过getPosition()方法返回，
     * 这个位置可能与拖动的之前的marker位置不一样
     *
     * @param marker
     */
    void onMarkerDragStart(Marker marker);

    /**
     * 被拖拽的过程中，这个marker的位置可以通过getPosition()方法返回，
     * 这个位置可能与拖动的之前的marker位置不一样
     *
     * @param marker 拖拽的Marker
     */
    void onMarkerDrag(Marker marker);

    /**
     * 拖拽结束，这个marker的位置可以通过getPosition()方法返回，
     * 这个位置可能与拖动的之前的marker位置不一样
     *
     * @param marker
     */
    void onMarkerDragEnd(Marker marker);
}
