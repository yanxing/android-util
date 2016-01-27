package com.baidu.map.event;

import com.baidu.mapapi.map.MapStatus;

/**
 * 地图状态改变回调
 * Created by lishuangxiang on 2016/1/27.
 */
public interface MapStatusChangeListener {

    /**
     * 手势操作地图、设置地图状态等操作导致地图状态开始改变。
     *
     * @param status 地图状态改变开始时的地图状态
     */
    void onMapStatusChangeStart(MapStatus status);

    /**
     * 地图状态变化中
     *
     * @param status 当前地图状态
     */
    void onMapStatusChange(MapStatus status);

    /**
     * 地图状态改变结束
     *
     * @param status 地图状态改变结束后的地图状态
     */
    void onMapStatusChangeFinish(MapStatus status);
}
