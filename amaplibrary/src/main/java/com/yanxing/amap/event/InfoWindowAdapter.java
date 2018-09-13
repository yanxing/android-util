package com.yanxing.amap.event;

import android.view.View;

import com.amap.api.maps2d.model.Marker;

/**
 * 自定义InfoWindow
 * @author 李双祥 on 2018/9/13.
 */
public interface InfoWindowAdapter {

    /**
     * 当返回view有效时，将使用自定义的view
     * @param marker
     * @return
     */
    View getInfoWindow(Marker marker);

    /**
     * 当返回view有效时，将使用自定义的view，和上面方法区别，这个方法不会修改默认的背景边框
     * @param marker
     * @return
     */
    View getInfoContents(Marker marker);
}
