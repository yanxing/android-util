package com.baidu.map;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.map.event.BaiduLocListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

public class BaiduLoc implements OnGetGeoCoderResultListener {
	public static final int coorType_bd09ll = 0;
	public static final int coorType_gcj02 = 1;
	public static final int coorType_bd09 = 2;
	private LocationClient mLocationClient;
	private BDMapLocationListener mBDLocationListener;
	private BDLocation mLocation;
	private ReverseGeoCodeResult reverseGeoCodeResult;
	private BaiduLocListener mBaiduLocListener;
	private GeoCoder mSearch;

	public BaiduLoc(Context context) {
		this(context,2000);
	}

	/**
	 * @param context
	 * @param scanSpan 间隔多少秒定位，单位ms
     */
	public BaiduLoc(Context context,int scanSpan) {
		try {
			mLocationClient = new LocationClient(context);
			mBDLocationListener = new BDMapLocationListener();
			mLocationClient.registerLocationListener(mBDLocationListener);

			LocationClientOption locClintOpt = new LocationClientOption();
			// 设置GPS
			locClintOpt.setOpenGps(true);
			// 设置定位模式
			locClintOpt.setLocationMode(LocationMode.Hight_Accuracy);
			// 返回的定位结果是百度经纬度，默认值gcj02
			locClintOpt.setCoorType("bd09ll");
			// 设置发起定位请求的间隔时间
			locClintOpt.setScanSpan(scanSpan);
			locClintOpt.setIsNeedAddress(true);
			mLocationClient.setLocOption(locClintOpt);

			mSearch = GeoCoder.newInstance();
			mSearch.setOnGetGeoCodeResultListener(this);
			// 开启定位
			mLocationClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	private String getLocCoor(int coorType) {
		switch (coorType) {
		case coorType_gcj02:
			return "gcj02";
		case coorType_bd09ll:
			return "bd09ll";
		case coorType_bd09:
			return "bd09";
		default:
			return "gcj02";
		}
	}
	public String getAddress(){
		return mLocation.getAddrStr();
	}
	public boolean isLocationValid(double lng, double lat) {
		if (lng == 4.9E-324 || lat == 4.9E-324) {
			return false;
		}
		if (lng == 0 || lat == 0) {
			return false;
		}
		return true;
	}

	public void setLocMode(LocationMode locMode) {
		LocationClientOption opt = mLocationClient.getLocOption();
		opt.setLocationMode(locMode);
		mLocationClient.setLocOption(opt);
	}

	public void setCoorType(int coorType) {
		LocationClientOption opt = mLocationClient.getLocOption();
		opt.setCoorType(getLocCoor(coorType));
		mLocationClient.setLocOption(opt);
	}

	public void setScanSpan(int scanSpan) {
		LocationClientOption opt = mLocationClient.getLocOption();
		opt.setScanSpan(scanSpan);
		mLocationClient.setLocOption(opt);
	}

	public void setIsNeedAddress(boolean isNeedAddress) {
		LocationClientOption opt = mLocationClient.getLocOption();
		opt.setIsNeedAddress(isNeedAddress);
		mLocationClient.setLocOption(opt);
	}

	public void setBaiduLocListener(BaiduLocListener baiduLocListener) {
		mBaiduLocListener = baiduLocListener;
	}

	public double getLatitude() {
		try {
			if (null != mLocation) {
				return mLocation.getLatitude();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double getLongitude() {
		try {
			if (null != mLocation) {
				return mLocation.getLongitude();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void startLocation() {
		try {
			if (null != mLocationClient) {
				mLocationClient.start();
				mLocationClient.requestLocation();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopLocation() {
		try {
			if (null != mLocationClient) {
				mLocationClient.stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		reverseGeoCodeResult = arg0;
		if (arg0 == null || arg0.error != SearchResult.ERRORNO.NO_ERROR) {
			if (null != mBaiduLocListener) {
				mBaiduLocListener.onLocationChanged(false, arg0);
			}
		}else{
			if (null != mBaiduLocListener) {
				mBaiduLocListener.onLocationChanged(true, arg0);
			}
		}
		stopLocation();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class BDMapLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			mLocation = location;
			if (null != mBaiduLocListener) {
				mBaiduLocListener.onLocationReceive(location);
			}
			if (isLocationValid(location.getLatitude(), location.getLongitude())
					&& null != mSearch) {
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
						.location(new LatLng(mLocation.getLatitude(), mLocation
								.getLongitude())));
			} else {
				startLocation();
			}
		}
	}
}
