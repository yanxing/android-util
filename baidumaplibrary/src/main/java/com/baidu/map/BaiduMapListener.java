package com.baidu.map;

import android.graphics.Bitmap;

import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.SuggestionResult;

//地图接口通知事件类
public interface BaiduMapListener {

	// 地图加载完毕
	public void onMapLoaded();

	// 搜索结果回调
	public void onPoiSearched(PoiResult result);
	// 搜索结果回调
	public void onPoiDetailSearched(PoiDetailResult result);
	
	// 搜索结果回调
	public void onSuggestionSearched(SuggestionResult result);	

	// 点击覆盖物
	public void onMarkerClick(Marker marker);

	// 截图完成
	public void onSnapshotReady(Bitmap snapshot);
	
	//地图被移动
	public void OnMapChanged(MapStatus arg0);
	
	//地图点击
	public void OnMapClick();

	/**
	 * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
	 * @param status 地图状态改变开始时的地图状态
	 */
	public void onMapStatusChangeStart(MapStatus status);

	/**
	 * 地图状态变化中
	 * @param status 当前地图状态
	 */
	public void onMapStatusChange(MapStatus status);

	/**
	 * 地图状态改变结束
	 * @param status 地图状态改变结束后的地图状态
	 */
	public void onMapStatusChangeFinish(MapStatus status);
	/**
	 * 驾车路线结果回调
	 * @param result
	 */
	public void	onGetDrivingRouteResult(DrivingRouteResult result);
	/**
	 * 换乘路线结果回调
	 * @param result
	 */
	public void	onGetTransitRouteResult(TransitRouteResult result);
	/**
	 * 步行路线结果回调
	 * @param result
	 */
	public void	onGetWalkingRouteResult(WalkingRouteResult result);
	/**
	 * 地图单击事件回调函数
	 * @param point
	 */
	void	onMapClick(LatLng point);
	/**
	 * 地图内 Poi 单击事件回调函数
	 * @param poi
	 * @return
	 */
	boolean	onMapPoiClick(MapPoi poi);
	/**
	 * 
	 * @param marker Marker 被拖拽的过程中。
	 */
	void	onMarkerDrag(Marker marker);
	/**
	 * 
	 * @param marker Marker 拖拽结束
	 */
	void	onMarkerDragEnd(Marker marker);
	/**
	 * 开始拖拽 Marker
	 * @param marker
	 */
	void onMarkerDragStart(Marker marker);
	
}
