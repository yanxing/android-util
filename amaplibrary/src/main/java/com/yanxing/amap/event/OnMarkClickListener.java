package com.yanxing.amap.event;

import com.amap.api.maps2d.model.Marker;

/**
 * mark点击事件
 *
 * @author 李双祥 on 2018/9/13.
 */
public interface OnMarkClickListener {

    boolean onMarkClick(Marker marker);
}
