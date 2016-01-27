package com.baidu.map.event;

import com.baidu.mapapi.map.Marker;

/**
 * Marker（覆盖物）拖拽监听回调
 * Created by lishuangxiang on 2016/1/27.
 */
public interface MarkerDragListener {

    /**
     * 被拖拽的过程中
     *
     * @param marker 拖拽的Marker
     */
    void onMarkerDrag(Marker marker);

    /**
     * 拖拽结束
     *
     * @param marker
     */
    void onMarkerDragEnd(Marker marker);

    /**
     * 开始拖拽 Marker
     *
     * @param marker
     */
    void onMarkerDragStart(Marker marker);
}
