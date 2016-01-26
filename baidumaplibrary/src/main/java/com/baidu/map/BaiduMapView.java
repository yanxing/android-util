package com.baidu.map;


import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

public class BaiduMapView implements OnGetPoiSearchResultListener, OnMapStatusChangeListener, OnMapClickListener, OnGetSuggestionResultListener, OnGetRoutePlanResultListener,OnMarkerDragListener{
	public MapView mMapView;
	public BaiduMap mBaiduMap;
	public LinearLayout mParentLayout;
	public PoiSearch mPoiSearch;
	public SuggestionSearch mSuggestionSearch;
	public Activity mParentActivity;
	public BaiduMapListener mMapListener;
	public float maxZoomLevel, minZoomLevel;
	public RoutePlanSearch mSearch ;
	public BaiduMapView(Activity activity, LinearLayout parentLayout, BaiduMapListener mapListener){
		mParentActivity = activity;
		mParentLayout = parentLayout;
		mMapListener = mapListener;
		try {
			mMapView = new MapView(activity);
			mParentLayout.setVisibility(View.VISIBLE);
			mParentLayout.addView(mMapView);
			mMapView.showZoomControls(false);
			mBaiduMap = mMapView.getMap();
			maxZoomLevel = mBaiduMap.getMaxZoomLevel();
			minZoomLevel = mBaiduMap.getMinZoomLevel();
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
			mBaiduMap.setMapStatus(msu);

			mBaiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

				public void onMapLoaded() {
					mMapListener.onMapLoaded();
				}
			});
			mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

