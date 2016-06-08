package com.amap.location.event;

import com.amap.api.location.AMapLocation;

/**
 * 定位监听
 * Created by lishuangxiang on 2016/6/8.
 */
public interface AMapLocListener {

    void onLocationChanged(AMapLocation aMapLocation);
}
