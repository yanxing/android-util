package com.yanxing.ui;

import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.map.BaiduMapView;
import com.baidu.map.MyDrivingRouteOverlay;
import com.baidu.map.event.BaiduLocListener;
import com.baidu.map.event.RoutePlanResultListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yanxing.base.BaseActivity;
import com.yanxing.base.MyApplication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 百度地图封装测试
 */
@EActivity(R.layout.activity_baidu_map_example)
public class BaiduMapExampleActivity extends BaseActivity implements RoutePlanResultListener,BaiduLocListener {

    @ViewById(R.id.map)
    LinearLayout mMap;

    private BaiduMapView mBaiduMapView;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        mBaiduMapView=new BaiduMapView(this, mMap);
        mBaiduMapView.setRoutePlanResultListener(this);
        //驾车路径
        LatLng fromLatLng = new LatLng(31.1145130000, 121.4112010000);
        PlanNode senderNode = PlanNode.withLocation(fromLatLng);
        LatLng toLatLng = new LatLng(31.2166060000, 121.4471340000);
        PlanNode receiverNode = PlanNode.withLocation(toLatLng);
        //定位
        MyApplication.baiduLoc.startLocation();
        MyApplication.baiduLoc.setBaiduLocListener(this);
        //最短距离路线
        mBaiduMapView.drivingSearch((new DrivingRoutePlanOption().policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST))
                .from(senderNode)
                .to(receiverNode));
        mBaiduMapView.setCenterOnly(fromLatLng.latitude, fromLatLng.longitude);
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("抱歉，未找到结果");
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMapView);
            mBaiduMapView.getmBaiduMap().setOnMarkerClickListener(overlay);
            int distance = result.getRouteLines().get(0).getDistance();
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
            MapStatusUpdate msu;
            if (distance < 18000 && distance > 9000) {
                msu = MapStatusUpdateFactory.zoomTo(13.0f);
            } else if (distance > 1000 & distance <= 4000) {
                msu = MapStatusUpdateFactory.zoomTo(16.0f);
            } else if (distance > 4000 && distance <= 9000) {
                msu = MapStatusUpdateFactory.zoomTo(15.0f);
            } else {
                msu = MapStatusUpdateFactory.zoomTo(12.0f);
            }
            mBaiduMapView.getmBaiduMap().setMapStatus(msu);
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {

    }

    @Override
    public void onLocationChanged(boolean result, ReverseGeoCodeResult geoResult) {
        if (geoResult != null && geoResult.getLocation() != null) {
            double longitude = geoResult.getLocation().longitude;
            double latitude = geoResult.getLocation().latitude;
            mBaiduMapView.setCenterOnly(latitude, longitude);
        }
    }

    @Override
    public void onLocationReceive(BDLocation location) {

    }
}
