package com.baidu.map;

import android.graphics.Color;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;

public class MyDrivingRouteOverlay extends DrivingRouteOverlay {

    public MyDrivingRouteOverlay(BaiduMapView baiduMapView) {
        super(baiduMapView.getmBaiduMap());
    }

    @Override
    public BitmapDescriptor getStartMarker() {
        return BitmapDescriptorFactory.fromResource(R.drawable.icon_location_start);
    }

    @Override
    public BitmapDescriptor getTerminalMarker() {
        return BitmapDescriptorFactory.fromResource(R.drawable.icon_location_end);
    }

/*	@Override
    public int getLineColor() {//更改线路颜色
		return Color.parseColor("#02bc19") ;
	}*/
}