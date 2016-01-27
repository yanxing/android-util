package com.baidu.map.event;

import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * POI检索回调，周边检索、区域检索和城市内检索。
 * Created by lishuangxiang on 2016/1/27.
 */
public interface PoiSearchResultListener {

    /**
     * POI搜索结果回调
     *
     * @param result
     */
    void onPoiSearched(PoiResult result);

    /**
     * POI搜索结果回调,详细信息
     *
     * @param result
     */
    void onPoiDetailSearched(PoiDetailResult result);

    /**
     * 地图内 Poi 单击事件回调函数
     *
     * @param poi
     * @return
     */
    boolean onMapPoiClick(MapPoi poi);
}
