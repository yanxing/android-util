package com.yanxing.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.amap.api.location.AMapLocation
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.CameraPosition
import com.amap.api.maps2d.model.Marker
import com.yanxing.amap.event.OnMapLocationListener
import com.yanxing.amap.event.OnMapStatusChangeListener
import com.yanxing.amap.event.OnMarkClickListener
import com.yanxing.amap.event.OnMarkerDragListener
import com.yanxing.base.BaseActivity
import kotlinx.android.synthetic.main.activity_activity_amap.*

/**
 * amaplibrary例子，添加marker，定位
 * @author 李双祥 on 2018/9/13.
 */
class AMapActivity : BaseActivity() {

    private val mMarkList = ArrayList<Marker>()
    private var mSelectMarker: Marker? = null
    private var mIndex = 0

    override fun getLayoutResID(): Int {
        return R.layout.activity_activity_amap
    }

    override fun afterInstanceView() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.setOnMarkClickListener(object : OnMarkClickListener {
            override fun onMarkClick(marker: Marker): Boolean {
                //这里title作为添加的mark标志（不含定位）,返回false
                showToast("第" + marker.title + "个marker")
                //重置选中的
                mapView.updateMarkIcon(mSelectMarker, R.mipmap.map_unselect)
                //选中
                mapView.updateMarkIcon(marker, R.mipmap.map_select)
                mSelectMarker = marker
                return true
            }
        })
        //定位
        mapView.setLocation(false, object : OnMapLocationListener {
            override fun onLocationChanged(aMapLocation: AMapLocation) {

            }
        })

        //拖动地图监听
        mapView.setOnMapStateChange(object : OnMapStatusChangeListener {
            override fun onMapStatusChange(cameraPosition: CameraPosition) {

            }

            override fun onMapStatusChangeFinish(cameraPosition: CameraPosition) {
                val lat = cameraPosition.target.latitude
                val lng = cameraPosition.target.longitude
                addMarker(lat, lng)
            }
        })
    }

    /**
     * 添加marker
     */
    fun addMarker(lat: Double, lng: Double) {
        val marker1 = mapView.addMark(lat + 0.00464, lng - 0.00042, mIndex++.toString(), null, R.mipmap.map_unselect, false)
        val marker2 = mapView.addMark(lat + 0.00214, lng - 0.00371, mIndex++.toString(), null, R.mipmap.map_unselect, false)
        val marker3 = mapView.addMark(lat - 0.00101, lng + 0.00518, mIndex++.toString(), null, R.mipmap.map_unselect, false)
        val marker4 = mapView.addMark(lat - 0.00624, lng + 0.00021, mIndex++.toString(), null, R.mipmap.map_unselect, false)
        mSelectMarker = mapView.addMark(lat + 0.00301, lng + 0.00318, mIndex++.toString(), null, R.mipmap.map_select, false)
        mMarkList.add(marker1)
        mMarkList.add(marker2)
        mMarkList.add(marker3)
        mMarkList.add(marker4)
        mMarkList.add(mSelectMarker!!)
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

}
