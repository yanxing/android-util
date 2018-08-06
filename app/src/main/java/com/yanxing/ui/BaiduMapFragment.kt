package com.yanxing.ui


import com.baidu.location.BDLocation
import com.baidu.map.BaiduLoc
import com.baidu.map.BaiduMapView
import com.baidu.map.MyDrivingRouteOverlay
import com.baidu.map.event.BaiduLocListener
import com.baidu.map.event.RoutePlanResultListener
import com.baidu.mapapi.map.MapStatusUpdate
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult
import com.baidu.mapapi.search.route.DrivingRoutePlanOption
import com.baidu.mapapi.search.route.DrivingRouteResult
import com.baidu.mapapi.search.route.PlanNode
import com.baidu.mapapi.search.route.TransitRouteResult
import com.baidu.mapapi.search.route.WalkingRouteResult
import com.yanxing.base.BaseFragment

import kotlinx.android.synthetic.main.fragment_baidu_map.*

/**
 * 百度地图封装测试
 */
class BaiduMapFragment : BaseFragment(), RoutePlanResultListener, BaiduLocListener {

    private lateinit var mBaiduMapView: BaiduMapView

    override fun getLayoutResID(): Int {
        return R.layout.fragment_baidu_map
    }

    override fun afterInstanceView() {
        mBaiduMapView = BaiduMapView(activity, map)
        mBaiduMapView.setRoutePlanResultListener(this)
        //驾车路径
        val fromLatLng = LatLng(31.1145130000, 121.4112010000)
        val senderNode = PlanNode.withLocation(fromLatLng)
        val toLatLng = LatLng(31.2166060000, 121.4471340000)
        val receiverNode = PlanNode.withLocation(toLatLng)
        //定位
        val baiduLoc = BaiduLoc(activity)
        baiduLoc.startLocation()
        baiduLoc.setBaiduLocListener(this)
        //最短距离路线
        mBaiduMapView.drivingSearch(DrivingRoutePlanOption()
                .policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST)
                .from(senderNode)
                .to(receiverNode))
        //设置视角中心
        mBaiduMapView.setCenterOnly(fromLatLng.latitude, fromLatLng.longitude)
    }

    override fun onGetDrivingRouteResult(result: DrivingRouteResult?) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast(getString(R.string.sorry_no_find))
        }
        if (result!!.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            val overlay = MyDrivingRouteOverlay(mBaiduMapView)
            mBaiduMapView.getmBaiduMap().setOnMarkerClickListener(overlay)
            val distance = result.routeLines[0].distance
            overlay.setData(result.routeLines[0])
            overlay.addToMap()
            overlay.zoomToSpan()
            val msu: MapStatusUpdate
            if (distance < 18000 && distance > 9000) {
                msu = MapStatusUpdateFactory.zoomTo(13.0f)
            } else if ((distance > 1000) and (distance <= 4000)) {
                msu = MapStatusUpdateFactory.zoomTo(16.0f)
            } else if (distance > 4000 && distance <= 9000) {
                msu = MapStatusUpdateFactory.zoomTo(15.0f)
            } else {
                msu = MapStatusUpdateFactory.zoomTo(12.0f)
            }
            mBaiduMapView.getmBaiduMap().setMapStatus(msu)
        }
    }

    override fun onGetTransitRouteResult(result: TransitRouteResult) {

    }

    override fun onGetWalkingRouteResult(result: WalkingRouteResult) {

    }

    override fun onLocationChanged(result: Boolean, geoResult: ReverseGeoCodeResult?) {
        if (geoResult != null && geoResult.location != null) {
            val longitude = geoResult.location.longitude
            val latitude = geoResult.location.latitude
            mBaiduMapView.setCenterOnly(latitude, longitude)
        }
    }

    override fun onLocationReceive(location: BDLocation) {

    }
}
