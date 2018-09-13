package com.yanxing.amap.event;

import com.amap.api.maps2d.model.Marker;

/**
 * 监听InfoWindow点击
 * @author 李双祥 on 2018/9/13.
 */
public interface OnInfoWindowClickListener {

    void onInfoWindowClick(Marker marker);
}
