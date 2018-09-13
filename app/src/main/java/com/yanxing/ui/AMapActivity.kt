package com.yanxing.ui

import android.os.Bundle
import com.amap.api.maps2d.model.Marker
import com.yanxing.amap.event.OnMarkClickListener
import com.yanxing.base.BaseActivity
import kotlinx.android.synthetic.main.activity_activity_amap.*

/**
 * 高德地图例子
 * @author 李双祥 on 2018/9/13.
 */
class AMapActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_activity_amap
    }

    override fun afterInstanceView() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.setOnMapLoadedListener {
            mapView.addMark(31.1770457039,121.3795065880,R.mipmap.map_unselect,false)
            mapView.addMark(31.1780003451,121.3764381409,R.mipmap.map_unselect,false)
            mapView.addMark(31.1737043838,121.3785195351,R.mipmap.map_unselect,false)
            mapView.addMark(31.1737778207,121.3832616806,R.mipmap.map_unselect,false)
            mapView.addMark(31.1790834841,121.3851928711,R.mipmap.map_select,false)
        }

        mapView.setOnMarkClickListener(object :OnMarkClickListener{
            override fun onMarkClick(marker: Marker): Boolean {
                showToast(marker.title)
                return true
            }
        })
        mapView.setLocationSourse(R.mipmap.location)
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

}
