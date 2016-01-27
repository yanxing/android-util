package com.baidu.map.event;


import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

/**
 * 地图加载一般回调
 */
public interface BaiduMapListener {

	/**
	 * 地图加载完毕
	 */
	void onMapLoaded();

	/**
	 * 点击覆盖物
	 * @param marker
     */
	void onMarkerClick(Marker marker);

	/**
	 * 地图被移动
	 * @param arg0
     */
	void OnMapChanged(MapStatus arg0);

	/**
	 * 地图点击
	 */
	void OnMapClick();

	/**
	 * 地图单击事件回调函数
	 * @param point
	 */
	void onMapClick(LatLng point);
	
}
