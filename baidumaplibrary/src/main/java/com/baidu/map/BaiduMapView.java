package com.baidu.map;


import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.map.event.BaiduMapListener;
import com.baidu.map.event.MapStatusChangeListener;
import com.baidu.map.event.MarkerDragListener;
import com.baidu.map.event.PoiSearchResultListener;
import com.baidu.map.event.RoutePlanResultListener;
import com.baidu.map.event.SnapshotListener;
import com.baidu.map.event.SuggestionResultListener;
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

public class BaiduMapView implements OnGetPoiSearchResultListener, OnMapStatusChangeListener,
        OnMapClickListener, OnGetSuggestionResultListener, OnGetRoutePlanResultListener, OnMarkerDragListener, SnapshotReadyCallback {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private LinearLayout mParentLayout;

    private BaiduMapListener mMapListener;
    //marker拖拽监听
    private MarkerDragListener mMarkerDragListener;
    //路线结果监听
    private RoutePlanResultListener mRoutePlanResultListener;
    //POI搜索监听
    private PoiSearchResultListener mPoiSearchResultListener;
    //搜索建议监听
    private SuggestionResultListener mSuggestionResultListener;
    //地图状态改变监听
    private MapStatusChangeListener mMapStatusChangeListener;
    //截屏监听
    private SnapshotListener mSnapshotListener;

    private RoutePlanSearch mSearch;
    private PoiSearch mPoiSearch;
    private SuggestionSearch mSuggestionSearch;

    private float maxZoomLevel;
    private float minZoomLevel;

    public BaiduMapView(Activity activity, LinearLayout parentLayout) {
        mParentLayout = parentLayout;
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
            mBaiduMap.setMyLocationEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置marker拖拽监听
     *
     * @param markerDragListener
     */
    public void setMarkerDragListener(MarkerDragListener markerDragListener) {
        mMarkerDragListener = markerDragListener;
        mBaiduMap.setOnMarkerDragListener(this);
    }

    /**
     * 设置路线监听
     *
     * @param routePlanResultListener
     */
    public void setRoutePlanResultListener(RoutePlanResultListener routePlanResultListener) {
        mRoutePlanResultListener = routePlanResultListener;
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    /**
     * 设置 POI检索监听
     *
     * @param poiSearchResultListener
     */
    public void setPoiSearchResultListener(PoiSearchResultListener poiSearchResultListener) {
        mPoiSearchResultListener = poiSearchResultListener;
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    /**
     * 设置搜索建议监听
     *
     * @param suggestionResultListener
     */
    public void setSuggestionResultListener(SuggestionResultListener suggestionResultListener) {
        mSuggestionResultListener = suggestionResultListener;
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
    }

    /**
     * 设置地图状态改变监听
     *
     * @param mapStatusChangeListener
     */
    public void setMapStatusChangeListener(MapStatusChangeListener mapStatusChangeListener) {
        mMapStatusChangeListener = mapStatusChangeListener;
        mBaiduMap.setOnMapStatusChangeListener(this);
    }

    public void setSnapshotListener(SnapshotListener snapshotListener) {
        mSnapshotListener = snapshotListener;
        mBaiduMap.snapshot(this);
    }

    /**
     * 设置地图加载、点击、移动监听
     *
     * @param listenter
     */
    public void setBaiduMapListener(BaiduMapListener listenter) {
        mMapListener = listenter;
        mBaiduMap.setOnMapClickListener(this);
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
    }

    /**
     * 设置视角中心
     *
     * @param latitude
     * @param longitude
     */
    public void setCenterOnly(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(location));
    }

    /**
     * 设置覆盖物
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param resid
     */
    public Overlay setOverlay(double latitude, double longitude, int resid) {
        Overlay overlay = null;
        try {
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(resid);
            if (bd == null) {
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
     * 设置覆盖物,不会触发MapStatus事件
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param resID
     */
    public Overlay setOverlayNoMapStatus(double latitude, double longitude, int resID) {
        Overlay overlay = null;
        try {
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(resID);
            if (bd == null) {
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
     * 设置覆盖物
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param bitmap
     */
    public Overlay setOverlay(double latitude, double longitude, Bitmap bitmap) {
        Overlay overlay = null;
        if (bitmap == null) {
            return overlay;
        }
        try {
            BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
            if (bd == null) {
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
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param popPiew
     */
    public Overlay setOverlay(double latitude, double longitude, View popPiew) {
        Overlay overlay = null;
        if (popPiew == null) {
            return overlay;
        }
        try {
            BitmapDescriptor bd = BitmapDescriptorFactory.fromView(popPiew);
            if (bd == null) {
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
     *
     * @param info
     * @param resID
     */
    public Overlay setOverlay(PoiInfo info, int resID) {
        Overlay overlay = null;
        if (info == null) {
            return overlay;
        }
        try {
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(resID);
            if (bd == null) {
                return overlay;
            }
            OverlayOptions oop = new MarkerOptions().position(info.location).icon(bd);
            overlay = mBaiduMap.addOverlay(oop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overlay;
    }

    /**
     * POI搜索
     *
     * @param keyword   关键词
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

    /**
     * 搜索建议
     *
     * @param keyword 关键字
     */
    public void suggestionSearch(String keyword) {
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(keyword));
    }

    /**
     * 发起驾车路线规划
     *
     * @param option
     * @return
     */
    public boolean drivingSearch(DrivingRoutePlanOption option) {
        return mSearch.drivingSearch(option);
    }

    /**
     * 发起换乘路线规划
     *
     * @param option
     * @return
     */
    public boolean transitSearch(TransitRoutePlanOption option) {
        return mSearch.transitSearch(option);
    }

    /**
     * 发起步行路线规
     *
     * @param option
     * @return
     */
    public boolean walkingSearch(WalkingRoutePlanOption option) {
        return mSearch.walkingSearch(option);
    }

    /**
     * 清空地图所有的 Overlay 覆盖物以及 InfoWindow
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

    @Override
    public void onGetSuggestionResult(SuggestionResult arg0) {
        mSuggestionResultListener.onSuggestionSearched(arg0);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMapListener.onMapClick(latLng);
    }

    @Override
    public boolean onMapPoiClick(MapPoi arg0) {
        mPoiSearchResultListener.onMapPoiClick(arg0);
        return true;
    }

    @Override
    public void onMapStatusChange(MapStatus arg0) {
        mMapStatusChangeListener.onMapStatusChange(arg0);
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus arg0) {
        mMapStatusChangeListener.onMapStatusChangeFinish(arg0);
    }

    @Override
    public void onMapStatusChangeStart(MapStatus arg0) {
        mMapStatusChangeListener.onMapStatusChangeStart(arg0);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult arg0) {
        mPoiSearchResultListener.onPoiDetailSearched(arg0);
    }

    @Override
    public void onGetPoiResult(PoiResult arg0) {
        mPoiSearchResultListener.onPoiSearched(arg0);
    }

    /**
     * 显示 InfoWindow
     *
     * @param infoWindow
     */
    public void showInfoWindow(InfoWindow infoWindow) {
        mBaiduMap.showInfoWindow(infoWindow);
    }

    /**
     * 隐藏当前 InfoWindow
     */
    public void hideInfoWindow() {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        mRoutePlanResultListener.onGetDrivingRouteResult(result);
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        mRoutePlanResultListener.onGetTransitRouteResult(result);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        mRoutePlanResultListener.onGetWalkingRouteResult(result);
    }

    public void setOnMarkerClickListener(OnMarkerClickListener overlay) {
        mBaiduMap.setOnMarkerClickListener(overlay);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        mMarkerDragListener.onMarkerDrag(marker);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mMarkerDragListener.onMarkerDragEnd(marker);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        mMarkerDragListener.onMarkerDragStart(marker);
    }

    @Override
    public void onSnapshotReady(Bitmap bitmap) {

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

    public void onResume() {
        if (null != mMapView) {
            mMapView.onResume();
        }
    }

    public void onPause() {
        if (null != mMapView) {
            mMapView.onPause();
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
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        mMapView = null;
    }

}
