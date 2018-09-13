package com.yanxing.amap;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.yanxing.amap.event.OnMapLocationListener;

/**
 * 高德定位，和地图容器分离的,可以单独使用
 * Created by lishuangxiang on 2016/6/8.
 */
public class MapLoc implements AMapLocationListener {

    private AMapLocationClient mLocationClient;
    private OnMapLocationListener mOnMapLocationListener;

    public MapLoc(Context context) {
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        // 设置地址信息
        locationOption.setNeedAddress(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        //发送请求间隔时间
        locationOption.setInterval(2000);
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationOption(locationOption);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
    }

    /**
     * 设置监听
     *
     * @param onMapLocationListener
     */
    public void setOnMapLocationListener(OnMapLocationListener onMapLocationListener) {
        mOnMapLocationListener = onMapLocationListener;
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            mOnMapLocationListener.onLocationChanged(aMapLocation);
        }
    }

    /**
     * 如果AMapLocationClient是在当前Activity实例化的，
     * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
     */
    public void onDestroy() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }
}
