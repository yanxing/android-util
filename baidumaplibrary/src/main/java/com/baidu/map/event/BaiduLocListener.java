package com.baidu.map.event;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 定位接口通知事件类
 */
public interface BaiduLocListener {
	/**
	 * 位置改变通知
	 * @param result
	 * @param geoResult
     */
	void onLocationChanged(boolean result, ReverseGeoCodeResult geoResult);
	void onLocationReceive(BDLocation location);
}
