package com.yanxing.amap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
import com.amap.location.R;
import com.yanxing.amap.event.InfoWindowAdapter;
import com.yanxing.amap.event.OnInfoWindowClickListener;
import com.yanxing.amap.event.OnMapLoadedListener;
import com.yanxing.amap.event.OnMapLocationListener;
import com.yanxing.amap.event.OnMapStatusChangeListener;
import com.yanxing.amap.event.OnMarkClickListener;
import com.yanxing.amap.event.OnMarkerDragListener;

/**
 * 高德地图容器
 *
 * @author 李双祥 on 2018/9/13.
 */
public class AMapView extends FrameLayout {

    private Context mContext;
    private MapView mMapView;
    private AMap mMap;

    /**
     * 初始化地图容器
     *
     * @param context
     */
    public AMapView(Context context) {
        this(context, null);
    }

    public AMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AMapView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        this.mContext = context;
        this.mMapView = new MapView(context);
        this.mMap = mMapView.getMap();
        this.mMap.getUiSettings().setZoomControlsEnabled(false);
        this.mMapView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(mMapView);
    }


    /**
     * 必须在Fragment/Activity调用
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }


    /**
     * 监听地图可视范围改变
     *
     * @param onMapStatusChangeListener
     */
    public void setOnMapStateChange(final OnMapStatusChangeListener onMapStatusChangeListener) {
        mMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                onMapStatusChangeListener.onMapStatusChange(cameraPosition);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                onMapStatusChangeListener.onMapStatusChangeFinish(cameraPosition);
            }
        });
    }


    /**
     * 设置地图定位
     *
     * @param showLocationIcon 是否隐藏定位图标
     */
    public void setLocation(boolean showLocationIcon, OnMapLocationListener onMapLocationListener) {
        if (showLocationIcon) {
            setLocation(0, onMapLocationListener);
        } else {
            setLocation(-1, onMapLocationListener);
        }
    }

    /**
     * 设置地图定位
     *
     * @param onMapLocationListener
     */
    public void setLocation(OnMapLocationListener onMapLocationListener) {
        setLocation(true, onMapLocationListener);
    }

    /**
     * 设置地图定位
     *
     * @param locationSourse        自定义定位图标
     * @param onMapLocationListener 定位成功回调
     */
    public void setLocation(final int locationSourse, final OnMapLocationListener onMapLocationListener) {
        final MapLoc mapLoc = new MapLoc(mContext);
        mapLoc.startLocation();
        mapLoc.setOnMapLocationListener(new OnMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                    LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    //显示定位图标
                    if (locationSourse != -1) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        //自定义图标
                        if (locationSourse != 0) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(locationSourse));
                        }
                        markerOptions.draggable(false).position(latLng);
                        mMap.addMarker(markerOptions);
                    }

                    if (onMapLocationListener != null) {
                        onMapLocationListener.onLocationChanged(aMapLocation);
                    }
                    mapLoc.stopLocation();
                }
            }
        });
    }


    /**
     * 设置marker拖拽监听
     *
     * @param onMarkerDragListener
     */
    public void setOnMarkerDragListener(final OnMarkerDragListener onMarkerDragListener) {
        mMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                onMarkerDragListener.onMarkerDragStart(marker);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                onMarkerDragListener.onMarkerDrag(marker);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                onMarkerDragListener.onMarkerDragEnd(marker);
            }
        });
    }

    /**
     * 监听地图加载完成
     *
     * @param onMapLoadedListener
     */
    public void setOnMapLoadedListener(final OnMapLoadedListener onMapLoadedListener) {
        mMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                onMapLoadedListener.onMapLoaded();
            }
        });
    }

    /**
     * 添加文本marker
     *
     * @param textOptions
     */
    public Text addTextMark(TextOptions textOptions) {
        return mMap.addText(textOptions);
    }

    /**
     * 添加文本marker
     *
     * @param latLng          经纬度
     * @param text            文本
     * @param textColor       文本颜色
     * @param textSize        文本字体大小
     * @param backgroundColor 文本背景色
     */
    public Text addTextMark(LatLng latLng, String text, int textColor, int textSize, int backgroundColor) {
        TextOptions textOptions = new TextOptions()
                .position(latLng)
                .text("Text")
                .fontColor(textColor)
                .backgroundColor(backgroundColor)
                .fontSize(textSize)
                .align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL);
        return mMap.addText(textOptions);
    }

    /**
     * 添加mark
     *
     * @param markerOptions
     */
    public Marker addMark(MarkerOptions markerOptions) {
        return mMap.addMarker(markerOptions);
    }

    /**
     * 添加一个覆盖物
     *
     * @param latitude      纬度
     * @param longitude     经度
     * @param resourceImage 图标，例如R.drawable.app_logo
     * @param drag          是否可以拖动
     */
    public Marker addMark(double latitude, double longitude, int resourceImage, boolean drag) {
        return addMark(latitude, longitude, null, null, resourceImage, drag);
    }

    /**
     * 添加一个覆盖物,默认InfoWindow
     *
     * @param title         标题
     * @param content       内容
     * @param resourceImage 图标，例如R.drawable.app_logo
     * @param drag          是否可以拖动
     */
    public Marker addMark(double latitude, double longitude, String title, String content, int resourceImage, boolean drag) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromResource(resourceImage))
                .draggable(drag);
        if (!TextUtils.isEmpty(title)) {
            markerOptions.title(title);
        }
        if (!TextUtils.isEmpty(content)) {
            markerOptions.snippet(content);
        }
        Marker marker = mMap.addMarker(markerOptions);
        jumpPoint(marker);
        return marker;
    }

    /**
     * 添加一个覆盖物
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param bitmap    图标
     * @param drag      是否可以拖动
     */
    public Marker addMark(double latitude, double longitude, Bitmap bitmap, boolean drag) {
        return addMark(latitude, longitude, null, null, bitmap, drag);
    }

    /**
     * 添加一个覆盖物,默认InfoWindow
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param bitmap    图标
     * @param drag      是否可以拖动
     */
    public Marker addMark(double latitude, double longitude, String title, String content, Bitmap bitmap, boolean drag) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .draggable(drag);
        if (!TextUtils.isEmpty(title)) {
            markerOptions.title(title);
        }
        if (!TextUtils.isEmpty(content)) {
            markerOptions.snippet(content);
        }
        Marker marker = mMap.addMarker(markerOptions);
        jumpPoint(marker);
        return marker;
    }

    /**
     * marker跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -60);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 400;

        final Interpolator interpolator = new AccelerateInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    /**
     * 更新覆盖物的图标
     *
     * @param marker
     * @param bitmap 图标
     */
    public void updateMarkIcon(Marker marker, Bitmap bitmap) {
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
    }

    /**
     * 更新覆盖物的图标
     *
     * @param marker
     * @param resourceImage 图标
     */
    public void updateMarkIcon(Marker marker, int resourceImage) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(resourceImage));
    }

    /**
     * 自定义InfoWindowAdapter
     *
     * @param infoWindowAdapter
     */
    public void setInfoWindow(final InfoWindowAdapter infoWindowAdapter) {
        mMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return infoWindowAdapter.getInfoWindow(marker);
            }

            @Override
            public View getInfoContents(Marker marker) {
                return infoWindowAdapter.getInfoContents(marker);
            }
        });
    }


    /**
     * 设置InfoWindow点击事件
     *
     * @param onInfoWindowClickListener
     */
    public void setOnInfoWindowClickListener(final OnInfoWindowClickListener onInfoWindowClickListener) {
        mMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                onInfoWindowClickListener.onInfoWindowClick(marker);
            }
        });
    }

    /**
     * mark点击监听
     *
     * @param markClickListener
     */
    public void setOnMarkClickListener(final OnMarkClickListener markClickListener) {
        mMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return markClickListener.onMarkClick(marker);
            }
        });
    }

    public AMap getMap() {
        return mMap;
    }

    public void setMap(AMap map) {
        mMap = map;
    }

    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }

    public void onResume() {
        mMapView.onResume();
    }

    public void onPause() {
        mMapView.onPause();
    }

    public void onDestroy() {
        if (mMap!=null) {
            mMap.clear();
        }
        if (mMapView!=null) {
            mMapView.onDestroy();
        }
    }

}
