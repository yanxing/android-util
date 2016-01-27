package com.baidu.map.event;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.sug.SuggestionResult;

/**
 * 搜索建议回调
 * Created by lishuangxiang on 2016/1/27.
 */
public interface SuggestionResultListener {

    /**
     * 搜索结果回调
     *
     * @param result
     */
    void onSuggestionSearched(SuggestionResult result);
}
