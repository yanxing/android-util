package com.yanxing.amap.event;

import com.amap.api.location.AMapLocation;

/**
 * 定位监听
 * Created by lishuangxiang on 2016/6/8.
 */
public interface OnMapLocationListener {

    void onLocationChanged(AMapLocation aMapLocation);
}
