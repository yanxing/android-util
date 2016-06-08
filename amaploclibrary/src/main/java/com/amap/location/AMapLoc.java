package com.amap.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.location.event.AMapLocListener;

/**
 * 高德定位
 * Created by lishuangxiang on 2016/6/8.
 */
public class AMapLoc implements AMapLocationListener {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMapLocListener mAMapLocListener;

    public AMapLoc(Context context) {
        mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        // 设置地址信息
        mLocationOption.setNeedAddress(true);
        // 设置是否开启缓存
        mLocationOption.setLocationCacheEnable(true);
        //发送请求间隔时间
        mLocationOption.setInterval(2000);
        // 设置定位模式为高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
    }

    /**
     * 设置监听
     *
     * @param aMapLocListener
     */
    public void setAMapLocListener(AMapLocListener aMapLocListener) {
        mAMapLocListener = aMapLocListener;
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
            mAMapLocListener.onLocationChanged(aMapLocation);
        }
    }

    /**
     * 如果AMapLocationClient是在当前Activity实例化的，
     * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
     */
    public void onDestroy() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }
}
