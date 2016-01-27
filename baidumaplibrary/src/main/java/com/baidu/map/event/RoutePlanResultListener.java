package com.baidu.map.event;

import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

/**
 * 路线结果回调
 * Created by lishuangxiang on 2016/1/27.
 */
public interface RoutePlanResultListener {
    /**
     * 驾车路线结果回调
     *
     * @param result
     */
    public void onGetDrivingRouteResult(DrivingRouteResult result);

    /**
     * 换乘路线结果回调
     *
     * @param result
     */
    public void onGetTransitRouteResult(TransitRouteResult result);

    /**
     * 步行路线结果回调
     *
     * @param result
     */
    public void onGetWalkingRouteResult(WalkingRouteResult result);
}