				public boolean onMarkerClick(Marker marker) {
					mMapListener.onMarkerClick(marker);
					return true;
				}
			});
			
			mBaiduMap.setOnMarkerDragListener(this);
			mBaiduMap.setOnMapClickListener(this);
			mBaiduMap.setOnMapStatusChangeListener(this);

            mBaiduMap.setMyLocationEnabled(true);
			mPoiSearch = PoiSearch.newInstance();
			mPoiSearch.setOnGetPoiSearchResultListener(this);

			mSuggestionSearch = SuggestionSearch.newInstance();
			mSuggestionSearch.setOnGetSuggestionResultListener(this);
			mSearch = RoutePlanSearch.newInstance();
			mSearch.setOnGetRoutePlanResultListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setListener(BaiduMapListener listenter) {
		mMapListener = listenter;
	}
	public void setCenterOnly(double latitude, double longitude) {
		LatLng location = new LatLng(latitude, longitude);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(location));
	}
	/**
	 * <li>设置覆盖物
	 * @param latitude  纬度
	 * @param longitude 经度
	 * @param resid 
	 */
	public Overlay setOverlay(double latitude, double longitude, int resid) {
		Overlay overlay = null;
		try {
			BitmapDescriptor bd= BitmapDescriptorFactory.fromResource(resid);
			if(bd == null){
				return overlay;
			}
			LatLng location = new LatLng(latitude, longitude);
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(location));
			OverlayOptions oop = new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bd).zIndex(9).draggable(true);
			overlay = mBaiduMap.addOverlay(oop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overlay;
	}

	/**
	 * <li>设置覆盖物,不会触发MapStatus事件
	 * @param latitude  纬度
	 * @param longitude 经度
	 * @param resid
	 */
	public Overlay setOverlayNoMapStatus(double latitude, double longitude, int resid) {
		Overlay overlay = null;
		try {
			BitmapDescriptor bd= BitmapDescriptorFactory.fromResource(resid);
			if(bd == null){
				return overlay;
			}
			OverlayOptions oop = new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bd).zIndex(9).draggable(true);
			overlay = mBaiduMap.addOverlay(oop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overlay;
	}

	/**
	 * 
	 * <li>设置覆盖物
	 * @param latitude  纬度
	 * @param longitude 经度
	 * @param bitmap
	 */
	public Overlay setOverlay(double latitude, double longitude, Bitmap bitmap) {
		Overlay overlay = null;
		if(bitmap == null){
			return overlay;
		}
		try {
			BitmapDescriptor bd= BitmapDescriptorFactory.fromBitmap(bitmap);
			if(bd == null){
				return overlay;
			}
			LatLng location = new LatLng(latitude, longitude); 
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(location));
			OverlayOptions oop = new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bd);
			overlay = mBaiduMap.addOverlay(oop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overlay;
	}
	/**
	 * 
		 * <li>设置覆盖物
	 * @param latitude  纬度
	 * @param longitude 经度
	 * @param popview
	 */
	public Overlay setOverlay(double latitude, double longitude, View popview) {
		Overlay overlay = null;
		if(popview == null){
			return overlay;
		}
		try {
			BitmapDescriptor bd= BitmapDescriptorFactory.fromView(popview);
			if(bd == null){
				return overlay;
			}
			LatLng location = new LatLng(latitude, longitude);
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(location));
			OverlayOptions oop = new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bd);
			overlay = mBaiduMap.addOverlay(oop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overlay;
	}
	/**
	 * 设置覆盖物
	 * @param info
	 * @param resid
	 */
	public Overlay setOverlay(PoiInfo info, int resid) {
		Overlay overlay = null;
		if(info == null){
			return overlay;
		}
		try {
			BitmapDescriptor bd= BitmapDescriptorFactory.fromResource(resid);
			if(bd == null){
				return overlay;
			}
			OverlayOptions oop = new MarkerOptions().position(info.location).icon(bd);
			overlay =mBaiduMap.addOverlay(oop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overlay;
	}
	/**
	 * 点位 搜索
	 * @param keyword 关键词
	 * @param latitude  纬度
	 * @param longitude 经度
	 */
	public void poiSearch(String keyword, double latitude, double longitude) {
		try {
			PoiNearbySearchOption opt = new PoiNearbySearchOption();
			opt.location(new LatLng(latitude, longitude));
			opt.keyword(keyword);
			opt.radius(20000);
			opt.pageCapacity(100);

			mPoiSearch.searchNearby(opt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void suggestionSearch(String keyword) {
		mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(keyword));
	}
	public void snapshot() {
		try {
			mBaiduMap.snapshot(new SnapshotReadyCallback() {

				public void onSnapshotReady(Bitmap snapshot) {
					mMapListener.onSnapshotReady(snapshot);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发起驾车路线规划
	 * @param option
	 * @return
	 */
	public boolean	drivingSearch(DrivingRoutePlanOption option){
		return mSearch.drivingSearch(option);
	}
	/**
	 * 发起换乘路线规划
	 * @param option
	 * @return
	 */
	public boolean	transitSearch(TransitRoutePlanOption option){
		return mSearch.transitSearch(option);
	}
	/**
	 * 发起步行路线规
	 * @param option
	 * @return
	 */
	public boolean	walkingSearch(WalkingRoutePlanOption option){
		return mSearch.walkingSearch(option);
	}
	/**
	 *<li> 清空地图所有的 Overlay 覆盖物以及 InfoWindow
	 */
	public void clearMap() {
		try {
			if (null != mBaiduMap) {
				mBaiduMap.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onPause() {
		if (null != mMapView) {
			mMapView.onPause();
		}
	}

	public void onResume() {
		if (null != mMapView) {
			mMapView.onResume();
		}
	}

	public void onDestroy() {
		if (null != mPoiSearch) {
			mPoiSearch.destroy();
		}

		if (null != mSuggestionSearch) {
			mSuggestionSearch.destroy();
		}

		if (null != mBaiduMap) {
			mBaiduMap.clear();
		}
		if(mMapView!=null){
			mMapView.onDestroy();
		}
		mMapView = null;
	}
	@Override
	public void onGetSuggestionResult(SuggestionResult arg0) {

	}

	@Override
	public void onMapClick(LatLng latLng) {
		mMapListener.onMapClick(latLng);
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		mMapListener.onMapPoiClick(arg0);
		return true;
	}

	@Override
	public void onMapStatusChange(MapStatus arg0) {
		mMapListener.onMapStatusChange(arg0);
	}

	@Override
	public void onMapStatusChangeFinish(MapStatus arg0) {
		mMapListener.onMapStatusChangeFinish(arg0);
	}

	@Override
	public void onMapStatusChangeStart(MapStatus arg0) {
		mMapListener.onMapStatusChangeStart(arg0);
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		mMapListener.onPoiDetailSearched(arg0);
	}

	@Override
	public void onGetPoiResult(PoiResult arg0) {  
		mMapListener.onPoiSearched(arg0);
	}
	/**
	 * 显示 InfoWindow
	 * @param infoWindow
	 */
	public void showInfoWindow(InfoWindow infoWindow){
		mBaiduMap.showInfoWindow(infoWindow);  
	}
	
	/**
	 * 隐藏当前 InfoWindow
	 */
	 public void hideInfoWindow(){
		 mBaiduMap.hideInfoWindow();
	 }
//	public void snapshot(){
//		
//	}
//	
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		mMapListener.onGetDrivingRouteResult(result);
	}
	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		mMapListener.onGetTransitRouteResult(result);
	}
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		mMapListener.onGetWalkingRouteResult(result);
	}
	public void setOnMarkerClickListener(OnMarkerClickListener overlay){
		mBaiduMap.setOnMarkerClickListener(overlay);
	}
	@Override
	public void onMarkerDrag(Marker marker) {
		mMapListener.onMarkerDrag(marker);
	}
	@Override
	public void onMarkerDragEnd(Marker marker) {
		mMapListener.onMarkerDragEnd(marker);
	}
	@Override
	public void onMarkerDragStart(Marker marker) {
		mMapListener.onMarkerDragStart(marker);
	}

	public BaiduMap getmBaiduMap() {
		return mBaiduMap;
	}

	public void setmBaiduMap(BaiduMap mBaiduMap) {
		this.mBaiduMap = mBaiduMap;
	}

	public MapView getmMapView() {
		return mMapView;
	}

	public void setmMapView(MapView mMapView) {
		this.mMapView = mMapView;
	}

	public RoutePlanSearch getmSearch() {
		return mSearch;
	}

	public void setmSearch(RoutePlanSearch mSearch) {
		this.mSearch = mSearch;
	}
}
