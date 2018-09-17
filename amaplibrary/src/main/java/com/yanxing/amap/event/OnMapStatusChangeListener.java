package com.yanxing.amap.event;

import com.amap.api.maps2d.model.CameraPosition;

/**
 * 监听地图可视范围改变
 *
 * @author 李双祥 on 2018/9/17.
 */
public interface OnMapStatusChangeListener {

    /**
     * 可视范围改变时回调此方法。
     *
     * @param cameraPosition 可视范围改变时的CameraPosition对象。
     */
    void onMapStatusChange(CameraPosition cameraPosition);

    /**
     * 用户对地图做出一系列改变地图可视区域的操作（如拖动、动画滑动、缩放）完成之后回调此方法。
     *
     * @param cameraPosition 可视范围改变时的CameraPosition对象。
     */
    void onMapStatusChangeFinish(CameraPosition cameraPosition);

}
